package app.sprouttales.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TtsEngine(private val context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var onReady: (() -> Unit)? = null
    private var ready: Boolean = false
    private var languageOk: Boolean = false

    fun initialize(onReady: () -> Unit) {
        this.onReady = onReady
        if (tts == null) tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val avail = tts?.isLanguageAvailable(Locale.SIMPLIFIED_CHINESE) ?: TextToSpeech.LANG_MISSING_DATA
            languageOk = (avail == TextToSpeech.LANG_AVAILABLE || avail == TextToSpeech.LANG_COUNTRY_AVAILABLE || avail == TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE)
            if (languageOk) {
                tts?.language = Locale.SIMPLIFIED_CHINESE
            }
            tts?.setSpeechRate(1.0f)
            tts?.setPitch(1.0f)
            ready = true
            onReady?.invoke()
        }
    }

    fun isReady(): Boolean = ready
    fun isLanguageAvailable(): Boolean = languageOk

    fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, System.currentTimeMillis().toString())
    }

    fun stop() { tts?.stop() }

    fun shutdown() { tts?.shutdown() }
}
