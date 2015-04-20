package com.cicada.web.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.cicada.core.model.page.Pagination;
import com.cicada.web.vo.DataTable;

public class BaseController {

	protected DataTable createDataTalbe(Pagination<?> pagination, HttpServletRequest request) {
		DataTable result = new DataTable();
		result.setDraw(NumberUtils.toInt(request.getParameter("draw")));
		result.setRecordsTotal(pagination.getTotalRows());
		result.setRecordsFiltered(pagination.getTotalRows());
		result.setData(pagination.getPageData());

		return result;
	}

	protected Map<String, String> toMap(Map<String, String[]> param) {
		Iterator<Entry<String, String[]>> entries = param.entrySet().iterator();
		Map<String, String> map = new HashMap<String, String>(param.size());
		while (entries.hasNext()) {
			Entry<String, String[]> entry = entries.next();
			String value = entry.getValue()[0];
			map.put(entry.getKey(), value);
		}
		return map;
	}
	
	/**
	 * 空字符串过滤
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
	}
	
}
