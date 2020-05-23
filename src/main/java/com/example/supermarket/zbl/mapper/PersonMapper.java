package com.example.supermarket.zbl.mapper;


import com.example.supermarket.zbl.domain.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface PersonMapper {
    //查询员工信息
    @Select("select * from stuff ,role" +
            " where stuff.rnum = role.rnum" +
            " and stuff.stu_num = #{useId}")
    List<Person>getAllInfo(String useId);

    //修改员工信息
    @Update("update stuff " +
            "set name = #{name},gender = #{gender},pid = #{pid}," +
            "age = #{age},password = #{pas}" +
            " where stuff.stu_num = #{stu_num}")
    Integer modifyAllInfo(String name,String gender,String pid,String stu_num,Integer age,String pas);

}
