package com.wanmi.demo.util;

import com.wanmi.demo.entity.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author caofang
 * @date 2020/7/28 21:55
 * @Description
 */
public class LoginCacheUtil {

    //模拟系统的数据缓存，如 Redis 之类的
    public static Map<String, User> loginUser = new HashMap<>();
}
