package br.com.rbs.request.withdraw.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Cache {

    public static String nextAuthorizationCode() {
        int sequence = new Cache.AuthorizationCodeCache().getNextAuthorizationCode();
        return StringUtils.leftPad(String.valueOf(sequence), 6, "0");
    }

    private static class AuthorizationCodeCache {

        private final String key = "authorizationCode";
        private static Map<String, Integer> reference = new HashMap();

        public Integer getNextAuthorizationCode() {
            Integer sequence = reference.get(key);
            if (sequence == null || sequence == 999999) {
                sequence = 0;
            }

            reference.put(key, ++sequence);
            return sequence;
        }
    }
}