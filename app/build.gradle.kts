import com.android.build.api.dsl.ApplicationBuildType
import news.headlines.NewsBuildType
import news.headlines.configureBuildType


plugins {
    alias(libs.plugins.news.android.application)
    alias(libs.plugins.news.android.application.compose)
    alias(libs.plugins.news.android.hilt)
}

android {
    namespace = "com.news.headlines"

    defaultConfig {
        applicationId = "com.news.headlines"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }


    configureBuildType(this, buildType = NewsBuildType.Release) {
        this as ApplicationBuildType

        isMinifyEnabled = true
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
        )
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(projects.presentation.launchpad)

    implementation(projects.core.domain)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)

    implementation(libs.coil.kt)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.material3.android)
    implementation(project(":presentation:details"))
}