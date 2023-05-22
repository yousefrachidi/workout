package ma.boumlyk.onboarding.ui.onboarding.accueil.home;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import ma.boumlyk.onboarding.adapters.OperationAdapter;
import ma.boumlyk.onboarding.models.Operation;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseViewModel;
import ma.boumlyk.onboarding.ui.onboarding.login.FLogin;
import ma.boumlyk.onboarding.ui.onboarding.registerInfo.FRegisterInfo;
import ma.boumlyk.onboarding.ui.onboarding.support.FSupport;

@HiltViewModel
public class FHomeViewModel extends BaseViewModel {

    OperationAdapter operationAdapter;

    @Inject
    public FHomeViewModel() {
    }

    public void initiateViewModel(BaseActivity requireActivity ) {
        super.initiateViewModel(requireActivity);
       // operationAdapter.feedData(getData());
    }

    private List<Operation> getData() {
        List<Operation> operations = new ArrayList<>();

        // Populate the list with dummy data
        operations.add(new Operation(1, 1621728000000L, 10.5));
        operations.add(new Operation(2, 1621641600000L, 15.25));
        operations.add(new Operation(3, 1621555200000L, 20.75));
        operations.add(new Operation(4, 1621468800000L, 12.0));
        operations.add(new Operation(5, 1621382400000L, 18.75));
        operations.add(new Operation(6, 1621296000000L, 9.99));
        operations.add(new Operation(7, 1621209600000L, 7.5));
        operations.add(new Operation(8, 1621123200000L, 21.5));
        operations.add(new Operation(9, 1621036800000L, 16.8));
        operations.add(new Operation(10, 1620950400000L, 11.25));
        return operations;
    }

    public void onCreateAccount() {
    }

    public void onNeedSupport() {
        fragment.postValue(new FSupport());
    }


    @Override
    public void onNextPressed() {
        super.onNextPressed();
        fragment.postValue(new FLogin());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fragment.postValue(new FRegisterInfo());
    }

    public OperationAdapter getAdapter(FragmentActivity requireActivity) {
       operationAdapter = new OperationAdapter(requireActivity, getData());
        return operationAdapter;
    }
}