package com.zhaowenx.resource.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaowenx on 2018/8/23.
 */
public class BeanCopierUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(BeanCopierUtil.class);

	public static final <T> T copyBean(Class<T> destClass, Object... srcArray) {
		if (destClass == null || isAllNull(srcArray)) {
			return null;
		}
		T destObj = null;
		try {
			destObj = destClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			LOGGER.error("创建对象失败,类名-{}", destClass.getName());
			throw new RuntimeException("复制对象失败", e);
		}
		for (Object srcObj : srcArray) {
			if (srcObj == null) {
				continue;
			}
			org.springframework.cglib.beans.BeanCopier copier = getBeanCopier(srcObj.getClass(), destClass);
			copier.copy(srcObj, destObj, null);
		}
		return destObj;
	}

	public static final <T> T copyBean(T destObj, Object... srcArray) {
		if (destObj == null || isAllNull(srcArray)) {
			return null;
		}
		for (Object srcObj : srcArray) {
			if (srcObj == null) {
				continue;
			}
			org.springframework.cglib.beans.BeanCopier copier = getBeanCopier(srcObj.getClass(), destObj.getClass());
			copier.copy(srcObj, destObj, null);
		}
		return destObj;
	}

	public static final <K, T> List<T> copyBeanList(Class<T> destClass, List<K> srcList) {
		List<T> destList = new ArrayList<T>();
		if (destClass == null || srcList == null || srcList.size() == 0) {
			return destList;
		}
		org.springframework.cglib.beans.BeanCopier copier = getBeanCopier(srcList.get(0).getClass(), destClass);
		for (Object src : srcList) {
			T obj = null;
			try {
				obj = destClass.newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				LOGGER.error("创建对象失败,类名-{}", destClass.getName());
				throw new RuntimeException("复制对象失败", e);
			}
			copier.copy(src, obj, null);
			destList.add(obj);
		}
		return destList;
	}

	private static boolean isAllNull(Object... srcArray) {
		if (ObjectUtils.isEmpty(srcArray)) {
			return true;
		}
		boolean isAllSrcNull = true;
		for (Object src : srcArray) {
			if (src != null) {
				isAllSrcNull = false;
				break;
			}

		}
		return isAllSrcNull;
	}

	private static org.springframework.cglib.beans.BeanCopier getBeanCopier(Class<?> srcClass, Class<?> destClass) {
		return org.springframework.cglib.beans.BeanCopier.create(srcClass, destClass, false);
	}
}
