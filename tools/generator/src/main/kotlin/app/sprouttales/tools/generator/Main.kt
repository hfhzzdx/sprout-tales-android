package app.sprouttales.tools.generator

import app.sprouttales.model.StoryPack
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

fun main() {
    val outDir = File("app/src/main/assets/stories")
    outDir.mkdirs()
    val outFile = File(outDir, "pack.json")
    val pack: StoryPack = StoryGenerator.generate()
    val json = Json { prettyPrint = true }
    outFile.writeText(json.encodeToString(StoryPack.serializer(), pack))
    println("Generated stories -> ${outFile.absolutePath}")
}
