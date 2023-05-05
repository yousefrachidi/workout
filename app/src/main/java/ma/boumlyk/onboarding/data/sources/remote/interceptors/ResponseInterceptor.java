package ma.boumlyk.onboarding.data.sources.remote.interceptors;


import android.content.Context;

import androidx.annotation.NonNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import ma.boumlyk.onboarding.models.cookie.Cookies;
import ma.boumlyk.onboarding.tools.security.Encryptor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

@Singleton
public class ResponseInterceptor implements Interceptor {

    Context context;
    Cookies cookies;
    ErrorHandler handler;
    Encryptor encryptor;

    @Inject
    public ResponseInterceptor(@ApplicationContext Context context, Cookies cookies, Encryptor encryptor, ErrorHandler handler) {
        this.context = context;
        this.cookies = cookies;
        this.handler = handler;
        this.encryptor = encryptor;

    }

    @NonNull
    public Response intercept(@NonNull Chain chain) throws IOException {
        NetworkException obj;


        try {
            Request request = chain.request();

            Response response = chain.proceed(request);

            obj = handler.handleResponseCode(response);

            if (obj == null)
                return response;
        } catch (Exception e) {
            e.printStackTrace();
            Timber.tag("ResponseInterceptor").e(e);
            Timber.tag(ErrorHandler.class.getSimpleName()).d("API URL : %s", chain.request().url().url().toString());
            throw handler.handleExceptions(e);
        }

        Timber.tag(ErrorHandler.class.getSimpleName()).d("API URL : %s", chain.request().url().url().toString());
        throw obj;
    }




}
