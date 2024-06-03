plugins {
    alias(libs.plugins.news.android.library)
}

android {
    namespace = "com.news.util"

    buildFeatures.buildConfig = true

    flavorDimensions += "version"

    productFlavors {
        create("bbc") {
            dimension = "version"
        }
        create("full"){
            dimension = "version"
        }
    }

}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.junit)
    implementation(libs.kotlinx.coroutines.test)
    api(libs.androidx.compose.ui.test)
    implementation(libs.androidx.ui.test.android)
    implementation(libs.biometric)

}