package com.sdware.javahereapi.util;

public class StringUtils {

    public boolean isEmpty(String parameter) {
        if (parameter != null && (!parameter.equals(""))) {
            return false;
        }
        return true;
    }
}
