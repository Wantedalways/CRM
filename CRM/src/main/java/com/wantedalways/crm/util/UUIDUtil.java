package com.wantedalways.crm.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UUIDUtil {

    public static String getUUID() {

        return String.valueOf(UUID.randomUUID()).replaceAll("-","");

    }
}
