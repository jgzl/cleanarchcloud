/*
 * Copyright [2020] [lihaifeng,xuhang]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.jgzl.application.codegen;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.github.jgzl.common.data.bo.BaseDo;

import java.util.Scanner;

/**
 * 演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 * @author lihaifeng
 */
public class CodeGeneratorTest {

	/**
	 * <p>
	 * 读取控制台内容
	 * </p>
	 */
	public static String scanner(String tip) {
		Scanner scanner = new Scanner(System.in);
		StringBuilder help = new StringBuilder();
		help.append("请输入" + tip + "：");
		System.out.println(help.toString());
		if (scanner.hasNext()) {
			String ipt = scanner.next();
			if (StrUtil.isNotEmpty(ipt)) {
				return ipt;
			}
		}
		throw new MybatisPlusException("请输入正确的" + tip + "！");
	}

	public static void main(String[] args) {
		DataSourceConfig dsc = new DataSourceConfig.Builder("jdbc:mysql://127.0.0.1:3306/sso?useUnicode=true&useSSL=false&characterEncoding=utf8","root","root").build();
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator(dsc);


		String projectPath = System.getProperty("user.dir");
		String moduleName = "application/business-upms";
		String moduleApplicationName = "upms";
		// 全局配置
		GlobalConfig gc = new GlobalConfig.Builder()
				.outputDir(projectPath + "/" + moduleName + "/src/main/java")
				.author("lihaifeng")
				.openDir(true)
				.enableSwagger()
				.build();

		// 包配置
		PackageConfig pc = new PackageConfig.Builder("com.github.jgzl.application",moduleApplicationName)
				.build();

		// 自定义配置
		InjectionConfig cfg = new InjectionConfig();

		// 如果模板引擎是 freemarker
		String templatePath = "/templates/mapper.xml.ftl";
		// 如果模板引擎是 velocity
		// String templatePath = "/templates/mapper.xml.vm";

		// 配置模板
		TemplateConfig templateConfig = new TemplateConfig.Builder()
				.mapperXml(null)
				.build();

		// 策略配置
		StrategyConfig strategy = new StrategyConfig.Builder()
				.addInclude("platform_sys_user,platform_dept,platform_log,platform_menu,platform_role,platform_user_connection"
						.split(","))
				.controllerBuilder()
				.enableRestStyle()
				.enableHyphenStyle()
				.serviceBuilder()
				.entityBuilder()
				.naming(NamingStrategy.underline_to_camel)
				.columnNaming(NamingStrategy.underline_to_camel)
				.superClass(BaseDo.class)
				.enableLombok()
				.mapperBuilder()
				.build();
		mpg.global(gc).packageInfo(pc).strategy(strategy);
		mpg.execute(new FreemarkerTemplateEngine());
	}
}
