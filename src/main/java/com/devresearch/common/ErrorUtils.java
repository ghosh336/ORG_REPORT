package com.devresearch.common;

import java.util.HashMap;
import java.util.Map;

public final class ErrorUtils {

    public static final String ERROR_1001 = "ERROR_1001";
    public static final String ERROR_1002 = "ERROR_1002";
    public static final String ERROR_1003 = "ERROR_1003";
    public static final String ERROR_1004 = "ERROR_1004";
    public static final String ERROR_1005 = "ERROR_1005";
    private static Map<String, String> ERROR_CODES = new HashMap<>();

    static {
        ERROR_CODES.put(ERROR_1001, "ERROR-1001 :Input File Not Found, Please check if the file exists in the required place.");
        ERROR_CODES.put(ERROR_1002, "ERROR-1002 :Incorrect data in file/Something went wrong, will not proceed further.");
        ERROR_CODES.put(ERROR_1003, "ERROR-1003 :Something went wrong, Please contact administrator.");
        ERROR_CODES.put(ERROR_1004, "ERROR-1004 :Missing/TooMany Values in file, will not proceed further.");
        ERROR_CODES.put(ERROR_1005, "ERROR-1005 :Input file is empty.");
    }

    public static String getMessage(String errorCode) {
        return ERROR_CODES.get(errorCode);
    }
}
