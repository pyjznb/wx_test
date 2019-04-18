package com.xmcc.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailPageDto {

    @NotBlank(message = "必须输入用户的微信openid")
    @ApiModelProperty(value = "买家微信openid",dataType = "String")
    private String openid;

    @NotBlank(message = "必须输入商品信息orderId")
    @ApiModelProperty(value = "商品信息orderId",dataType = "String")
    private String orderId;

}
