package com.cicada.web.controller.dish;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cicada.core.constant.MessageEnum;
import com.cicada.core.model.dish.Dish;
import com.cicada.service.api.dish.DishService;
import com.cicada.web.vo.BaseResult;
import com.cicada.web.vo.VO;

@Controller
@RequestMapping("dish")
public class DishController {

	private static final Logger logger = LoggerFactory.getLogger(DishController.class);

	@Autowired
	private DishService dishService;

	@RequestMapping("view")
	public ModelAndView dish() {
		return new ModelAndView("dish/view");
	}

	@ResponseBody
	@RequestMapping("randomSearch")
	public VO randomSearch(HttpServletRequest request) {
		try {
			Dish dish = null;
			List<String> list = new ArrayList<String>();
			dish = dishService.randomSearch(list);
			return new BaseResult(dish);
		} catch (Exception e) {
			logger.error("search dish error", e);
			return new BaseResult(request, MessageEnum.SYSTEM_IS_BUSY, false);
		}
	}
}
