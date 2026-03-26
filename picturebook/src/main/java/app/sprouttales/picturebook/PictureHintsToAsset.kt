package app.sprouttales.picturebook

object PictureHintsToAsset {
    fun pick(hints: List<String>): String {
        return when {
            hints.any { it.contains("森林") } -> "illus_forest"
            hints.any { it.contains("校园") } -> "illus_school"
            hints.any { it.contains("海边") } -> "illus_sea"
            else -> "illus_default"
        }
    }
}
