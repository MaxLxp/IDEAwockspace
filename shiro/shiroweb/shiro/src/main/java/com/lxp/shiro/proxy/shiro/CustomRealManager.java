package com.lxp.shiro.proxy.shiro;

import com.lxp.shiro.service.UserService;
import com.lxp.shiro.util.CustomStringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomRealManager extends AuthorizingRealm{

    @Resource
    private UserService userService;

    /**
     * 授权认证
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String userName = (String) principals.getPrimaryPrincipal();
        Set<String> roles  = getRolesByUserName(userName);
        Set<String> permissions = getPermissionsByUserName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);
        return simpleAuthorizationInfo;
    }

    /**
     * 登录认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String) token.getPrincipal();
        String passWord = getPassWordByUserName(userName);
        if(CustomStringUtils.isEmpty(passWord)){
            return null;
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(userName,passWord,"realm");
        return simpleAuthenticationInfo;
    }

    private String getPassWordByUserName(String userName) {
        String passWord = userService.getPassWordByUserName(userName);
        return passWord;
    }

    private Set<String> getPermissionsByUserName(String userName) {
        List<String> permissionsList = userService.getPermissionsByUserName(userName);
        Set<String> permissionsSet = new HashSet<>(permissionsList);
        return permissionsSet;
    }

    private Set<String> getRolesByUserName(String userName) {
        List<String> rolesList  = userService.getRolesByUserName(userName);
        Set<String> rolesSet = new HashSet<>(rolesList);
        return rolesSet;
    }

}
