package ma.boumlyk.onboarding.models.cookie;


import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.RoomWarnings;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ma.boumlyk.onboarding.Configs;
import ma.boumlyk.onboarding.data.CookiesRepository;
import ma.boumlyk.onboarding.data.sources.local.FileManager;
import ma.boumlyk.onboarding.data.sources.local.prefs.SharePreferenceManager;
import ma.boumlyk.onboarding.models.tools.Language;
import ma.boumlyk.onboarding.models.tools.Model;
import ma.boumlyk.onboarding.tools.Utils;
import ma.boumlyk.onboarding.tools.gson.GsonProvider;
import timber.log.Timber;


@Singleton
@Entity(tableName = "myCookies")
@SuppressWarnings(RoomWarnings.PRIMARY_KEY_FROM_EMBEDDED_IS_DROPPED)
public class Cookies extends Model {

    @SerializedName("ID")
    public static String ID = "UNIQUE_ID";
    @SerializedName("GROUP_ID")
    public static final String GROUP_ID = "01";
    @SerializedName("INSTITUTION_ID")
    public static final String INSTITUTION_ID = "021";
    @SerializedName("CHANNEL_ID")
    public static final String CHANNEL_ID = "02";
    @SerializedName("SUB_CHANNEL_ID")
    public static final String SUB_CHANNEL_ID = "01";


    @NonNull
    @PrimaryKey
    @SerializedName("cookiesId")
    String cookiesId = ID;
    @SerializedName("language")
    Language language = Configs.DEFAULT_LANGUAGE;
    @SerializedName("installationId")
    String installationId;
    @SerializedName("notificationToken")
    String notificationToken = "";
    @SerializedName("accessToken")
    String accessToken = "";
    @SerializedName("synchronizationReference")
    long synchronizationReference = 0;
    @SerializedName("defaultUsername")
    String defaultUsername = "";
    @SerializedName("tempProductIds")
    List<Long> tempProductIds;
    @SerializedName("cguDecision")
    boolean cguDecision = false;
    @SerializedName("receiveAds")
    boolean receiveAds = false;
    @SerializedName("isVActivated")
    boolean isVisioActivated;
//    @Embedded()
//    @SerializedName("tempContact")
//    Contact tempContact = new Contact();
    @SerializedName("preferredMeetingDay")
    long preferredMeetingDay = Utils.INVALID_DATE;
    @SerializedName("preferredMeetingTimeSlot")
    String preferredMeetingTimeSlot = null;
    @SerializedName("isBActivated")
    Boolean isBiometricsActivated = false;
    @SerializedName("ids")
    Map<String, String> passwords = new HashMap<>();


    @Ignore
    @SerializedName("tmpStepId")
    String tmpStepId = "";

    @Ignore
    String operatorTypeChoosed = "";

    @Ignore
    boolean shouldGenerateOtherGuid = true;

    @Ignore
    String currentGuid = "";
    @Ignore
    @SerializedName("applicationId")
    String applicationId;
    @Ignore
    @SerializedName("deviceId")
    String deviceId;

    @Ignore
    @SerializedName("resourcesDownloaded")
    Boolean resourcesDownloaded = false;
    @Ignore
    @SerializedName("agenciesDownloaded")
    Boolean agenciesDownloaded = false;
    @Ignore
    @SerializedName("productsDownloaded")
    Boolean productsDownloaded = false;
    @Ignore
    @SerializedName("documentDownloaded")
    Boolean documentDownloaded = false;
    @Ignore
    @SerializedName("isBackupFinished")
    Boolean isBackupFinished = false;
    @Ignore
    @SerializedName("tempSkyIdServiceId")
    String tempSkyIdServiceId = "";
    @Ignore
    @SerializedName("tempSkyIdProductId")
    String tempSkyIdProductId = "";
    @Ignore
    @SerializedName("rootingStep")
    String rootingStep = null;

    @Ignore
    @SerializedName("currentStep")
    String currentStep;

    @Ignore
    @SerializedName("isSigne")
    Boolean isSigne = false;

    @Ignore
    @SerializedName("isScan")
    Boolean isScan = false;

    @Ignore
    @SerializedName("isIconVisible")
    Boolean isIconVisible = false;

    @Ignore
    @SerializedName("isPublicCategorie")
    Boolean isPublicCategory = false;

    @Ignore
    @SerializedName("codeValidation")
    String codeValidation;

    @Ignore
    FileManager fileManager;

    public Cookies() {
    }

    @Inject
    @SuppressLint("HardwareIds")
    public Cookies(@ApplicationContext Context context, CookiesRepository cookiesRepository, FileManager fileManager) {
        Timber.tag("CookiesTAG").d("Cookies instantiated ...%s :: %s", getInstanceId(), GsonProvider.getInstance().toJson(this));
        applicationId = context.getPackageName();
        SharePreferenceManager manager=new SharePreferenceManager(context);
        deviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        if (currentGuid == null || currentGuid.isEmpty()) {
            shouldGenerateOtherGuid=false;
            currentGuid = Utils.GuidGenerator();
        }
        if (manager.getInstallationID("").isEmpty()){
            installationId = deviceId + "__" + currentGuid;
            manager.setInstallationID(installationId);
        }else{
            installationId=manager.getInstallationID("");
        }
        this.fileManager = fileManager;
        cookiesRepository.getCookies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(mCookies -> {
                    if ((mCookies != null) && (!isBackupFinished)) {
                        update(mCookies);
                        initAppScope();
                        isBackupFinished = true;
                        Timber.tag("CookiesTAG").d("Cookies retrieved from db ...%s :: %s", getInstanceId(), GsonProvider.getInstance().toJson(this));
                    }
                }).doOnError(throwable -> {
                    Timber.tag("CookiesTAG").e(throwable);
                })
                .subscribe();
        Timber.tag("CookiesTAG").d("Cookies instantiation finish ...%s :: %s", getInstanceId(), GsonProvider.getInstance().toJson(this));
    }

    public long getSynchronizedTimeStamp() {
        return this.synchronizationReference + System.currentTimeMillis();
    }

    public void updateSynchronizationReference(String serverTime) {
        try {
            this.synchronizationReference = (Long.parseLong(serverTime) - System.currentTimeMillis());
        } catch (Exception ignored) {}
    }

    public boolean isShouldGenerateOtherGuid() {
        return shouldGenerateOtherGuid;
    }

    public String getCurrentGuid() {
        return currentGuid;
    }

    public void setCurrentGuid(String currentGuid) {
        this.currentGuid = currentGuid;
    }

    public void setShouldGenerateOtherGuid(boolean shouldGenerateOtherGuid) {
        this.shouldGenerateOtherGuid = shouldGenerateOtherGuid;
    }


    public boolean isOnEditMode() {
        if (rootingStep == null)
            return false;
        else {
            return rootingStep.equals("**:**:**");
        }
    }

    ////////////////////////////////////////////////////////////////////////////////
    //////////////////////////// Getters & Setters /////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////


    public String getOperatorTypeChoosed() {
        return operatorTypeChoosed;
    }

    public void setOperatorTypeChoosed(String operatorTypeChoosed) {
        this.operatorTypeChoosed = operatorTypeChoosed;
    }

    public void update(Cookies other) {
        this.language = other.language;
        this.notificationToken = other.notificationToken;
        this.defaultUsername = other.defaultUsername;
        this.tempProductIds = other.tempProductIds;
        this.cguDecision = other.cguDecision;
        this.receiveAds = other.receiveAds;
        this.isVisioActivated = other.isVisioActivated;
        this.accessToken = other.accessToken;
        this.isBiometricsActivated = other.isBiometricsActivated;
    }

    public void initAppScope() {
        resourcesDownloaded = false;
        agenciesDownloaded = false;
        productsDownloaded = false;
        documentDownloaded = false;
        rootingStep = null;
    }

    public void initAgentScope() {


    }


    @NonNull
    public String getCookiesId() {
        return cookiesId;
    }

    public void setCookiesId(@NonNull String cookiesId) {
        this.cookiesId = cookiesId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getInstallationId() {
        return installationId;
    }

    public void setInstallationId(String installationId) {
        this.installationId = installationId;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getDefaultUsername() {
        return defaultUsername;
    }

    public void setDefaultUsername(String defaultUsername) {
        this.defaultUsername = defaultUsername;
    }

    public boolean isCguDecision() {
        return cguDecision;
    }

    public void setCguDecision(boolean cguDecision) {
        this.cguDecision = cguDecision;
    }

    public boolean isReceiveAds() {
        return receiveAds;
    }

    public void setReceiveAds(boolean receiveAds) {
        this.receiveAds = receiveAds;
    }

    public String getNotificationToken() {
        return notificationToken;
    }

    public void setNotificationToken(String notificationToken) {
        this.notificationToken = notificationToken;
    }

    public Boolean getProductsDownloaded() {
        return productsDownloaded;
    }

    public void setProductsDownloaded(Boolean productsDownloaded) {
        this.productsDownloaded = productsDownloaded;
    }

    public Boolean getAgenciesDownloaded() {
        return agenciesDownloaded;
    }

    public void setAgenciesDownloaded(Boolean agenciesDownloaded) {
        this.agenciesDownloaded = agenciesDownloaded;
    }

    public long getPreferredMeetingDay() {
        return preferredMeetingDay;
    }

    public void setPreferredMeetingDay(long preferredMeetingDay) {
        this.preferredMeetingDay = preferredMeetingDay;
    }

    public String getPreferredMeetingTimeSlot() {
        return preferredMeetingTimeSlot;
    }

    public void setPreferredMeetingTimeSlot(String preferredMeetingTimeSlot) {
        this.preferredMeetingTimeSlot = preferredMeetingTimeSlot;
    }

    public List<Long> getTempProductIds() {
        return tempProductIds;
    }

    public void setTempProductIds(List<Long> tempProductIds) {
        this.tempProductIds = tempProductIds;
    }

    public boolean isVisioActivated() {
        return isVisioActivated;
    }

    public void setVisioActivated(boolean visioActivated) {
        isVisioActivated = visioActivated;
    }

    public String getTmpStepId() {
        return tmpStepId;
    }

    public void setTmpStepId(String tmpStepId) {
        this.tmpStepId = tmpStepId;
    }

    public String getTempSkyIdServiceId() {
        return tempSkyIdServiceId;
    }

    public void setTempSkyIdServiceId(String tempSkyIdServiceId) {
        this.tempSkyIdServiceId = tempSkyIdServiceId;
    }

    public String getTempSkyIdProductId() {
        return tempSkyIdProductId;
    }

    public void setTempSkyIdProductId(String tempSkyIdProductId) {
        this.tempSkyIdProductId = tempSkyIdProductId;
    }

    public Boolean getDocumentDownloaded() {
        return documentDownloaded;
    }

    public void setDocumentDownloaded(Boolean documentDownloaded) {
        this.documentDownloaded = documentDownloaded;
    }

    public Boolean getResourcesDownloaded() {
        return resourcesDownloaded;
    }

    public void setResourcesDownloaded(Boolean resourcesDownloaded) {
        this.resourcesDownloaded = resourcesDownloaded;
    }

    public void setIsBackupFinished(Boolean isBackupFinished) {
        this.isBackupFinished = isBackupFinished;
    }

    public Boolean getIsBackupFinished() {
        return isBackupFinished;
    }

    public String getRootingStep() {
        return rootingStep;
    }

    public void setRootingStep(String rootingStep) {
        this.rootingStep = rootingStep;
    }
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Boolean getBiometricsActivated() {
        return isBiometricsActivated;
    }

    public void setBiometricsActivated(Boolean biometricsActivated) {
        isBiometricsActivated = biometricsActivated;
    }

    public Map<String, String> getPasswords() {
        return passwords;
    }

    public void setPasswords(Map<String, String> passwords) {
        this.passwords = passwords;
    }

    public String getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(String currentStep) {
        this.currentStep = currentStep;
    }

    public Boolean getScan() {
        return isScan;
    }

    public void setScan(Boolean scan) {
        isScan = scan;
    }

    public Boolean getSigne() {
        return isSigne;
    }

    public void setSigne(Boolean signe) {
        isSigne = signe;
    }

    public Boolean getIconVisible() {
        return isIconVisible;
    }

    public void setIconVisible(Boolean iconVisible) {
        isIconVisible = iconVisible;
    }

    public Boolean getPublicCategorie() {
        return isPublicCategory;
    }

    public void setPublicCategorie(Boolean publicCategorie) {
        isPublicCategory = publicCategorie;
    }

    public String getCodeValidation() {
        return codeValidation;
    }

    public void setCodeValidation(String codeValidation) {
        this.codeValidation = codeValidation;
    }


    public long getSynchronizationReference() {
        return synchronizationReference;
    }

    public void setSynchronizationReference(long synchronizationReference) {
        this.synchronizationReference = synchronizationReference;
    }
}