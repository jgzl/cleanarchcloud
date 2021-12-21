package com.github.jgzl.infra.flow.webservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StudentDTO implements Serializable {
	private Long id;
	private String username;
	private String nickname;
	private List<String> hobbies;
}
