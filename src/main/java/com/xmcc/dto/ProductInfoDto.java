package com.xmcc.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xmcc.model.ProductInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDto implements Serializable{

    @JsonProperty("id")
    private String productId;

    /** 名字 */
    @JsonProperty("name") //前端返回的数据名字定义成 name
    private String productName;

    /** 单价 */
    @JsonProperty("price")
    private BigDecimal productPrice;

    /** 描述 */
    @JsonProperty("description")
    private String productDescription;

    /** 小图 */
    @JsonProperty("icon")
    private String productIcon;

    //转换成Dto
    public static ProductInfoDto build(ProductInfo productInfo){
        ProductInfoDto dto = new ProductInfoDto();
        BeanUtils.copyProperties(productInfo,dto);
        return dto;
    }
}
