package ma.boumlyk.onboarding.tools.security.tools;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Locale;

public class HashManager {

    public static final int Base64_FORM = 1;
    public static final int HEX_FORM = 2;

    private static final SecureRandom random = new SecureRandom();


    public static String GuidGenerator() {

        return String.format(Locale.getDefault(), "%13d%08d%09d", System.currentTimeMillis(), random.nextInt(99999999), random.nextInt(999999999));

    }

    public static String hashString(String data, int form) {
        if (Base64_FORM == form)
            return Base64.encodeToString(hashString(data, getAppropriateHash()), Base64.NO_WRAP);
        return toHex(hashString(data, getAppropriateHash()));
    }

    public static String hashString(String data, int form, HashMethod method) {
        if (Base64_FORM == form)
            return Base64.encodeToString(hashString(data, method), Base64.NO_WRAP);
        return toHex(hashString(data, method));
    }

    public static byte[] hashString(String data, HashMethod method) {
         return hashBasedOnDigest(data, method.getHashString());
    }
    private static byte[] hashBasedOnDigest(String data, String algorithm) {
        byte[] digest = new byte[0];
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.reset();
            messageDigest.update(data.getBytes());
            digest = messageDigest.digest();
        } catch (NoSuchAlgorithmException ignored) {
        }
        return digest;
    }

    public static HashMethod getAppropriateHash() {
        HashMethod method = null;

        if (isDigestAvailable(HashMethod.SHA512.getHashString())) {
            method = HashMethod.SHA512;
        } else if (isDigestAvailable(HashMethod.SHA384.getHashString())) {
            method = HashMethod.SHA384;
        } else if (isDigestAvailable(HashMethod.SHA256.getHashString())) {
            method = HashMethod.SHA256;
        } else if (isDigestAvailable(HashMethod.SHA1.getHashString())) {
            method = HashMethod.SHA1;
        }else{
           method=HashMethod.SHA256;// i added this to overcome error provided by sonaqube
        }
        return method;
    }

    private static boolean isDigestAvailable(String method) {
        try {
            MessageDigest.getInstance(method);
        } catch (Exception notAvailable) {
            return false;
        }

        return true;
    }

    public enum HashMethod {
         SHA512() {
            @Override
            public String getHashString() {
                return "SHA-512";
            }
        }, SHA384() {
            @Override
            public String getHashString() {
                return "SHA-384";
            }
        }, SHA256() {
            @Override
            public String getHashString() {
                return "SHA-256";
            }
        }, SHA1() {
            @Override
            public String getHashString() {
                return "SHA-1";
            }
        };

        public abstract String getHashString();

    }


    public static String toHex(byte[] data) {
        char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        final StringBuilder sb = new StringBuilder(data.length * 2);
        for (byte datum : data) {
            sb.append(DIGITS[(datum >>> 4) & 0x0F]);
            sb.append(DIGITS[datum & 0x0F]);
        }
        return sb.toString();
    }


}