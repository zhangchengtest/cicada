package com.cicada.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import com.google.common.base.Predicate;

public final class ScannerUtils {

	private ScannerUtils() {
	}

	public static <T> Set<Class<? extends T>> scan4Subclasses(Class<T> clazz, final String pkgName,
			final boolean includeAbstract) {

		final Predicate<String> pkgFilter = new FilterBuilder.Include(FilterBuilder.prefix(pkgName));

		Reflections reflections = new Reflections(new ConfigurationBuilder().filterInputsBy(pkgFilter)
				.setUrls(ClasspathHelper.forPackage(pkgName))
				.setScanners(new SubTypesScanner().filterResultsBy(pkgFilter)));

		Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(clazz);

		if (!includeAbstract) {
			Set<Class<? extends T>> result = new HashSet<Class<? extends T>>(subTypes.size());
			for (Class<? extends T> type : subTypes) {
				if (!Modifier.isAbstract(type.getModifiers())) {
					result.add(type);
				}
			}
			return result;
		}

		return subTypes;
	}

	/**
	 * scan for all types that are annotated with the given annotation.
	 * 
	 * @param pkgName
	 *            the base package name
	 * @param anno
	 *            the annotation class
	 * @param includeAbstract
	 *            <code>true</code> to include abstract types, <code>false</code> to exclude
	 * @return
	 */
	public static Set<Class<?>> scan4Annotation(final String pkgName, Class<? extends Annotation> anno,
			final boolean includeAbstract) {

		Reflections reflections = new Reflections(pkgName);

		Set<Class<?>> subTypes = reflections.getTypesAnnotatedWith(anno, true);

		if (!includeAbstract) {
			Set<Class<?>> result = new HashSet<Class<?>>(subTypes.size());
			for (Class<?> type : subTypes) {
				if (!Modifier.isAbstract(type.getModifiers())) {
					result.add(type);
				}
			}
			return result;
		}

		return subTypes;
	}

	/**
	 * <p>
	 * scan for subclasses and annotations in certain package.<br />
	 * parentClass/pkgName/anno should have at least one non-null value.
	 * </p>
	 * 
	 * @param parentClass
	 * @param pkgName
	 * @param anno
	 * @param includeAbstract
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Set<Class<?>> scan(Class<T> parentClass, final String pkgName, Class<? extends Annotation> anno,
			final boolean includeAbstract) {
		if (parentClass == null && pkgName == null && anno == null) {
			throw new RuntimeException("either parentClass or pkgName or anno must not be null.");
		}

		ConfigurationBuilder builder = new ConfigurationBuilder();

		if (pkgName != null) {
			builder.filterInputsBy(new FilterBuilder.Include(FilterBuilder.prefix(pkgName)));
			builder.forPackages(pkgName);
		}

		Reflections reflections = new Reflections(builder);
		Set<Class<?>> mergeResult = new HashSet<Class<?>>();

		// subclass
		if (parentClass != null) {
			Set<Class<? extends T>> subTypes = reflections.getSubTypesOf(parentClass);
			if (subTypes != null) {
				for (Class<? extends T> x : subTypes) {
					mergeResult.add(x);
				}
			}
		}

		// anno
		if (anno != null) {
			Set<Class<?>> annoTypes = reflections.getTypesAnnotatedWith(anno, true);
			if (annoTypes != null) {
				// intersection
				if (parentClass != null) {
					mergeResult = new HashSet<Class<?>>(CollectionUtils.intersection(mergeResult, annoTypes));
				} else {
					mergeResult = annoTypes;
				}
			}
		}

		// filter abstract
		if (!includeAbstract) {
			Set<Class<?>> result = new HashSet<Class<?>>(mergeResult.size());
			for (Class<?> type : mergeResult) {
				if (!Modifier.isAbstract(type.getModifiers())) {
					result.add(type);
				}
			}
			return result;
		} else {
			return mergeResult;
		}

	}

}
