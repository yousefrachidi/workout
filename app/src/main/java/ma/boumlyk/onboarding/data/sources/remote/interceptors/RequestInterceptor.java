package ma.boumlyk.onboarding.data.sources.remote.interceptors;


import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import ma.boumlyk.onboarding.BuildConfig;
import ma.boumlyk.onboarding.models.cookie.Cookies;
import ma.boumlyk.onboarding.tools.firebase.IAMNotificationManager;
import ma.boumlyk.onboarding.tools.gson.GsonProvider;
import ma.boumlyk.onboarding.tools.security.AuthenticityChecker;
import ma.boumlyk.onboarding.tools.security.Encryptor;
import ma.boumlyk.onboarding.tools.security.checkers.signature.SignatureDetector;
import ma.boumlyk.onboarding.ui.onboarding.OnboardingActivity;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

@Singleton
public class RequestInterceptor implements Interceptor {

    Cookies cookies;
    Context context;
    Encryptor encryptor;
    ErrorHandler handler;
    IAMNotificationManager notificationManager;

    @Inject
    public RequestInterceptor( Cookies cookies, Context context, Encryptor encryptor, ErrorHandler handler, IAMNotificationManager notificationManager) {
        this.cookies = cookies;
        this.context = context;
        this.encryptor = encryptor;
        this.handler = handler;
        this.notificationManager = notificationManager;
    }

    @NonNull
    public Response intercept(@NonNull Chain chain) throws IOException {

        if (!AuthenticityChecker.isSafeEnvironment(context)) {
            new Handler(Looper.getMainLooper()).postDelayed(this::logOut, 1000);
            throw handler.handleExceptions(new NetworkException("999", "APK not allowed ...", false));
        } else {
            Request originalRequest = chain.request();
            Map<String, String> request_headers = getRequestHeaders(originalRequest);
            Request.Builder builder = originalRequest.newBuilder();
            for (Map.Entry<String, String> entry : request_headers.entrySet())
                if (entry.getValue() != null)
                    builder.header(entry.getKey(), entry.getValue());
            HttpUrl.Builder url_builder = originalRequest.url().newBuilder();
            Request request = builder.url(url_builder.build()).build();
            return chain.proceed(request);
        }
    }


    private Map<String, String> getRequestHeaders(Request originalRequest) {
        Map<String, String> headers = new HashMap<>();
        headers.put("group_id", Cookies.GROUP_ID);
        headers.put("institution_id", Cookies.INSTITUTION_ID);
        headers.put("channel_id", Cookies.CHANNEL_ID); //  Cookies.CHANNEL_ID
        headers.put("sub_channel_id", Cookies.SUB_CHANNEL_ID);
        headers.put("application_id", cookies.getApplicationId());
        headers.put("device_id", cookies.getDeviceId());
        headers.put("installation_id", cookies.getInstallationId());
        headers.put("version_build_date", BuildConfig.BUILD_DATE);
        headers.put("version_build_timestamp", "" + BuildConfig.BUILD_TIMESTAMP);
        return headers;

    }


    private String getAPIKey(String url) {
        try {
            Gson gson = GsonProvider.getInstance();
            JsonObject json = new JsonObject();
            long time = cookies.getSynchronizedTimeStamp();
//            long time = System.currentTimeMillis();
            json.addProperty("device_id", cookies.getDeviceId());
            json.addProperty("application_id", cookies.getApplicationId());
            json.addProperty("channel_id", Cookies.CHANNEL_ID);
            json.addProperty("timestamp", time);
            json.addProperty("api_name", url.replaceAll(BuildConfig.BACKEND_BASE_URL, ""));
            json.addProperty("signature", SignatureDetector.getTempSignature(context, time));
            return encryptor.encryptData(gson.toJson(json));
        } catch(Exception e){
            return "";
        }
    }

    public void logOut() {
        cookies.initAppScope();
        cookies.setAccessToken(null);
//        cookies.initCustomerScope(context.getPackageName());
        Intent myIntent = new Intent(context, OnboardingActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(myIntent);
    }

    private Request appendToRequestBody(Request originalRequest, Map<String, String> parameters) {
        try {
            String subtype = Objects.requireNonNull(Objects.requireNonNull(originalRequest.body()).contentType()).subtype();
            if (subtype.contains("json")) {
                return appendToJsonRequestBody(originalRequest, parameters);
            } else if (subtype.contains("form")) {
                return appendToFormDataRequestBody(originalRequest, parameters);
            }
        } catch (Exception ignored) {
        }
        return originalRequest;
    }

    private Request appendToJsonRequestBody(Request originalRequest, Map<String, String> parameters) {
        try {
            RequestBody requestBody = Objects.requireNonNull(originalRequest.body());
            JSONObject obj = new JSONObject(bodyToString(requestBody));
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                if (!obj.has(entry.getKey()))
                    obj.put(entry.getKey(), entry.getValue());
            }
            RequestBody temRequestBody = RequestBody.create(requestBody.contentType(), obj.toString());
            return originalRequest.newBuilder().method(originalRequest.method(), temRequestBody).build();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return originalRequest;
    }

    private Request appendToFormDataRequestBody(Request originalRequest, Map<String, String> parameters) {
        try {
            MultipartBody requestBody = (MultipartBody) Objects.requireNonNull(originalRequest.body());
            List<MultipartBody.Part> requestBodyParts = requestBody.parts();
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder(requestBody.boundary());
            bodyBuilder = bodyBuilder.setType(requestBody.type());
            for (MultipartBody.Part part : requestBodyParts) {
                bodyBuilder.addPart(part);
            }
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                bodyBuilder.addFormDataPart(entry.getKey(), entry.getValue());
            }
            MultipartBody formBody = bodyBuilder.build();
            originalRequest = originalRequest.newBuilder().method(originalRequest.method(), formBody).build();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return originalRequest;
    }

    private String bodyToString(final RequestBody request) {
        try(Buffer buffer = new Buffer()) {

            if (request != null)
                request.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return null;
    }


}
