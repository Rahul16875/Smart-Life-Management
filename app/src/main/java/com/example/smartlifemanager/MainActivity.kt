package com.example.smartlifemanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.smartlifemanager.ui.navigation.SmartLifeManagerNavGraph
import com.example.smartlifemanager.ui.theme.SmartLifeManagerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartLifeManagerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    SmartLifeManagerNavGraph()
                }
            }
        }
    }
}
