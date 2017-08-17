package com.alphatica.genotick.ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("SameParameterValue")
public class Parameters {
    private final Map<String,String> map = new HashMap<>();
    public Parameters(String[] args) {
        for(String arg: args) {
            String key = parseKey(arg);
            String value = parseValue(arg);
            map.put(key,value);
        }
    }

    private String parseValue(String arg) {
        int eqPos = arg.indexOf("=");
        if(eqPos >= 0) {
            return arg.substring(eqPos + 1, arg.length());
        } else {
            return arg;
        }
    }

    private String parseKey(String arg) {
        int eqPos = arg.indexOf("=");
        if(eqPos >= 0) {
            return arg.substring(0,eqPos);
        } else {
            return arg;
        }
    }

    public String getValue(String key) {
        return map.get(key);
    }

    public void removeKey(String key) {
        map.remove(key);
    }

    public boolean allConsumed() {
        return map.isEmpty();
    }

    public Collection<String> getUnconsumed() {
        return map.values();
    }
}
