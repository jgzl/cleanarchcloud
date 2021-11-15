package com.github.jgzl.application.dto;
import java.io.Serializable;
import lombok.Data;
@Data
public class WebServiceHeaderDTO implements Serializable {
	private String requestId;
	private String serviceName;
}
