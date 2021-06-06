package com.github.jgzl.application.service;

import java.util.Map;

public interface MailService {
	public boolean sendTemplateMail(String title, String toUser, String templateName, Map<String, Object> params);
}
