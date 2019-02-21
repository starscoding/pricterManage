package com.azxx.picture.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @version V1.0
 * @class: IndexController
 * @description:
 * @author: xuzheng
 * @create: 2019-02-03 15:43
 **/

@Controller
public class IndexController {
    private static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String index(Model model) {
        return "/index";
    }

    @RequestMapping(path = "/fileManage/index",method = RequestMethod.GET)
    public String fileManage(){
        return  "/fileManage/fileManage";
    }

    @RequestMapping(path = "/groupManage/index",method = RequestMethod.GET)
    public String groupManage(){
        return  "/groupManage/groupManage";
    }

    @RequestMapping(path = "/welcome/index",method = RequestMethod.GET)
    public String welcome(){
        return  "/welcome/welcome";
    }

    @RequestMapping(path = "/main",method = RequestMethod.GET)
    public String main(){
        return  "/main";
    }
}
