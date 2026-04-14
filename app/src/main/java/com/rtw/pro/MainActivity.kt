package com.rtw.pro

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.rtw.pro.app.AppRuntimeComposition

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startupHandler = AppRuntimeComposition.provideMainAppStartupHandler()
        val runtimeState = startupHandler.onCreate(
            mapConfig = AppRuntimeComposition.defaultMapConfig(),
            streetViewConfig = AppRuntimeComposition.defaultStreetViewConfig()
        )

        val debugText = buildString {
            appendLine("Ride The World Pro")
            appendLine("Runtime status dashboard")
            appendLine()
            appendLine("authReady: ${runtimeState.authReady}")
            appendLine("mapReady: ${runtimeState.mapReady}")
            appendLine("mapMode: ${runtimeState.mapMode}")
            appendLine("mapErrorCode: ${runtimeState.mapErrorCode ?: "none"}")
            appendLine("pushTokenSynced: ${runtimeState.pushTokenSynced}")
            appendLine()
            appendLine("mapMessage:")
            appendLine(runtimeState.mapMessage.ifBlank { "(empty)" })
        }

        setContentView(
            TextView(this).apply {
                text = debugText
                textSize = 16f
                setPadding(48, 96, 48, 48)
            }
        )
    }
}
