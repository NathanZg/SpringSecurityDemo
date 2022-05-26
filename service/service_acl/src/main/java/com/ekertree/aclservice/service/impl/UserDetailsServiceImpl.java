package com.ekertree.aclservice.service.impl;

import com.ekertree.aclservice.entity.User;
import com.ekertree.aclservice.service.PermissionService;
import com.ekertree.aclservice.service.UserService;
import com.ekertree.security.pojo.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: UserDetailServiceImpl
 * Description:
 * date: 2022/5/25 21:46
 *
 * @author Ekertree
 * @since JDK 1.8
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //根据用户名查询数据
        User user = userService.selectByUsername(username);
        //判断
        if(user == null){
            throw new UsernameNotFoundException("用户不存在！");
        }
        com.ekertree.security.pojo.User curUser = new com.ekertree.security.pojo.User();
        BeanUtils.copyProperties(user, curUser);

        //根据用户信息查询权限列表
        List<String> permissionValueList = permissionService.selectPermissionValueByUserId(user.getId());
        SecurityUser securityUser = new SecurityUser();
        securityUser.setCurrentUserInfo(curUser);
        securityUser.setPermissionValueList(permissionValueList);
        return securityUser;
    }
}
