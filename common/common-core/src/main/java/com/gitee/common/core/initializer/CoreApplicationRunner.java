package com.gitee.common.core.initializer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.boot.ApplicationArguments;
import org.springframework.core.annotation.Order;

import com.gitee.common.core.config.CoreProperties;
import com.gitee.common.core.util.FileUtils;
import com.gitee.common.core.util.JsonUtils;
import com.gitee.common.core.util.LogUtils;

/**
 * 容器生命周期监听程序
 * */
@Order
public class CoreApplicationRunner implements org.springframework.boot.ApplicationRunner {

	@Override
	public void run(ApplicationArguments var1) throws Exception {
		saveStatus("STARTED");
	}

	private void saveStatus(String status) {
		HashMap<String, Object> map = new HashMap<>(2);
		map.put("data", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		map.put("state", status);
		FileUtils.writeAllText("status", JsonUtils.serialize(map));
		LogUtils.info(CoreApplicationRunner.class, CoreProperties.Project, "应用已正常启动!");
	}
}
