/*
 * Copyright [2020] [lihaifeng,xuhang]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.jgzl.application.model.convertor;

import java.util.List;

import com.github.jgzl.application.model.bo.OrderBO;
import com.github.jgzl.application.model.bo.OrderItemBO;
import com.github.jgzl.application.model.vo.OrderVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.github.jgzl.application.model.dao.OrderDo;
import com.github.jgzl.application.model.dao.OrderItemDo;

/**
 * @author lihaifeng
 */
@Mapper
public interface OrderConvertor {
	OrderConvertor INSTANCE = Mappers.getMapper(OrderConvertor.class);

	/**
	 * 根据order数据库对象转换为order业务对象
	 * @param orderDo
	 * @return OrderBO
	 */
	@Mappings({
			@Mapping(source = "id", target = "orderId")
	})
    OrderBO convertDo2BO(OrderDo orderDo);

	/**
	 * dao->bo
	 * @param orderItemDo
	 * @return
	 */
	@Mappings({
			@Mapping(source = "id", target = "orderItemId")
	})
    OrderItemBO convertDo2BO(OrderItemDo orderItemDo);

	/**
	 * 批量转换
	 * @param orderItemDos
	 * @return
	 */
	List<OrderItemBO> convertDo2BOList(List<OrderItemDo> orderItemDos);

	/**
	 * vo->bo
	 * @param orderVo
	 * @return
	 */
	OrderBO covertVo2BO(OrderVo orderVo);

	/**
	 * vo->dao
	 * @param orderVo
	 * @return
	 */
	@Mappings({
			@Mapping(source = "orderId", target = "id")
	})
	OrderDo covertVo2Do(OrderVo orderVo);
}
