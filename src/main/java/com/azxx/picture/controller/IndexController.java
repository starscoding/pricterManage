package com.azxx.picture.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @RequestMapping("/index")
    public String index(Model model) {
        return "/index";
    }
}
