package com.xmcc.repository;

import com.xmcc.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//泛型1：实体类类型  泛型2：主键类型
public interface StudentRepository extends JpaRepository<Student,Integer> {

    //关键字定义
       //根据id来查询
    List<Student> findAllByIdIn(List<Integer> ids);

    //自定义sql 如果想在查询的所有中里面只拿一条数据, 那么可以使用limit
    //jap底层实现是 hibernate  hibernate：hql(基于实体类进行查询) jpa：jpql(基于实体类)
    @Query(value = "select * from student where id = ?1",nativeQuery = true)
    Student queryStudentByStudentId(Integer id);

    @Query(value = "select s from Student s where id = ?1")
    Student studentByStudentId(Integer id);

}
