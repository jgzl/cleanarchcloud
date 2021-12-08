package com.github.jgzl.infra.flow.webservice.dto;
import java.io.Serializable;
import lombok.Data;
@Data
public class WebServiceHeaderDTO implements Serializable {
	private String requestId;
	private String serviceName;
}
