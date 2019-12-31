package com.gitee.application.model.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@Builder
public class WelcomeVO {
  private String userName;

  private String urlName;

  private String urlDescription;
}
