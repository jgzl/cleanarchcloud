package com.github.jgzl.common.cache.sequence;

/**
 * @author lihaifeng
 */
public interface Sequence {

	/**
	 * Redis Key
	 *
	 * @return String
	 */
	String key();

	/**
	 * Redis Prefix
	 *
	 * @return UN => UN20210909000001
	 */
	String prefix();
}
