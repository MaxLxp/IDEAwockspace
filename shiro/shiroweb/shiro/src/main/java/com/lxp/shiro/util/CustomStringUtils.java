package com.lxp.shiro.util;

import org.springframework.util.StringUtils;

public class CustomStringUtils extends StringUtils {

    public static boolean isEmpty(String str){
        if(str == null || "".equals(str)){
            return true;
        }
        return false;
    }
}
