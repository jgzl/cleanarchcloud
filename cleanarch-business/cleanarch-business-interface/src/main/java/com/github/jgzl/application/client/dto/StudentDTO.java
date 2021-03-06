package com.github.jgzl.application.client.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class StudentDTO implements Serializable {
	private Long id;
	private String username;
	private String nickname;
	private List<String> hobbies;
}
