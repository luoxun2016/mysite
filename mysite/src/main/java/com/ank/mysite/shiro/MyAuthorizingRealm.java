package com.ank.mysite.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.ank.mysite.entity.Permission;
import com.ank.mysite.entity.Role;
import com.ank.mysite.entity.User;
import com.ank.mysite.service.UserService;

public class MyAuthorizingRealm extends AuthorizingRealm {

	@Autowired
	private UserService userService;

	/**
	 * 权限认证，获取登录用户的权限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();
		// 此处连库匹配了登录用户的数据，具体怎么做，需要根据个人需求而定
		User user = userService.findByName(loginName);
		
		Set<String> roles = new HashSet<String>();
		Set<String> permissions = new HashSet<String>();
		
		if (user != null) {
			// 获取用户的角色名称
			List<Role> roleList = user.getRoleList();
			if(roleList != null && !roleList.isEmpty()){
				for(Role role : roleList){
					roles.add(role.getRoleName());
					
					// 获取用户的权限
					List<Permission> pList = role.getPermissionList();
					if(pList != null && !pList.isEmpty()){
						for(Permission p : pList){
							permissions.add(p.getPermissionName());
						}
					}
				}
			}
			
			SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
			simpleAuthorInfo.addRoles(roles);
			simpleAuthorInfo.addStringPermissions(permissions);
			
			return simpleAuthorInfo;
		}
		
		//若该方法什么都不做直接返回null的话,就会导致任何用户访问时都会自动跳转到unauthorizedUrl指定的地址
		return null;
	}

	/**
	 * 登录认证，创建用户的登录信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// 判断用户登录状态
		User user = userService.findByName(token.getUsername());
		if (user != null) {
			// 保存用户登录信息到认证中
			return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
		}
		return null;
	}
	
}