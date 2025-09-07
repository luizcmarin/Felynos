// =============================================================================
// Arquivo: com.marin.catfeina.ui.SobreScreen.kt
// Descrição: Tela "Sobre".
// =============================================================================
package com.marin.catfeina.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marin.catfeina.ui.Icones
import com.marin.catfeina.ui.theme.CatfeinaTheme
import com.airbnb.lottie.compose.*
import com.marin.catfeina.AppInfo
import com.marin.catfeina.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SobreScreen(
    onNavigateBack: () -> Unit,
    onNavigateToPrivacyPolicy: () -> Unit
) {
    // Carregue a composição da animação Lottie a partir do arquivo JSON
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.catfeina_carregando))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever // Loop infinito
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.titulo_sobre) ) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icones.Voltar,
                            contentDescription = stringResource(R.string.botao_voltar)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Coloque a animação onde você quiser na tela
                LottieAnimation(
                    composition = composition,
                    progress = { progress },
                    modifier = Modifier
                        .size(200.dp) // Defina o tamanho da animação
                        .padding(bottom = 16.dp)
                )


                Text(
                    text = stringResource(R.string.titulo_sobre),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(id = R.string.app_versao, AppInfo.VERSION_NAME),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Seu companheiro financeiro, guiado pelo adorável mascote Cashito.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Cashito é um gato curioso e motivador, pronto para te ajudar a " +
                            "alcançar suas metas financeiras de uma forma divertida.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                Spacer(modifier = Modifier.height(48.dp))
                Text(
                    text = stringResource(R.string.app_desenvolvido_por),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.titulo_politica_privacidade),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable { onNavigateToPrivacyPolicy() }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SobreScreenPreview() {
    CatfeinaTheme {
        SobreScreen(
            onNavigateBack = {},
            onNavigateToPrivacyPolicy = {}
        )
    }
}