package com.github.jgzl.infra.upms.mapper;

import com.github.jgzl.common.data.datascope.ExtendBaseMapper;
import com.github.jgzl.infra.upms.api.entity.SysDeptRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author lihaifeng
 * @since 2018-02-12
 */
@Mapper
public interface SysDeptRelationMapper extends ExtendBaseMapper<SysDeptRelation> {

	/**
	 * 删除部门关系表数据
	 *
	 * @param id 部门ID
	 */
	void deleteDeptRelationsById(Integer id);

	/**
	 * 更改部分关系表数据
	 *
	 * @param deptRelation
	 */
	void updateDeptRelations(SysDeptRelation deptRelation);

}
