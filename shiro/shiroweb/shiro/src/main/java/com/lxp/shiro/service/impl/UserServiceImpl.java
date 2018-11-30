package com.lxp.shiro.service.impl;

import com.lxp.shiro.dao.UserDao;
import com.lxp.shiro.service.UserService;
import com.lxp.shiro.util.CustomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserDao userDao;

    /**
     * 获取用户密码
     *
     * @param userName 用户名
     * @return
     */
    @Override
    public String getPassWordByUserName(String userName) {
        String password = userDao.qryPassWordByUserName(userName);
        if (CustomStringUtils.isEmpty(password)){
            return null;
        }
        return password;
    }

    /**
     * 获取用户权限
     *
     * @param userName 用户名
     * @return
     */
    @Override
    public List<String> getPermissionsByUserName(String userName) {
        List<String> permissions = userDao.qryPermissionsByUserName(userName);
        return permissions;
    }

    /**
     * 获取用户角色
     *
     * @param userName 用户名
     * @return
     */
    @Override
    public List<String> getRolesByUserName(String userName) {
        List<String> roles = userDao.qryRolesByUserName(userName);
        return roles;
    }
}
