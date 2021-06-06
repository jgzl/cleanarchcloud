package com.github.jgzl.application.client.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.github.jgzl.application.client.common.WebServiceConstants;
import com.github.jgzl.application.client.dto.StudentRequestDTO;
import com.github.jgzl.application.client.dto.StudentResponseDTO;

@WebService(targetNamespace = WebServiceConstants.CAMUNDA_NAMESPACE)
public interface StudentWebService<T> {
	@WebMethod
	public StudentResponseDTO fetchStudent(StudentRequestDTO<T> student);
}
