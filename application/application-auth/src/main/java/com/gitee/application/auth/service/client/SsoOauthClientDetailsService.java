package com.gitee.application.auth.service.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.common.security.dao.SsoOauthClientDetailsDAO;
import com.gitee.common.security.vo.SsoOauthClientDetailsVO;

/**
 * 客户端Service
 *
 * @author lihaifeng
 * 2019/7/5 14:55
 */
public interface SsoOauthClientDetailsService extends IService<SsoOauthClientDetailsDAO> {

    /**
     * 查询客户端VO
     *
     * @param clientId
     * @return
     */
    SsoOauthClientDetailsVO getVo(String clientId);

    /**
     * 更新客户端
     *
     * @param vo 客户端
     * @return
     */
    Boolean update(SsoOauthClientDetailsVO vo);

    /**
     * 新增客户端
     *
     * @param vo 客户端
     * @return
     */
    Boolean add(SsoOauthClientDetailsVO vo);

    /**
     * 删除客户端VO
     *
     * @param clientId
     * @return
     */
    Boolean delete(String clientId);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    IPage<SsoOauthClientDetailsVO> selectPageVo(Page page);
}
