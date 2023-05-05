package ma.boumlyk.onboarding.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import ma.boumlyk.onboarding.data.sources.local.db.IAMDatabase;
import ma.boumlyk.onboarding.data.sources.local.db.dao.CookiesDao;

@Module
@InstallIn(SingletonComponent.class)
public class DBModule {


    @Provides
    @Singleton
    public static IAMDatabase getIAMDatabase(@ApplicationContext Context context) {
        return IAMDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    public static CookiesDao getCookiesDao(IAMDatabase database) {
        return database.cookiesDao();
    }
}
