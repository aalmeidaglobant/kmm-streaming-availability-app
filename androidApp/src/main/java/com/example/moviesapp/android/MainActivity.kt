package com.example.moviesapp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.moviesapp.MoviesSDK
import com.example.moviesapp.cache.DriverFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviesapp.domain.model.Show
import kotlinx.coroutines.launch

data class ShowScreenState(
    val isLoading: Boolean = false,
    val shows: List<Show> = emptyList()
)

class ShowViewModel(private val sdk: MoviesSDK) : ViewModel() {
    private val _state = mutableStateOf(ShowScreenState())
    val state: State<ShowScreenState> = _state

    init {
        viewModelScope.launch {
            val shows = sdk.getShows(false)
            _state.value = _state.value.copy(
                isLoading = false,
                shows = shows
            )
        }
    }

    fun loadShows() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val shows = sdk.getShows(true)
            _state.value = _state.value.copy(
                isLoading = false,
                shows = shows
            )
        }
    }
}

class MainActivity : ComponentActivity() {
    private val sdk by lazy {
        MoviesSDK(DriverFactory(this@MainActivity))
    }



    @OptIn(ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ShowViewModel(sdk)
        setContent {
           val state by viewModel.state
            val pullRefreshState = rememberPullRefreshState(
                refreshing = state.isLoading,
                onRefresh = viewModel::loadShows
            )


            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
                        ShowTitles(state.shows.map { it.title })
                        PullRefreshIndicator(
                            refreshing = viewModel.state.value.isLoading,
                            state = pullRefreshState,
                            modifier = Modifier.align(Alignment.TopCenter),
                            backgroundColor = if (viewModel.state.value.isLoading) Color.Red else Color.Green,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowTitles(titles: List<String>) {

    LazyColumn {
        items(titles) { title ->
            Spacer(Modifier.size(8.dp))
            Card(
                backgroundColor = MaterialTheme.colors.surface,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(width = 240.dp, height = 100.dp)
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center,
                )
            }

        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        ShowTitles(listOf(""))
    }
}
