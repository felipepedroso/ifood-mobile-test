import br.pedroso.tweetsentiment.buildsrc.AndroidConfig
import br.pedroso.tweetsentiment.buildsrc.Dependencies

plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(AndroidConfig.compileSdk)

    defaultConfig {
        minSdkVersion(AndroidConfig.minSdk)
        targetSdkVersion(AndroidConfig.targetSdk)
    }

    dexOptions {
        preDexLibraries = false
        dexInProcess = false
        javaMaxHeapSize = "4g"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    lintOptions {
        isAbortOnError = false
    }

    packagingOptions {
        exclude("LICENSE.txt")
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/NOTICE")
        exclude("META-INF/LICENSE")
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    val dependenciesModules = arrayOf(":domain")

    dependenciesModules.forEach { module -> implementation(project(module)) }

    with(Dependencies) {
        networkDependencies.forEach { implementation(it) }
        testDependencies.forEach { testImplementation(it) }
        androidTestDependencies.forEach { androidTestImplementation(it) }
    }
}
