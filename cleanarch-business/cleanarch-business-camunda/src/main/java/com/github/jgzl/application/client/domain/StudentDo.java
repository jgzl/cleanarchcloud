package com.github.jgzl.application.client.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StudentDo implements Serializable {
	private Long id;
	private String username;
	private String nickname;
	private List<String> hobbies;
}
