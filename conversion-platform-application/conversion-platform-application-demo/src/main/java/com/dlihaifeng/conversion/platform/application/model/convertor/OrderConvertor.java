package com.dlihaifeng.conversion.platform.application.model.convertor;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.dlihaifeng.conversion.platform.application.model.bo.OrderBO;
import com.dlihaifeng.conversion.platform.application.model.bo.OrderItemBO;
import com.dlihaifeng.conversion.platform.application.model.dao.OrderDAO;
import com.dlihaifeng.conversion.platform.application.model.dao.OrderItemDAO;

/**
 * @author lihaifeng
 */
@Mapper
public interface OrderConvertor {
  OrderConvertor INSTANCE = Mappers.getMapper(OrderConvertor.class);

  /**
   * 根据order数据库对象转换为order业务对象
   * @param orderDAO
   * @return OrderBO
   */
  @Mappings({
      @Mapping(source = "id", target = "orderId")
  })
  OrderBO convert(OrderDAO orderDAO);

  /**
   * dao->bo
   * @param orderItemDAO
   * @return
   */
  @Mappings({
      @Mapping(source = "id", target = "orderItemId")
  })
  OrderItemBO convert(OrderItemDAO orderItemDAO);

  /**
   * 批量转换
   * @param orderItemDAOs
   * @return
   */
  List<OrderItemBO> convert(List<OrderItemDAO> orderItemDAOs);
}
