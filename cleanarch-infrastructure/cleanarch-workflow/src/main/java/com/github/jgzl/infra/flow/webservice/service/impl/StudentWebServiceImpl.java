package com.github.jgzl.infra.flow.webservice.service.impl;

import com.github.jgzl.infra.flow.webservice.common.WebServiceConstants;
import com.github.jgzl.infra.flow.webservice.dto.*;
import com.github.jgzl.infra.flow.webservice.service.StudentWebService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import javax.jws.WebService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
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

	public static void main(String[] args) {
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean=new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setAddress("http://127.0.0.1:8101/services/student?wsdl");
		jaxWsProxyFactoryBean.setServiceClass(StudentWebService.class);

		StudentWebService studentWebService = (StudentWebService) jaxWsProxyFactoryBean.create(); // 创建客户端对象
		Client proxy= ClientProxy.getClient(studentWebService);
		HTTPConduit conduit=(HTTPConduit)proxy.getConduit();
		HTTPClientPolicy policy=new HTTPClientPolicy();
		policy.setConnectionTimeout(1000);
		policy.setReceiveTimeout(1000);
		conduit.setClient(policy);

		WebServiceHeaderDTO webServiceHeaderDTO = new WebServiceHeaderDTO();
		WebServiceBodyDTO<StudentDTO> webServiceBodyDTO = new WebServiceBodyDTO<>();
		StudentRequestDTO<StudentDTO> studentRequestDTO = new StudentRequestDTO<>();
		StudentDTO student = new StudentDTO();
		webServiceHeaderDTO.setRequestId(UUID.randomUUID().toString());
		webServiceHeaderDTO.setServiceName("student");
		student.setUsername("lihaifeng");
		student.setNickname("lihaifeng");
		webServiceBodyDTO.setData(student);
		studentRequestDTO.setServiceHeader(webServiceHeaderDTO);
		studentRequestDTO.setServiceBody(webServiceBodyDTO);
		StudentResponseDTO studentResponseDTO= studentWebService.fetchStudent(studentRequestDTO);
		Optional.ofNullable(studentResponseDTO.getStudents()).ifPresent(studentDTO -> {
			log.info("studentDTO:{}",studentDTO);
		});
	}
}
