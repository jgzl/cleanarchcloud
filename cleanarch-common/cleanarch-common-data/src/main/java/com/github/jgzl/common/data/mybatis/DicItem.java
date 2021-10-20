package com.github.jgzl.common.data.mybatis;

import lombok.Data;

import java.io.Serializable;

@Data
public class DicItem implements Serializable {
	private String itemKey;
	private String itemName;
}
