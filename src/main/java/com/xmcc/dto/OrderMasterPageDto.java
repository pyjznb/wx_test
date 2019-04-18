package com.xmcc.dto;

import com.xmcc.model.OrderDetail;
import com.xmcc.model.OrderMaster;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderMasterPageDto extends OrderMaster{

    @Valid //表示需要嵌套验证
    @ApiModelProperty(value = "商品详情",dataType = "list")
    private List<OrderDetail> orderDetailList;

    public static OrderMasterPageDto build(OrderMaster orderMaster){
        OrderMasterPageDto dto = new OrderMasterPageDto();
        BeanUtils.copyProperties(orderMaster,dto);
        return dto;
    }

}
