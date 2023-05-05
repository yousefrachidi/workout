package ma.boumlyk.onboarding.data;


import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;
import ma.boumlyk.onboarding.data.sources.remote.services.NotificationService;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Singleton
public class NotificationRepository {

    private final NotificationService notificationService;


    @Inject
    public NotificationRepository(NotificationService notificationService) {
        this.notificationService = notificationService;
    }


    public Single<List<String>> getNotifications() {
        return notificationService.getNotifications().flatMap(serviceResponse -> {
            if (serviceResponse.getStatusCode().equals("000")) {
                return Single.just(serviceResponse.getResponseData());
            } else {
                return Single.error(new Exception("Notification fetch failed !"));
            }
        });
    }

    public Single<List<String>> changeStatusNotification(String notificationId,String status) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("notificationId", notificationId)
                .addFormDataPart("status", status)
                .build();

        return notificationService.changeStatusNotification(requestBody).flatMap(serviceResponse -> {
            if (serviceResponse.getStatusCode().equals("000")) {
                return Single.just(serviceResponse.getResponseData());
            } else {
                return Single.error(new Exception("Notification fetch failed !"));
            }
        });
    }

}


