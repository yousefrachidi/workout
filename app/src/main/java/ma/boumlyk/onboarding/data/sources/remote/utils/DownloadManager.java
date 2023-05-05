package ma.boumlyk.onboarding.data.sources.remote.utils;

import android.content.Context;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import dagger.hilt.android.qualifiers.ApplicationContext;
import okhttp3.ResponseBody;
import retrofit2.Response;
import timber.log.Timber;


public class DownloadManager {

    @ApplicationContext
    Context applicationContext;

    public DownloadManager() {
    }

    public boolean processResponseBody(Response<ResponseBody> response, File folderName, String fileName) {
        ResponseBody body = response.body();
        if (body != null)
            return saveDownloadedFile(body.byteStream(), folderName, fileName);
        return false;
    }

    public boolean processResponseBody(Response<ResponseBody> response, String fileName) {
        ResponseBody body = response.body();
        try {
            Timber.tag("downloadAndGetFile").d("ResponseBody ::: " + body.byteStream().available());
        } catch (IOException e) {
           // e.printStackTrace();
        }

        if (body != null)
            return saveDownloadedFile(body.byteStream(), fileName);
        return false;
    }

    public boolean saveDownloadedFile(InputStream inputStream, String fileName) {
        try (InputStream bis = new BufferedInputStream(inputStream, 1024 * 8)){
            int count;
            byte[] data = new byte[1024 * 4];
            File outputFile = new File(fileName);
            try(OutputStream output = new FileOutputStream(outputFile)) {
                while ((count = bis.read(data)) != -1)
                    output.write(data, 0, count);
                return true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return false;
    }


    public boolean saveDownloadedFile(InputStream inputStream, File folderName, String fileName) {
        try (InputStream bis = new BufferedInputStream(inputStream, 1024 * 8)){
            int count;
            byte[] data = new byte[1024 * 4];
            File outputFile = new File(folderName, fileName);

            try(OutputStream output = new FileOutputStream(outputFile)){
                while ((count = bis.read(data)) != -1)
                    output.write(data, 0, count);
                return true;
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
        return false;
    }


}
