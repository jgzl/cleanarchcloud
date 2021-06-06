package com.github.jgzl.application.client.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class WebServiceBodyDTO<T> implements Serializable {
	private T data;
}
