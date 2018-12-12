package com.imooc.sell.Utils;

import com.imooc.sell.enums.CodeEnum;

public class EnumUtil {
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass) {
        for (T t : enumClass.getEnumConstants()) {
            if (code.equals(t.getCode())) {
                return t;
            }
        }
        return null;
    }
}