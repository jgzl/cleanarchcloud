package com.github.jgzl.infra.flow.webservice.service;

import com.github.jgzl.infra.flow.webservice.common.WebServiceConstants;
import com.github.jgzl.infra.flow.webservice.dto.StudentRequestDTO;
import com.github.jgzl.infra.flow.webservice.dto.StudentResponseDTO;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(targetNamespace = WebServiceConstants.CAMUNDA_NAMESPACE)
public interface StudentWebService<T> {
	@WebMethod
    StudentResponseDTO fetchStudent(StudentRequestDTO<T> student);
}
