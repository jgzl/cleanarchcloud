package com.github.jgzl.infra.flow.webservice.dto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;
@Data
public class StudentResponseDTO implements Serializable {
	private List<StudentDTO> students;
}
