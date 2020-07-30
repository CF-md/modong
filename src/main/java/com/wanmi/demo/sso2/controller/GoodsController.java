package com.wanmi.demo.sso2.controller;

import com.wanmi.demo.sso2.interceptor.LoginInterceptor;
import com.wanmi.demo.sso2.pojo.UserInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("goods")
public class GoodsController {


    @PostMapping
    public ResponseEntity<Boolean> createGoods(@RequestParam String goodsName) {
        UserInfo loginUser = LoginInterceptor.getLoginUser();
        if (null != loginUser) {
            return new ResponseEntity<>(true,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
