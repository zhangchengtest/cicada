/**
 * 
 */
package com.cicada.core.model.menu;

import java.util.List;

import com.cicada.core.model.TextualIDModel;

/**
 * @author shawn
 *
 */
public class Menu extends TextualIDModel {

	/**
	 * 菜单名称
	 */
	private String name;
	/**
	 * 菜单样式
	 */
	private String css;
	/**
	 * 菜单链接
	 */
	private String link;
	/*
	 * Html <a> 中的title 标签
	 */
	private String tips;
	/**
	 * 是否有子菜单
	 */
	private boolean hasChild;

	private String parentId;
	private String key;
	private WebappEnum webapp;
	private int sortOrder;
	private MenuStatus status;
	
	private List<Menu> subMenu;
	
	public List<Menu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}

	public boolean isHasChild() {
		return hasChild;
	}

	public void setHasChild(boolean hasChild) {
		this.hasChild = hasChild;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public WebappEnum getWebapp() {
		return webapp;
	}

	public void setWebapp(WebappEnum webapp) {
		this.webapp = webapp;
	}

	public int getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}

	public MenuStatus getStatus() {
		return status;
	}

	public void setStatus(MenuStatus status) {
		this.status = status;
	}

}
