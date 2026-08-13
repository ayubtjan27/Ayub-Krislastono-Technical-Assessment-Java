package com.ayub.assessment.api.utility;

public final class HexUtil {

    private static final char[] HEX_ARRAY =
            "0123456789ABCDEF".toCharArray();

    private HexUtil() {
    }

    public static String encode(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return "";
        }

        char[] result = new char[bytes.length * 2];

        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;

            result[i * 2] = HEX_ARRAY[value >>> 4];
            result[i * 2 + 1] = HEX_ARRAY[value & 0x0F];
        }

        return new String(result);
    }

    public static byte[] decode(String hex) {
        if (hex == null || hex.isEmpty()) {
            return new byte[0];
        }

        if ((hex.length() & 1) != 0) {
            throw new IllegalArgumentException(
                    "Invalid HEX string length");
        }

        byte[] result = new byte[hex.length() / 2];

        for (int i = 0; i < hex.length(); i += 2) {
            int high = Character.digit(hex.charAt(i), 16);
            int low = Character.digit(hex.charAt(i + 1), 16);

            if (high == -1 || low == -1) {
                throw new IllegalArgumentException(
                        "Invalid HEX character");
            }

            result[i / 2] =
                    (byte) ((high << 4) | low);
        }

        return result;
    }
}