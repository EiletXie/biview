package com.suntak.biview.service;

import com.suntak.biview.entity.SalesOrder;

import java.util.List;
import java.util.Map;

public interface SalesOrderService {

    /**
     * 传入业务员数组，接单金额，做成两个对应数组，以符合Echart插件要求
     * @param p_year
     * @param p_month
     * @return Map<String,String[]>
     */
     Map<String,String[]> rankSalesEnigneer(String p_year,String p_month);

    /**
     * 将业务属区-接单金额键值对，做成两个对应数组，以符合Echart插件要求
     * @param p_year
     * @param p_month
     * @return Map<String,String[]>
     */
     List<Map<String,String>> rankSalesArea(String p_year,String p_month);

    /**
     * 获取接单达成率最后的3位业务员
     * @param p_year
     * @param p_month
     * @return
     */
     List<SalesOrder> getLastSalesEnigneer(String p_year, String p_month);

    /**
     * 获取业务属区达成情况
     * @param p_year
     * @param p_month
     * @return
     */
     List<SalesOrder> getCompareSalesArea(String p_year,String p_month);

    /**
     * 获取指定年月的接单总额与目标总额
     * @param p_year
     * @param p_month
     * @return
     */
     SalesOrder getCompareSaleTotal(String p_year,String p_month);
}
