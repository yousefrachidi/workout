package ma.boumlyk.onboarding.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import ma.boumlyk.onboarding.data.sources.local.db.SalafDatabase;
import ma.boumlyk.onboarding.data.sources.local.db.dao.CookiesDao;
import ma.boumlyk.onboarding.data.sources.local.db.dao.CustomerDao;

@Module
@InstallIn(SingletonComponent.class)
public class DBModule {


    @Provides
    @Singleton
    public static SalafDatabase getIAMDatabase(@ApplicationContext Context context) {
        return SalafDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    public static CookiesDao getCookiesDao(SalafDatabase database) {
        return database.cookiesDao();
    }

    @Provides
    @Singleton
    public static CustomerDao getCustomerDao(SalafDatabase database) {
        return database.customerDao();
    }

}
