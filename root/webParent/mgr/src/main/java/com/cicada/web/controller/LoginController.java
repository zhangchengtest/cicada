package com.cicada.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cicada.core.model.user.User;
import com.cicada.service.api.user.UserService;
import com.cicada.service.bean.UserLoginRequest;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.service.exception.ServiceException;
import com.cicada.utils.StringUtils;
import com.cicada.web.constant.WebConstant;
/**
 * 后台管理员登录
 * @author zhangcheng
 *
 */
@Controller
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("userLoginRequest") @Validated UserLoginRequest userLoginRequest, BindingResult errors) {

		Map<String, Object> result = new HashMap<String, Object>();
		boolean isSuccess = false;
		// userLoginRequest.setPassword("");
		// request.setAttribute("userLoginRequest",userLoginRequest);
		try {
			if (errors.hasErrors()) {
				return new ModelAndView("user/login");
			}
			String validateCodeExist = (String) request.getSession().getAttribute("validateCode");
			request.getSession().removeAttribute("validateCode");
			if (StringUtils.isNotEmpty(validateCodeExist)) {
				String validateCode = userLoginRequest.getValidateCode();
				if (!StringUtils.equalsIgnoreCase(validateCode, validateCodeExist)) {
					errors.rejectValue("validateCode", "validate.code.wrong");
					return new ModelAndView("user/login");
				}
			}

		

			User userContext = userService.login(userLoginRequest);
			request.getSession().setAttribute(WebConstant.Session.USER_CONTEXT, userContext);
			result.put("isSuccess", true);
			isSuccess = true;
		} catch (ServiceException | RuntimeServiceException e) {
			logger.error("the user {} login fail: {}", userLoginRequest.getUsername(), e);
			errors.reject("user.name.or.password.wrong");
		}
		if (!isSuccess) {
			return new ModelAndView("user/login");
		}

		return new ModelAndView("redirect:/");
	}

	@RequestMapping("tologin")
	public ModelAndView tologin(){

		return new ModelAndView("user/login","userLoginRequest", new UserLoginRequest());
	}
}
