package ma.boumlyk.onboarding.tools.security.tools;

import android.content.Context;
import android.util.Base64;


import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import ma.boumlyk.onboarding.tools.gson.GsonProvider;
import ma.boumlyk.onboarding.tools.security.Encryptor;
import timber.log.Timber;


public final class AESEncryptor {

    private static final String AES_MODE = "AES/GCM/NoPadding"; // "AES/GCM/NoPadding";
    public static final String SECRET_KEY_TYPE = "PBKDF2WithHmacSHA256";
    public static final int ITERATION_COUNT = 22;
    public static final int KEY_LENGTH = 256;
    public static final int GCM_TAG_LENGTH = 16;

    private static byte[] IV = {0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    public static String generateKey() {
        return HashManager.hashString(HashManager.GuidGenerator(), HashManager.HEX_FORM, HashManager.HashMethod.SHA256);
    }

    public static String encrypt(Context context, String key, String message) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretKeySpec keySpec = generateKeySpec(context, key);
        byte[] result = encrypt(keySpec, Base64.encode(message.getBytes(Encryptor.CHARSET), Base64.NO_WRAP));
        return Base64.encodeToString(result, Base64.NO_WRAP);
    }

    public static String decrypt(Context context, String key, String cipherMessage) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        SecretKeySpec keySpec = generateKeySpec(context, key);
        Timber.tag("RequestInterceptor").d("RequestInterceptor-keySpec ::: %s", GsonProvider.getInstance().toJson(keySpec.getEncoded()));
        byte[] cipherBytes = Base64.decode(cipherMessage, Base64.NO_WRAP);
        Timber.tag("RequestInterceptor").d("RequestInterceptor-cipherBytes ::: %s", Base64.encodeToString(cipherBytes, Base64.NO_WRAP));
        return new String(Base64.decode(decrypt(keySpec, cipherBytes), Base64.NO_WRAP));
    }

    public static SecretKeySpec generateKeySpec(Context context, final String key) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_TYPE);
        KeySpec spec = new PBEKeySpec(key.toCharArray(),
                context.getPackageName().getBytes(),
                ITERATION_COUNT,
                KEY_LENGTH);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private static byte[] encrypt(SecretKeySpec keySpec, byte[] message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);
        return cipher.doFinal(message);
    }

    private static byte[] decrypt(SecretKeySpec keySpec, byte[] cipherMessage) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(AES_MODE);
        GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH * 8, IV);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);
        return cipher.doFinal(cipherMessage);
    }


    private AESEncryptor() throws IllegalAccessException {
        throw new IllegalAccessException();
    }
}