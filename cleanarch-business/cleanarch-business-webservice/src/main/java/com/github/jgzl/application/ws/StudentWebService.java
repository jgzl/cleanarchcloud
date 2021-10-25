package com.github.jgzl.application.ws;
import javax.jws.WebMethod;
import javax.jws.WebService;
import com.github.jgzl.application.common.WebServiceConstants;
import com.github.jgzl.application.dto.StudentRequestDTO;
import com.github.jgzl.application.dto.StudentResponseDTO;

@WebService(targetNamespace = WebServiceConstants.CAMUNDA_NAMESPACE)
public interface StudentWebService<T> {
	@WebMethod
	public StudentResponseDTO fetchStudent(StudentRequestDTO<T> student);
}
