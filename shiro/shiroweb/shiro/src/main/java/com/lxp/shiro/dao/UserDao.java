package com.lxp.shiro.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserDao {

    /**
     * 查询用户角色
     * @param userName
     * @return
     */
  List<String> qryRolesByUserName(@Param("userName") String userName);

    /**
     * 查询用户密码
     * @param userName
     * @return
     */
    String qryPassWordByUserName(@Param("userName") String userName);

    /**
     * 查询用户角色权限
     * @param userName
     * @return
     */
    List<String> qryPermissionsByUserName(String userName);
}
