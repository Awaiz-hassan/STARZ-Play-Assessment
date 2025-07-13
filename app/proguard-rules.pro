
# Retrofit + Gson
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn okio.**


# Gson annotations & models
-keepattributes Signature
-keepattributes Exceptions
-keepattributes RuntimeVisibleAnnotations


# OkHttp
-dontwarn okhttp3.logging.**
-keep class okhttp3.** { *; }


# Coil
-dontwarn coil.**
-keep class coil.** { *; }


# Jetpack Compose
-keep class androidx.compose.ui.tooling.** { *; }
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.material3.** { *; }
-dontwarn androidx.compose.**

# Hilt / Dagger
-keep class dagger.** { *; }
-keep class javax.inject.** { *; }
-dontwarn dagger.hilt.**

# Keep ViewModel Hilt injection
-keepclassmembers class androidx.lifecycle.ViewModel {
    <init>(...);
}

-keepclassmembers,allowshrinking class * {
    @dagger.hilt.android.lifecycle.HiltViewModel <init>(...);
}

# Kotlin / Coroutines
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**
-dontwarn kotlinx.coroutines.**

# AndroidX / Jetpack
-keep class androidx.lifecycle.** { *; }
-keep class androidx.datastore.** { *; }
-dontwarn androidx.datastore.**


# ExoPlayer
-keep class com.google.android.exoplayer2.** { *; }
-dontwarn com.google.android.exoplayer2.**

