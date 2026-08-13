package com.ayub.assessment.api.utility;

import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/* Triple DES encryption and decryption utility */
public final class TripleDesUtil {

    private static final String ALGORITHM = "DESede";

    private static final String TRANSFORMATION = "DESede/ECB/PKCS5Padding";

    /*
     * 24-byte Triple DES key.
     * The value is represented as a 48-character HEX string.
     */
    private static final String SECRET_KEY_HEX =
            "697276616E7261626962756C6C6168726976616E726162";

    private static final SecretKeySpec SECRET_KEY;

    static {
        byte[] keyBytes = HexUtil.decode(SECRET_KEY_HEX);

        if (keyBytes.length != 24) {
            throw new IllegalStateException("Triple DES key must contain 24 bytes");
        }

        SECRET_KEY = new SecretKeySpec(keyBytes, ALGORITHM);
    }

    private TripleDesUtil() {
    }

    public static String encrypt(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, SECRET_KEY);

            byte[] encrypted = cipher.doFinal(
                    value.getBytes(StandardCharsets.UTF_8)
            );

            return HexUtil.encode(encrypted);
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("Failed to encrypt value", e);
        }
    }

    public static String decrypt(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }

        try {
            byte[] encrypted = HexUtil.decode(value);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, SECRET_KEY);

            byte[] decrypted = cipher.doFinal(encrypted);

            return new String(decrypted, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException | IllegalArgumentException e) {
            throw new IllegalStateException("Failed to decrypt value", e);
        }
    }

    public static boolean isEncrypted(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }

        if ((value.length() & 1) != 0) {
            return false;
        }

        try {
            byte[] decoded = HexUtil.decode(value);

            if (decoded.length == 0 || decoded.length % 8 != 0) {
                return false;
            }

            String decrypted = decrypt(value);

            return !decrypted.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }
}