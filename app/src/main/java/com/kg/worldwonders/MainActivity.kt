package com.kg.worldwonders

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.kg.worldwonders.common.ApiResult
import com.kg.worldwonders.domain.model.Webcam
import com.kg.worldwonders.domain.repository.WebcamRepository
import com.kg.worldwonders.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var repository: WebcamRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                DebugFeed(repository)
            }
        }
    }
}

@Composable
fun DebugFeed(repository: WebcamRepository) {
    var webcams by remember { mutableStateOf<List<Webcam>>(emptyList()) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        when (val result = repository.getListOfWebcams(limit = 10)) {
            is ApiResult.Success -> webcams = result.data
            is ApiResult.Error -> error = result.message
            ApiResult.Loading -> Unit
        }
    }

    when {
        error != null -> Text(
            text = "Error: $error",
            modifier = Modifier.padding(16.dp),
        )
        webcams.isEmpty() -> Text("Loading…", modifier = Modifier.padding(16.dp))
        else -> LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(webcams, key = { it.id }) { webcam ->
                Column(Modifier.padding(vertical = 8.dp)) {
                    Text(webcam.title, style = MaterialTheme.typography.titleMedium)
                    Text("${webcam.city}, ${webcam.country}")
                    Text(webcam.categories.joinToString { it.name })
                    Text(webcam.player?.day ?: "no player", maxLines = 1)
                }
            }
        }
    }
}
