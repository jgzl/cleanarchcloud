package com.github.jgzl.infra.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.infra.upms.api.dto.SysOauthClientDetailsDTO;
import com.github.jgzl.infra.upms.api.entity.SysOauthClientDetails;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lihaifeng
 * @since 2018-05-15
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetails> {

	/**
	 * 通过ID删除客户端
	 * @param clientId
	 * @return
	 */
	Boolean removeByClientId(String clientId);

	/**
	 * 根据客户端信息
	 * @param clientDetailsDTO
	 * @return
	 */
	Boolean updateClientById(SysOauthClientDetailsDTO clientDetailsDTO);

	/**
	 * 添加客户端
	 * @param clientDetailsDTO
	 * @return
	 */
	Boolean saveClient(SysOauthClientDetailsDTO clientDetailsDTO);

	/**
	 * 分页查询客户端信息
	 * @param page
	 * @param query
	 * @return
	 */
	Page queryPage(Page page, SysOauthClientDetails query);

}
