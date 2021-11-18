package com.github.jgzl.infra.flow.webservice.dto;
import java.io.Serializable;
import lombok.Data;

@Data
public class WebServiceBodyDTO<T> implements Serializable {
	private T data;
}
