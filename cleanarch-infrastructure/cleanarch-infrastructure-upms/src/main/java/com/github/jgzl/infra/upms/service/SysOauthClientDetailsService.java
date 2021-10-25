package com.github.jgzl.infra.upms.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.common.api.dataobject.SysOauthClientDetails;
import com.github.jgzl.common.api.vo.SysOauthClientDetailsVo;

/**
 * 客户端Service
 *
 * @author lihaifeng
 * 2019/7/5 14:55
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {

    /**
     * 查询客户端Vo
     *
     * @param clientId
     * @return
     */
    SysOauthClientDetailsVo getVo(String clientId);

    /**
     * 更新客户端
     *
     * @param vo 客户端
     * @return
     */
    Boolean update(SysOauthClientDetailsVo vo);

    /**
     * 新增客户端
     *
     * @param vo 客户端
     * @return
     */
    Boolean add(SysOauthClientDetailsVo vo);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    IPage<SysOauthClientDetailsVo> selectPageVo(Page page);
}
