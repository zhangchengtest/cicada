/**
 * 
 */
package com.cicada.web.velocity;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;
import org.apache.velocity.tools.view.ViewToolContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cicada.core.model.Model;
import com.cicada.core.model.menu.Menu;
import com.cicada.core.model.menu.WebappEnum;
import com.cicada.service.api.CRUDService;
import com.cicada.web.spring.SpringContext;

/**
 * @author shawn
 *
 */
public class MenuDirective extends Directive {
	private static final Logger logger = LoggerFactory.getLogger(MenuDirective.class);

	@Override
	public String getName() {
		return "Menu";
	} // 指定指令的名称

	@Override
	public int getType() {
		return BLOCK;
	} // 指定指令类型为块指令

	

	@Override
	public boolean render(InternalContextAdapter context, Writer writer, Node node) throws IOException,
			ResourceNotFoundException, ParseErrorException, MethodInvocationException {
		ViewToolContext viewToolContext = (ViewToolContext) context.getInternalUserContext();
		HttpServletRequest request = viewToolContext.getRequest();

		writer.write(getMenuStr(getMENUS(), request.getContextPath()));
		return true;
	}
	
	public String getMenuStr(List<Menu> menus, String contextPath) {
		String s = "";
		for (int i = 0; i < menus.size(); i++) {
			s += menuToString(menus.get(i), contextPath);
		}
		return s;
	}
	

	/**
	 * 遍历一个菜单项，并生成Html代码
	 * 
	 * @param menu
	 * @return
	 */
	public String menuToString(Menu menu, String contextPath) {
		logger.debug("the menu {}({}) is structing", menu.getName(), menu.getID());
		String s = "";
		if (menu.isHasChild()) {
			s += "<li id='" + menu.getID() + "'>";
			s += "<a href='#' class='menu-dropdown'>";
			s += "<i class='" + menu.getCss() + "'></i>";
			s += "<span class='menu-text'>" + menu.getName() + "</span>";
			s += "<i class='menu-expand'></i>";
			s += "</a>";
			s += "<ul class='submenu'>";
			for (int i = 0; i < menu.getSubMenu().size(); i++) {
				s += menuToString(menu.getSubMenu().get(i), contextPath);
			}
			s += "</ul>";
			s += "</li>";
		} else {
			s += "<li id='" + menu.getID() + "'>";
			s += "<a href='" + contextPath + "/" + menu.getLink() + "'>";
			s += "<i class='" + menu.getCss() + "'></i>";
			s += "<span class='menu-text'>" + menu.getName() + "</span>";
			s += "</a>";
			s += "</li>";
		}
		return s;
	}

	// public Menu[] getMENUS() {
	// return MENUS;
	// }

	/**
	 * 从数据库获取menu数据，并组装数据
	 * @return
	 */
	public List<Menu> getMENUS() {
		Map<String, Menu> menuMap = new HashMap<String, Menu>();
		List<Menu> rootMenus = new ArrayList<Menu>();
		List<Menu> secondMenus = null;
		List<Menu> thirdMenus = null;
		Menu rootMenu = null;
		Menu secondMenu = null;
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
			for (Model bean : menus) {
				Menu menu = (Menu) bean;
				menuMap.put(menu.getID(), menu);
				if (menu.getID().length() == 2) {
					rootMenus.add(menu);
				} else if (menu.getID().length() == 4) {
					rootMenu = menuMap.get(menu.getParentId());
					secondMenus = rootMenu.getSubMenu();
					if (secondMenus == null) {
						secondMenus = new ArrayList<Menu>();
						rootMenu.setSubMenu(secondMenus);
					}
					secondMenus.add(menu);
				} else if (menu.getID().length() == 6) {
					secondMenu = menuMap.get(menu.getParentId());
					thirdMenus = secondMenu.getSubMenu();
					if (thirdMenus == null) {
						thirdMenus = new ArrayList<Menu>();
						secondMenu.setSubMenu(thirdMenus);
					}
					thirdMenus.add(menu);
				}
			}
		} catch (Exception e) {
			logger.error("found no data", e);
		}

		return rootMenus;
	}

}
