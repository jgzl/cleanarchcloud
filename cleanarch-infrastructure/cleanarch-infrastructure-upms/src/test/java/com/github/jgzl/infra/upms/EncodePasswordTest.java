package com.github.jgzl.infra.upms;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncodePasswordTest {

	PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	public void encode(String password) {
		System.out.println(encoder.encode(password));
	}

	public static void main(String[] args) {
		new EncodePasswordTest().encode("123456");
	}
}
