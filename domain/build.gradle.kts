import br.pedroso.tweetsentiment.buildsrc.Dependencies
import br.pedroso.tweetsentiment.buildsrc.Versions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("kotlin")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    with(Dependencies) {
        domainDependencies.forEach { implementation(it) }
        testDependencies.forEach { testImplementation(it) }
    }
}

val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = Versions.kotlinJvmTarget
}
compileTestKotlin.kotlinOptions {
    jvmTarget = Versions.kotlinJvmTarget
}
