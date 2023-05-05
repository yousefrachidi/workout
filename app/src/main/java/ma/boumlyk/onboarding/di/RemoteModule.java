package ma.boumlyk.onboarding.di;


import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import io.reactivex.plugins.RxJavaPlugins;
import ma.boumlyk.onboarding.BuildConfig;
import ma.boumlyk.onboarding.data.sources.remote.APISettings;
import ma.boumlyk.onboarding.data.sources.remote.HttpsTrustManager;
import ma.boumlyk.onboarding.data.sources.remote.interceptors.ErrorHandler;
import ma.boumlyk.onboarding.data.sources.remote.interceptors.RequestInterceptor;
import ma.boumlyk.onboarding.data.sources.remote.interceptors.ResponseInterceptor;
import ma.boumlyk.onboarding.data.sources.remote.services.NotificationService;
import ma.boumlyk.onboarding.data.sources.remote.utils.TokenAuthenticator;
import ma.boumlyk.onboarding.models.cookie.Cookies;
import ma.boumlyk.onboarding.tools.firebase.IAMNotificationManager;
import ma.boumlyk.onboarding.tools.gson.GsonProvider;
import ma.boumlyk.onboarding.tools.message.Messenger;
import ma.boumlyk.onboarding.tools.security.Encryptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
@InstallIn(SingletonComponent.class)
public class RemoteModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit(Cookies cookies, Encryptor encryptor, @ApplicationContext Context context, Messenger messenger, IAMNotificationManager notificationManager) {

        TokenAuthenticator authenticator = new TokenAuthenticator(cookies);
                RxJavaPlugins.setErrorHandler(throwable -> {
            Timber.e(throwable, ErrorHandler.class.getSimpleName());
        });

        OkHttpClient.Builder httpClientBuilder = HttpsTrustManager.getUnsafeOkHttpClient() // HttpsTrustManager.getUnsafeOkHttpClient()
                .connectTimeout(APISettings.CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(APISettings.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(APISettings.WRITE_TIMEOUT, TimeUnit.SECONDS);

        httpClientBuilder.interceptors().clear();

        if (BuildConfig.DEBUG)
            httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));

        httpClientBuilder.addInterceptor(new RequestInterceptor(cookies, context, encryptor, new ErrorHandler(messenger), notificationManager));
        httpClientBuilder.addInterceptor(new ResponseInterceptor(context, cookies, encryptor, new ErrorHandler(messenger)));
        httpClientBuilder.followRedirects(false);
        httpClientBuilder.followSslRedirects(false);
        httpClientBuilder.authenticator(authenticator);

        OkHttpClient okHttpClient = httpClientBuilder.build();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BuildConfig.BACKEND_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(GsonProvider.getInstance()));

        Retrofit retrofitClient = retrofitBuilder.client(okHttpClient).build();
//        authenticator.setLoginService(retrofitClient.create(LoginService.class));
        return retrofitClient;

    }
    @Provides
    @Singleton
    public NotificationService provideNotificationService(Retrofit retrofit) {
        return retrofit.create(NotificationService.class);
    }


}
