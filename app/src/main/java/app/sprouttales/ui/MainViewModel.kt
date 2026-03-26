package app.sprouttales.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import app.sprouttales.data.AssetStories
import app.sprouttales.model.Story
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(app: Application) : AndroidViewModel(app) {
    private val _stories = MutableStateFlow<List<Story>>(emptyList())
    val stories: StateFlow<List<Story>> = _stories

    private val _current = MutableStateFlow<Story?>(null)
    val current: StateFlow<Story?> = _current

    init { load() }

    private fun load() {
        viewModelScope.launch {
            _stories.value = AssetStories.load(getApplication())
        }
    }

    fun play(story: Story) {
        _current.value = story
    }
}
