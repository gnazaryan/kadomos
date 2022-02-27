package com.kadomos.apigw.util;

import java.util.List;

public class Convenience {

    public static boolean isNotNullOrEmptyList(List input) {
        return (input != null && !input.isEmpty());
    }
}
