package com.cicada.service.impl.helper;

import org.dom4j.Entity;

import com.cicada.core.model.Model;
import com.cicada.dao.CRUDDao;

public final class RegistryEntry {

	private String pojoName;
	private Class<? extends Model> modelClass;
	private Class<? extends CRUDDao> mapperClass;
	private String mapperName;

	public RegistryEntry() {
	}

	public RegistryEntry(String pojoName, Class<? extends Model> modelClass, Class<? extends Entity> entityClass,
			Class<? extends CRUDDao> mapperClass, String mapperName) {
		super();
		this.pojoName = pojoName;
		this.modelClass = modelClass;
		this.mapperClass = mapperClass;
		this.mapperName = mapperName;
	}

	public String getPojoName() {
		return pojoName;
	}

	public void setPojoName(String pojoName) {
		this.pojoName = pojoName;
	}

	public Class<? extends Model> getModelClass() {
		return modelClass;
	}

	public void setModelClass(Class<? extends Model> modelClass) {
		this.modelClass = modelClass;
	}

	public Class<? extends CRUDDao> getMapperClass() {
		return mapperClass;
	}

	public void setMapperClass(Class<? extends CRUDDao> mapperClass) {
		this.mapperClass = mapperClass;
	}

	public String getMapperName() {
		return mapperName;
	}

	public void setMapperName(String mapperName) {
		this.mapperName = mapperName;
	}


}
