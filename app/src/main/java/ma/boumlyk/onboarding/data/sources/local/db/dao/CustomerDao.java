package ma.boumlyk.onboarding.data.sources.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import ma.boumlyk.onboarding.models.customer.Customer;

@Dao
public interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertOrUpdateCustomer(Customer customer);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<List<Long>> insertOrUpdateCustomers(List<Customer> customers);

    @Query("SELECT * FROM customers WHERE customerId = :customerId")
    Single<Customer> getCustomer(String customerId);

    @Query("SELECT * FROM customers")
    Single<List<Customer>> getCustomers();

    @Query("SELECT EXISTS(SELECT * FROM customers WHERE customerId = :customerId)")
    Single<Boolean> exist(String customerId);

    @Query("SELECT EXISTS(SELECT * FROM customers)")
    Single<Boolean> hasItem();

    @Query("DELETE FROM customers")
    Completable deleteAll();

}
