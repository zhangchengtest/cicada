package com.cicada.web.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cicada.core.model.dish.Dish;
import com.cicada.service.api.CRUDService;
import com.cicada.service.exception.RuntimeServiceException;
import com.cicada.service.exception.ServiceException;
import com.cicada.web.vo.TreeData;
import com.cicada.web.vo.TreeDataState;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private CRUDService crudService;

	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		String lang = request.getParameter("lang");
		String[] allLangs = new String[] { "en", "zh" };
		if (StringUtils.isNotEmpty(lang) && Arrays.asList(allLangs).contains(lang)) {
			return new ModelAndView("index", "cacheUpdate", 1);
		}
		return new ModelAndView("index");
	}
	
	@ResponseBody
	@RequestMapping("testService")
	public String testService(HttpServletRequest request, HttpServletResponse response) {
		try {
			crudService.retrieve(Dish.class, "123");
			return "yes";
		} catch (RuntimeServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "no";
	}

	@RequestMapping("tree")
	public ModelAndView tree(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		// System.out.println(request.getSession().get);
		return new ModelAndView("test/tree");
	}

	@RequestMapping("dynamicTree")
	public ModelAndView dynamicTree(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		// System.out.println(request.getSession().get);
		return new ModelAndView("test/dynamicTree");
	}

	@ResponseBody
	@RequestMapping("treeData")
	public Object[] treeData(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		TreeData treeData = new TreeData("01", "通道", "", null, new TreeData[] {
				new TreeData("0101", "通道", "glyphicon glyphicon-leaf", new TreeDataState(true), null),
				new TreeData("0102", "通道", "glyphicon glyphicon-leaf", new TreeDataState(true), null) });

		return new Object[] { treeData };
	}

	@RequestMapping("left")
	public ModelAndView left(HttpServletRequest request, HttpServletResponse response) {

		return new ModelAndView("left");
	}
}