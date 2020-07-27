import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.gitee.application.DemoApplication;
import com.gitee.application.service.MailService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {DemoApplication.class})
public class MailServiceTest {

	@Autowired
	private MailService mailService;

	@Test
	public void sendTemplateMailTest(){
		Map<String, Object> mailContentMap = new HashMap<>();
		mailContentMap.put("contactName", "sylujia");
		mailContentMap.put("platformAddr", "https://www.baidu.com");
		mailContentMap.put("loginName", "sylujia");
		mailContentMap.put("loginPswd", "123456");
		mailContentMap.put("payPswd", "123456");
		mailContentMap.put("contactMail", "li7hai26@qq.com");
		mailContentMap.put("contactPhone", "13144112255");
		try {
			mailService.sendTemplateMail("系统账号开通", "li7hai26@qq.com","AccountOpenMail.ftl", mailContentMap);
		} catch (Exception e) {
			log.error("账号开通邮件发送失败：{}", mailContentMap , e);
		}
		log.info("邮件发送成功");
	}



}
