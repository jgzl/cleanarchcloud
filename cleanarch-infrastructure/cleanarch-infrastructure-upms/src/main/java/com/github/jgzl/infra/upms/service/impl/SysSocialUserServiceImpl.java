package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.amazonaws.services.s3.model.S3Object;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.oss.OssProperties;
import com.github.jgzl.common.oss.service.OssTemplate;
import com.github.jgzl.common.security.util.SecurityUtils;
import com.github.jgzl.infra.upms.dataobject.SysFile;
import com.github.jgzl.infra.upms.dataobject.SysSocialUser;
import com.github.jgzl.infra.upms.mapper.SysFileMapper;
import com.github.jgzl.infra.upms.mapper.SysSocialUserMapper;
import com.github.jgzl.infra.upms.service.SysFileService;
import com.github.jgzl.infra.upms.service.SysSocialUserService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysSocialUserServiceImpl extends ServiceImpl<SysSocialUserMapper, SysSocialUser> implements SysSocialUserService {

	@Override
	public List<SysSocialUser> findSocialUserByUserIdAndSource(String userId, String source) {
		return this.baseMapper.findSocialUserByUserIdAndSource(userId,source);
	}
}
