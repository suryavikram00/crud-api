package com.crud.api.utility;

import static com.crud.api.constants.Endpoints.ENDPOINT_V1_VERSION;
import java.util.Base64;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonUtils {

    public static byte getByteFromBoolean(boolean bool) {

        return bool ? Byte.parseByte("1") : Byte.parseByte("0");

    }

    public static String encodeStringToBase64(String input) {

        byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes());
        return new String(encodedBytes);

    }

    public static String randomString(int n) {

        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            int index = (int) (AlphaNumericString.length() * Math.random());
            sb.append(AlphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public static boolean canSkipFilter(String url) {
        if (url.equals("/" + ENDPOINT_V1_VERSION + "/login") || url.contains("status") || url.contains("swagger") || url.contains("api-docs")) {
            return true;
        }
        return false;
    }

    public static boolean canSkipAuthorizationFilter(String url) {
        if (url.contains("excel")) {
            return true;
        } else {
            return false;
        }
    }

}
