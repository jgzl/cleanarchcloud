/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.jgzl.common.security.exception;

import com.github.jgzl.common.security.component.CustomOauth2ExceptionSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author lihaifeng
 * @date 2018/7/8
 * 自定义OAuth2Exception
 */
@JsonSerialize(using = CustomOauth2ExceptionSerializer.class)
public class CustomOauth2Exception extends OAuth2Exception {

	private String oauth2ErrorCode;

	public CustomOauth2Exception(final String msg, final Throwable t) {
		super(msg, t);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return this.oauth2ErrorCode;
	}

	public void setOauth2ErrorCode(final String oauth2ErrorCode) {
		this.oauth2ErrorCode = oauth2ErrorCode;
	}
}
