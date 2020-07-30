package com.wanmi.demo.sso2.service;
import com.wanmi.demo.sso2.config.JwtProperties;
import com.wanmi.demo.sso2.pojo.UserInfo;
import com.wanmi.demo.sso2.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    @Autowired
    private JwtProperties jwtProperties;

    public String auth(String username, String password) {
        try {
            // 2.判断user
            if ("cao".equals(username) && "123".equals(password)) {
                // 3.jwtUtils生成jwt类型的token,userInfo是载荷，即为了安全仅携带用户id与用户名
                UserInfo userInfo = new UserInfo();
                userInfo.setId(1L);
                userInfo.setUsername(username);
                return   JwtUtils.generateToken(userInfo, this.jwtProperties.getPrivateKey(), this.jwtProperties.getExpire());//单位分钟
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
