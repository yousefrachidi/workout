package ma.boumlyk.onboarding.data.sources.local;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.Single;
import timber.log.Timber;

@Singleton
public class FileManager {

    final String MAIN_DIRECTORY_NAME = "IAM@Moroco";
    final String PRODUCTS_DIRECTORY = "products";
    final String DOCUMENTS_DIRECTORY = "documents";
    final String DOWNLOADS_DIRECTORY = "downloads";
    final String SHARE_DIRECTORY = "shared_files";

    public static final String TEMPLATE_FILE_NAME = "iamTemplate.jpg";

    public static int COMPRESS_QUALITY = 80;

    public static File mainDirectory = null;
    public static File productsDirectory = null;
    public static File downloadsDirectory = null;
    public static File documentsDirectory = null;
    public static File shareDirectory = null;

    @Inject
    public FileManager(@ApplicationContext Context applicationContext) {
        try {
            mainDirectory = new File(applicationContext.getFilesDir().getParentFile(), MAIN_DIRECTORY_NAME);
            downloadsDirectory = new File(mainDirectory, DOWNLOADS_DIRECTORY);
            productsDirectory = new File(mainDirectory, PRODUCTS_DIRECTORY);
            shareDirectory = new File(applicationContext.getFilesDir(), SHARE_DIRECTORY);
            downloadsDirectory.mkdirs();
            productsDirectory.mkdirs();
            mainDirectory.mkdirs();
            shareDirectory.mkdirs();

            copyTemplateFiles(applicationContext);

        } catch (Exception e) {
            Timber.d("Exception .... ");
        }
    }

    public void copyTemplateFiles(Context applicationContext) {

    }

    public void feedCustomerId(String customerId) {
        try {
            documentsDirectory = new File(mainDirectory.getAbsolutePath() + File.separator + customerId + File.separator + DOCUMENTS_DIRECTORY);
            documentsDirectory.mkdirs();
        } catch (Exception ignored) {
            Timber.d("Exception .... ");

        }
    }


    public void renameCustomerId(String oldCustomerId, String newCustomerId) {
        try{
            String oldPath = FileManager.mainDirectory + File.separator + oldCustomerId;
            String newPath = FileManager.mainDirectory + File.separator + newCustomerId;
            boolean isRename = false;
            File fileChanged = new File(newPath);
            if (new File(oldPath).exists()) {
                isRename = new File(oldPath).renameTo(fileChanged);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static String copyDocumentFile(String infileName, String referenceId) {

        Timber.tag("infileNameinfileName").d(" : %s", infileName);

        try {
            int read;
            String outfileName;
            byte[] buffer = new byte[1024];
            long modificationDate = System.currentTimeMillis();
            if (infileName.lastIndexOf(".") != -1)
                outfileName = documentsDirectory.getAbsolutePath() + File.separator + referenceId + "_" + modificationDate + infileName.substring(infileName.lastIndexOf("."));
            else
                outfileName = documentsDirectory.getAbsolutePath() + File.separator + referenceId + "_" + modificationDate;

            Timber.tag("ckeckFile").d(" :isfile exist : %s", new File(outfileName).exists());
            try (InputStream in=new FileInputStream(infileName)){
                    try(OutputStream out = new FileOutputStream(outfileName)) {
                        while ((read = in.read(buffer)) != -1)
                            out.write(buffer, 0, read);
                    }catch (IOException e){
                        Timber.d("Exception .... ");
                    }
                return outfileName;
            }
        } catch (Exception ignored) {
            return null;
        }
    }

    public static String copyDocumentFile(Bitmap bmp, String referenceId) {
        File file;

            long modificationDate = System.currentTimeMillis();
            String outfileName = documentsDirectory.getAbsolutePath() + File.separator + referenceId + "_" + modificationDate + ".jpg";

            file = new File(outfileName); // the File to save , append increasing numeric counter to prevent files from getting overwritten.
            try(OutputStream fOut = new FileOutputStream(file)){
                bmp.compress(Bitmap.CompressFormat.JPEG, COMPRESS_QUALITY, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                return outfileName;
            } catch (FileNotFoundException e) {
                Timber.d("Exception .... ");
            } catch (IOException e) {
                Timber.d("Exception .... ");
            }

        return null;
    }


    public static Single<Boolean> sharePDFFileDoc(Context context, String absoluteFileName) {
        return Single.create(subscriber -> {
            try {
                Uri pdfUri;
                File folder = new File(context.getFilesDir(), "shared_files");
                if (!folder.exists())
                    folder.mkdirs();
                pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", new File(absoluteFileName));
                Intent share = new Intent();
                share.setAction(Intent.ACTION_SEND);
                share.setType("application/pdf");
                share.putExtra(Intent.EXTRA_STREAM, pdfUri);
                context.startActivity(Intent.createChooser(share, "Share"));
                subscriber.onSuccess(true);
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });

    }


    public static Single<Boolean> exportPDFFile(Context context, String absoluteFileName) {
        return Single.create(subscriber -> {
            try {
                Uri pdfUri;
                Intent intent;
                File pdfFile = new File(absoluteFileName);
                pdfUri = FileProvider.getUriForFile(context, context.getPackageName() + ".provider", pdfFile);
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(pdfUri, "application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                Intent my_intent = Intent.createChooser(intent, "IAM ");
                context.startActivity(my_intent);
                subscriber.onSuccess(true);
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });

    }


    public static Single<Boolean> downlaodPDFFileDoc(Context context, String absoluteFileName) {
        return Single.create(subscriber -> {
            try {
                Uri pdfUri;
                File folder = new File(context.getFilesDir(), "download_file");
                if (!folder.exists())
                    folder.mkdirs();
                pdfUri = FileProvider.getUriForFile(context, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString(), new File(absoluteFileName));

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(pdfUri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Intent my_intent = Intent.createChooser(intent, "IAM ");
                context.startActivity(my_intent);

                subscriber.onSuccess(true);
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });

    }


    //Method to generate a MIME Type
    private static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////


    public File getMainDirectory() {
        return mainDirectory;
    }

    public File getProductsDirectory() {
        return productsDirectory;
    }

    public File getDownloadsDirectory() {
        return downloadsDirectory;
    }

    public File getDocumentsDirectory() {
        return documentsDirectory;
    }
}
