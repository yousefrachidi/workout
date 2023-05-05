package ma.boumlyk.onboarding.data;


import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ma.boumlyk.onboarding.data.sources.local.db.dao.CookiesDao;
import ma.boumlyk.onboarding.models.cookie.Cookies;


@Singleton
public class CookiesRepository {

    private final CookiesDao cookiesDao;

    @Inject
    public CookiesRepository(CookiesDao cookiesDao) {
        this.cookiesDao = cookiesDao;
    }

    public Single<Cookies> getCookies() {
        return cookiesDao.exist(Cookies.ID).flatMap(exist -> {
            if (exist)
                return cookiesDao.getCookies(Cookies.ID);
            else
                return Single.just(new Cookies());
        });
    }

    public Single<Boolean> updateCookies(Cookies cookies) {
        return cookiesDao.insertOrUpdateCookies(cookies).flatMap(aLong -> Single.just(aLong != -1));
    }

}
