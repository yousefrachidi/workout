package ma.boumlyk.onboarding.tools.security;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import ma.boumlyk.onboarding.tools.security.checkers.application.ApplicationDetector;
import ma.boumlyk.onboarding.tools.security.checkers.signature.SignatureDetector;
import ma.boumlyk.onboarding.tools.security.tools.AESEncryptor;
import ma.boumlyk.onboarding.tools.security.tools.HashManager;
import timber.log.Timber;

@Singleton
public class Encryptor {

    public static final String CHARSET = "UTF-8";

    Context context;

    @Inject
    public Encryptor(@ApplicationContext Context context) {
        this.context = context;
    }

    public String encryptData(String data) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, NoSuchProviderException, InvalidKeyException, UnsupportedEncodingException {
        return AESEncryptor.encrypt(context, getKey(context), data);
    }

    public String decryptData(String encryptedData) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, NoSuchProviderException, InvalidKeyException {
        return AESEncryptor.decrypt(context, getKey(context), encryptedData);
    }

    public String getKey(Context context) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.FRANCE);
            df.setTimeZone(TimeZone.getTimeZone("gmt"));
            String sb = SignatureDetector.getCurrentSignature(context) +
                    ApplicationDetector.getCurrentApplicationId(context) +
                    df.format(new Date(0L));
            String key = HashManager.toHex(MessageDigest.getInstance("SHA-256").digest(sb.getBytes())).toLowerCase();
            Timber.tag("RequestInterceptor").d("RequestInterceptor- getKey0 ::: %s", key);
            return key;
        } catch (Exception e) {
            Timber.tag("getAPIKey").e(e);
            return "";
        }
    }

    public  String processPassword(String password) {
        try{
            return encryptData(HashManager.hashString(password, HashManager.HEX_FORM, HashManager.HashMethod.SHA256));
        }catch (Exception e){
           return "";
        }
    }
}