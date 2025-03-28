import java.util.Properties

plugins {
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    id("architectcoders.android.application")
    id("architectcoders.android.application.compose")
    id("architectcoders.di.library.compose")
}

android {
    namespace = "ing.espinoza.architectcoders"

    defaultConfig {
        applicationId = "ing.espinoza.architectcoders"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "ing.espinoza.architectcoders.di.HiltTestRunner"

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").readText().byteInputStream())

        //val tmdbApiKey = properties.getProperty("TMDB_APY_KEY", "")
        //buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
        buildConfigField("String", "TMDB_API_KEY", "\"e193ff71447384fda468b9edf33c6a88\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":domain:movie"))
    implementation(project(":domain:region"))

    implementation(project(":framework:core"))
    implementation(project(":framework:movie"))
    implementation(project(":framework:region"))

    implementation(project(":feature:common"))
    implementation(project(":feature:home"))
    implementation(project(":feature:detail"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.play.services.location)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)
    androidTestImplementation(libs.androidx.room.ktx)
    kspAndroidTest(libs.androidx.room.compiler)
    androidTestImplementation(libs.okhttp.mockwebserver)
}