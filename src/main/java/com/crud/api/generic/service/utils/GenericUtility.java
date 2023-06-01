/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crud.api.generic.service.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NMSLAP570
 */
public class GenericUtility {

    public static <T> String getFieldNames(Class<T> clazz) {
        List<String> fieldNames = new ArrayList();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equalsIgnoreCase("serialVersionUID")) {
                continue;
            }
            fieldNames.add(field.getName());
        }
        return String.join(",", fieldNames);
    }

    public static <T> String extractFieldValues(T object) throws IllegalAccessException {
        List<String> fieldValues = new ArrayList<>();
        Field[] fields = object.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equalsIgnoreCase("serialVersionUID")) {
                continue;
            }
            field.setAccessible(true);
            Object value = field.get(object);
            if (value != null) {
                String fieldValue = String.valueOf(value).replace(",", "\\,"); // Escape pipe symbols
                fieldValues.add('"' + fieldValue + '"');
            } else {
                fieldValues.add(""); // Add an empty string if the value is null
            }
        }
        return String.join(",", fieldValues);
    }

}
