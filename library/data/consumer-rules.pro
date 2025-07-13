# Keep DTOs for Gson parsing
-keep class com.starz.play.coding.data.dataSource.remote.dto.** { *; }

# Keep Retrofit API interfaces
-keep interface com.starz.play.coding.data.dataSource.remote.apiService.** { *; }

# Keep interceptors
-keep class com.starz.play.coding.data.dataSource.remote.interceptor.** { *; }

# TypeToken
-keep class com.google.gson.reflect.TypeToken

# Keep fields annotated with @SerializedName
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# Keep classes used with Gson
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.reflect.TypeToken

# Keep any TypeToken usage (to prevent reflection issues)
-keepclassmembers class * {
    @com.google.gson.reflect.TypeToken <fields>;
}
-keep class * implements com.google.gson.reflect.TypeToken