package com.marin.catfeina.ui.informativo

// import androidx.compose.foundation.text.ClickableText // REMOVIDA
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.marin.catfeina.R
import com.marin.catfeina.dominio.Icones
import com.marin.catfeina.dominio.MenuTresPontinhos
import com.marin.catfeina.ui.componentes.textoformatado.AplicacaoAnotacaoLink
import com.marin.catfeina.ui.componentes.textoformatado.AplicacaoAnotacaoTooltip
import com.marin.catfeina.ui.componentes.textoformatado.AplicacaoSpanStyle
import com.marin.catfeina.ui.componentes.textoformatado.ElementoConteudo
import com.marin.catfeina.ui.theme.ThemeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformativoScreen(
    onNavegarParaTras: () -> Unit,
    viewModel: InformativoViewModel = hiltViewModel(checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }, null),
    themeViewModel: ThemeViewModel = hiltViewModel() // Adicionado se for usar para menu
) {
    val uiState by viewModel.uiState.collectAsState()
    val localContext = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val snackbarHostState = remember { SnackbarHostState() } // CORRIGIDO: Declaração

    // O ViewModel já deve carregar o informativo baseado no ID do SavedStateHandle em seu init.
    // O LaunchedEffect para carregar pode não ser necessário aqui se o ViewModel já faz isso.
    // Se precisar forçar o carregamento por alguma razão específica da tela:
    // LaunchedEffect(Unit) { // ou alguma chave apropriada se o ID puder mudar dinamicamente na tela
    //     viewModel.carregarInformativo() // Assegure que não é private e não precisa de ID como param aqui
    // }

    // Efeito para observar erros e mostrar Snackbar
    LaunchedEffect(uiState.error) {
        uiState.error?.let { errorMessage ->
            snackbarHostState.showSnackbar(
                message = errorMessage,
                duration = SnackbarDuration.Long
            )
            viewModel.clearError() // CORRIGIDO: Assegure que este método existe e é público no ViewModel
        }
    }

    // Efeito para observar tooltips e mostrar Snackbar
    LaunchedEffect(uiState.tooltipParaMostrar) { // CORRIGIDO: Assegure que tooltipParaMostrar existe no uiState
        uiState.tooltipParaMostrar?.let { tooltipText ->
            snackbarHostState.showSnackbar(
                message = "Tooltip: $tooltipText", // Ou use um Composable de Tooltip mais sofisticado
                duration = SnackbarDuration.Short
            )
            viewModel.clearTooltip() // Assegure que este método existe e é público no ViewModel
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(uiState.informativo?.titulo ?: "Informativo") },
                navigationIcon = {
                    IconButton(onClick = onNavegarParaTras) {
                        Icon(
                            Icones.Voltar, // Usando seu Icones.Voltar
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    val menuExpandedState = remember { mutableStateOf(false) } // Para o menu
                    IconButton(onClick = { menuExpandedState.value = true }) {
                        Icon(Icones.TresPontosVertical, "Mais opções")
                    }
                    if (uiState.informativo != null) {
                        MenuTresPontinhos( // Use seu composable de menu
                            expanded = menuExpandedState,
                            onDismissRequest = { menuExpandedState.value = false },
                            themeViewModel = themeViewModel, // Passando o ThemeViewModel
                            informativoTitulo = uiState.informativo?.titulo,
                            informativoConteudo = viewModel.getConteudoParaTTS() // Assumindo que este método existe
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors( // Exemplo de cores
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
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }
            }

            uiState.informativo != null || uiState.elementosConteudoFormatado.isNotEmpty() -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (!uiState.informativo?.imagem.isNullOrBlank()) {
                        item {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(localContext)
                                        .data("file:///android_asset/catfeina/informativos/${uiState.informativo!!.imagem}")
                                        .crossfade(true)
                                        .error(R.drawable.ic_launcher_background)
                                        .placeholder(R.drawable.ic_launcher_foreground)
                                        .build()
                                ),
                                contentDescription = uiState.informativo?.titulo ?: "Imagem",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp)
                                    .padding(bottom = 8.dp),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    if (uiState.elementosConteudoFormatado.isNotEmpty()) {
                        items(uiState.elementosConteudoFormatado, key = { elemento -> elemento.idUnico
                        }) { elemento ->
                            RenderizarElementoConteudo( // CORRIGIDO: Chamada
                                elemento = elemento,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize, // Ajuste conforme necessário
                                uriHandler = uriHandler,
                                viewModel = viewModel
                            )
                        }
                    } else if (!uiState.isLoading) {
                        item {
                            Text(
                                "Conteúdo não disponível.",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            else -> {
                Box(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) { Text("Informativo não encontrado.") }
            }
        }
    }
}


@Composable
private fun RenderizarElementoConteudo(
    elemento: ElementoConteudo,
    fontSize: androidx.compose.ui.unit.TextUnit,
    uriHandler: androidx.compose.ui.platform.UriHandler, // uriHandler é passado, mas não usado diretamente se Text lida com LinkAnnotation.Url
    viewModel: InformativoViewModel // CORRIGIDO: viewModel é usado para tooltips
) {
    val baseTextStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = fontSize)

    when (elemento) {
        is ElementoConteudo.Paragrafo, is ElementoConteudo.ItemLista -> {
            val textoCru =
                if (elemento is ElementoConteudo.Paragrafo) elemento.textoCru else (elemento as ElementoConteudo.ItemLista).textoItem
            val aplicacoesEmLinha =
                if (elemento is ElementoConteudo.Paragrafo) elemento.aplicacoesEmLinha else (elemento as ElementoConteudo.ItemLista).aplicacoesEmLinha

            if (textoCru.isNotBlank() || aplicacoesEmLinha.isNotEmpty()) {
                val annotatedString = buildAnnotatedString {
                    append(textoCru)
                    aplicacoesEmLinha.forEach { aplicacao ->
                        if (aplicacao.intervalo.first >= 0 && aplicacao.intervalo.last < textoCru.length) {
                            val start = aplicacao.intervalo.first
                            val endExclusive = aplicacao.intervalo.endInclusive + 1
                            when (aplicacao) {
                                is AplicacaoSpanStyle -> {
                                    // ---- INÍCIO DA CORREÇÃO PRINCIPAL ----
                                    var currentStyle = SpanStyle() // Começa com um SpanStyle base

                                    aplicacao.fontWeight?.let {
                                        currentStyle = currentStyle.copy(fontWeight = it)
                                    }
                                    aplicacao.fontStyle?.let {
                                        currentStyle = currentStyle.copy(fontStyle = it)
                                    }
                                    aplicacao.textDecoration?.let {
                                        currentStyle = currentStyle.copy(textDecoration = it)
                                    }
                                    // Adicione outros atributos de SpanStyle que você possa ter em AplicacaoSpanStyle

                                    if (aplicacao.isDestaque) {
                                        currentStyle = currentStyle.copy(
                                            background = MaterialTheme.colorScheme.tertiaryContainer, // Cor de fundo para destaque
                                            color = MaterialTheme.colorScheme.onTertiaryContainer     // Cor do texto sobre o destaque
                                        )
                                    }
                                    // Se isDestaque for falso e você quiser uma cor de texto padrão específica
                                    // que não seja a cor padrão do Composable Text, você pode definir aqui:
                                    // else {
                                    //    currentStyle = currentStyle.copy(color = MaterialTheme.colorScheme.onBackground)
                                    // }

                                    addStyle(
                                        style = currentStyle,
                                        start = start,
                                        end = endExclusive
                                    )
                                    // ---- FIM DA CORREÇÃO PRINCIPAL ----
                                }

                                is AplicacaoAnotacaoLink -> {
                                    val link = LinkAnnotation.Url(
                                        url = aplicacao.url,
                                        linkInteractionListener = { /* Opcional: Log ou outra ação antes do UriHandler */ }
                                    )
                                    addLink(link, start, endExclusive)
                                    addStyle(
                                        SpanStyle(
                                            color = MaterialTheme.colorScheme.primary,
                                            textDecoration = TextDecoration.Underline
                                        ), start, endExclusive
                                    )
                                }

                                is AplicacaoAnotacaoTooltip -> {
                                    val tooltipAnnotation = LinkAnnotation.Clickable(
                                        tag = "TOOLTIP_CLICK",
                                        linkInteractionListener = { annotationText ->
                                            viewModel.mostrarTooltip(aplicacao.textoTooltip) // CORRIGIDO: viewModel é usado
                                        }
                                    )
                                    addLink(tooltipAnnotation, start, endExclusive)
                                    addStyle(
                                        SpanStyle(
                                            textDecoration = TextDecoration.Underline,
                                            color = MaterialTheme.colorScheme.tertiary
                                        ), start, endExclusive
                                    )
                                }
                            }
                        }
                    }
                }

                val textContent = @Composable {
                    Text( // CORRIGIDO: Usando Text em vez de ClickableText
                        text = annotatedString,
                        style = baseTextStyle.copy(textAlign = TextAlign.Start),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                if (elemento is ElementoConteudo.ItemLista) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(text = "• ", style = baseTextStyle)
                        Box(modifier = Modifier.weight(1f)) { textContent() }
                    }
                } else {
                    textContent()
                }
            }
        }

        is ElementoConteudo.Cabecalho -> {
            val style = when (elemento.nivel) {
                1 -> MaterialTheme.typography.headlineLarge.copy(fontSize = fontSize * 1.5f)
                2 -> MaterialTheme.typography.headlineMedium.copy(fontSize = fontSize * 1.3f)
                3 -> MaterialTheme.typography.headlineSmall.copy(fontSize = fontSize * 1.15f)
                else -> baseTextStyle.copy(fontWeight = FontWeight.Bold, fontSize = fontSize * 1.1f)
            }
            Text(
                text = elemento.texto,
                style = style,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )
        }

        is ElementoConteudo.Imagem -> {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data("file:///android_asset/catfeina/informativos/${elemento.nomeArquivo}")
                        .crossfade(true).error(R.drawable.ic_launcher_background)
                        .placeholder(R.drawable.ic_launcher_foreground).build()
                ),
                contentDescription = elemento.textoAlternativo,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp), contentScale = ContentScale.Crop
            )
        }

        is ElementoConteudo.Citacao -> {
            Text(
                text = "“${elemento.texto}”",
                style = baseTextStyle.copy(fontStyle = FontStyle.Italic),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 8.dp, top = 4.dp, bottom = 4.dp)
            )
        }

        ElementoConteudo.LinhaHorizontal -> {
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}

