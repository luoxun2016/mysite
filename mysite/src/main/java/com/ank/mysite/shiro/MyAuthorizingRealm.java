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
	 * Ȩ����֤����ȡ��¼�û���Ȩ��
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();
		// �˴�����ƥ���˵�¼�û������ݣ�������ô������Ҫ���ݸ����������
		User user = userService.findByName(loginName);
		
		Set<String> roles = new HashSet<String>();
		Set<String> permissions = new HashSet<String>();
		
		if (user != null) {
			// ��ȡ�û��Ľ�ɫ����
			List<Role> roleList = user.getRoleList();
			if(roleList != null && !roleList.isEmpty()){
				for(Role role : roleList){
					roles.add(role.getRoleName());
					
					// ��ȡ�û���Ȩ��
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
		
		//���÷���ʲô������ֱ�ӷ���null�Ļ�,�ͻᵼ���κ��û�����ʱ�����Զ���ת��unauthorizedUrlָ���ĵ�ַ
		return null;
	}

	/**
	 * ��¼��֤�������û��ĵ�¼��Ϣ
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		// �ж��û���¼״̬
		User user = userService.findByName(token.getUsername());
		if (user != null) {
			// �����û���¼��Ϣ����֤��
			return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), getName());
		}
		return null;
	}
	
}