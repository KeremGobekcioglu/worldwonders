package com.kg.worldwonders

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.kg.worldwonders.common.ApiResult
import com.kg.worldwonders.domain.model.Webcam
import com.kg.worldwonders.domain.repository.WebcamRepository
import com.kg.worldwonders.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var repository: WebcamRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                PlayerSpike()
            }
        }
    }
}

@Composable
fun PlayerSpike() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {

    WebcamPlayer(
            url = "https://webcams.windy.com/webcams/public/embed/player/1203536559/day",
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .align(Alignment.Center),
    )
    }
}

@Composable
fun WebcamPlayer(
    url: String,
    modifier: Modifier = Modifier,
) {
    if (BuildConfig.DEBUG) {
        WebView.setWebContentsDebuggingEnabled(true)
    }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.mediaPlaybackRequiresUserGesture = false
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.setSupportZoom(false)
                setInitialScale(100)

                webChromeClient = WebChromeClient()
                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        Log.d("PLAYER", "loaded: $url")
                        view?.postDelayed({
                            view.evaluateJavascript(
                                """
                                (function() {
                                    var v = document.querySelector('video');
                                    if (v) { v.play(); return 'video'; }
                                    var btn = document.querySelector('[class*=play]');
                                    if (btn) { btn.click(); return 'button'; }
                                    return 'none';
                                })();
                                """.trimIndent(),
                            ) { result -> Log.d("PLAYER", "autoplay attempt result: $result") }
                        }, 500)
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?,
                    ) {
                        Log.e("PLAYER", "error ${error?.errorCode} on ${request?.url}")
                    }
                }
                loadUrl(url)
            }
        },
    )
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
        error != null -> Text("Error: $error", Modifier.padding(16.dp))
        webcams.isEmpty() -> Text("Loading…", Modifier.padding(16.dp))
        else -> LazyColumn(
            modifier = Modifier.fillMaxSize().statusBarsPadding(),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(webcams, key = { it.id }) { webcam ->
                Column(Modifier.padding(vertical = 8.dp)) {
                    Text(webcam.title, style = MaterialTheme.typography.titleMedium)
                    Text("${webcam.city}, ${webcam.country}")
                    Text(webcam.categories.joinToString { it.name })
                    Text(webcam.player?.day ?: "no player")
                }
            }
        }
    }
}