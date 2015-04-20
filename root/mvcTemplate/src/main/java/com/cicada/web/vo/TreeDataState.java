package com.cicada.web.vo;

public class TreeDataState {
	private boolean selected;

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public TreeDataState(boolean selected) {
		super();
		this.selected = selected;
	}

}
