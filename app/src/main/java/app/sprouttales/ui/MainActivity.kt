package app.sprouttales.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import app.sprouttales.model.Story

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val vm: MainViewModel = viewModel(factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application))
                    MainScreen(vm)
                }
            }
        }
    }
}

@Composable
fun MainScreen(vm: MainViewModel) {
    val stories by vm.stories.collectAsState()
    val current by vm.current.collectAsState()

    Row(Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f)) {
            StoryList(stories) { vm.play(it) }
        }
        Divider(modifier = Modifier.width(1.dp).fillMaxHeight())
        Box(Modifier.weight(1f)) {
            PlayerPane(current)
        }
    }
}

@Composable
fun StoryList(stories: List<Story>, onPlay: (Story) -> Unit) {
    Column(Modifier.fillMaxSize()) {
        Text("星芽故事", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
        LazyColumn(Modifier.fillMaxSize()) {
            items(stories) { s ->
                ListItem(
                    headlineContent = { Text(s.title) },
                    supportingContent = { Text("${s.ageRange} · ${s.theme}") },
                    modifier = Modifier.clickable { onPlay(s) }
                )
                Divider()
            }
        }
    }
}
