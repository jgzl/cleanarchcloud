package com.github.jgzl.infra.flow.webservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class StudentRequestDTO<T> implements Serializable {
	private WebServiceHeaderDTO serviceHeader;
	private WebServiceBodyDTO<T> serviceBody;
}
