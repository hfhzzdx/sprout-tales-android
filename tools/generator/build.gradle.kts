plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
    implementation(project(":core-model"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
}

java { toolchain { languageVersion.set(org.gradle.jvm.toolchain.JavaLanguageVersion.of(17)) } }

tasks.register("run") {
    group = "generation"
    description = "生成 ≥320 篇模板故事到 app/src/main/assets/stories/pack.json"
    doLast {
        val outDir = project.rootProject.file("app/src/main/assets/stories")
        outDir.mkdirs()
        val outFile = outDir.resolve("pack.json")
        val pack = app.sprouttales.tools.generator.StoryGenerator.generate()
        outFile.writeText(kotlinx.serialization.json.Json { prettyPrint = true }.encodeToString(app.sprouttales.model.StoryPack.serializer(), pack))
        println("Generated stories -> ${outFile.absolutePath}")
    }
}
