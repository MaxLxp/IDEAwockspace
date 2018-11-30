package com.lxp.shiro.service;


import java.util.List;

public interface UserService {
    /**
     * 获取用户密码
     *
     * @param userName 用户名
     * @return
     */
    String getPassWordByUserName(String userName);

    /**
     * 获取用户权限
     *
     * @param userName 用户名
     * @return
     */
    List<String> getPermissionsByUserName(String userName);

    /**
     * 获取用户角色
     *
     * @param userName 用户名
     * @return
     */
    List<String> getRolesByUserName(String userName);
}
