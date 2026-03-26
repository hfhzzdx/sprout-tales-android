package app.sprouttales.data

import android.content.Context
import app.sprouttales.model.Story
import app.sprouttales.model.StoryPack
import kotlinx.serialization.json.Json

object AssetStories {
    private val json = Json { ignoreUnknownKeys = true }

    fun load(context: Context): List<Story> {
        val assets = context.assets
        val primary = "stories/pack.json"
        val fallback = "stories/pack.sample.json"
        val path = when {
            exists(assets, primary) -> primary
            exists(assets, fallback) -> fallback
            else -> return emptyList()
        }
        val txt = assets.open(path).bufferedReader().use { it.readText() }
        val pack = json.decodeFromString(StoryPack.serializer(), txt)
        return pack.stories
    }

    private fun exists(am: android.content.res.AssetManager, path: String): Boolean = try {
        am.open(path).close(); true
    } catch (_: Exception) { false }
}
