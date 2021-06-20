package br.pedroso.tweetsentiment.buildsrc

object Dependencies {
    private val kotlin = arrayOf(
        "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}",
        "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    )

    private val coroutines = arrayOf(
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}",
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    )

    private val koin = arrayOf(
        "io.insert-koin:koin-android:${Versions.koin}",
        "io.insert-koin:koin-androidx-viewmodel:${Versions.koin}"
    )

    private val retrofit = arrayOf(
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}",
        "com.squareup.okhttp3:logging-interceptor:4.9.1"
    )

    private val room = arrayOf(
        "androidx.room:room-runtime:${Versions.room}",
        "androidx.room:room-ktx:${Versions.room}",
        "androidx.room:room-ktx:${Versions.room}"
    )

    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    private val architectureComponents = arrayOf(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.architectureComponents}",
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.architectureComponents}"
    )

    private val joda = arrayOf("net.danlew:android.joda:2.10.3")

    val testDependencies = arrayOf(
        "junit:junit:${Versions.junit}",
        "org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlin}",
        "com.google.truth:truth:1.1.3"
    )

    val androidTestDependencies = arrayOf(
        "androidx.test:runner:${Versions.expressoTools}",
        "androidx.test.espresso:espresso-core:${Versions.expressoCore}"
    )

    @OptIn(ExperimentalStdlibApi::class)
    val appDependencies = buildList {
        add("androidx.appcompat:appcompat:1.3.0")
        add("androidx.core:core-ktx:1.5.0")
        add("androidx.fragment:fragment-ktx:1.3.4")
        add("androidx.constraintlayout:constraintlayout:2.0.4")
        add("androidx.recyclerview:recyclerview:1.2.1")
        add("androidx.legacy:legacy-support-v4:1.0.0")
        add("com.google.android.material:material:1.3.0")
        add("com.squareup.picasso:picasso:2.8")
        add("androidx.palette:palette:1.0.0")
        add("de.hdodenhof:circleimageview:3.1.0")
        addAll(kotlin)
        addAll(koin)
        addAll(coroutines)
        addAll(retrofit)
        addAll(room)
        addAll(architectureComponents)
        addAll(joda)
    }

    @OptIn(ExperimentalStdlibApi::class)
    val presentationDependencies = buildList {
        add("com.github.hadilq.liveevent:liveevent:1.2.0")
        addAll(kotlin)
        addAll(coroutines)
        addAll(architectureComponents)
    }

    @OptIn(ExperimentalStdlibApi::class)
    val networkDependencies = buildList {
        addAll(retrofit)
        addAll(kotlin)
        addAll(coroutines)
        addAll(joda)
    }

    @OptIn(ExperimentalStdlibApi::class)
    val deviceDependencies = buildList {
        add("com.orhanobut:hawk:2.0.1")
        addAll(kotlin)
        addAll(coroutines)
        addAll(joda)
        addAll(room)
    }


    @OptIn(ExperimentalStdlibApi::class)
    val domainDependencies = buildList {
        addAll(kotlin)
        addAll(coroutines)
        addAll(joda)
    }
}

object Versions {
    const val kotlin = "1.5.10"
    const val coroutines = "1.5.0"
    const val koin = "2.2.3"
    const val retrofit = "2.9.0"
    const val room = "2.3.0"
    const val architectureComponents = "2.3.1"
    const val junit = "4.13.2"
    const val mockitoKotlin = "3.2.0"
    const val expressoCore = "3.1.0"
    const val expressoTools = "1.1.0"
    const val kotlinJvmTarget = "1.8"
}

object AndroidConfig {
    const val minSdk = 23
    const val targetSdk = 30
    const val compileSdk = 30
    const val applicationId = "br.pedroso.tweetsentiment"
    const val versionCode = 1
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}