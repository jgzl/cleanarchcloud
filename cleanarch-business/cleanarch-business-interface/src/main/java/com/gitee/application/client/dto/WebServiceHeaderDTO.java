package com.gitee.application.client.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class WebServiceHeaderDTO implements Serializable {
	private String requestId;
	private String serviceName;
}
