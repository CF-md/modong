package com.wanmi.demo.controller;

import com.wanmi.demo.util.LoginCacheUtil;
import com.wanmi.demo.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author caofang
 * @date 2020/7/28 21:54
 * @Description
 */
@Controller
public class LoginController {


    @PostMapping("/login")
    public String doLogin(User user, HttpSession session, HttpServletResponse response) {
        // 校验用户名密码
        if("cao".equals(user.getUName()) && "123".equals(user.getUPwd())) {
            // 保存用户登录信息
            String token = UUID.randomUUID().toString();
            System.out.println("login.token===" + token);
            Cookie cookie = new Cookie("TOKEN", token);
            //设置域名，实现数据跨域共享
            cookie.setDomain("sso.com");
            // 把cookie写到客户端
            response.addCookie(cookie);
            //模拟将用户信息存入Redis
            LoginCacheUtil.loginUser.put(token, user);
        }else {
            session.setAttribute("msg", "用户名或密码错误");
            return "login";
        }
        //登录信息校验成功，重定向到原来的系统
        String targetUrl = (String) session.getAttribute("target");
        //如果是直接从登录系统登录的，校验成功后默认跳转到主系统 sys1 的首页
        if(StringUtils.isEmpty(targetUrl)) {
            targetUrl = "http://sys1.sso.com:8081/index";
        }
        return "redirect:" + targetUrl;
    }

    /**
     * 其他同域名的系统通过该接口获取登录用户的信息
     * @param token
     * @return
     */
    @RequestMapping("/info")
    @ResponseBody
    public ResponseEntity<User> getUserInfo(String token){
        if(!StringUtils.isEmpty(token)) {
            User user = LoginCacheUtil.loginUser.get(token);
            return ResponseEntity.ok(user);
        }else {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
