package com.github.jgzl.application.auth.service.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.common.security.dataobject.SysOauthClientDetailsDO;
import com.github.jgzl.common.security.vo.SysOauthClientDetailsVO;

/**
 * 客户端Service
 *
 * @author lihaifeng
 * 2019/7/5 14:55
 */
public interface SysOauthClientDetailsService extends IService<SysOauthClientDetailsDO> {

    /**
     * 查询客户端VO
     *
     * @param clientId
     * @return
     */
    SysOauthClientDetailsVO getVo(String clientId);

    /**
     * 更新客户端
     *
     * @param vo 客户端
     * @return
     */
    Boolean update(SysOauthClientDetailsVO vo);

    /**
     * 新增客户端
     *
     * @param vo 客户端
     * @return
     */
    Boolean add(SysOauthClientDetailsVO vo);

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
    IPage<SysOauthClientDetailsVO> selectPageVo(Page page);
}
