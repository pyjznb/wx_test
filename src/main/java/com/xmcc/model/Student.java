package com.xmcc.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data //相当于 get set ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "student")// 指定是数据库中的哪一张表
@Entity
public class Student implements Serializable{

    @Id//指定主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键的生成策略
    private Integer id;

    private String name;

    private String gender;

    private String age;
}
