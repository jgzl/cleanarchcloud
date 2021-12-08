package com.github.jgzl.infra.flow.webservice.service;
import javax.jws.WebMethod;
import javax.jws.WebService;

import com.github.jgzl.infra.flow.webservice.common.WebServiceConstants;
import com.github.jgzl.infra.flow.webservice.dto.StudentRequestDTO;
import com.github.jgzl.infra.flow.webservice.dto.StudentResponseDTO;

@WebService(targetNamespace = WebServiceConstants.CAMUNDA_NAMESPACE)
public interface StudentWebService<T> {
	@WebMethod
	public StudentResponseDTO fetchStudent(StudentRequestDTO<T> student);
}
