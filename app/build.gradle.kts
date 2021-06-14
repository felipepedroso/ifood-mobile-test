import br.pedroso.tweetsentiment.buildsrc.AndroidConfig
import br.pedroso.tweetsentiment.buildsrc.Dependencies

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(AndroidConfig.compileSdk)
    defaultConfig {
        applicationId = AndroidConfig.applicationId
        minSdkVersion(AndroidConfig.minSdk)
        targetSdkVersion(AndroidConfig.targetSdk)
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName
        testInstrumentationRunner = AndroidConfig.testInstrumentationRunner

        javaCompileOptions {
            annotationProcessorOptions {
                arguments(
                    mapOf(
                        "room.schemaLocation" to "$projectDir/schemas".toString(),
                        "room.incremental" to "true",
                        "room.expandProjection" to "true"
                    )
                )
            }
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    androidExtensions {
        isExperimental = true
    }

    val naturalLanguageApiKey: String by project
    val twitterConsumerKey: String by project
    val twitterConsumerSecret: String by project

    val propertiesMap = mapOf(
        "NATURAL_LANGUAGE_API_KEY" to naturalLanguageApiKey,
        "TWITTER_CONSUMER_KEY" to twitterConsumerKey,
        "TWITTER_CONSUMER_SECRET" to twitterConsumerSecret
    )

    buildTypes.forEach { buildType ->
        propertiesMap.forEach { (constantName, value) ->
            buildType.buildConfigField("String", constantName, value)
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val dependenciesModules = arrayOf(":device", ":network", ":domain", ":presentation")

    dependenciesModules.forEach { module -> implementation(project(module)) }

    with(Dependencies) {
        appDependencies.forEach { implementation(it) }
        testDependencies.forEach { testImplementation(it) }
        androidTestDependencies.forEach { androidTestImplementation(it) }
    }
}
