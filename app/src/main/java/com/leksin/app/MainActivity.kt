package com.leksin.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.leksin.app.ui.screens.child.ChildMainScreen
import com.leksin.app.ui.theme.LeksinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeksinTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Langsung ke Halaman Anak (Child Dashboard) untuk testing tanpa login
                    ChildMainScreen(
                        modifier = Modifier.padding(innerPadding),
                        onStartGameSession = {
                            // TODO: Mulai sesi permainan
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChildMainPreview() {
    LeksinTheme {
        ChildMainScreen(
            onStartGameSession = {}
        )
    }
}