package app.sprouttales.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import java.util.Locale

class TtsEngine(private val context: Context) : TextToSpeech.OnInitListener {
    private var tts: TextToSpeech? = null
    private var onReady: (() -> Unit)? = null

    fun initialize(onReady: () -> Unit) {
        this.onReady = onReady
        if (tts == null) tts = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            tts?.language = Locale.SIMPLIFIED_CHINESE
            onReady?.invoke()
        }
    }

    fun speak(text: String) {
        tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, System.currentTimeMillis().toString())
    }

    fun stop() { tts?.stop() }

    fun shutdown() { tts?.shutdown() }
}
