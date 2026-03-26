package app.sprouttales.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var playing by remember { mutableStateOf(false) }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "星芽故事", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(12.dp))
        Text(text = "离线故事 · 导入 TXT/EPUB · 系统TTS · 后台播放")
        Spacer(Modifier.height(24.dp))
        Row {
            Button(onClick = { playing = !playing }) {
                Text(if (playing) "暂停" else "播放示例")
            }
            Spacer(Modifier.width(12.dp))
            OutlinedButton(onClick = { /* TODO open importer */ }) {
                Text("导入故事")
            }
        }
    }
}
