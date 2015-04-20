package com.cicada.web.helper.json;

import java.io.IOException;

import com.cicada.core.model.Currency;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 用于将currency转化为name
 * 
 * @author zhangcheng
 *
 */
public class CurrencyJsonSerializer extends JsonSerializer<Currency> {

	@Override
	public void serialize(Currency currency, JsonGenerator jgen, SerializerProvider provider) throws IOException,
			JsonProcessingException {
		try {
			jgen.writeString(currency.getName());
		} catch (Exception e) {
			jgen.writeString("");
		}
	}
}