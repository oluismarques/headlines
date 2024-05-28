plugins {
    alias(libs.plugins.news.android.library)
}

android {
    namespace = "com.news.util"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.junit)
    implementation(libs.kotlinx.coroutines.test)
    api(libs.androidx.compose.ui.test)
    implementation(libs.androidx.ui.test.android)
}