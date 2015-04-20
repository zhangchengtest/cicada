package com.cicada.web.spring;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.validation.MessageInterpolator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;
import org.hibernate.validator.spi.resourceloading.ResourceBundleLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityLayoutViewResolver;

import com.cicada.web.controller.HomeController;
import com.cicada.web.velocity.CacheDirective;
import com.cicada.web.velocity.MenuDirective;
import com.cicada.web.velocity.PageDirective;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { HomeController.class })
public class WebConfig extends ServiceContainer {

	private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		System.out.println("AllInterceptor");

	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {

	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/skin/js/**").addResourceLocations("/skin/js/").setCachePeriod(31556926);
		registry.addResourceHandler("/skin/css/**").addResourceLocations("/skin/css/").setCachePeriod(31556926);
		registry.addResourceHandler("/skin/image/**").addResourceLocations("/skin/image/").setCachePeriod(31556926);
		registry.addResourceHandler("/assets/**").addResourceLocations("/assets/").setCachePeriod(31556926);
	}

	@Bean
	public CustomSimpleMappingExceptionResolver getCustomSimpleMappingExceptionResolver() {
		CustomSimpleMappingExceptionResolver resolver = new CustomSimpleMappingExceptionResolver();
		resolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return resolver;
	}

	public Validator getValidator() {
		ResourceBundleLocator defaultResourceBundleLocator = new PlatformResourceBundleLocator(VALIDATION_FILE_NAME);
		MessageInterpolator defaultMessageInterpolator = new ResourceBundleMessageInterpolator(
				defaultResourceBundleLocator);
		LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		bean.setMessageInterpolator(defaultMessageInterpolator);
		bean.setProviderClass(HibernateValidator.class);
		// bean.setValidationMessageSource(messageSource());
		return bean;
	}

	@SuppressWarnings("static-access")
	@Bean
	public VelocityConfigurer getVelocityConfig() {
		VelocityConfigurer velocityConfig = new VelocityConfigurer();
		velocityConfig.setResourceLoaderPath(getViewFolder());
		velocityConfig.setConfigLocation(getSpringResourceLoader().getResource(
				CLASSPATH_PREFIX + VELOCITY_CONFIG_LOCATION));
		Map<String, Object> velocityPropertiesMap = new HashMap<String, Object>();
		velocityPropertiesMap.put("input.encoding", DEFAULT_ENCODING);
		velocityPropertiesMap.put("output.encoding", DEFAULT_ENCODING);
		velocityPropertiesMap.put(
				"userdirective",
				PageDirective.class.getName() +","
						+ CacheDirective.class.getName() + "," + MenuDirective.class.getName());
		velocityConfig.setVelocityPropertiesMap(velocityPropertiesMap);

		return velocityConfig;
	}

	@Bean
	public VelocityLayoutViewResolver getViewResolver() {
		VelocityLayoutViewResolver resolver = new VelocityLayoutViewResolver(); // new VelocityViewResolver();
		resolver.setLayoutUrl("master/masterpage.vm");
		resolver.setSuffix(getViewSuffix());
		resolver.setContentType(DEFAULT_CONTENT_TYPE);
		resolver.setExposeRequestAttributes(true);
		resolver.setExposeSessionAttributes(true);
		resolver.setToolboxConfigLocation(getToolboxConfigLocation());
		// resolver.setViewClass(VelocityToolboxView.class);
		resolver.setRequestContextAttribute(REQUEST_CONTEXT_ATTRIBUTE);
		resolver.setOrder(0);
		return resolver;
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		super.extendMessageConverters(converters);
		for (HttpMessageConverter<?> httpMessageConverter : converters) {
			if (httpMessageConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter convert = (MappingJackson2HttpMessageConverter) httpMessageConverter;
				ObjectMapper objectMapper = convert.getObjectMapper();
				objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
			}
		}
	}

	@Bean
	public DefaultKaptcha getCaptchaProducer() {
		DefaultKaptcha captchaProducer = new DefaultKaptcha();
		Properties props = new Properties();
		props.setProperty("kaptcha.border", "no");
		props.setProperty("kaptcha.border.color", "105,179,90");
		props.setProperty("kaptcha.textproducer.font.color", "blue");
		props.setProperty("kaptcha.image.width", "125");
		props.setProperty("kaptcha.image.height", "45");
		props.setProperty("kaptcha.textproducer.font.size", "no");
		props.setProperty("kaptcha.session.key", "45");
		props.setProperty("kaptcha.textproducer.char.length", "4");
		props.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");

		Config config = new Config(new Properties());
		captchaProducer.setConfig(config);

		return captchaProducer;
	}
}
