package com.cicada.web.constant;

public final class WebConstant {

	private WebConstant() {
	}

	/**
	 * session中用到的一些变量，注销的时候必须清空所有
	 * @author zhangcheng
	 *
	 */
	public static final class Session {
		public static final String USER_CONTEXT = "userContext";
		public static final String SIDE_BAR = "sideBar";
	}

	/**
	 * datatable 的一些参数名称是框架自定义的
	 * 
	 * @author zhangcheng
	 *
	 */
	public static final class DataTable {
		public static final String PAGE_NUM = "start";
		public static final String PAGE_SIZE = "length";
		public static final String SEARCH_VALUE = "search[value]";
	}

}
