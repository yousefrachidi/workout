package ma.boumlyk.onboarding.ui.tools;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UITools {
    public static void setOnLoadingState(boolean is_on_loading_state, RelativeLayout lyParent) {
        try {
            for (int i = 0; i < lyParent.getChildCount(); i++) {
                View view = lyParent.getChildAt(i);
                if (view instanceof ProgressBar) {
                    //show progress
                    view.setVisibility(is_on_loading_state ? View.VISIBLE : View.GONE);
                } else if (view instanceof TextView) {
                    //opacity txt
                    view.setAlpha(is_on_loading_state ? 0.1f : 1);
                }
                //disable btn
                lyParent.setEnabled(!is_on_loading_state);
            }

        } catch (Exception e) {
            // e.printStackTrace();
        }

    }

}
