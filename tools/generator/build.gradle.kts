plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    application
}

dependencies {
    implementation(project(":core-model"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}

java { toolchain { languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(17)) } }

application {
    mainClass.set("app.sprouttales.tools.generator.MainKt")
}
