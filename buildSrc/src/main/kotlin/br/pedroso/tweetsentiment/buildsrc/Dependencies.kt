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
        "org.koin:koin-android:${Versions.koin}",
        "org.koin:koin-android-viewmodel:${Versions.koin}"
    )

    private val retrofit = arrayOf(
        "com.squareup.retrofit2:retrofit:${Versions.retrofit}",
        "com.squareup.retrofit2:converter-gson:${Versions.retrofit}",
        "com.squareup.okhttp3:logging-interceptor:3.8.0"
    )

    val timber = arrayOf(
        "com.jakewharton.timber:timber:4.7.1"
    )

    val room = arrayOf(
        "androidx.room:room-runtime:${Versions.room}",
        "androidx.room:room-ktx:${Versions.room}",
        "androidx.room:room-ktx:${Versions.room}"
    )

    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    private val architectureComponents = arrayOf(
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.architectureComponents}",
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.architectureComponents}"
    )

    private val joda = arrayOf("net.danlew:android.joda:2.10.3")

    val testDependencies = arrayOf(
        "junit:junit:${Versions.junit}",
        "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    )

    val androidTestDependencies = arrayOf(
        "androidx.test:runner:${Versions.expressoTools}",
        "androidx.test.espresso:espresso-core:${Versions.expressoCore}"
    )
    private val appDependencies = mutableListOf(
        "androidx.appcompat:appcompat:1.1.0",
        "androidx.core:core-ktx:1.2.0",
        "androidx.fragment:fragment-ktx:1.2.4",
        "androidx.constraintlayout:constraintlayout:2.0.0-beta4",
        "androidx.recyclerview:recyclerview:1.1.0",
        "androidx.cardview:cardview:1.0.0",
        "androidx.legacy:legacy-support-v4:1.0.0",
        "com.google.android.material:material:1.1.0",
        "com.squareup.picasso:picasso:2.71828",
        "androidx.palette:palette:1.0.0",
        "de.hdodenhof:circleimageview:3.0.0"
    ).apply {
        addAll(kotlin)
        addAll(koin)
        addAll(coroutines)
        addAll(timber)
        addAll(retrofit)
        addAll(room)
        addAll(architectureComponents)
        addAll(joda)
    }.toList()


    val presentationDependencies = mutableListOf(
        "com.github.hadilq.liveevent:liveevent:1.2.0"
    ).apply {
        addAll(kotlin)
        addAll(coroutines)
        addAll(architectureComponents)
        addAll(timber)
    }.toList()

    val networkDependencies = mutableListOf<String>().apply {
        addAll(retrofit)
        addAll(kotlin)
        addAll(coroutines)
        addAll(timber)
        addAll(joda)
    }.toList()

    val deviceDependencies = mutableListOf(
        "com.orhanobut:hawk:2.0.1"
    ).apply {
        addAll(kotlin)
        addAll(coroutines)
        addAll(joda)
        addAll(room)
        addAll(timber)
    }.toList()

    val domainDependencies = mutableListOf<String>().apply {
        addAll(kotlin)
        addAll(coroutines)
        addAll(joda)
    }.toList()
}

object Versions {
    const val kotlin = "1.3.72"
    const val coroutines = "1.3.5"
    const val koin = "2.1.5"
    const val retrofit = "2.6.0"
    const val room = "2.2.5"
    const val architectureComponents = "2.2.0"
    const val junit = "4.12"
    const val mockitoKotlin = "2.1.0"
    const val expressoCore = "3.1.0"
    const val expressoTools = "1.1.0"
    const val kotlinJvmTarget = "1.8"
}

object AndroidConfig {
    const val minSdk = 21
    const val targetSdk = 29
    const val compileSdk = 29
    const val applicationId = "br.pedroso.tweetsentiment"
    const val versionCode = 1
    const val versionName = "1.0"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}