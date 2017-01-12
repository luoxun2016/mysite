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

import com.ank.mysite.captcha.JCapchaHelper;
import com.ank.mysite.entity.User;

@Controller
public class LoginController {
	/**
	 * 显示登录页
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model) {
		return "auth/login";
	}
	
	/**
	 * 无权限显示
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/unauthorized")
	public String unauthorized(HttpServletRequest request, Model model) {
		return "auth/unauthorized";
	}
	
	/**
	 * 验证码
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/verifycode")
	public void verifyCode(HttpServletRequest request, HttpServletResponse response ){
		JCapchaHelper.generateVerifyCode(request, response);
	}
	
	/**
	 * 实际的登录代码 如果登录成功，跳转至首页；登录失败，则将失败信息反馈对用户
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
			msg = "登录密码错误.";
		} catch (ExcessiveAttemptsException e) {
			msg = "登录失败次数过多";
		} catch (LockedAccountException e) {
			msg = "帐号已被锁定.";
		} catch (DisabledAccountException e) {
			msg = "帐号已被禁用. ";
		} catch (ExpiredCredentialsException e) {
			msg = "帐号已过期.";
		} catch (UnknownAccountException e) {
			msg = "帐号不存在.";
		} catch (UnauthorizedException e) {
			msg = "您没有得到相应的授权！";
		}

		model.addAttribute("error", msg);
		
		for(Entry<String, Object> entry : model.asMap().entrySet()){
			redirectAttributes.addFlashAttribute(entry.getKey(), entry.getValue());
		}
		
		return "redirect:login";
	}
	
}
