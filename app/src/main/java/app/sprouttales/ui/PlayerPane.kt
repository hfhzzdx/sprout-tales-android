package app.sprouttales.ui

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.sprouttales.model.Story
import app.sprouttales.media.PlayerService
import app.sprouttales.tts.TtsEngine

@Composable
fun PlayerPane(current: Story?) {
    val ctx = LocalContext.current
    var ready by remember { mutableStateOf(false) }
    val tts = remember { TtsEngine(ctx) }

    DisposableEffect(Unit) {
        tts.initialize { ready = true }
        onDispose { tts.shutdown() }
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        if (current == null) {
            Text("请选择一个故事开始播放")
            return@Column
        }
        Text(current.title, style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(8.dp))
        Text("${current.ageRange} · ${current.theme}")
        Spacer(Modifier.height(16.dp))

        var idx by remember(current.id) { mutableStateOf(0) }
        val paragraphs = current.paragraphs
        Text(paragraphs.getOrNull(idx) ?: "")
        Spacer(Modifier.height(16.dp))
        Row {
            OutlinedButton(onClick = { if (idx > 0) idx-- }) { Text("上一段") }
            Spacer(Modifier.width(8.dp))
            Button(onClick = {
                if (ready) tts.speak(paragraphs.getOrNull(idx) ?: "")
                // 启动前台服务（占位通知），确保后台播放体验
                ctx.startForegroundService(Intent(ctx, PlayerService::class.java))
            }) { Text("朗读这一段") }
            Spacer(Modifier.width(8.dp))
            OutlinedButton(onClick = { if (idx < paragraphs.lastIndex) idx++ }) { Text("下一段") }
        }
    }
}
