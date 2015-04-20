package com.cicada.web.velocity;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;

import com.cicada.core.model.Currency;
import com.cicada.web.spring.SpringContext;

public class CurrencyTag {

	public String render()
	{
		List<Currency> currencies = Currency.getAllValues();
		StringBuffer buffer = new StringBuffer();
		for(Currency currency: currencies)
		{
			buffer.append("<option value=\""+currency.getName()+"\">"+currency.getName()+"</option>");
		}
		
		return buffer.toString();
	}
	
	public String render(String chonseValue)
	{
		MessageSource messageSource = (MessageSource)SpringContext.getBean("messageSource");
		System.out.println(messageSource.getMessage("screen.index.welome", null, new Locale("en")));
		List<Currency> currencies = Currency.getAllValues();
		StringBuffer buffer = new StringBuffer();
		for(Currency currency: currencies)
		{
			if (StringUtils.isNoneEmpty(chonseValue) && chonseValue.equals(currency.getName()))
			{
				buffer.append("<option value=\""+currency.getName()+"\" selected>"+currency.getName()+"</option>");
			}
			else
			{
				buffer.append("<option value=\""+currency.getName()+"\">"+currency.getName()+"</option>");
			}
		
		}
		
		return buffer.toString();
	}
	
	public String render(Currency currency)
	{
		return render(currency.getName());
	}
}
