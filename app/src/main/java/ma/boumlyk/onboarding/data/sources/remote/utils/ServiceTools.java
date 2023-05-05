package ma.boumlyk.onboarding.data.sources.remote.utils;

import android.content.Context;

import androidx.annotation.NonNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

@Singleton
public class ServiceTools {


    Context applicationContext;

    @Inject
    public ServiceTools(@ApplicationContext Context base) {
        applicationContext = base;
    }


    public List<MultipartBody.Part> prepareFileParts(/*Set<DocumentFile> files*/) {
        List<MultipartBody.Part> parts = new ArrayList<>();
//        for (DocumentFile file : files)
//            parts.add(prepareFilePart(file.getReferenceId(), new File(file.getAbsoluteFileName())));
        return parts;
    }




    @NonNull
    public RequestBody prepareStringPart(String descriptionString) {
        return RequestBody.create(descriptionString, MultipartBody.FORM); // MediaType.parse("application/json")
    }

    @NonNull
    public MultipartBody.Part prepareFilePart(String partName, File file) {
        return MultipartBody.Part.createFormData(partName, file.getName(), RequestBody.create(file, MediaType.parse("application/octet-stream")));
    }

}
