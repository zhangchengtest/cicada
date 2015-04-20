package com.cicada.web.vo;

public class TreeData {
	private String id;
	private String text;
	private String icon;
	private TreeDataState state;
	private TreeData[] children;

	public TreeData(String id, String text, String icon, TreeDataState state, TreeData[] children) {
		this.id = id;
		this.text = text;
		this.icon = icon;
		this.state = state;
		this.children = children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public TreeData[] getChildren() {
		return children;
	}

	public void setChildren(TreeData[] children) {
		this.children = children;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TreeDataState getState() {
		return state;
	}

	public void setState(TreeDataState state) {
		this.state = state;
	}

}
