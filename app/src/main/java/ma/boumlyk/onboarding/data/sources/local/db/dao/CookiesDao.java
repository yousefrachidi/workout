package ma.boumlyk.onboarding.data.sources.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import io.reactivex.Completable;
import io.reactivex.Single;
import ma.boumlyk.onboarding.models.cookie.Cookies;

@Dao
public interface CookiesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertOrUpdateCookies(Cookies cookies);

    @Query("SELECT * FROM myCookies WHERE cookiesId = :cookies_id")
    Single<Cookies> getCookies(String cookies_id);

    @Query("SELECT EXISTS(SELECT * FROM myCookies WHERE cookiesId = :cookies_id)")
    Single<Boolean> exist(String cookies_id);

    @Query("SELECT EXISTS(SELECT * FROM myCookies)")
    Single<Boolean> hasItem();

    @Query("DELETE FROM myCookies")
    Completable deleteAll();


}