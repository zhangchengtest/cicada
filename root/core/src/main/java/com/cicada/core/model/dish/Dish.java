package com.cicada.core.model.dish;

import com.cicada.core.model.TextualIDModel;

public class Dish extends TextualIDModel {

	private String name;
	private String photo;
	private boolean isBreakfast;
	private String ownerId;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public boolean isBreakfast() {
		return isBreakfast;
	}
	public void setBreakfast(boolean isBreakfast) {
		this.isBreakfast = isBreakfast;
	}
	
}
