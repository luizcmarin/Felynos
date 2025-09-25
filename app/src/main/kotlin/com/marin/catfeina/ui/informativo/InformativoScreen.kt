package com.marin.catfeina.ui.informativo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
// import androidx.compose.foundation.layout.Row // Removido se não usado diretamente aqui
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
// import androidx.compose.material3.HorizontalDivider // Removido se não usado diretamente aqui
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
// import androidx.compose.ui.platform.LocalUriHandler // Removido, pois é usado dentro do Renderizador
// import androidx.compose.ui.text.LinkAnnotation // Removido
// import androidx.compose.ui.text.SpanStyle // Removido
// import androidx.compose.ui.text.buildAnnotatedString // Removido
// import androidx.compose.ui.text.font.FontStyle // Removido
// import androidx.compose.ui.text.font.FontWeight // Removido
import androidx.compose.ui.text.style.TextAlign
// import androidx.compose.ui.text.style.TextDecoration // Removido
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.marin.catfeina.R
import com.marin.catfeina.dominio.Icones
import com.marin.catfeina.dominio.MenuTresPontinhos
// Importe o Renderizador e os modelos de dados se ainda não estiverem
import com.marin.catfeina.ui.componentes.textoformatado.RenderizarElementoConteudo // <<< ADICIONE ESTE IMPORT
import com.marin.catfeina.ui.componentes.textoformatado.ElementoConteudo // Pode já estar importado
import com.marin.catfeina.ui.theme.ThemeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformativoScreen(
    onNavegarParaTras: () -> Unit,
    viewModel: InformativoViewModel = hiltViewModel(checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }, null),
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val localContext = LocalContext.current
    // val uriHandler = LocalUriHandler.current // Movido para dentro do Renderizador
    val snackbarHostState = remember { SnackbarHostState() }

    // ... (LaunchedEffects permanecem os mesmos) ...
    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Long
            )
            viewModel.clearError()
        }
    }

    LaunchedEffect(uiState.tooltipParaMostrar) {
        uiState.tooltipParaMostrar?.let { tooltipText ->
            snackbarHostState.showSnackbar(
                message = "Tooltip: $tooltipText",
                duration = SnackbarDuration.Short
            )
            viewModel.clearTooltip()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(uiState.informativo?.titulo ?: "Informativo") },
                navigationIcon = {
                    IconButton(onClick = onNavegarParaTras) {
                        Icon(Icones.Voltar, contentDescription = "Voltar")
                    }
                },
                actions = {
                    val menuExpandedState = remember { mutableStateOf(false) }
                    IconButton(onClick = { menuExpandedState.value = true }) {
                        Icon(Icones.TresPontosVertical, "Mais opções")
                    }
                    if (uiState.informativo != null) {
                        MenuTresPontinhos(
                            expanded = menuExpandedState,
                            onDismissRequest = { menuExpandedState.value = false },
                            themeViewModel = themeViewModel,
                            informativoTitulo = uiState.informativo?.titulo,
                            informativoConteudo = viewModel.getConteudoParaTTS()
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        when {
            uiState.isLoading -> {
                Box(
                    Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            uiState.informativo != null || uiState.elementosConteudoFormatado.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Imagem principal do informativo (se houver)
                    if (!uiState.informativo?.imagem.isNullOrBlank()) {
                        item {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(localContext)
                                        .data("file:///android_asset/catfeina/informativos/${uiState.informativo!!.imagem}")
                                        .crossfade(true)
                                        .error(R.drawable.ic_launcher_background) // Substitua se necessário
                                        .placeholder(R.drawable.ic_launcher_foreground) // Substitua se necessário
                                        .build()
                                ),
                                contentDescription = uiState.informativo?.titulo ?: "Imagem",
                                modifier = Modifier.fillMaxWidth().height(200.dp).padding(bottom = 8.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    // Renderização dos elementos de conteúdo formatado
                    if (uiState.elementosConteudoFormatado.isNotEmpty()) {
                        items(
                            items = uiState.elementosConteudoFormatado, // <<< CORREÇÃO AQUI
                            key = { elemento -> elemento.idUnico }
                        ) { elemento ->
                            // Chamada para o Renderizador movido e modificado
                            RenderizarElementoConteudo(
                                elemento = elemento,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                tooltipHandler = viewModel // ViewModel agora implementa TooltipHandler
                                // uriHandler não é mais passado diretamente daqui
                            )
                        }
                    } else if (!uiState.isLoading) {
                        item {
                            Text(
                                "Conteúdo não disponível.",
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            else -> {
                Box(
                    Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                    contentAlignment = Alignment.Center
                ) { Text("Informativo não encontrado.") }
            }
        }
    }
}

// =======================================================================================
// REMOVA A FUNÇÃO RenderizarElementoConteudo DESTE ARQUIVO (InformativoScreen.kt)
// ELA FOI MOVIDA PARA ui.componentes.textoformatado.TextoFormatadoRenderer.kt
// =======================================================================================
// @Composable
// private fun RenderizarElementoConteudo(...) {
//    ...
// }
