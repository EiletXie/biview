package com.suntak.biview.controller;

import com.suntak.biview.entity.Msg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/salesView")
public class SalesViewController {

    @GetMapping("/test")
    @ResponseBody
    public Msg rankSalesman(){
        int [] nums = {30,50,45,90,23};
        String[] names = {"alice","tom","bob","eilet","jack"};
        return Msg.success().add("nums",nums).add("names",names);
    }
}
