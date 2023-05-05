package ma.boumlyk.onboarding.data.sources.remote.utils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Single;


public class APIResponse<T> {

    @SerializedName("status_code")
    String statusCode;
    @SerializedName("status_label")
    String statusLabel;
    @SerializedName("response_data")
    private T responseData;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }


    public <Z> Single<Z> onFailedRequest(boolean sentSuccessfully) {
        return Single.error(new FailedResponse(statusCode, statusLabel, sentSuccessfully));
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
