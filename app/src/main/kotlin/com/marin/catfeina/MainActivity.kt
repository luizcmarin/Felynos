// =============================================================================
// Arquivo: com.marin.catfeina.MainActivity.kt
// Descrição: Atividade principal que inicializa o aplicativo Catfeina.
//            Responsável pela configuração da tela de splash (padrão e Lottie),
//            gerenciamento do tema do aplicativo (claro/escuro/sistema) com base
//            nas preferências do usuário (obtidas via PreferenciasViewModel),
//            e pela configuração do comportamento edge-to-edge da UI.
// =============================================================================
package com.marin.catfeina

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.marin.catfeina.dominio.PreferenciaTema
import com.marin.catfeina.ui.preferencias.PreferenciasViewModel
import com.marin.catfeina.ui.theme.CatfeinaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var keepSplashScreenOn = true
    private val preferenciasViewModel: PreferenciasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { keepSplashScreenOn }

        splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
            val splashScreenView = splashScreenViewProvider.view
            val fadeOut = ObjectAnimator.ofFloat(
                splashScreenView,
                View.ALPHA,
                1f,
                0f
            )
            fadeOut.interpolator = AnticipateInterpolator()
            fadeOut.duration = 300L
            fadeOut.doOnEnd {
                splashScreenViewProvider.remove()
            }
            fadeOut.start()
        }

        setContent {
            val preferenciaTemaAtual by preferenciasViewModel.preferenciaTema.collectAsState()
            val systemIsDark = isSystemInDarkTheme()

            val aplicarTemaEscuro = when (preferenciaTemaAtual) {
                PreferenciaTema.SYSTEM -> systemIsDark
                PreferenciaTema.LIGHT -> false
                PreferenciaTema.DARK -> true
            }

            DisposableEffect(aplicarTemaEscuro) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.auto(
                        Color.Transparent.toArgb(),
                        Color.Transparent.toArgb(),
                        detectDarkMode = { aplicarTemaEscuro }
                    ),
                    navigationBarStyle = SystemBarStyle.auto(
                        Color.Transparent.toArgb(),
                        Color.Transparent.toArgb(),
                        detectDarkMode = { aplicarTemaEscuro }
                    )
                )
                onDispose {}
            }

            CatfeinaTheme(
                darkTheme = aplicarTemaEscuro,
                dynamicColor = false
            ) {
                var showLottieSplash by remember { mutableStateOf(true) }

                if (showLottieSplash) {
                    AppEntry(
                        onAnimationFinish = {
                            showLottieSplash = false
                        },
                        signalSplashScreenReadyToDismiss = {
                            this@MainActivity.keepSplashScreenOn = false
                        }
                    )
                } else {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun AppEntry(
    onAnimationFinish: () -> Unit,
    signalSplashScreenReadyToDismiss: () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.catfeina_pata))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = 1,
        speed = 1f
    )

    LaunchedEffect(Unit) {
        signalSplashScreenReadyToDismiss()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxSize(0.7f)
        )
    }

    LaunchedEffect(progress) {
        if (progress == 1.0f) {
            onAnimationFinish()
        }
    }
}
