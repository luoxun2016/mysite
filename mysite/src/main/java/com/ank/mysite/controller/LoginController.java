package com.ank.mysite.controller;

import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ank.mysite.annotation.Syslog;
import com.ank.mysite.captcha.JCapchaHelper;
import com.ank.mysite.entity.User;

@Controller
public class LoginController {
	/**
	 * ��ʾ��¼ҳ
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	@Syslog(module="ģ��", method="����" , description="����")
	public String login(HttpServletRequest request, Model model) {
		return "login";
	}
	
	/**
	 * ��Ȩ����ʾ
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/unauthorized")
	public String unauthorized(HttpServletRequest request, Model model) {
		return "unauthorized";
	}
	
	/**
	 * ��֤��
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/verifycode")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response ){
		JCapchaHelper.generateVerifyCode(request, response);
	}
	
	/**
	 * ʵ�ʵĵ�¼���� �����¼�ɹ�����ת����ҳ����¼ʧ�ܣ���ʧ����Ϣ�������û�
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/dologin")
	public String dologin( @ModelAttribute("user") @Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
		if(bindingResult.hasErrors()){
			for(Entry<String, Object> entry : model.asMap().entrySet()){
				redirectAttributes.addFlashAttribute(entry.getKey(), entry.getValue());
			}
			return "redirect:login";
		}
		
		String msg = "";
		String username = user.getUsername();
		String password = user.getPassword();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		token.setRememberMe(true);
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			if (subject.isAuthenticated()) {
				return "redirect:/";
			} else {
				return "redirect:login";
			}
		} catch (IncorrectCredentialsException e) {
			msg = "��¼�������. Password for account " + token.getPrincipal() + " was incorrect.";
		} catch (ExcessiveAttemptsException e) {
			msg = "��¼ʧ�ܴ�������";
		} catch (LockedAccountException e) {
			msg = "�ʺ��ѱ�����. The account for username " + token.getPrincipal() + " was locked.";
		} catch (DisabledAccountException e) {
			msg = "�ʺ��ѱ�����. The account for username " + token.getPrincipal() + " was disabled.";
		} catch (ExpiredCredentialsException e) {
			msg = "�ʺ��ѹ���. the account for username " + token.getPrincipal() + "  was expired.";
		} catch (UnknownAccountException e) {
			msg = "�ʺŲ�����. There is no user with username of " + token.getPrincipal();
		} catch (UnauthorizedException e) {
			msg = "��û�еõ���Ӧ����Ȩ��" + e.getMessage();
		}

		model.addAttribute("message", msg);
		for(Entry<String, Object> entry : model.asMap().entrySet()){
			redirectAttributes.addFlashAttribute(entry.getKey(), entry.getValue());
		}
		
		return "redirect:login";
	}
	
}
