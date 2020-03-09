/**
 *
 */
package com.gitee.gateway.model;

import java.util.Date;

import lombok.Data;


@Data
public class TokenInfo {

	private boolean active;

	private String client_id;

	private String[] scope;

	private String user_name;

	private String[] aud;

	private Date exp;

	private String[] authorities;
}
