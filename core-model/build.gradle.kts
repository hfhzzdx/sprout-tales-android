plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

java { toolchain { languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(17)) } }

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}
