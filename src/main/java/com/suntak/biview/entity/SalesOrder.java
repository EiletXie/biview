package com.suntak.biview.entity;

public class SalesOrder {
    private String object_name;
    private String fact_amount; // 实际接单金额
    private String target_amount; //目标接单金额
    private String percentage;  // 百分比


    public String getObject_name() {
        return object_name;
    }

    public void setObject_name(String object_name) {
        this.object_name = object_name;
    }

    public String getFact_amount() {
        return fact_amount;
    }

    public void setFact_amount(String fact_amount) {
        this.fact_amount = fact_amount;
    }

    public String getTarget_amount() {
        return target_amount;
    }

    public void setTarget_amount(String target_amount) {
        this.target_amount = target_amount;
    }

    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
