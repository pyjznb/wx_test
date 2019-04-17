package com.xmcc.service.impl;

import com.xmcc.common.ResultEnums;
import com.xmcc.common.ResultResponse;
import com.xmcc.dto.ProductCategoryDto;
import com.xmcc.dto.ProductInfoDto;
import com.xmcc.model.ProductCategory;
import com.xmcc.model.ProductInfo;
import com.xmcc.repository.ProductCategoryRepository;
import com.xmcc.repository.ProductInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public ResultResponse queryList(){
        List<ProductCategory> all = productCategoryRepository.findAll();
        List<ProductCategoryDto> productCategoryDtoList =
                all.stream().map(ProductCategory -> ProductCategoryDto.build(ProductCategory)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(all)){
            return ResultResponse.fail();
        }
        List<Integer> typeList =
                productCategoryDtoList.stream().map(productCategoryDto -> productCategoryDto.getCategoryType()).collect(Collectors.toList());

        List<ProductInfo> productInfoList =
                productInfoRepository.findByProductStatusAndCategoryTypeIn(ResultEnums.PRODUCT_UP.getCode(),typeList);
                List<ProductCategoryDto> productCategoryDtos =
                        productCategoryDtoList.parallelStream().map(productCategoryDto -> {
                    productCategoryDto.setProductInfoDtoList(productInfoList.stream()
                    .filter(productInfo -> productInfo.getCategoryType() == productCategoryDto.getCategoryType())
                    .map(productInfo -> ProductInfoDto.build(productInfo)).collect(Collectors.toList()));
                    return productCategoryDto;
                }).collect(Collectors.toList());

                return ResultResponse.success(productCategoryDtos);
    }
}
