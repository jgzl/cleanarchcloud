package com.github.jgzl.infra.upms.service;
import com.github.jgzl.common.data.mybatis.SuperService;
import com.github.jgzl.infra.upms.domain.dto.ResourceQueryDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Resource;
import com.github.jgzl.infra.upms.domain.vo.VueRouter;
import java.util.List;
/**
 * <p>
 * 业务接口
 * 资源
 * </p>
 *
 * @author lihaifeng
 * @since 2020-07-03
 */
public interface ResourceService extends SuperService<Resource> {
    /**
     * 查询 拥有的资源
     *
     * @param resource resource
     * @return 查询结果
     */
    List<VueRouter> findVisibleResource(ResourceQueryDTO resource);


    /**
     * 添加资源
     *
     * @param resource 资源
     */
    void addResource(Resource resource);
    /**
     * 修改资源
     *
     * @param resource 资源
     */
    void editResourceById(Resource resource);
    /**
     * 删除资源
     *
     * @param resourceId resourceId
     */
    void delResource(Long resourceId);

}
