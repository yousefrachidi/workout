package ma.boumlyk.onboarding.data.sources.remote.utils;

import androidx.annotation.NonNull;

import ma.boumlyk.onboarding.models.cookie.Cookies;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;


public class TokenAuthenticator implements Authenticator {

    private final Cookies cookies;

    public TokenAuthenticator(Cookies cookies) {
        this.cookies = cookies;
    }

//    public void setLoginService(LoginService loginService){
//        this.loginService = loginService;
//    }

    @Override
    public Request authenticate(Route route, @NonNull Response response){
        try{
          /*  if (loginService != null) {
                APIResponse<JsonObject> serviceResponse = loginService.handShake().blockingGet();
                if (serviceResponse.getStatusCode().equals("000")) {
                    return response.request().newBuilder()
                            .header("access_token", cookies.getAccessToken())
                            .build();
                }
            }*/
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

