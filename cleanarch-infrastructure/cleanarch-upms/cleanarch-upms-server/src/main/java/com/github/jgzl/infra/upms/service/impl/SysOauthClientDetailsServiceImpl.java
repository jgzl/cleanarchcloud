package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.CommonConstants;
import com.github.jgzl.common.core.util.SpringContextHolder;
import com.github.jgzl.infra.upms.api.dto.SysOauthClientDetailsDTO;
import com.github.jgzl.infra.upms.api.entity.SysOauthClientDetails;
import com.github.jgzl.infra.upms.config.ClientDetailsInitRunner;
import com.github.jgzl.infra.upms.mapper.SysOauthClientDetailsMapper;
import com.github.jgzl.infra.upms.service.SysOauthClientDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author lihaifeng
 * @since 2018-05-15
 */
@Service
@RequiredArgsConstructor
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails>
		implements SysOauthClientDetailsService {

	/**
	 * 通过ID删除客户端
	 * @param clientId
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId")
	public Boolean removeByClientId(String clientId) {
		// 更新库
		baseMapper
				.delete(Wrappers.<SysOauthClientDetails>lambdaQuery().eq(SysOauthClientDetails::getClientId, clientId));
		// 更新Redis
		SpringContextHolder.publishEvent(new ClientDetailsInitRunner.ClientDetailsInitEvent(clientId));
		return Boolean.TRUE;
	}

	/**
	 * 根据客户端信息
	 * @param clientDetailsDTO
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientDetailsDTO.clientId")
	public Boolean updateClientById(SysOauthClientDetailsDTO clientDetailsDTO) {
		// copy dto 对象
		SysOauthClientDetails clientDetails = new SysOauthClientDetails();
		BeanUtils.copyProperties(clientDetailsDTO, clientDetails);

		// 获取扩展信息,插入开关相关
		String information = clientDetailsDTO.getAdditionalInformation();
		JSONObject informationObj = JSONUtil.parseObj(information)
				.set(CommonConstants.CAPTCHA_FLAG, clientDetailsDTO.getCaptchaFlag())
				.set(CommonConstants.ENC_FLAG, clientDetailsDTO.getEncFlag());
		clientDetails.setAdditionalInformation(informationObj.toString());

		// 更新数据库
		baseMapper.updateById(clientDetails);
		// 更新Redis
		SpringContextHolder.publishEvent(new ClientDetailsInitRunner.ClientDetailsInitEvent(clientDetails));
		return Boolean.TRUE;
	}

	/**
	 * 添加客户端
	 * @param clientDetailsDTO
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveClient(SysOauthClientDetailsDTO clientDetailsDTO) {
		// copy dto 对象
		SysOauthClientDetails clientDetails = new SysOauthClientDetails();
		BeanUtils.copyProperties(clientDetailsDTO, clientDetails);

		// 获取扩展信息,插入开关相关
		String information = clientDetailsDTO.getAdditionalInformation();
		JSONUtil.parseObj(information).set(CommonConstants.CAPTCHA_FLAG, clientDetailsDTO.getCaptchaFlag())
				.set(CommonConstants.ENC_FLAG, clientDetailsDTO.getEncFlag());

		// 插入数据
		this.baseMapper.insert(clientDetails);
		// 更新Redis
		SpringContextHolder.publishEvent(new ClientDetailsInitRunner.ClientDetailsInitEvent(clientDetails));
		return Boolean.TRUE;
	}

	/**
	 * 分页查询客户端信息
	 * @param page
	 * @param query
	 * @return
	 */
	@Override
	public Page queryPage(Page page, SysOauthClientDetails query) {
		Page<SysOauthClientDetails> selectPage = baseMapper.selectPage(page, Wrappers.query(query));

		// 处理扩展字段组装dto
		List<SysOauthClientDetailsDTO> collect = selectPage.getRecords().stream().map(details -> {
			String information = details.getAdditionalInformation();
			String captchaFlag = JSONUtil.parseObj(information).getStr(CommonConstants.CAPTCHA_FLAG);
			String encFlag = JSONUtil.parseObj(information).getStr(CommonConstants.ENC_FLAG);
			SysOauthClientDetailsDTO dto = new SysOauthClientDetailsDTO();
			BeanUtils.copyProperties(details, dto);
			dto.setCaptchaFlag(captchaFlag);
			dto.setEncFlag(encFlag);
			return dto;
		}).collect(Collectors.toList());

		// 构建dto page 对象
		Page<SysOauthClientDetailsDTO> dtoPage = new Page<>(page.getCurrent(), page.getSize(), selectPage.getTotal());
		dtoPage.setRecords(collect);
		return dtoPage;
	}

}
