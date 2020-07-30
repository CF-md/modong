package com.wanmi.demo.sso1.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author caofang
 * @date 2020/7/28 21:51
 * @Description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String uName;
    private String uPwd;
}