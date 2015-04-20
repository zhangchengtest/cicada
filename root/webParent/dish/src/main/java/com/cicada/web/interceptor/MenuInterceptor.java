package com.cicada.web.interceptor;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cicada.core.model.Model;
import com.cicada.core.model.menu.Menu;
import com.cicada.core.model.menu.WebappEnum;
import com.cicada.service.api.CRUDService;
import com.cicada.web.spring.SpringContext;

public class MenuInterceptor extends HandlerInterceptorAdapter {

	private static final Logger log = LoggerFactory.getLogger(MenuInterceptor.class);

	/**
	 * key 为 url
	 */
	private static Map<String, Menu> urlMenuMap = new HashMap<String, Menu>();

	private static Map<String, String[]> urlMap = new HashMap<String, String[]>();

	/**
	 * 共享左侧栏id add edit和list共享
	 * 
	 * @param arr
	 */
	private static void assembleNormal(String... arr) {
		for (String str : arr) {
			urlMap.put(str + "/list", new String[] { str + "/add", str + "/edit", str + "/save", str + "/update"});
		}
	}

	static {
		assembleNormal(new String[] { "channel", "acquirer", "channelProvider", "global/bank", "bankAccount" });
		// TODO 添加更多共享的url
		urlMap.put("merchant/list", new String[] { "merchant/commonpage/", "merchant/cr_page", "merchant/cr_page/",
				"merchant/create/", "merchantWithDrawalConfig/withdraw/", "merchantWithDrawalConfig/withdrawUpdate",
				"merchantaccessurl/list/" });
		urlMap.put("audit/log/list", new String[] { "audit/log/select/" });
		urlMap.put("admin/userManager/list", new String[] { "admin/userManager/select/" });
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

		if (urlMenuMap.isEmpty()) {
			retriveMenus();
		}
		String url = request.getRequestURI();
		if (url.indexOf(request.getContextPath()) != -1) {
			if (url.indexOf("?") != -1) {
				url = url.substring(request.getContextPath().length() + 1, url.indexOf("?"));
			} else {
				url = url.substring(request.getContextPath().length() + 1);
			}
		}
		log.trace("the request uri is {}", url);
		for (String str : urlMenuMap.keySet()) {
			if (url.indexOf(str) != -1) {
				request.setAttribute("chosenMenuId", urlMenuMap.get(str).getID());
			}
		}
	}

	public void retriveMenus() {
		try {
			CRUDService crudService = (CRUDService)SpringContext.getBean("crudService");
			Menu filterBean = new Menu();
			filterBean.setWebapp(WebappEnum.CONSOLE);
			List<Menu> menus = crudService.retrieveBy(filterBean);
			Collections.sort(menus, new Comparator<Model>() {

				@Override
				public int compare(Model o1, Model o2) {
					Menu menu1 = (Menu) o1;
					Menu menu2 = (Menu) o2;
					if (menu1.getID().length() < menu2.getID().length()) {
						return -1;
					}

					return 0;
				}
			});
			for (Menu menu : menus) {
				if (StringUtils.isNotEmpty(menu.getLink()) && !StringUtils.equals(menu.getLink(), "#")) {
					urlMenuMap.put(menu.getLink(), menu);
					if (urlMap.containsKey(menu.getLink())) {
						String[] arr = urlMap.get(menu.getLink());
						for (String str : arr) {
							urlMenuMap.put(str, menu);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("found no data", e);
		}

	}

}
