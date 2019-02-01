package com.azxx.demon.controller;

import com.azxx.demon.entity.User;
import io.swagger.annotations.*;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("swagger")
@Api(tags = "测试",value = "swagger 测试")
public class SwaggerController {

    @ApiOperation(value = "获取用户信息",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path",name = "name",dataType = "string",required = true,value = "用户名",defaultValue = "hello world!")
    })
    @ApiResponses({
            @ApiResponse(code=200,message="处理成功",response = User.class)
    })
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public User getUser(String name){
        User user = new User();
        user.setUserName(name);
        return user;
    }
}
