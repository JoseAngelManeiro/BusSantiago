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

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

# OkHttp
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# Retrofit with Gson converter
-keep class retrofit2.converter.gson.** { *; }
-dontwarn retrofit2.converter.gson.**

# Retrofit API interface
-keep interface org.galio.bussantiago.data.api.ApiService

# Keep models used in API calls
-keep class org.galio.bussantiago.data.entity.** { *; }

# Preserve Retrofit annotations
-keepattributes Signature,RuntimeVisibleAnnotations,AnnotationDefault

# Keep methods with Retrofit annotations (e.g., GET, POST)
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}

-keepattributes Exceptions

-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
