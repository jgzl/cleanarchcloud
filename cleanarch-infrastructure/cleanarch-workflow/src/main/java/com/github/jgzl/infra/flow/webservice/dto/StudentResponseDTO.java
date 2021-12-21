package com.github.jgzl.infra.flow.webservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StudentResponseDTO implements Serializable {
	private List<StudentDTO> students;
}
