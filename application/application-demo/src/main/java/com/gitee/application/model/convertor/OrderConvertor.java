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

package com.gitee.application.model.convertor;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.gitee.application.model.bo.OrderBO;
import com.gitee.application.model.bo.OrderItemBO;
import com.gitee.application.model.dao.OrderDO;
import com.gitee.application.model.dao.OrderItemDO;
import com.gitee.application.model.vo.OrderVO;

/**
 * @author lihaifeng
 */
@Mapper
public interface OrderConvertor {
  OrderConvertor INSTANCE = Mappers.getMapper(OrderConvertor.class);

  /**
   * 根据order数据库对象转换为order业务对象
   * @param orderDO
   * @return OrderBO
   */
  @Mappings({
      @Mapping(source = "id", target = "orderId")
  })
  OrderBO convertDO2BO(OrderDO orderDO);

  /**
   * dao->bo
   * @param orderItemDO
   * @return
   */
  @Mappings({
      @Mapping(source = "id", target = "orderItemId")
  })
  OrderItemBO convertDO2BO(OrderItemDO orderItemDO);

  /**
   * 批量转换
   * @param orderItemDOs
   * @return
   */
  List<OrderItemBO> convertDO2BOList(List<OrderItemDO> orderItemDOs);

  /**
   * vo->bo
   * @param orderVO
   * @return
   */
  OrderBO covertVO2BO(OrderVO orderVO);

  /**
   * vo->dao
   * @param orderVO
   * @return
   */
  @Mappings({
      @Mapping(source = "orderId", target = "id")
  })
  OrderDO covertVO2DO(OrderVO orderVO);
}
