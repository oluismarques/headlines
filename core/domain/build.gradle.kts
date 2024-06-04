plugins {
    alias(libs.plugins.news.android.library)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.news.core.domain"

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
    implementation(projects.core.util)

    implementation(libs.hilt.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.paging.common)

    ksp(libs.hilt.compiler)
}