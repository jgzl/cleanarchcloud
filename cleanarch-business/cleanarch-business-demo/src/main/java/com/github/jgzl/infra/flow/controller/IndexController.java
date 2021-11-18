package com.github.jgzl.infra.flow.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
/**
 * @author lihaifeng
 */
@Slf4j
@RestController
@RequestMapping
public class IndexController {
	@Autowired
	private Environment environment;
	@Autowired
	private StringRedisTemplate redisTemplate;
	@Value("${redis.topic.chat}")
	private String chatTopic;
	@GetMapping("ping")
	public String ping() {
		log.info("ping_url:{}", environment.getProperty("server.port"));
		return environment.getProperty("server.port");
	}
	/**
	 * 发送消息
	 * 先登录 http://www.websocket-test.com/ 打开三个窗口输入
	 * ws://127.0.0.1:8020/ws?token=lihaifeng1
	 * ws://127.0.0.1:8020/ws?token=lihaifeng2
	 * ws://127.0.0.1:8020/ws?token=lihaifeng3
	 * 最后再调用这个方法，
	 * 发送 http://127.0.0.1:8020/sendMsg?msg=%E7%BE%A4%E5%8F%91WebSocket%E6%B6%88%E6%81%AF
	 * 发送 http://127.0.0.1:8020/sendMsg?msg=%E7%BE%A4%E5%8F%91WebSocket%E6%B6%88%E6%81%AF
	 * 如果没有名字李海峰,则
	 * @param msg
	 * @return
	 */
	@GetMapping("/sendMsg")
	public String sendMsg(String msg) {
		redisTemplate.convertAndSend(chatTopic, msg);
		return "success";
	}
	/**
	 * 上传文件
	 * @param multipartFile
	 * @param request
	 * @param response
	 */
	@SneakyThrows
	@PostMapping("/upload")
	public void upload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request,
			HttpServletResponse response) {
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
				"attachment;fileName=" + java.net.URLEncoder.encode(multipartFile.getOriginalFilename(), "UTF-8"));
		response.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
		response.setCharacterEncoding("UTF-8");
		IOUtils.copy(multipartFile.getInputStream(), response.getOutputStream());
	}
}
