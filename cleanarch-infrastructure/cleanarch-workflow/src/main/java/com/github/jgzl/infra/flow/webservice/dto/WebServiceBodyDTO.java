package com.github.jgzl.infra.flow.webservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class WebServiceBodyDTO<T> implements Serializable {
	private T data;
}
