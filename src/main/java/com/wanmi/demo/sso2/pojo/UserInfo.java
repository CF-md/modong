package com.wanmi.demo.sso2.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 载荷对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private Long id;

    private String username;

    private String password;

    public UserInfo(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}