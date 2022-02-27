package com.kadomos.apigw.util;

import java.util.Map;

public class Convenience {

    /**
     * The convenient method to check if the inputed map is empty or null
     * @param input
     * @return
     */
    public static Boolean isNotNullOrEmptyMap(Map input) {
        return input != null && !input.isEmpty();
    }
}
