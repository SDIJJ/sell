package com.imooc.sell.Utils;

import java.util.Random;

public class KeyUtil {
    public static synchronized String getUniqueKey(){
        Random random=new Random();
        Integer number=random.nextInt(900000)+10000;
        return System.currentTimeMillis()+number.toString();
    }
}
