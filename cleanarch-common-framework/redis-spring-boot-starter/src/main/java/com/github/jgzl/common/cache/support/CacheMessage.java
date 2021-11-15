package com.github.jgzl.common.cache.support;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.io.Serializable;
/**
 * @author lihaifeng
 * @version 1.0.0
 */
@Data
@AllArgsConstructor
public class CacheMessage implements Serializable {
	private String cacheName;
	private Object key;
}
