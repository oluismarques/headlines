package news.headlines

import com.android.build.api.dsl.ApplicationBuildType
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension

enum class NewsBuildType(val displayName: String, val applicationIdSuffix: String? = null) {
    Debug("debug", ".dev"),
    Release("release"),
}

fun configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    buildTypeConfigurationBlock: BuildType.(buildType: NewsBuildType) -> Unit = {},
) {
    commonExtension.apply {
        buildFeatures.buildConfig = true
        buildTypes {
            NewsBuildType.values().forEach {
                getByName(it.displayName) {
                    buildTypeConfigurationBlock(this, it)
                    if (this@apply is ApplicationExtension && this is ApplicationBuildType) {
                        if (it.applicationIdSuffix != null) {
                            applicationIdSuffix = it.applicationIdSuffix
                        }
                    }
                }
            }
        }
    }
}

fun configureBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    buildType: NewsBuildType,
    buildTypeConfigurationBlock: BuildType.() -> Unit = {},
) {
    configureBuildTypes(commonExtension) {
        if (it == buildType) buildTypeConfigurationBlock()
    }
}
