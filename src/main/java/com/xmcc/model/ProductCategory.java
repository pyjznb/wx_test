package com.xmcc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@DynamicUpdate
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category")
public class ProductCategory implements Serializable{
    /** 类目id. */
    @Id  //主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) //表示自增IDENTITY：mysql SEQUENCE:oracle
    private Integer categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

}
