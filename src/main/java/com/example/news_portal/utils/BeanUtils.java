package com.example.news_portal.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class BeanUtils {
    @SneakyThrows
    public void copyNonNullProperties(Object source, Object destination) {

        Class<?> clazz = source.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(source);

            // копируем все ненулевые поля, а поля - это Objects
            if (value != null) {
                field.set(destination, value);
            }
        }
    }
    public String instantToFormattedString(Instant instant) {

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM HH:mm:ss");
        String formattedString = formatter.format(zonedDateTime);

        return formattedString;
    }
}
