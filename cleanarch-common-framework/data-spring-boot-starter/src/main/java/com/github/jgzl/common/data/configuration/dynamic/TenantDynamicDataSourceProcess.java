package com.github.jgzl.common.data.configuration.dynamic;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.creator.HikariDataSourceCreator;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.support.ScriptRunner;
import com.github.jgzl.common.core.util.StringUtils;
import com.github.jgzl.common.data.configuration.dynamic.event.body.EventAction;
import com.github.jgzl.common.data.configuration.dynamic.event.body.TenantDynamicDatasource;
import com.github.jgzl.common.data.properties.FrameworkMpProperties;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author Levin
 */
@Slf4j
public class TenantDynamicDataSourceProcess {

    private static final String CREATE_DATABASE_SCRIPT = "CREATE DATABASE IF NOT EXISTS %s DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;";

    @Resource
    private DataSource dataSource;
    @Resource
    private HikariDataSourceCreator hikariDataSourceCreator;
    @Resource
    private FrameworkMpProperties properties;

    public void handler(EventAction action, TenantDynamicDatasource dynamicDatasource) {
        if (Objects.isNull(dynamicDatasource)) {
            log.warn("event dynamicDatasource is null....");
            return;
        }
        if (Objects.isNull(action)) {
            log.warn("event action is null....");
            return;
        }
        log.info("接收租户事件消息: - {} - {}", action, dynamicDatasource);
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        final FrameworkMpProperties.MultiTenant multiTenant = properties.getMultiTenant();
        String database = multiTenant.getDsPrefix() + dynamicDatasource.getCode();
        if (action == EventAction.DEL) {
            ds.removeDataSource(database);
            log.info("数据源移除成功 - {}", database);
            return;
        }
        if (action == EventAction.INIT) {
            // 创建数据库
            DataSourceProperty dataSourceProperty = new DataSourceProperty();
            dataSourceProperty.setPoolName(dynamicDatasource.getPoolName() + "_" + dynamicDatasource.getCode());
            dataSourceProperty.setDriverClassName(dynamicDatasource.getDriverClassName());
            String url = "jdbc:mysql://" + dynamicDatasource.getHost();
            dataSourceProperty.setUrl(url);
            dataSourceProperty.setUsername(dynamicDatasource.getUsername());
            dataSourceProperty.setPassword(dynamicDatasource.getPassword());
            dataSourceProperty.setLazy(false);
            DataSource dataSource = hikariDataSourceCreator.createDataSource(dataSourceProperty);
            log.debug("数据源信息 - {} - {} - {}", dataSourceProperty.getUsername(), dataSourceProperty.getPassword(), database);
            final String createDatabaseScript = String.format(CREATE_DATABASE_SCRIPT, database);
            log.debug("数据库创建执行成功 - {}", createDatabaseScript);
            try (Connection conn = dataSource.getConnection(); Statement stat = conn.createStatement()) {
                stat.executeUpdate(createDatabaseScript);
            } catch (Exception e) {
                log.error("执行创建数据库脚本异常", e);
            }
        }
        DataSourceProperty dataSourceProperty = new DataSourceProperty();
        dataSourceProperty.setPoolName(dynamicDatasource.getPoolName() + "_" + dynamicDatasource.getCode());
        dataSourceProperty.setDriverClassName(dynamicDatasource.getDriverClassName());
        String url = "jdbc:mysql://" + dynamicDatasource.getHost() +
                "/" + database + "?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true&serverTimezone=GMT%2B8&useSSL=false&allowPublicKeyRetrieval=true";
        dataSourceProperty.setUrl(url);
        dataSourceProperty.setUsername(dynamicDatasource.getUsername());
        dataSourceProperty.setPassword(dynamicDatasource.getPassword());
        dataSourceProperty.setLazy(true);
        DataSource dataSource = hikariDataSourceCreator.createDataSource(dataSourceProperty);
        log.debug("数据源信息 - {} - {} - {}", dataSourceProperty.getUsername(), dataSourceProperty.getPassword(), database);
        ds.addDataSource(database, dataSource);
        log.info("数据源添加成功 - {}", database);
        final Set<String> dsSets = ds.getDataSources().keySet();
        log.debug("连接池信息 - {}", dsSets);

    }

    public String buildDb(String tenantCode) {
        final FrameworkMpProperties.MultiTenant multiTenant = properties.getMultiTenant();
        if (StringUtils.isBlank(tenantCode) || StringUtils.equals(tenantCode, multiTenant.getSuperTenantCode())) {
            return multiTenant.getDefaultDsName();
        }
        return multiTenant.getDsPrefix() + tenantCode;
    }

    public String getHeaderDsKey(HttpServletRequest request) {
        return buildDb(request.getHeader(properties.getMultiTenant().getTenantCodeColumn()));
    }

    public void initSqlScript(String tenantCode) {
        runScript(buildDb(tenantCode));
    }

    private void runScript(String dsKey) {
        DynamicRoutingDataSource ds = (DynamicRoutingDataSource) dataSource;
        // 从ThreadLocal中获取当前数据源
        final DataSource dataSource = ds.getDataSource(dsKey);
        ScriptRunner scriptRunner = new ScriptRunner(false, ";");
        final FrameworkMpProperties.MultiTenant multiTenant = properties.getMultiTenant();
        final List<String> tenantSqlScripts = multiTenant.getTenantSqlScripts();

        if (CollectionUtil.isEmpty(tenantSqlScripts)) {
            return;
        }
        tenantSqlScripts.forEach(script -> scriptRunner.runScript(dataSource, script));
    }

}
