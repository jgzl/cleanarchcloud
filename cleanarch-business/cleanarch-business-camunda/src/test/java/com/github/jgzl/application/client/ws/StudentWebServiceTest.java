package com.github.jgzl.application.client.ws;

import java.util.Optional;
import java.util.UUID;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import com.github.jgzl.application.client.dto.StudentDTO;
import com.github.jgzl.application.client.dto.StudentRequestDTO;
import com.github.jgzl.application.client.dto.StudentResponseDTO;
import com.github.jgzl.application.client.dto.WebServiceBodyDTO;
import com.github.jgzl.application.client.dto.WebServiceHeaderDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudentWebServiceTest {
	public static void main(String[] args) {
		JaxWsProxyFactoryBean jaxWsProxyFactoryBean=new JaxWsProxyFactoryBean();
		jaxWsProxyFactoryBean.setAddress("http://127.0.0.1:8071/services/student?wsdl");
		jaxWsProxyFactoryBean.setServiceClass(StudentWebService.class);

		StudentWebService studentWebService = (StudentWebService) jaxWsProxyFactoryBean.create(); // 创建客户端对象
//		Client proxy= ClientProxy.getClient(studentWebService);
//		HTTPConduit conduit=(HTTPConduit)proxy.getConduit();
//		HTTPClientPolicy policy=new HTTPClientPolicy();
//		policy.setConnectionTimeout(1000);
//		policy.setReceiveTimeout(1000);
//		conduit.setClient(policy);

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
		Optional.ofNullable(studentResponseDTO.getStudents()).get().forEach(
				studentDTO -> {
					log.info("studentDTO:{}",studentDTO);
				}
		);
	}
}
