package com.wanmi.demo.sso2.controller;


import com.wanmi.demo.sso2.config.JwtProperties;
import com.wanmi.demo.sso2.pojo.UserInfo;
import com.wanmi.demo.sso2.service.AuthService;
import com.wanmi.demo.sso2.utils.CookieUtils;
import com.wanmi.demo.sso2.utils.JwtUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@EnableConfigurationProperties(JwtProperties.class) //使用配置类中的属性
public class AuthController {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private AuthService authService;

    /**
     * 当用户点击登录，将用户名和密码传到授权中心，先去查询是否存在此用户，如有，则处理返回token登录凭证，
     * 当还需要登录业务时，凭借jwt中的载荷解析出来用户信息，就可以直接登录，就相当于之前的session，
     *
     * @param userInfo
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/auth")
    public ResponseEntity<Void> login(
            @RequestBody UserInfo userInfo,
            HttpServletRequest request,
            HttpServletResponse response) {
        //获取登录凭证token，赋值cookie中
        String token = this.authService.auth(userInfo.getUsername(), userInfo.getPassword());
        if (StringUtils.isBlank(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        System.out.println(this.jwtProperties.getCookieName());
        //有此用户，将token封装到cookie中，方便后面取出使用，此Cookie中的时间是秒，所以*60 即 30分钟
        CookieUtils.setCookie(request, response, this.jwtProperties.getCookieName(), token, this.jwtProperties.getExpire() * 60);
        return ResponseEntity.ok(null);
    }
}
