package com.xmcc.service.impl;

import com.xmcc.common.ResultEnums;
import com.xmcc.common.ResultResponse;
import com.xmcc.dto.ProductCategoryDto;
import com.xmcc.dto.ProductInfoDto;
import com.xmcc.exception.CustomException;
import com.xmcc.model.ProductCategory;
import com.xmcc.model.ProductInfo;
import com.xmcc.repository.ProductCategoryRepository;
import com.xmcc.repository.ProductInfoRepository;
import com.xmcc.service.ProductInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductInfoServiceImpl implements ProductInfoService{

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
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

    @Override
    public ResultResponse<ProductInfo> queryById(String productId) {
        if (StringUtils.isBlank(productId)){
            return ResultResponse.fail(ResultEnums.PARAM_ERROR.getMsg());
        }
        Optional<ProductInfo> byId = productInfoRepository.findById(productId);
        if (!byId.isPresent()){
            return ResultResponse.fail(ResultEnums.NOT_EXITS.getMsg());//商品不存在
        }
        ProductInfo productInfo = byId.get();
        if (productInfo.getProductStatus() == ResultEnums.PRODUCT_DOWN.getCode()){//如果商品已下架
            return ResultResponse.fail(ResultEnums.PRODUCT_DOWN.getMsg());
        }
        return ResultResponse.success(productInfo);
    }


    //修改
    @Override
    public void updateProduct(ProductInfo productInfo) {
        productInfoRepository.save(productInfo);
    }
}
