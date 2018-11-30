package com.lxp.shiro.proxy.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CustomShiroRolesFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object object) throws Exception {

        Subject subject = getSubject(request,response);
        String[] roles = (String[]) object;
        if (roles == null || roles.length == 0){
            return true;
        }
        for (String role : roles){
            if(subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
