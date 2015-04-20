package com.cicada.web.spring.convert;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

/**
 * springmvc String转换成Date
 * @author test
 *
 */
public class StringToDate implements Converter<String, Date> {

	private static final Logger logger = LoggerFactory.getLogger(StringToDate.class);

	@Override
	public Date convert(String source) {

		try {
			return DateUtils.parseDate(source);
		} catch (ParseException e) {
			logger.warn("can not {} convert to time", source);
			return new Date();
		}
	}

}