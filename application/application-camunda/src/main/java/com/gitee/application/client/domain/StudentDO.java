package com.gitee.application.client.domain;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class StudentDO implements Serializable {
	private Long id;
	private String username;
	private String nickname;
	private List<String> hobbies;
}
