package app.sprouttales.model

import kotlinx.serialization.Serializable

data class StoryId(val value: String)

@Serializable
data class Story(
    val id: String,
    val title: String,
    val ageRange: String, // "3-5" / "6-8"
    val theme: String,
    val paragraphs: List<String>,
    val pictureHints: List<String> = emptyList(),
    val durationEstimateSec: Int = 0
)

@Serializable
data class StoryPack(
    val stories: List<Story>
)
