package com.github.jgzl.infra.flow.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.github.jgzl.infra.flow.common.WebServiceConstants;
import com.github.jgzl.infra.flow.dto.StudentRequestDTO;
import com.github.jgzl.infra.flow.dto.StudentResponseDTO;

@WebService(targetNamespace = WebServiceConstants.CAMUNDA_NAMESPACE)
public interface StudentWebService<T> {
	@WebMethod
	public StudentResponseDTO fetchStudent(StudentRequestDTO<T> student);
}
