#-obfuscationdictionary ProGuardDictionaries/dictionary_method.txt
#-classobfuscationdictionary ProGuardDictionaries/dictionary_random_class.txt
#-packageobfuscationdictionary ProGuardDictionaries/dictionary_random_class.txt
#-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
-optimizationpasses 5

-keep class me.liangfei.indicator.**{
    *;
}
-keep class com.google.**{
    *;
}

####################################################################################################
##########################################    logs   ###############################################

-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int d(...);
    public static int i(...);
}

-assumenosideeffects class timber.log.Timber {
    public static *** d(...);
    public static *** v(...);
}


####################################################################################################
##########################################    models   #############################################

-keep enum ma.boumlyk.onboarding.models.** { *; }

####################################################################################################
##########################################    Room   ###############################################

-keep class * extends androidx.room.RoomDatabase
-dontwarn androidx.room.paging.**
-keepclassmembers,allowobfuscation class * {
@com.google.gson.annotations.SerializedName <fields>;
}

####################################################################################################
################################## SQLCipher #######################################################

 -keep,includedescriptorclasses class net.sqlcipher.** { *; }
 -keep,includedescriptorclasses interface net.sqlcipher.** { *; }

####################################################################################################
########################################## Retrofit2 ###############################################

-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*
-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

####################################################################################################
############################################ okhttp3 ###############################################
-dontwarn javax.annotation.**
-adaptresourcefilenames okhttp3/internal/publicsuffix/PublicSuffixDatabase.gz
-dontwarn org.codehaus.mojo.animal_sniffer.*
-dontwarn okhttp3.internal.platform.**
-dontwarn org.conscrypt.**
-dontwarn org.bouncycastle.**
-dontwarn org.openjsse.**

####################################################################################################
############################################### okio ###############################################
# Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
-dontwarn org.codehaus.mojo.animal_sniffer.*

####################################################################################################
########################################## Crashlytics #############################################

# Crashlytics
-keepattributes *Annotation*
-keep class com.crashlytics.** { *; }
-dontwarn com.crashlytics.**
-keepattributes SourceFile,LineNumberTable,*Annotation*
-keep public class * extends java.lang.Exception
-keep class com.crashlytics.android.**
-keep class com.shockwave.** { *; }
-keep class com.shockwave.**


