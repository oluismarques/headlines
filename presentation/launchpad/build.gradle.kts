plugins {
    alias(libs.plugins.news.android.feature)
    alias(libs.plugins.news.android.library.compose)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.news.feature.launchpad"

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

dependencies {
    implementation(projects.core.util)

    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.compose.material3.windowSizeClass)
    implementation(libs.androidx.compose.ui.test)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}