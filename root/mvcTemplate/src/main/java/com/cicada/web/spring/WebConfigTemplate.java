package com.cicada.web.spring;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.format.FormatterRegistry;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityToolboxView;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import com.cicada.web.spring.convert.StringToDate;
import com.cicada.web.velocity.PageDirective;

/**
 * <p>
 * Web configuration template<br />
 * Subclasses must add the following annotation:<br/>
 * <code>@ComponentScan(basePackageClasses = { XController.class })</code><br />
 * where XController is the root entry for all controllers.
 * </p>
 * 
 * @author Hermano
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { WebConfigTemplate.class })
public class WebConfigTemplate extends WebMvcConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebConfigTemplate.class);

	protected static final String CLASSPATH_PREFIX = ResourcePatternResolver.CLASSPATH_URL_PREFIX;
	protected static final String CLASSPATH_ALL_PREFIX = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX;
	protected static final String I18N_FILE_NAME = "i18n/messages";
	protected static final String VALIDATION_FILE_NAME = "validation/errors";

	protected static final String DEFAULT_VIEW_FOLER = "/WEB-INF/views";
	protected static final String DEFAULT_FILE_SUFFIX = ".vm";
	protected static final String DEFAULT_ENCODING = "UTF-8";
	protected static final String DEFAULT_CONTENT_TYPE = "text/html;charset=utf-8";
	protected static final String TOOL_BOX_CONFIG_LOCATION = "/WEB-INF/classes/toolbox.xml";
	protected static final String REQUEST_CONTEXT_ATTRIBUTE = "rc";

	protected static final String VELOCITY_CONFIG_LOCATION = "velocity.properties";

	protected static final String DEFAULT_ERROR_FOLDER = "/errorPage";
	protected static final String DEFAULT_ERROR_VIEW = DEFAULT_ERROR_FOLDER + "/defaultError";
	protected static final String DEFAULT_EXCEPTION_ATTRIBUTE_NAME = "exception";

	@Autowired
	Environment env;

	// equivalents for <mvc:resources/> tags
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/").setCachePeriod(31556926);
		registry.addResourceHandler("/js/**").addResourceLocations("/js/").setCachePeriod(31556926);
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.info("adding LocaleChangeInterceptor...");
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		registry.addInterceptor(localeChangeInterceptor);
	}

	@Bean
	public LocaleResolver localeResolver() {
		CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
		cookieLocaleResolver.setDefaultLocale(StringUtils.parseLocaleString("en"));
		return cookieLocaleResolver;
	}

	// equivalent for <mvc:default-servlet-handler/> tag
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	protected String getViewFolder() {
		return DEFAULT_VIEW_FOLER + "/";
	}

	protected String getViewSuffix() {
		return DEFAULT_FILE_SUFFIX;
	}

	protected String getToolboxConfigLocation() {
		return TOOL_BOX_CONFIG_LOCATION;
	}

	// @Bean
	// public InternalResourceViewResolver getInternalResourceViewResolver() {
	// InternalResourceViewResolver resolver = new InternalResourceViewResolver();
	// resolver.setViewClass(JstlView.class);
	// resolver.setPrefix(getViewFolder());
	// resolver.setSuffix(getViewSuffix());
	// return resolver;
	// }

	@Bean
	public SpringResourceLoader getSpringResourceLoader() {
		return new SpringResourceLoader();
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
		velocityPropertiesMap.put("userdirective", PageDirective.class.getName());
		velocityConfig.setVelocityPropertiesMap(velocityPropertiesMap);

		return velocityConfig;
	}

	@Bean
	public ViewResolver getViewResolver() {
		VelocityViewResolver resolver = new VelocityViewResolver();
		resolver.setSuffix(getViewSuffix());
		resolver.setContentType(DEFAULT_CONTENT_TYPE);
		resolver.setExposeRequestAttributes(true);
		resolver.setExposeSessionAttributes(true);
		resolver.setToolboxConfigLocation(getToolboxConfigLocation());
		resolver.setViewClass(VelocityToolboxView.class);
		resolver.setRequestContextAttribute(REQUEST_CONTEXT_ATTRIBUTE);
		resolver.setOrder(0);
		return resolver;
	}

	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		String[] paths = { CLASSPATH_PREFIX + I18N_FILE_NAME, CLASSPATH_PREFIX + VALIDATION_FILE_NAME };
		messageSource.setBasenames(paths);
		// if true, the key of the message will be displayed if the key is not
		// found, instead of throwing a NoSuchMessageException
		messageSource.setUseCodeAsDefaultMessage(true);
		messageSource.setDefaultEncoding(DEFAULT_ENCODING);
		// messageSource.setCacheSeconds(0);
		return messageSource;
	}

	@Override
	public void configurePathMatch(PathMatchConfigurer matcher) {
		matcher.setUseSuffixPatternMatch(false);
	}

	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new StringToDate());
	}
	
	@Bean(name="springContext")
	public SpringContext getSpringContext() throws Exception {
		return new SpringContext();
	}

	// @Override
	// public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
	// super.configureMessageConverters(converters);
	// StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
	// List<MediaType> supportedMediaTypes = new ArrayList<MediaType>(3);
	// supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
	// supportedMediaTypes.add(MediaType.APPLICATION_JSON);
	// supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
	// supportedMediaTypes.add(MediaType.APPLICATION_XML);
	// supportedMediaTypes.add(MediaType.TEXT_HTML);
	// supportedMediaTypes.add(MediaType.TEXT_PLAIN);
	// supportedMediaTypes.add(MediaType.TEXT_XML);
	// stringConverter.setSupportedMediaTypes(supportedMediaTypes);
	// converters.add(stringConverter);
	// }

}
