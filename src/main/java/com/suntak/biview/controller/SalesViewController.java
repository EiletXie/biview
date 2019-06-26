package com.suntak.biview.controller;

import com.suntak.biview.entity.Msg;
import com.suntak.biview.entity.SalesOrder;
import com.suntak.biview.service.SalesOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/salesView")
public class SalesViewController {

    @Resource
    SalesOrderService salesOrderService;

    @GetMapping("/test")
    @ResponseBody
    public Msg rankSalesman(){


        // 目前接单数据
        List<Map<String,String>> areadata = salesOrderService.rankSalesArea("2019","6");
        Map<String,String[]> saledata = salesOrderService.rankSalesEnigneer("2019","6");

        // 计划接单数据（对比处理）
        List<SalesOrder> lastSalesList = salesOrderService.getLastSalesEnigneer("2019","6");
        List<SalesOrder> areaTargetList = salesOrderService.getCompareSalesArea("2019","6");

        // 接单总额与目标总额
        SalesOrder totalSales = salesOrderService.getCompareSaleTotal("2019","6");

        return Msg.success().add("areadata",areadata).add("saledata",saledata)
                .add("lastSalesList",lastSalesList).add("areaTargetList",areaTargetList).add("totalSales",totalSales);
    }
}
