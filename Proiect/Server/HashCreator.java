package Proiect.Server;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class HashCreator {

    public static String passwordHash(final String password, final String salt)
            throws NoSuchAlgorithmException,
            InvalidKeySpecException,
            NoSuchProviderException {
        int iterations = 1000;
        byte[] hash = createPBEHash(password, iterations, salt.getBytes(), 64);
        return convertToHex(hash);
    }

    private static byte[] createPBEHash(final String password, final int iterations,
            final byte[] salt, final int keyLength) throws NoSuchAlgorithmException,
            InvalidKeySpecException {
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(),
                salt, iterations, keyLength * 8);
        SecretKeyFactory skf = SecretKeyFactory
                .getInstance("PBKDF2WithHmacSHA1");
        return skf.generateSecret(spec).getEncoded();
    }

    public static String createHash(String s) throws NoSuchAlgorithmException {
        MessageDigest msgDgst = MessageDigest.getInstance("SHA-256");
        return convertToHex(msgDgst.digest(s.getBytes(StandardCharsets.UTF_8)));
    }

    private static String convertToHex(final byte[] messageDigest) {
        BigInteger bigint = new BigInteger(1, messageDigest);
        String hexText = bigint.toString(16);
        while (hexText.length() < 32) {
            hexText = "0".concat(hexText);
        }
        return hexText;
    }
}