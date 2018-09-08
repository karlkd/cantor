package io.cantor.service;

import java.util.Iterator;
import java.util.Map;

public class Utils {

    private static final String EMPTY = "";

    public static String convertToString(Map<String, Object> data) {
        StringBuilder builder = new StringBuilder();
        if (null == data || data.isEmpty())
            return EMPTY;
        builder.append("{");
        Iterator<Map.Entry<String, Object>> iterator = data.entrySet()
                                                           .iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> entry = iterator.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                builder.append(String.format("\"%s\":\"%s\"", key, value));
            } else if (value instanceof Number) {
                builder.append(String.format("\"%s\": %s", key, value));
            } else if (value instanceof Map) {
                String newValue = convertToString((Map<String, Object>) value);
                builder.append(String.format("\"%s\": %s", key, newValue));
            }
            if (iterator.hasNext())
                builder.append(",");
        }
        builder.append("}");

        return builder.toString();
    }
}
