package com.example.supermarket.ljy.domain

import lombok.Data

import java.sql.Date

/**
 * @author jleo
 * @date 2020/4/30
 */
@Data
class Stock_out {
    String num
    String cnum
    Date outdate
    Integer sum
    String stu_num
}
