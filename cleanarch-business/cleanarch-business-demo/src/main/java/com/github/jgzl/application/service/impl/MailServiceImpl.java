package com.github.jgzl.application.service.impl;

import java.util.Map;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.github.jgzl.application.service.MailService;

import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String from;

	@Override
	public boolean sendTemplateMail(String title, String toUser, String templateName, Map<String, Object> params) {
		try {

			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			// 发件人
			helper.setFrom(from);
			//收件人
			helper.setTo(toUser);
			//邮件标题
			helper.setSubject(title);

			Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
			configuration.setClassForTemplateLoading(this.getClass(), "/templates");
			String text = FreeMarkerTemplateUtils
					.processTemplateIntoString(configuration.getTemplate(templateName), params);
			//// text：内容，true:为HTML邮件（false则为普通文本邮件）
			helper.setText(text, true);
			DataSource dataSource1 = new FileDataSource("/Users/lihaifeng/Downloads/需求.jpg");
			DataSource dataSource2 = new FileDataSource("/Users/lihaifeng/Downloads/需求.jpg");
			helper.addAttachment("附件1.jpg",dataSource1);
			helper.addAttachment("附件2.jpg",dataSource2);
			mailSender.send(mimeMessage);

		} catch (Exception e) {
			log.error("sendTemplateMail 发送模板邮件错误，", e);
			throw new RuntimeException(e);
		}
		return true;
	}

}
