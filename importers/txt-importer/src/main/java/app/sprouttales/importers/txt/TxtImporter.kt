package app.sprouttales.importers.txt

import java.nio.charset.Charset

object TxtImporter {
    fun detectCharset(bytes: ByteArray): Charset {
        return when {
            bytes.size >= 3 && bytes[0] == 0xEF.toByte() && bytes[1] == 0xBB.toByte() && bytes[2] == 0xBF.toByte() -> Charsets.UTF_8
            else -> Charsets.UTF_8 // 简化：默认 UTF-8，可扩展 GBK 等
        }
    }

    fun splitParagraphs(text: String): List<String> =
        text.replace("\r\n", "\n")
            .split("\n\n")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
}
