package app.sprouttales.importers.epub

import org.jsoup.Jsoup
import java.util.zip.ZipFile

object EpubImporter {
    data class Result(
        val title: String,
        val paragraphs: List<String>,
        val images: List<String>
    )

    fun import(path: String): Result {
        ZipFile(path).use { zip ->
            val container = zip.getInputStream(zip.getEntry("META-INF/container.xml"))
                .reader().readText()
            val opfPath = Regex("full-path=\"([^\\\"]+)\"").find(container)?.groupValues?.get(1)
                ?: error("OPF not found")
            val opf = zip.getInputStream(zip.getEntry(opfPath)).reader().readText()
            val title = Regex("<dc:title>(.*?)</dc:title>").find(opf)?.groupValues?.get(1) ?: "EPUB故事"
            val spineIds = Regex("<itemref[^>]*idref=\"([^\\\"]+)\"").findAll(opf).map { it.groupValues[1] }.toList()
            val manifest = Regex("""<item[^>]*id=\"([^\"]+)\"[^>]*href=\"([^\"]+)\"[^>]*>""")
                .findAll(opf).associate { it.groupValues[1] to it.groupValues[2] }
            val base = opfPath.substringBeforeLast('/', "")
            val paragraphs = mutableListOf<String>()
            val images = mutableListOf<String>()
            for (id in spineIds) {
                val href = manifest[id] ?: continue
                val entry = if (base.isEmpty()) href else "$base/$href"
                val html = zip.getInputStream(zip.getEntry(entry)).reader().readText()
                val doc = Jsoup.parse(html)
                paragraphs += doc.select("p").map { it.text().trim() }.filter { it.isNotEmpty() }
                images += doc.select("img").mapNotNull { it.attr("src").takeIf { s -> s.isNotBlank() } }
            }
            return Result(title, paragraphs, images)
        }
    }
}
