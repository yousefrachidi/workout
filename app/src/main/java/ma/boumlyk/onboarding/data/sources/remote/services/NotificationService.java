package ma.boumlyk.onboarding.data.sources.remote.services;

import java.util.List;

import io.reactivex.Single;
import ma.boumlyk.onboarding.data.sources.remote.utils.APIResponse;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface NotificationService {

    String GET_NOTIFICATION_URL_SUFFIX = "notification/getUserNotifications";
    String CHANGE_STATUS_NOTIFICATION_URL_SUFFIX = "notification/notificationStatus";

    @GET(GET_NOTIFICATION_URL_SUFFIX)
    Single<APIResponse<List<String>>> getNotifications();

    @POST(CHANGE_STATUS_NOTIFICATION_URL_SUFFIX)
    Single<APIResponse<List<String>>> changeStatusNotification(@Body RequestBody body);

}
