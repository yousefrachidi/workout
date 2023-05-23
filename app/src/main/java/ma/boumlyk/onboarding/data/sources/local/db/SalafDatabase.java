package ma.boumlyk.onboarding.data.sources.local.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import net.sqlcipher.database.SupportFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.hilt.android.qualifiers.ApplicationContext;
import ma.boumlyk.onboarding.data.sources.local.db.dao.CookiesDao;
import ma.boumlyk.onboarding.data.sources.local.db.dao.CustomerDao;
import ma.boumlyk.onboarding.models.cookie.Cookies;
import ma.boumlyk.onboarding.models.customer.Customer;
import ma.boumlyk.onboarding.tools.security.checkers.signature.SignatureDetector;
import ma.boumlyk.onboarding.tools.security.tools.HashManager;


@Database(entities = {Cookies.class, Customer.class,}, version = 03, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class SalafDatabase extends RoomDatabase {

    public abstract CookiesDao cookiesDao();

    public abstract CustomerDao customerDao();

    private static SalafDatabase instance = null;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static synchronized SalafDatabase getInstance(@ApplicationContext Context context) {

            synchronized (SalafDatabase.class) {
                if (instance == null)
                    instance = buildDatabase(context);
            }
        return instance;
    }

    private static SalafDatabase buildDatabase(Context context) {
        final byte[] passphrase = HashManager.hashString(SignatureDetector.getCurrentSignature(context)+ context.getPackageName(), HashManager.HEX_FORM).getBytes();
        final SupportFactory factory = new SupportFactory(passphrase);
        return Room.databaseBuilder(context, SalafDatabase.class, "boumlyk-db")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                    }
                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                    }
                })
                .fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build();
    }

    public static void destroyDatabase() {
        instance = null;
    }

}
