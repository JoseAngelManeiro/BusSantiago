# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# === Retrofit & OkHttp ===
#-dontwarn okhttp3.**
#-dontwarn retrofit2.**
-dontwarn javax.annotation.Nullable
-keep class retrofit2.** { *; }
-keep class com.squareup.okhttp3.logging.** { *; }
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

# === Gson (used with Retrofit converter) ===
-keep class com.google.gson.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepattributes Signature
-keepattributes *Annotation*

# Retrofit API interface
-keep interface org.galio.bussantiago.data.api.ApiService

# Keep models used in API calls
-keep class org.galio.bussantiago.data.entity.** { *; }
