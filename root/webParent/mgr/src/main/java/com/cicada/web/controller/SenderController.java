package com.cicada.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 用于重定向页面
 */
@Controller
public class SenderController {

	private static final Logger logger = LoggerFactory.getLogger(SenderController.class);
	
	public SenderController() {
	}

	/**
	 * to add permission role page
	 * @return
	 */
	@RequestMapping("/permission/permissionRole/toAddPermissionRole.action")
	public ModelAndView addPermissionRole() {
		return new ModelAndView("permission/permissionRole/toAddPermissionRole");
	}
	
	
	
}
