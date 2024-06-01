plugins {
    alias(libs.plugins.news.android.library)
    alias(libs.plugins.news.android.hilt)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.news.core.data"

    buildFeatures.buildConfig = true

    buildTypes {
        debug {
            buildConfigField(
                "String",
                "BaseUrl",
                "\"http://newsapi.org/v2/\""
            )
            buildConfigField(
                "String",
                "API_KEY",
                "\"${getProperty("local.properties", "news_api_key") ?: System.getenv("API_KEY")}\""
            )
        }
        release {
            buildConfigField(
                "String",
                "BaseUrl",
                "\"http://newsapi.org/v2/\""
            )

            buildConfigField(
                "String",
                "API_KEY",
                "\"${getProperty("local.properties", "news_api_key") ?: System.getenv("API_KEY")}\""
            )
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.util)

    implementation(libs.androidx.core.ktx)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.svg)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.kotlinx.serialization.json)
}

fun getProperty(filename: String, propName: String): String? {
    val propsFile = rootProject.file(filename)
    if (propsFile.exists()) {
        return com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir, providers).getProperty(propName)
    } else {
        print("$filename does not exist!")
    }
    return null
}