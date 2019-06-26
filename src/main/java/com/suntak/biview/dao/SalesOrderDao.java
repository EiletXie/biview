package com.suntak.biview.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SalesOrderDao {

    /**
     * 获取业绩前30位的业务员排名和接单金额
     * @return
     */
    List<Map<String,String>> rankSalesEnigneer(@Param("p_year") String p_year,@Param("p_month") String p_month);

    /**
     * 获取业务属区的名称和接单金额
     * @return
     */
    List<Map<String,String>> rankSalesArea(@Param("p_year") String p_year,@Param("p_month") String p_month);


    /**
     * 获取目前所有的业务员排名和接单金额
     * @return
     */
    List<Map<String,String>> rankAllSalesEnigneer(@Param("p_year") String p_year,@Param("p_month") String p_month);

    /**
     * 获取所有的业务员指定月份目标接单金额
     * @return
     */
    List<Map<String,String>> allTargetSalesEnigneer(String p_date);

    /**
     * 获取业务属区的名称和指定月份目标接单金额
     * @return
     */
    List<Map<String,String>> allTargetSalesArea(String p_date);

    /**
     * 获取指定年月的接单总额
     * @param p_year
     * @param p_month
     * @return
     */
    float getSalesTotal(@Param("p_year") String p_year,@Param("p_month") String p_month);

    /**
     * 获取指定年月的接单总额
     * @param p_date
     * @return
     */
    float getTargetSalesTotal(String p_date);
}
