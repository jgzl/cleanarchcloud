package com.github.jgzl.infra.flow.webservice.dto;
import java.io.Serializable;
import lombok.Data;

@Data
public class StudentRequestDTO<T> implements Serializable {
	private WebServiceHeaderDTO serviceHeader;
	private WebServiceBodyDTO<T> serviceBody;
}