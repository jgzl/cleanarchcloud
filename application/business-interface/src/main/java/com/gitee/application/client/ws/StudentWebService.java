package com.gitee.application.client.ws;

import javax.jws.WebMethod;
import javax.jws.WebService;

import com.gitee.application.client.common.WebServiceConstants;
import com.gitee.application.client.dto.StudentRequestDTO;
import com.gitee.application.client.dto.StudentResponseDTO;

@WebService(targetNamespace = WebServiceConstants.CAMUNDA_NAMESPACE)
public interface StudentWebService<T> {
	@WebMethod
	public StudentResponseDTO fetchStudent(StudentRequestDTO<T> student);
}
