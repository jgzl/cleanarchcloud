package com.gitee.application.model.convertor;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import com.gitee.application.model.bo.OrderBO;
import com.gitee.application.model.bo.OrderItemBO;
import com.gitee.application.model.dao.OrderDO;
import com.gitee.application.model.dao.OrderItemDAO;
import com.gitee.application.model.vo.OrderVO;

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
  OrderBO convertDAO2BO(OrderDO orderDAO);

  /**
   * dao->bo
   * @param orderItemDAO
   * @return
   */
  @Mappings({
      @Mapping(source = "id", target = "orderItemId")
  })
  OrderItemBO convertDAO2BO(OrderItemDAO orderItemDAO);

  /**
   * 批量转换
   * @param orderItemDAOs
   * @return
   */
  List<OrderItemBO> convertDAO2BOList(List<OrderItemDAO> orderItemDAOs);

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
  OrderDO covertVO2DAO(OrderVO orderVO);
}
