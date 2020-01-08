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

package com.gitee.common.security.login;

import javax.sql.DataSource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;

import com.gitee.common.core.constant.CacheConstants;

/**
 * @author lihaifeng
 */
public class PlatformClientUserDetailsServiceImpl extends JdbcClientDetailsService {

  public PlatformClientUserDetailsServiceImpl(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  @Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
  public ClientDetails loadClientByClientId(String clientId) throws InvalidClientException {
    BaseClientDetails user = (BaseClientDetails) super.loadClientByClientId(clientId);
    return user;
  }
}
