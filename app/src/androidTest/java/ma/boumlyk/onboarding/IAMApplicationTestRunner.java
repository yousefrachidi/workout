package ma.boumlyk.onboarding;

import android.app.Application;
import android.content.Context;

import androidx.test.runner.AndroidJUnitRunner;

import dagger.hilt.android.testing.HiltTestApplication;
import timber.log.Timber;

// A custom runner to set up the instrumented application class for tests.
public final class IAMApplicationTestRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
            Timber.plant();
        return super.newApplication(cl, HiltTestApplication.class.getName(), context);
    }
}