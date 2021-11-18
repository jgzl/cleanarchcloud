package com.github.jgzl.infra.flow.webservice.service.impl;
import java.util.List;
import javax.jws.WebService;

import com.github.jgzl.infra.flow.webservice.common.WebServiceConstants;
import com.github.jgzl.infra.flow.webservice.dto.StudentDTO;
import com.github.jgzl.infra.flow.webservice.dto.StudentRequestDTO;
import com.github.jgzl.infra.flow.webservice.dto.StudentResponseDTO;
import com.github.jgzl.infra.flow.webservice.service.StudentWebService;
import com.google.common.collect.Lists;

@WebService(targetNamespace = WebServiceConstants.CAMUNDA_NAMESPACE)
public class StudentWebServiceImpl implements StudentWebService<StudentDTO> {
	@Override
	public StudentResponseDTO fetchStudent(StudentRequestDTO<StudentDTO> student) {
		List<StudentDTO> students = Lists.newArrayList();
		StudentDTO data = student.getServiceBody().getData();
		StudentResponseDTO responseDTO=new StudentResponseDTO();
		String username = data.getUsername();
		String nickname = data.getNickname();
		for (long i = 0L; i < 10L; i++) {
			StudentDTO studentDTO=new StudentDTO();
			studentDTO.setId(i);
			studentDTO.setUsername(username);
			studentDTO.setNickname(nickname);
			studentDTO.setHobbies(Lists.newArrayList("兴趣"+i,"兴趣"+i+""+i));
			students.add(studentDTO);
		}
		responseDTO.setStudents(students);
		return responseDTO;
	}
}
