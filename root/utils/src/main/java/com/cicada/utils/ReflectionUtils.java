package com.cicada.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.reflections.Reflections;

public final class ReflectionUtils extends Reflections {

	public static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T invokeGetter(Object instance, String propertyName) {
		String methodName = "get" + StringUtils.capitalize(propertyName);
		try {
			Method tMethod = instance.getClass().getMethod(methodName);
			return (T) invokeMethod(tMethod, instance);
		} catch (SecurityException e) {
			throw new RuntimeException("SecurityException while invoke " + methodName + "() of Class: "
					+ instance.getClass(), e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("NoSuchMethodException while invoke " + methodName + "() of Class: "
					+ instance.getClass(), e);
		}
	}

	public static void invokeSetter(Object instance, String propertyName, Object value) {
		if (value == null) {
			String methodName = "set" + StringUtils.capitalize(propertyName);
			Class<?>[] clazz = null;
			Method[] methods = instance.getClass().getMethods();
			int count = 0;
			for (Method method : methods) {
				if (methodName.equals(method.getName())) {
					clazz = method.getParameterTypes();
					count++;
				}
			}
			if (count == 0) {
				throw new RuntimeException("NoSuchMethodException while invoke " + methodName + "() of Class: "
						+ instance.getClass());
			} else if (count > 1) {
				throw new RuntimeException("Value can not be null for this method.");
			} else {
				invokeSetter(instance, propertyName, value, clazz);
			}
		} else {
			invokeSetter(instance, propertyName, value, value.getClass());
		}
	}

	public static Class<?> getParameterType(Object instance, String propertyName) {
		try {
			return instance.getClass().getField(propertyName).getType();
		} catch (SecurityException e) {
			throw new RuntimeException("SecurityException while get Field Type " + propertyName + " of Class: "
					+ instance.getClass(), e);
		} catch (NoSuchFieldException e) {
			throw new RuntimeException("NoSuchFieldException while get Field Type " + propertyName + " of Class: "
					+ instance.getClass(), e);
		}
	}

	public static void invokeSetter(Object instance, String propertyName, Object value, Class<?>... parameterTypes) {
		String methodName = "set" + StringUtils.capitalize(propertyName);
		Method tMethod = null;
		try {
			tMethod = getAccessibleMethod(instance.getClass(), methodName, parameterTypes);
		} catch (SecurityException e) {
			throw new RuntimeException("SecurityException while invoke " + methodName + "() of Class: "
					+ instance.getClass(), e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("NoSuchMethodException while invoke " + methodName + "() of Class: "
					+ instance.getClass(), e);
		}

		invokeMethod(tMethod, instance, value);
	}

	public static <T> T invokeStaticMethod(Class<?> clazz, String methodName, Object value, Class<?>... parameterTypes) {
		Method tMethod = null;
		try {
			tMethod = getAccessibleMethod(clazz, methodName, parameterTypes);
		} catch (SecurityException e) {
			throw new RuntimeException("SecurityException while invoke " + methodName + "() of Class: " + clazz, e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("NoSuchMethodException while invoke " + methodName + "() of Class: " + clazz, e);
		}
		if (parameterTypes.length > 0) {
			return invokeMethod(tMethod, null, value);
		} else {
			return invokeMethod(tMethod, null);
		}
	}

	public static Method getAccessibleMethod(Class<?> clazz, final String methodName, final Class<?>... parameterTypes)
			throws NoSuchMethodException {
		try {
			return clazz.getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			Method[] methods = clazz.getMethods();
			// 先判断参数类型相同
			for (Method method : methods) {
				if (methodName.equals(method.getName())
						&& isParameterTypesEqual(parameterTypes, method.getParameterTypes())) {
					return method;
				}
			}
			// 再判断传入的参数类型为方法签名参数类型的子类
			for (Method method : methods) {
				if (methodName.equals(method.getName())
						&& isParameterTypesAssignableFrom(parameterTypes, method.getParameterTypes())) {
					return method;
				}
			}
			throw e;
		}
	}

	public static boolean isParameterTypesEqual(Class<?>[] parameterTypes1, Class<?>[] parameterTypes2) {
		if (parameterTypes1 == parameterTypes2) {
			return true;
		} else if (parameterTypes1 == null && parameterTypes2 != null) {
			return false;
		} else if (parameterTypes1 != null && parameterTypes2 == null) {
			return false;
		} else if (parameterTypes1.length != parameterTypes2.length) {
			return false;
		} else {
			for (int i = 0; i < parameterTypes1.length; i++) {
				Class<?> class1 = getPrimitiveClass(parameterTypes1[i]);
				Class<?> class2 = getPrimitiveClass(parameterTypes2[i]);
				if (class1 != class2) {
					return false;
				}
			}
			return true;
		}
	}

	/**
	 * 判断第一个参数类型组中的每个class是否都与第二个参数类型组中class相等，或为第二个参数类型组中class的子类
	 * 
	 * @param parameterTypes1
	 * @param parameterTypes2
	 * @return
	 */
	public static boolean isParameterTypesAssignableFrom(Class<?>[] parameterTypes1, Class<?>[] parameterTypes2) {
		if (parameterTypes1 == parameterTypes2) {
			return true;
		} else if (parameterTypes1 == null && parameterTypes2 != null) {
			return false;
		} else if (parameterTypes1 != null && parameterTypes2 == null) {
			return false;
		} else if (parameterTypes1.length != parameterTypes2.length) {
			return false;
		} else {
			for (int i = 0; i < parameterTypes1.length; i++) {
				Class<?> class1 = getPrimitiveClass(parameterTypes1[i]);
				Class<?> class2 = getPrimitiveClass(parameterTypes2[i]);
				if (!class2.isAssignableFrom(class1)) {
					return false;
				}
			}
			return true;
		}
	}

	public static Class<?> getPrimitiveClass(Class<?> clazz) {
		if (Integer.class.equals(clazz)) {
			return Integer.TYPE;
		} else if (Long.class.equals(clazz)) {
			return Long.TYPE;
		} else if (Double.class.equals(clazz)) {
			return Double.TYPE;
		} else if (Short.class.equals(clazz)) {
			return Short.TYPE;
		} else if (Byte.class.equals(clazz)) {
			return Byte.TYPE;
		} else if (Float.class.equals(clazz)) {
			return Float.TYPE;
		} else if (Character.class.equals(clazz)) {
			return Character.TYPE;
		} else if (Boolean.class.equals(clazz)) {
			return Boolean.TYPE;
		} else {
			return clazz;
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T invokeMethod(Method tMethod, Object instance, Object... args) {
		try {
			return (T) tMethod.invoke(instance, args);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException("IllegalArgumentException while invoke " + tMethod.getName() + "() of Class: "
					+ tMethod.getDeclaringClass(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("IllegalAccessException while invoke " + tMethod.getName() + "() of Class: "
					+ tMethod.getDeclaringClass(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("InvocationTargetException while invoke " + tMethod.getName() + "() of Class: "
					+ tMethod.getDeclaringClass(), e);
		}
	}

	/**
	 * get generic parameter type of the given class. <br/>
	 * exceptions will be swallowed and <code>null</code> will be returned.
	 * 
	 * @param inputClass
	 * @param argIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Class<T> getGenericParameterType(Class<?> inputClass, int argIndex) {
		try {
			if (argIndex < 0) {
				return null;
			}
			ParameterizedType type = ParameterizedType.class.cast(inputClass.getGenericSuperclass());
			if (type != null) {
				Type[] argTypes = type.getActualTypeArguments();
				if (argTypes != null && argTypes.length > argIndex) {
					return (Class<T>) argTypes[argIndex];
				}
			}
			return null;
		} catch (Exception e) {
			return null;
		}

	}
}
