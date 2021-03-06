package com.example.supermarket.zbl.mapper;

import com.example.supermarket.zbl.domain.Stock;
import org.apache.ibatis.annotations.*;


import java.sql.Date;
import java.util.List;

@Mapper
public interface StockMapper {
    //插入出库单
    @Insert("insert into stock_out(num,cnum,outdate,sum,stu_num,region,name,cname)" +
            "values (#{num},#{cnum},#{outdate}," +
            "#{sum},#{stu_num},#{region},#{name},#{cname})")
    Integer insertStockOut(String num, String cnum,  Date outdate, Integer sum, String stu_num,String region,
                            String name,String cname);

    //查询商品号
    @Select("select cnum from stock_in")
    List<String> queryCnum();

    //查询库存表商品余额
    @Select("select count from stock where cnum = #{cnum}")
    Integer querySum(String cnum);

    //查询出库单号
    @Select("select num from stock_out where num = #{num}")
    String queryNum(String num);

    //修改库存表商品数量
    @Update("update stock set count = #{sum} where cnum = #{cnum}")
    Integer updateSum(String cnum,Integer sum);

    //修改架上商品数量
    @Update("update shelf set count = count + #{sum} , price_out = #{price} where cnum = #{cnum}")
    Integer updateCom(Integer sum,String cnum,Integer price);
    //新增架上商品
    @Insert("insert into shelf(cnum,name,region,p_date,safe_date,price_out,count) " +
            "values(#{cnum},#{name},#{region},#{p_date},#{safe_date},#{price_out},#{count})")
    Integer addCommodity(String cnum,String name,String region,Date p_date,String safe_date,Integer price_out,Integer count);
    //查询商品进价
    @Select("select price from stock_in where num = #{num}")
    Integer queryPrice(String num);
    //查询架上商品商品号
    @Select("select cnum from shelf where cnum = #{cnum}")
    String queryShelfcnum(String cnum);
    //查询商品名
    @Select("select name from stock where cnum = #{cnum}")
    String queryName(String cnum);
    //查询生产日期
    @Select("select p_date from stock where  cnum = #{cnum}")
    Date queryPdate(String cnum);
    //查询保质期
    @Select("select safe_date from provider_commodity where cnum = #{cnum}")
    String querySafedate(String cnum);
    //查看出库单数量
    @Select("select count(*) from stock_out")
    Integer queryStockoutRows();
    //仓库管理员查询其出库单数量
    @Select("select count(*) from stock_out where stu_num = #{stu_num}")
    Integer queryStockoutRowsByStu(String stu_num);
    //查询角色号
    @Select("select rid from stuff_role where  sid = #{stu_num}")
    Integer queryRnum(String stu_num);
    //查询库存
    @Select("select * from stock")
    List<Stock> queryStock();
    //查询库存详情根据商品号
    @Select("select * from stock where cnum = #{cnum}")
    List<Stock> queryStockByCnum(String cnum);
    //向用户分析表插入数据
    @Insert("insert into userBehavior values(#{stu_num},#{url},#{qdate})")
    Integer insertData(String stu_num, String url, Date qdate);

}
