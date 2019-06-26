package com.suntak.biview.service.impl;

import com.suntak.biview.dao.SalesOrderDao;
import com.suntak.biview.entity.SalesOrder;
import com.suntak.biview.service.SalesOrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    @Resource
    SalesOrderDao salesOrderDao;

    @Override
    public Map<String,String[]> rankSalesEnigneer(String p_year,String p_month){
        List<Map<String,String>> list =  salesOrderDao.rankSalesEnigneer(p_year,p_month);
        ArrayList<String> usernameList = new ArrayList<>();
        ArrayList<String> totalList = new ArrayList<>();

        for ( Map<String,String> map: list) {
            usernameList.add(map.get("objectname"));
            totalList.add(map.get("total"));
        }

        String[] usernames = new String[usernameList.size()];
        String[] totals = new String[totalList.size()];
        for (int i = 0; i < usernameList.size(); i++) {
            usernames[i] = usernameList.get(i);
            totals[i] = String.valueOf(totalList.get(i));
        }
        Map<String, String[]> saledata = new HashMap<>();
        saledata.put("usernameList",usernames);
        saledata.put("totalList",totals);
        return saledata;
    }

    @Override
    public  List<Map<String,String>> rankSalesArea(String p_year,String p_month){
        List<Map<String,String>> list =  salesOrderDao.rankSalesArea(p_year,p_month);
        return list;
    }

    @Override
    public List<SalesOrder> getLastSalesEnigneer(String p_year, String p_month) {
        // 获取指定月份所有的业务员接单金额统计
        List<Map<String,String>> enigneerList =  salesOrderDao.rankAllSalesEnigneer(p_year,p_month);
        ArrayList<SalesOrder> saleList = new ArrayList<>();
        for ( Map<String,String> map: enigneerList) {
            SalesOrder sobj = new SalesOrder();
            sobj.setObject_name(map.get("objectname"));
            sobj.setFact_amount(String.valueOf(map.get("total")));
            sobj.setTarget_amount("无");
            sobj.setPercentage("无");
            saleList.add(sobj);
        }

        // 将年月进行处理， 让 p_date 为 "2019-06"这种格式
        p_month = Integer.valueOf(p_month) < 10 ? ('0'+p_month) : p_month;
        String p_date = p_year + "-" + p_month;
        // 获取指定月份 业务员  目标 接单金额统计
        List<Map<String,String>> enigneerTargetList =  salesOrderDao.allTargetSalesEnigneer(p_date);
        // 数据处理过滤后的业务员列表 dealSaleList
        ArrayList<SalesOrder> dealSaleList = new ArrayList<>();


       // ArrayList<SalesOrder> grepLastList = new ArrayList<>();
        int [] lastList = new int[3];
        int num = 0;
         for (Map<String,String> map: enigneerTargetList) {
            String username = map.get("objectname");
            String target_amount = String.valueOf(map.get("total"));

            for (SalesOrder sale : saleList) {
                if(username != null && username.equals(sale.getObject_name())){
                    sale.setTarget_amount(target_amount);
                    int percent = (int)((Float.valueOf(sale.getFact_amount())
                            / Float.valueOf(target_amount)) * 10000);
                    sale.setPercentage(percent+"");
                    // 这里使用最小堆的思想进行 业务员中达成率最后几名

                    if(num < 3)
                        lastList[num] = percent;
                    //进行排序处理
                    else if( num == 3)
                    Arrays.sort(lastList);
                   // 当它比最小堆的堆顶元素还小时，替换并将堆进行重排序
                    else if(num >= 3 && lastList[2] > percent){
                        lastList[2] = percent;
                        Arrays.sort(lastList);
                    }
                   num++;


                    //String percentage = (percent + "").substring(0,4) + '%';
                    sale.setPercentage(percent+"");
                    dealSaleList.add(sale);
                    //saleList.remove(sale);
                }
            }
        }


        List<SalesOrder> grepList = new ArrayList<>();
        for (SalesOrder salesOrder: dealSaleList) {
            boolean flag = true;
            int percent = Integer.valueOf(salesOrder.getPercentage());  //.substring(0,5) + '%';
            for (int i = 0; i < lastList.length ; i++){
                if(percent == lastList[i])
                    flag = false;
            }
            if(!flag) {
                // dealSaleList.remove(salesOrder);
                //else
                salesOrder.setPercentage((percent / 100.0) + "%");
                grepList.add(salesOrder);
            }
        }
        return grepList;
    }

    @Override
    public List<SalesOrder> getCompareSalesArea(String p_year, String p_month) {

        // 获取指定月份所有的业务属区接单金额统计
        List<Map<String,String>> salesAreaList =  salesOrderDao.rankSalesArea(p_year,p_month);
        ArrayList<SalesOrder> areaList = new ArrayList<>();
        for ( Map<String,String> map: salesAreaList) {
            SalesOrder area = new SalesOrder();
            area.setObject_name(map.get("objectname"));
            area.setFact_amount(String.valueOf(map.get("total")));
            area.setTarget_amount("无");
            area.setPercentage("无");
            areaList.add(area);
        }

        // 将年月进行处理， 让 p_date 为 "2019-06"这种格式
        p_month = Integer.valueOf(p_month) < 10 ? ('0'+p_month) : p_month;
        String p_date = p_year + "-" + p_month;
        // 获取指定月份 业务员  目标 接单金额统计
        List<Map<String,String>> areaTargetList =  salesOrderDao.allTargetSalesArea(p_date);
        // 数据处理过滤后的业务员列表 dealAreaList
        ArrayList<SalesOrder> dealAreaList = new ArrayList<>();

        for (Map<String,String> map: areaTargetList) {
            String areaname = map.get("objectname");
            String target_amount = String.valueOf(map.get("total"));

            for (SalesOrder area : areaList) {
                if (areaname != null && areaname.equals(area.getObject_name())) {
                    area.setTarget_amount(target_amount);
                    String percentage = ((Float.valueOf(area.getFact_amount())
                            / Float.valueOf(target_amount)) * 100 + "").substring(0, 5) + '%';
                    area.setPercentage(percentage);
                    dealAreaList.add(area);
                    //areaList.remove(area);
                }
            }
        }
        return dealAreaList;
    }

    @Override
    public SalesOrder getCompareSaleTotal(String p_year, String p_month) {

        SalesOrder order = new SalesOrder();
        order.setObject_name("接单总额");
        float fact_amount = salesOrderDao.getSalesTotal(p_year,p_month);
        // 将年月进行处理， 让 p_date 为 "2019-06"这种格式
        p_month = Integer.valueOf(p_month) < 10 ? ('0'+p_month) : p_month;
        String p_date = p_year + "-" + p_month;
        float target_amount = salesOrderDao.getTargetSalesTotal(p_date);
        order.setFact_amount(Math.ceil(fact_amount)+"");
        order.setTarget_amount(Math.ceil(target_amount)+"");

        String percentage = ((fact_amount / target_amount) * 100 + "").substring(0,4) + '%';
        order.setPercentage(percentage);
        return order;
    }
}
