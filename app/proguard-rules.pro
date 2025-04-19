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

# === General Android Support ===
-keep class androidx.lifecycle.** { *; }
-keep class androidx.activity.ComponentActivity { *; }
-keep class androidx.fragment.app.Fragment { *; }

# Keep ViewModel if using SavedStateHandle, etc.
-keep class androidx.lifecycle.ViewModel { *; }
-keepclassmembers class * extends androidx.lifecycle.ViewModel {
    <init>(...);
}

# === Koin (DI) ===
# Keep Koin modules and factories
-keep class org.koin.** { *; }

-keepclassmembers class * {
    @org.koin.core.annotation.* <methods>;
}
-keepattributes *Annotation*

# === Tests Only ===
# Robolectric can be ignored during production minification
-dontwarn org.robolectric.**

# === Useful for debugging exceptions ===
-keepattributes Exceptions

# === Optional: Keep all annotations for debugging, reflection, or DI ===
-keepattributes RuntimeVisibleAnnotations, RuntimeInvisibleAnnotations

-dontwarn org.conscrypt.Conscrypt$Version
-dontwarn org.conscrypt.Conscrypt
-dontwarn org.conscrypt.ConscryptHostnameVerifier
-dontwarn org.openjsse.javax.net.ssl.SSLParameters
-dontwarn org.openjsse.javax.net.ssl.SSLSocket
-dontwarn org.openjsse.net.ssl.OpenJSSE
