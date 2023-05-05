package ma.boumlyk.onboarding.di;

import android.content.Context;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import ma.boumlyk.onboarding.data.CookiesRepository;
import ma.boumlyk.onboarding.data.sources.local.FileManager;
import ma.boumlyk.onboarding.models.cookie.Cookies;
import ma.boumlyk.onboarding.tools.firebase.IAMNotificationManager;
import ma.boumlyk.onboarding.tools.gson.GsonProvider;
import ma.boumlyk.onboarding.tools.permissions.PermissionManager;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {

    private Context context;

    public AppModule() { }

    @Provides
    @Singleton
    public Gson provideGson() {
        return GsonProvider.getInstance();
    }

    @Provides
    @Singleton
    public Cookies provideCookies(@ApplicationContext Context appContext, CookiesRepository cookiesRepository, FileManager fileManager) {
        return new Cookies(appContext, cookiesRepository, fileManager);
    }

    @Provides
    @Singleton
    public IAMNotificationManager provideFirebaseManager(@ApplicationContext Context appContext, Cookies cookies) {
        return new IAMNotificationManager(appContext, cookies);
    }

    @Provides
    @Singleton
    public PermissionManager providePermissionManager(@ApplicationContext Context appContext) {
        return new PermissionManager(appContext);
    }

}
