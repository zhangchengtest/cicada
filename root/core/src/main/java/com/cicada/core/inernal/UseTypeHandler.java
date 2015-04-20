package com.cicada.core.inernal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * a customized annotation indicating that the annotated enumeration class should be handled by the given handler
 * (specified via value).
 * 
 * @author hermano
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface UseTypeHandler {

	// "org.apache.ibatis.type.EnumOrdinalTypeHandler";
	// "org.apache.ibatis.type.EnumTypeHandler";

	String value() default "org.apache.ibatis.type.EnumOrdinalTypeHandler";
}
