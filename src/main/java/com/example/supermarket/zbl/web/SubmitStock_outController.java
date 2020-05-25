package com.example.supermarket.zbl.web;


import com.example.supermarket.ljy.domain.Stock_out;
import com.example.supermarket.zbl.service.StockService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/stockout")
public class SubmitStock_outController {
    @Resource
    StockService stockService;
    private static Logger logger = Logger.getLogger(SubmitStock_outController.class);
    @RequestMapping(value = "/subStock_out")
    public void  submitStockout(HttpServletResponse response, @RequestParam(value = "num")String num,
                                @RequestParam(value = "region")String region, @RequestParam(value = "price_out")String price,@RequestParam(value = "cnum")String cnum,
                                @RequestParam(value = "sum")String sum, HttpSession session)
            throws IOException {
        session.setAttribute("name", "小苏");
        session.setAttribute("stu_num", "202000001");
        //生成出库日期
        long time = System.currentTimeMillis();
        Date outdate = new Date(time);
        //拿到经手人姓名
        String name = session.getAttribute("name").toString();
        //拿到经手人账号
        String stu_num = session.getAttribute("stu_num").toString();
        Stock_out stock_out = new Stock_out(num, cnum, outdate, Integer.parseInt(sum), stu_num, region, name, stockService.queryName(cnum));


        response.setContentType("text/json;charset=utf-8");
        //查询商品库存数量
        System.out.println(stock_out.getCnum());
        Integer sums = stockService.querySum(stock_out.getCnum());

        List<String> cnums = stockService.queryCnums();

        //判断出库单号是否重复
//        System.out.println(Integer.parseInt(sum));
            System.out.println(sums);
        System.out.println(stock_out.toString());

                Integer content = stockService.insertStockOut(stock_out.getNum(), stock_out.getCnum(), stock_out.getOutdate(), stock_out.getSum(), stu_num,region,stock_out.getName(),stock_out.getCname());
                System.out.println(content);
                if (content == 0) {
                    response.getWriter().write("-1");
                } else {
                    stockService.updateSum(stock_out.getCnum(), sums - stock_out.getSum());
                    //如果架上商品没有该出库商品，则架上新增商品，有则更新架上商品数量和售价
                    if (stockService.queryShelfcnum(cnum) == null) {
                        String cname = stockService.queryName(cnum);
                        Date p_date = stockService.queryPdate(cnum);
                        String safe_date = stockService.querySafedate(cnum);
                        //上架新商品
                        stockService.addCommodity(cnum, cname, region, p_date, safe_date, Integer.parseInt(price), Integer.parseInt(sum));
                    } else {
                        //更新架上商品数量和价格
                        stockService.updateCom(stock_out.getSum(), stock_out.getCnum(), Integer.parseInt(price));
                    }

                    response.getWriter().write("1");
                }



        }




    //拿到商品号
    @RequestMapping(value = "/getCnum")
    public void  getCnum(HttpServletResponse response)throws IOException{
        String content = stockService.queryCnum();
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(content == null ?"0":content);
    }
    //拿到出库单数据数量
    @RequestMapping(value = "/getRows")
    public void getRows(HttpServletResponse response,HttpSession session)throws IOException{
        //获得账号
        String stu_num = session.getAttribute("stu_num").toString();

        //获得角色号
        String rnum = stockService.queryRnum(stu_num);
        //定义数据量
        Integer content = 0;
        //如果是总经理或副总经理,则拿到所有数据
        if ("01".equals(rnum)||"02".equals(rnum)){
            content = stockService.queryStockoutRows();
            //如果是仓库管理员，则拿到自己经手的出库单数据
        }else {
            content = stockService.queryStockoutRowsByStu(stu_num);
        }
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(content == 0 ? 0 :content);
    }

    @RequestMapping(value = "stock")
    public void queryStock(HttpServletResponse response,HttpSession session)throws IOException{

//        session.setAttribute("name","小苏");
        String content = stockService.queryStock();
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(content);

    }
    @RequestMapping(value = "stockInfo")
    public void queryStockByCnum(HttpServletResponse response, HttpSession session
                                    , @RequestParam(value = "cnum")String cnum)throws IOException{

//        session.setAttribute("name","小苏");
        String content = stockService.queryStockByCnum(cnum);
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(content);

    }


}
