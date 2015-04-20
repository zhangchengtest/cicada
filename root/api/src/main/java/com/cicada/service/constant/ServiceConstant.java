package com.cicada.service.constant;

public final class ServiceConstant {

	private ServiceConstant() {
	}

	public static final class Hessian {
		
		public static final String BASE_URL = "/remoting";
		public static final String CRUD_SERVICE_URL = BASE_URL + "/CRUDService";
		public static final String TEST_SERVICE_URL = BASE_URL + "/TestService";
		// public static final String _SERVICE_URL = BASE_URL + "/";

		public static final String MERCHANT_SERVICE_URL = BASE_URL + "/MerchantService";
		public static final String USER_SERVICE_URL = BASE_URL + "/UserService";
		public static final String CURRENCY_SERVICE_URL = BASE_URL + "/CurrencyService";
		public static final String ORDER_SERVICE_URL = BASE_URL + "/OrderService";
		public static final String RISK_SERVICE_URL = BASE_URL + "/RiskService";
		public static final String SECURITY_SERVICE_URL = BASE_URL + "/SecurityService";
		public static final String SENDER_SERVICE_URL = BASE_URL + "/SenderService";
		public static final String PERMISSION_SERVICE_URL = BASE_URL + "/PermissionService";
		public static final String CHANNEL_SERVICE_URL = BASE_URL + "/ChannelService";
		public static final String CHANNEL_PROVIDER_SERVICE_URL =  BASE_URL + "/ChannelProviderService";
		public static final String BANK_ACCOUNT_SERVICE_URL = BASE_URL + "/BankAccountService";
		public static final String PAYMENT_PRODUCT_SERVICE_URL = BASE_URL + "/PaymentProductService";
		public static final String ACQUIRER_SERVICE_URL = BASE_URL + "/AcquirerService";
		public static final String BANK_SERVICE_URL = BASE_URL + "/BankService";
		public static final String PAYMENT_SERVICE_URL = BASE_URL + "/PaymentService";
		public static final String PRODUCT_SERVICE_URL = BASE_URL + "/ProductService";
		public static final String MAIL_SERVICE_URL = BASE_URL + "/MailService";

		public static final String BANK_POST_PROCESS_SERVICE_URL = BASE_URL + "/bankPostProcessService";
		public static final String DISH_SERVICE_URL = BASE_URL + "/dishService";

	}
	
	public static final class AOP_NAME {
		public static final String CRUD_SERVICE = "crudService";
		public static final String CACHE_SERVICE = "cacheService";
		
		public static final String DISH_SERVICE = "dishService";

		public static final String MERCHANT_SERVICE = "merchantService";
		public static final String PERMISSION_SERVICE = "permissionService";
		public static final String ADDRESS_SERVICE = "addressService";
		public static final String CURRENCY_SERVICE = "currencyService";
		public static final String SECURITY_SERVICE = "securityService";
		public static final String USER_SERVICE = "userService";
		public static final String PAYMENT_SERVICE = "paymentService";

		public static final String BANK_SERVICE = "bankService";
		public static final String BANK_ACCOUNT_SERVICE = "bankAccountService";
		public static final String PAYMENT_PRODUCT_SERVICE = "paymentProductService";
		public static final String CHANNEL_SERVICE = "channelService";
		public static final String CHANNEL_PROVIDER_SERVICE = "channelProviderService";

		public static final String MAIL_SERVICE = "mailService";
		
		public static final String BANK_POST_PROCESS_SERVICE = "bankPostProcessService";
		
		public static final String PRODUCT_SERVICE = "productService";
		
	}

}
