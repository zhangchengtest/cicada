package com.cicada.core.constant;

/**
 * 国际化信息常量管理
 * 
 * @author zhangcheng
 *
 */
public enum MessageEnum {

	SAVE_SUCCESS("0001","{save.success}", "save success"),

	SAVE_FAIL("0007","{save.fail}", "save fail"),
	
	DELETE_SUCCESS("0008","{delete.success}", "delete success"),
	
	DELETE_FAIL("0009","{delete.fail}", "delete fail"),
	
	UPDATE_SUCCESS("0002", "{update.success}", "update success"),

	UPDATE_FAIL("0003", "{update.fail}", "update fail"),

	NOT_FOUND("0004", "{can.not.find.object}", "can not find the object "), 
	
	RATE_HAVE_NOT_BEEN_SET_UP("0005", "{rate.have.not.been.set.up}", "rate have not been set up"),
	
	BIZ_HAVE_NOT_BEEN_OPEN("0006", "{biz.have.not.been.open}", "business have not beenn open"),

	SETTLE_ACCOUNT_DOES_NOT_EXIST("0006", "{settle.account.does.not.exist}", "settle account does not exist"),

	WITHDRAW_CONFIG_DOES_NOT_EXIST("0006", "{withdraw.config.does.not.exist}", "withdraw config does not exist"),
	
	NOT_FOUND_MERCHANT("0004", "{can.not.find.object}", "can not find the merchant"),

	STATUS_IS_OVER_DATE("0004", "{status.is.over.date}", "status is over date"),
	
	SYSTEM_IS_BUSY("0004", "{system.is.busy}", "system is busy, try later...");

	private MessageEnum(String id, String code, String defaultMsg) {
		this.id = id;
		this.defaultMsg = defaultMsg;
		this.code = code;
	}

	private String defaultMsg;
	private String code;
	private String id;

	public String getDefaultMsg() {
		return defaultMsg;
	}

	public void setDefaultMsg(String defaultMsg) {
		this.defaultMsg = defaultMsg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
