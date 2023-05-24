package ma.boumlyk.onboarding.ui.onboarding.checkPhone;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.chaos.view.PinView;
import com.davidmiguel.numberkeyboard.NumberKeyboardListener;

import ma.boumlyk.onboarding.R;
import ma.boumlyk.onboarding.databinding.FCheckPhoneBinding;
import ma.boumlyk.onboarding.databinding.FRegisterPhoneBinding;
import ma.boumlyk.onboarding.ui.BaseActivity;
import ma.boumlyk.onboarding.ui.BaseFragment;
import ma.boumlyk.onboarding.ui.onboarding.uploadDoc.FUploadDoc;

public class FCheckPhone extends BaseFragment {


    FCheckPhoneBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.f_check_phone, container, false);
        viewModel = new ViewModelProvider(this).get(FCheckPhoneViewModel.class);
        binding.setViewModel((FCheckPhoneViewModel) viewModel);
        binding.setLifecycleOwner(this);
        initiateView();
        initiateObservers();
        return binding.getRoot();
    }

    private void initiateObservers() {
        ((FCheckPhoneViewModel) viewModel).initiateViewModel((BaseActivity) requireActivity() );
        viewModel.actions.observe(requireActivity(), actions -> {
            for (String action : actions) {
                switch (action) {
                     case BaseActivity.ACTION_FINISH_LOADING:
                        onFinishLoading();
                        break;
                    case BaseActivity.ACTION_START_LOADING:
                        onStartLoading();
                        break;
                }
            }
        });
        viewModel.animateViewFromBottomToTop(binding.relativeLayout2);

    }

    private void initiateView() {

        binding.keyword.setListener(new NumberKeyboardListener() {
            @Override
            public void onNumberClicked(int i) {
                binding.pinView.setText(binding.pinView.getText() +""+ i);
            }

            @Override
            public void onLeftAuxButtonClicked() {
            }

            @Override
            public void onRightAuxButtonClicked() {

                String text =  binding.pinView.getText().toString();
                if (text.length() > 0) {
                    String newText = text.substring(0, text.length() - 1);
                    binding.pinView.setText(newText);
                    binding.pinView.setSelection(newText.length());
                }

            }
        });



        binding.pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No action needed
            }

            @Override
            public void afterTextChanged(Editable s) {
                String otp = s.toString();
                if (otp.length() == binding.pinView.getItemCount()) {

                    if (otp.equals("0000")) {
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment, new FUploadDoc(), new FUploadDoc().getTag())
                                .addToBackStack(null)
                                .commit();
                    }else {
                        Toast.makeText(requireContext(), "Entered OTP: 0000" , Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void slideDown(View view) {
        view.setVisibility(View.INVISIBLE);
        TranslateAnimation animate = new TranslateAnimation(0, 0, view.getHeight(), 0);
        animate.setDuration(1500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
      //  view.setVisibility(View.VISIBLE);
    }




    private void onStartLoading() {
       // UITools.setOnLoadingState(true, requireActivity(), binding.footerLayout.btnCnx, R.id.btn_progress, R.id.btn_next);
    }

    private void onFinishLoading() {
        //UITools.setOnLoadingState(false, requireActivity(), binding.footerLayout.btnCnx, R.id.btn_progress, R.id.btn_next);
    }
}