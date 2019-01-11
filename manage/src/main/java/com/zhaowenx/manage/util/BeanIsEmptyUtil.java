package com.zhaowenx.manage.util;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhaowenx on 2018/8/23.
 */
public class BeanIsEmptyUtil {

    private static final Logger logger = LoggerFactory.getLogger(BeanIsEmptyUtil.class);

    /**
     * 验证实体对象是否为空
     * @param bean
     * @param attributeName
     *  自定义验证参数
     *  @retun flase:表示没有空值  true:表示有空值
     */
    public static boolean isEmpty(Object bean, String... attributeName) {
        List<String> lists = Arrays.asList(attributeName);
        List<String> names = new ArrayList<String>();
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(bean);
        for (PropertyDescriptor origDescriptor : origDescriptors) {
            String name = origDescriptor.getName();
            if ("class".equals(name)) {
                continue;
            }
            names.add(name);
            if (lists.contains(name)) {

                if (PropertyUtils.isReadable(bean, name)) {
                    try {
                        Object value = PropertyUtils.getSimpleProperty(bean, name);
                        if (value == null || "".equals(value)) {
                            return true;
                        } else {
                            continue;
                        }
                    } catch (IllegalArgumentException ie) {
                        ;
                    } catch (Exception e) {
                        ;
                    }
                }
            } else {
                continue;
            }
        }
        if(compare(lists,names)){
            logger.info("自定义参数在实体类中没有！");
            return true;
        }
        return false;
    }


    /**
     * 验证实体对象是否为空
     * 如果对象属性为空，则判断该对象为空。
     * @param bean
     * @return
     */
    public static boolean isEmpty(Object bean) {
        PropertyDescriptor[] origDescriptors = PropertyUtils.getPropertyDescriptors(bean);
        for (PropertyDescriptor origDescriptor : origDescriptors) {
            String name = origDescriptor.getName();
            if ("class".equals(name)) {
                continue;
            }
            if (PropertyUtils.isReadable(bean, name)) {
                try {
                    Object value = PropertyUtils.getSimpleProperty(bean, name);
                    if (value == null || "".equals(value)) {
                        return true;
                    } else {
                        continue;
                    }
                } catch (IllegalArgumentException ie) {
                    ;
                } catch (Exception e) {
                    ;
                }
            }
        }
        return false;
    }

    /**
     * 验证实体类是否有，传进来的参数
     * @param list1
     * @param list2
     * @return true:有实体类没有的参数  false:实体类中有
     */
    public static boolean compare(List<String> list1, List<String> list2) {
        for(String str1 : list1){
            if(!list2.contains(str1)){
                return true;
            }
        }
        return false;
    }
}
