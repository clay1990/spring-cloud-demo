package com.clay.util;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DataObjectUtils {

    public static <T, R> T copyObject(R sourceObject, Class<T> tClass) {
        T targetObject = BeanUtils.instantiateClass(tClass);
        BeanUtils.copyProperties(sourceObject, targetObject);
        return targetObject;
    }

    public static <T, R> T copyObject(R sourceObject, Class<T> tClass, String... ignores) {
        T targetObject = BeanUtils.instantiateClass(tClass);
        BeanUtils.copyProperties(sourceObject, targetObject, ignores);
        return targetObject;
    }

    public static <T, R> List<T> copyList(List<R> sourceList, Class<T> tClass) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new ArrayList<T>(0);
        }
        return sourceList.stream()
                .map(sourceObject -> DataObjectUtils.copyObject(sourceObject, tClass))
                .collect(Collectors.toList());
    }

    public static <T, R> List<T> copyList(List<R> sourceList, Class<T> tClass, String... ignores) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new ArrayList<T>(0);
        }
        return sourceList.stream()
                .map(sourceObject -> DataObjectUtils.copyObject(sourceObject, tClass, ignores))
                .collect(Collectors.toList());
    }


}
