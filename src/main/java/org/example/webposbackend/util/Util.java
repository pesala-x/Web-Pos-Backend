package org.example.webposbackend.util;

import java.util.UUID;

public class Util {
    public static String idGenerate(){
        return UUID.randomUUID().toString();
    }
    public static String itemIdGenerate(){
        return UUID.randomUUID().toString();
    }
}
