plugins {
    id("io.gitlab.arturbosch.detekt").version("1.8.0")
}

buildscript {
    repositories {
        google()
        jcenter()
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.6.3")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    }
}



allprojects {
    repositories {
        google()
        jcenter()
    }

    apply {
        plugin("io.gitlab.arturbosch.detekt")
    }
}

tasks {
    val clean by registering(Delete::class) {
        delete(buildDir)
    }
}
