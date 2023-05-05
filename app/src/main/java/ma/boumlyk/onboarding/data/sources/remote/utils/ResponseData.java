package ma.boumlyk.onboarding.data.sources.remote.utils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class ResponseData<T> {

    @SerializedName("customerExist")
    Boolean customerExist;
    @SerializedName("Customer")
    private T customer;

    public Boolean getCustomerExist() {
        return customerExist;
    }

    public void setCustomerExist(Boolean customerExist) {
        this.customerExist = customerExist;
    }



    public T getCustomer() {
        return customer;
    }

    public void setCustomer(T customer) {
        this.customer = customer;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
