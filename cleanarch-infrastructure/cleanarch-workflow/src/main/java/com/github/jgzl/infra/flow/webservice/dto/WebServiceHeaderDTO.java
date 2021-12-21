package com.github.jgzl.infra.flow.webservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebServiceHeaderDTO implements Serializable {
	private String requestId;
	private String serviceName;
}
