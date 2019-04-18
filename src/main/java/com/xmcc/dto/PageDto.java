package com.xmcc.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageDto {

    @NotBlank(message = "必须输入用户的微信openid")
    @ApiModelProperty(value = "买家微信openid",dataType = "String")
    private String openid;

    private Integer size;//每页显示的条数

    private Integer page;//当前页码

    /*private List<T> orderDetailList;//封装的订单项*/


}
