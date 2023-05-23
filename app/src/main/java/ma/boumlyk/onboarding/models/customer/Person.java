package ma.boumlyk.onboarding.models.customer;

import com.google.gson.annotations.SerializedName;

import ma.boumlyk.onboarding.models.tools.Model;

public class Person extends Model {

    @SerializedName("firstName")
    protected String firstName;

    @SerializedName("lastName")
    protected String lastName;

    @SerializedName("fullName")
    protected String fullName;



    @SerializedName("country")
    protected String country;

    @SerializedName("city")
    protected String city;

    @SerializedName("address")
    protected String address;

    @SerializedName("phone")
    protected String phone;

    @SerializedName("email")
    protected String email;

    public Person() {
    }

    public Person(String firstName, String lastName, String country, String city, String address, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
