package ma.boumlyk.onboarding.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.text.TextUtilsCompat;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


    private static SecureRandom random = new SecureRandom();
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    static final Pattern VALID_PHONE_REGEX1 = Pattern.compile("^06\\d{8}$");
    static final Pattern VALID_PHONE_REGEX2 = Pattern.compile("^07\\d{8}$");
    static final Pattern VALID_Code_REGEX = Pattern.compile("^\\d{4}$");

    public static final long INVALID_DATE = 9999999999999L;

    public static String mainDirectoryName = "SkyID@InDataCore";

    public static File mainDirectory = null;

    public static CountDownTimer countDownTimer;

    public static void Init(Context activity) {
        mainDirectory = new File(activity.getFilesDir().getParentFile(), mainDirectoryName);
        if (!mainDirectory.exists()) mainDirectory.mkdirs();
    }


    public static Date getFormattedDate(String strDate) {
        try {
            strDate = strDate.replaceAll("[^/0123456789]", "");
            return new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).parse(strDate);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }

    public static String getDate(long time) {
        if (time == INVALID_DATE)
            return "";
        try {
            Calendar calendar = Calendar.getInstance(Locale.FRANCE);
            calendar.setTimeInMillis(time);
            calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
            return DateFormat.format("dd/MM/yyyy", calendar).toString();
        } catch (Exception ignored) {
            return "";
        }
    }

    public static String getDate2(long time) {
        try {
            Calendar calendar = Calendar.getInstance(Locale.FRANCE);
            calendar.setTimeInMillis(time);
            calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
            return DateFormat.format("dd MMMM yyyy", calendar).toString();
        } catch (Exception ignored) {
            return "";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    public static String getDayFromTimeStamp(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
        return new SimpleDateFormat("dd-MM-yy", Locale.getDefault()).format(new Date(timeStamp));
    }


    @SuppressLint("SimpleDateFormat")
    public static String getDateTimeFromDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateTimeFromMillisecond(long timeStamp) {
        return new SimpleDateFormat("dd/MM/yyyy hh:mm").format(new Date(timeStamp));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMonthFromMills(long timeStamp) {
        return new SimpleDateFormat("MMMM").format(new Date(timeStamp));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getTimeFromMills(long timeStamp) {

        return new SimpleDateFormat("DD - hh:mm").format(new Date(timeStamp));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateFromMillisecond(int from) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, from);
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date(calendar.getTimeInMillis()));
    }


    public static String getHourAndMinutesFromTimeStamp(long timeStamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, TimeZone.getDefault().getOffset(calendar.getTimeInMillis()));
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(timeStamp));
    }

    public static String getHourAndMinutesFrom2TimeStamp(long timeStamp) {
        return new SimpleDateFormat("HH'h':mm", Locale.getDefault()).format(new Date(timeStamp));
    }


    public static long dateToTimeStamp(String strDate) {
        try {
            Date date;
            strDate = strDate.replaceAll("[^0123456789]", "");
            if (strDate.length() == 6)
                date = new SimpleDateFormat("ddMMyy", Locale.FRANCE).parse(strDate);
            else
                date = new SimpleDateFormat("ddMMyyyy", Locale.FRANCE).parse(strDate);
            if (date != null)
                return date.getTime();
        } catch (Exception e) {
            // e.printStackTrace();
        }
        return INVALID_DATE;
    }


    public static Date dateToDate(String strDate) {
        try {
            Date date;
            strDate = strDate.replaceAll("[^0123456789]", "");
            if (strDate.length() == 6)
                date = new SimpleDateFormat("ddMMyy", Locale.FRANCE).parse(strDate);
            else
                date = new SimpleDateFormat("ddMMyyyy", Locale.FRANCE).parse(strDate);
            if (date != null)
                return date;
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return null;
    }


    public static String GetDeiveID(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find() && (!emailStr.trim().equals(""));
    }


    public static boolean is_phone_number(String str) {
        str = str.replaceAll("[ -]", "").trim();
        if (str.isEmpty())
            return false;

        return str.matches("\\d+(?:\\.\\d+)?");
    }

    /**
     * function return Time  00:00:00
     *
     * @return
     */
    public static String getTimeformatMillisecond(long milliseconds) {

        String result = "";
        try {

            int hours = (int) ((milliseconds / (1000 * 60 * 60)) % 24);
            int minutes = (int) ((milliseconds / (1000 * 60)) % 60);
            int seconds = (int) ((milliseconds / 1000) % 60);
            //hours
            if (hours > 0) {
                if (hours < 10) {
                    result += "0" + hours + ":";
                } else result += hours + ":";
            }
            //minutes
            if (minutes < 10) {
                result += "0" + minutes;
            } else result += minutes;

            //second
            if (seconds < 10) {
                result += ":0" + seconds;
            } else result += ":" + seconds;
        } catch (Exception e) {
            //e.printStackTrace();

        }

        return result;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////

    public static Spanned getHiddenPhone(String str) {
        try {
            str = str.trim().toLowerCase(Locale.ROOT);
            StringBuilder res = new StringBuilder();
            res.append(str.substring(0, 4));
            res.append("<font color='#AAAAAA'>  ");
            res.append(new String(new char[str.substring(4, str.length() - 2).length()]).replace("\0", "X"));
            res.append("</font>");
            res.append(str.substring(str.length() - 2));
            return Html.fromHtml(res.toString());
        } catch (Exception ignored) {
            return Html.fromHtml(str);
        }
    }

    public static Spanned getHiddenEmail(String str) {
        try {
            str = str.trim().toLowerCase(Locale.ROOT);
            StringBuilder res = new StringBuilder();
            res.append("<font color='#AAAAAA'>  ");
            res.append(new String(new char[str.substring(0, str.lastIndexOf('@') - 3).length()]).replace("\0", "x"));
            res.append("</font>");
            res.append(str.substring(str.lastIndexOf('@') - 3));
            return Html.fromHtml(res.toString());
        } catch (Exception ignored) {
            return Html.fromHtml(str);
        }
    }


    public static void reverseTimer(int Seconds, final TextView textView) {
        if (countDownTimer != null)
            countDownTimer.cancel();
        textView.setVisibility(View.VISIBLE);
        countDownTimer = new CountDownTimer(Seconds * 1000L + 1000, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                textView.setText(String.format(Locale.FRANCE, "%02d", minutes) + ":" + String.format(Locale.FRANCE, "%02d", seconds));
            }

            public void onFinish() {
                textView.setText("");
            }
        };
        countDownTimer.start();
    }


    public static boolean isValidPassword(String password) {

        password = Utils.stripAccents(password.trim()).toUpperCase();

        Pattern pattern;
        Matcher matcher;
        final String SECRETE_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=\\S+$).{8,}$";
        //   final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[)(@_#$%*/&:;,$'^&+=!])(?=\\S+$).{8,}$";
        pattern = Pattern.compile(SECRETE_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static String stripAccents(String s) {
        s = Normalizer.normalize(s, Normalizer.Form.NFD);
        s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return s;
    }


    public static boolean validatePhone(String emailStr) {
        Matcher matcher1 = VALID_PHONE_REGEX1.matcher(emailStr);
        Matcher matcher2 = VALID_PHONE_REGEX2.matcher(emailStr);
        return (matcher1.find() || matcher2.find()) && (!emailStr.trim().equals(""));
    }

    public static boolean validateCode(String emailStr) {
        Matcher matcher = VALID_Code_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean validatePatente(String Str) {
        return Str.matches("[0-9]+") && Str.length() > 2;
    }

    public static Drawable setTint(Drawable d, int color) {
        Drawable wrappedDrawable = DrawableCompat.wrap(d);
        DrawableCompat.setTint(wrappedDrawable, color);
        return wrappedDrawable;
    }

    public static boolean isRTL(Locale locale) {
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == View.LAYOUT_DIRECTION_RTL;
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            //e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        return sb.toString();
    }


    public static boolean isRTL(Context context) {
        return TextUtilsCompat.getLayoutDirectionFromLocale(getCurrentLocale(context)) == View.LAYOUT_DIRECTION_RTL;
    }

    public static Locale getCurrentLocale(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getResources().getConfiguration().getLocales().get(0);
        } else {
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }


    public static Calendar getDateFromString(String date0) {

        String myformat;
        date0 = date0.replaceAll("[^1234567890]", "");

        if (date0.length() == 8) {
            myformat = "ddMMyy";
        } else {
            myformat = "ddMMyyyy";
        }

        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(Objects.requireNonNull(new SimpleDateFormat(myformat, Locale.US).parse(date0)));
            return calendar;
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }

    }


    public static void hideKeyboard(Activity activity, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            // e.printStackTrace();
        }
    }


    public static boolean is64Bit() {
        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                // Android API 21之前不支持64位CPU
                return false;
            }

            Class<?> clzVMRuntime = Class.forName("dalvik.system.VMRuntime");
            if (clzVMRuntime == null) {
                return false;
            }
            Method mthVMRuntimeGet = clzVMRuntime.getDeclaredMethod("getRuntime");
            if (mthVMRuntimeGet == null) {
                return false;
            }
            Object objVMRuntime = mthVMRuntimeGet.invoke(null);
            if (objVMRuntime == null) {
                return false;
            }
            Method sVMRuntimeIs64BitMethod = clzVMRuntime.getDeclaredMethod("is64Bit");
            if (sVMRuntimeIs64BitMethod == null) {
                return false;
            }
            Object objIs64Bit = sVMRuntimeIs64BitMethod.invoke(objVMRuntime);
            if (objIs64Bit instanceof Boolean) {
                return (boolean) objIs64Bit;
            }
        } catch (Throwable e) {

        }
        return false;
    }


    public static void hideKeyboard2(Activity activity) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }


    public static Bitmap getBitmapFromFile(String imageFile) {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imageFile, options);

    }

    public static Bitmap getBitmapFromAssets(Context context, String filename) throws IOException {
        Bitmap bitmap;
        AssetManager asm = context.getAssets();
        InputStream is = null;
        try {
            is = asm.open(filename);
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            //e.printStackTrace();
            return null;
        } finally {
            if (is != null) {
                is.close();
            }
        }
        return bitmap.copy(bitmap.getConfig(), true);
    }


    public static boolean SaveBitmapInFile(Bitmap pictureBitmap, File file, int compressQuality) {
        try (OutputStream fOut = new FileOutputStream(file);){
            pictureBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
            fOut.flush();
            fOut.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }


    public static String SaveBitmapInAppDirectory(Bitmap pictureBitmap, String name, int compressQuality) {
        File file;
        try {
            String imageFileName = name + ".jpg";
            File folder = new File(Utils.mainDirectory, "/tempFiles");
            if (!folder.exists()) folder.mkdirs();
            file = new File(folder, imageFileName); // the File to save , append increasing numeric counter to prevent files from getting overwritten.

            try(OutputStream fOut = new FileOutputStream(file)){
                pictureBitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, fOut); // saving the Bitmap to a file compressed as a JPEG with 85% compression rate
                fOut.flush();
            }
        } catch (Exception e) {
            return "";
        }
        return file.getAbsolutePath();
    }


    public static String GuidGenerator() {

        return String.format(Locale.getDefault(), "%13d%08d%09d", System.currentTimeMillis(), random.nextInt(99999999), random.nextInt(999999999));

    }


    public Bitmap drawTextToBitmap(Context gContext,
                                   int gResId,
                                   String gText) {

        Resources resources = gContext.getResources();
        float scale = resources.getDisplayMetrics().density;

        Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

        Bitmap.Config bitmapConfig =
                bitmap.getConfig();
        // set default bitmap config if none
        if (bitmapConfig == null) {
            bitmapConfig = Bitmap.Config.ARGB_8888;
        }
        // resource bitmaps are imutable,
        // so we need to convert it to mutable one
        bitmap = bitmap.copy(bitmapConfig, true);

        Canvas canvas = new Canvas(bitmap);
        // new antialised Paint
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        // text color - #3D3D3D
        paint.setColor(Color.rgb(61, 61, 61));
        // text size in pixels
        paint.setTextSize((int) (14 * scale));
        // text shadow
        paint.setShadowLayer(1f, 0f, 1f, Color.WHITE);

        // draw text to the Canvas center
        Rect bounds = new Rect();
        paint.getTextBounds(gText, 0, gText.length(), bounds);
        int x = (bitmap.getWidth() - bounds.width()) / 2;
        int y = (bitmap.getHeight() + bounds.height()) / 2;

        canvas.drawText(gText, x, y, paint);

        return bitmap;
    }


}
