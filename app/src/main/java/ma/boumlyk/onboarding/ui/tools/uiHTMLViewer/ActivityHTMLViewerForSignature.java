package ma.boumlyk.onboarding.ui.tools.uiHTMLViewer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebView;

import dagger.hilt.android.AndroidEntryPoint;
import ma.boumlyk.onboarding.ui.BaseActivity;

@AndroidEntryPoint
public class ActivityHTMLViewerForSignature extends BaseActivity {


    private ProgressDialog progressDialog;
    WebView webView;

    Dialog privacyPolicyDialog;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* binding = DataBindingUtil.setContentView(this, R.layout.activity_htmlviewer_for_signature);
        viewModel = new ViewModelProvider(ActivityHTMLViewerForSignature.this).get(ActivityHTMLViewerForSignatureViewModel.class);
        binding.setViewModel((ActivityHTMLViewerForSignatureViewModel) viewModel);
        binding.setLifecycleOwner(this);
        initiateView();
        initiateObservers();*/
    }

  /*  @SuppressLint("SetJavaScriptEnabled")
    private void initiateView() {
        webView=(WebView)binding.htmlView;
        WebSettings settings = webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        settings.setDefaultFontSize(18);
        webView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    progressDialog.show();
                }
                if (progress == 100) {
                    progressDialog.dismiss();
                }
            }
        });

    }*/

    /*private void initiateObservers() {
        viewModel.actions.observe(ActivityHTMLViewerForSignature.this, actions -> {
            for (String action : actions) {
                switch (action) {
                    case ACTION_START_LOADING_DIALOG:
                        onStartLoadingDialog();
                        break;
                    case ACTION_FINISH_LOADING_DIALOG:
                        onFinishLoadingDialog();
                        break;
                    case ACTION_START_LOADING:
                        onStartLoading();
                        break;
                    case ACTION_FINISH_LOADING:
                        onFinishLoading();
                        break;
                    case ACTION_OPEN_SIGNATURE_DIALOG:
                        openDialogToSign();
                        break;
                }
            }
        });


    }

    private void onStartLoadingDialog() {
        if (progressDialog == null) {
            binding.checkboxAccept.setEnabled(false);
            progressDialog = Utils.createProgressDialog(this);
            progressDialog.show();
        }
    }

    private void onFinishLoadingDialog() {
        binding.checkboxAccept.setEnabled(true);
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
*/
   /* private void onStartLoading() {
        binding.checkboxAccept.setEnabled(false);
        UITools.setOnLoadingState(true, binding.lyNext);
    }

    private void onFinishLoading() {
        binding.checkboxAccept.setEnabled(true);
        UITools.setOnLoadingState(false, binding.lyNext);
    }


    public void openDialogToSign() {
        ActivityHTMLViewerForSignatureViewModel dialogViewModel = ((ActivityHTMLViewerForSignatureViewModel) viewModel);
        Dialog signatureDialog = new Dialog(this, R.style.AlertDialog);
        DialogSignatureBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_signature, null, false);
        signatureDialog.setContentView(dialogBinding.getRoot());
        signatureDialog.setCancelable(false);
        dialogBinding.signaturePad.setPenColor(Color.BLACK);
        signatureDialog.show();
    }

    public void openDialogPrivacPolicy() {
        ActivityHTMLViewerForSignatureViewModel dialogViewModel = ((ActivityHTMLViewerForSignatureViewModel) viewModel);
        privacyPolicyDialog = new Dialog(this, R.style.AlertDialog);
        DialoguePrivacyPolicyBinding dialogBinding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialogue_privacy_policy, null, false);
        privacyPolicyDialog.setContentView(dialogBinding.getRoot());
        privacyPolicyDialog.setCancelable(false);
        dialogBinding.setViewModel(dialogViewModel);
        dialogBinding.imgClose.setOnClickListener(v -> {
        });
        dialogBinding.pdfView.fromAsset("privacy_policy.pdf").load();
        privacyPolicyDialog.create();
        privacyPolicyDialog.show();
    }

    private void dissmisDialogue(){
        if (privacyPolicyDialog!=null && privacyPolicyDialog.isShowing()){
            privacyPolicyDialog.dismiss();
        }
    }*/



}
