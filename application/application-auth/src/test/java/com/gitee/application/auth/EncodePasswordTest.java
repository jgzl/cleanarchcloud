package com.gitee.application.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodePasswordTest {

	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	public void encode(String password) {
		System.out.println(encoder.encode(password));
	}

	public static void main(String[] args) {
		new EncodePasswordTest().encode("123456");
	}
}
