package com.cicada.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cicada.web.constant.WebConstant;

/**
 * 管理员登出
 * 
 * @author zhangcheng
 *
 */
@Controller
public class LogoutController {

	/**
	 * 删除session中缓存的值userContext,sideBar..
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().removeAttribute(WebConstant.Session.USER_CONTEXT);
		request.getSession().removeAttribute(WebConstant.Session.SIDE_BAR);
		return new ModelAndView("redirect:/tologin");
	}

}
