package ma.boumlyk.onboarding.models.customer;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

import com.google.gson.annotations.SerializedName;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
@Entity(tableName = "customers")
public class Customer extends Person {


    @NonNull
    @PrimaryKey
    @SerializedName("customerId")
    String customerId;

    @Inject
    public Customer() {
    }

    public Customer(String firstName, String lastName, String country, String city, String address, String phone, String email, @NonNull String customerId) {
        super(firstName, lastName, country, city, address, phone, email);
        this.customerId = customerId;
    }

    @NonNull
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(@NonNull String customerId) {
        this.customerId = customerId;
    }
}
