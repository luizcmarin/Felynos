// =============================================================================
// Arquivo: com.marin.catfeina.ui.poesias.PoesiaDetailScreen.kt
// Descrição: Composable que exibe a tela de detalhes de uma poesia.
//            Apresenta o título, imagem, metadados, corpo da poesia
//            (HTML renderizado via WebView com tamanho de fonte ajustável),
//            e informações adicionais. Permite marcar como favorita/lida.
//            Usa PoesiaDetailViewModel para dados, ações e preferência de fonte.
// =============================================================================
package com.marin.catfeina.ui.poesias

import android.annotation.SuppressLint
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.DoneAll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.marin.catfeina.R
import com.marin.catfeina.data.entity.Poesia
import com.marin.catfeina.dominio.UiEvent
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoesiaDetailScreen(
    viewModel: PoesiaDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val fontSizeMultiplier: Float by viewModel.fontSizeMultiplier.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    val message = if (event.args.isEmpty()) context.getString(event.messageResId)
                    else context.getString(event.messageResId, *event.args.toTypedArray())
                    snackbarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
                }
                is UiEvent.ShowToast -> {
                    val message = if (event.args.isEmpty()) context.getString(event.messageResId)
                    else context.getString(event.messageResId, *event.args.toTypedArray())
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
                is UiEvent.NavigateUp -> onNavigateBack()
            }
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            PoesiaDetailTopAppBar(
                poesia = uiState.poesia,
                onNavigateBack = onNavigateBack,
                onToggleFavorito = viewModel::toggleFavorito,
                onToggleLido = viewModel::toggleLido,
                scrollBehavior = scrollBehavior
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                uiState.isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = MaterialTheme.colorScheme.primary)
                uiState.error != null -> ErrorStateView(errorMessage = uiState.error!!)
                uiState.poesia != null -> PoesiaDetailContent(poesia = uiState.poesia!!, fontSizeMultiplier = fontSizeMultiplier)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PoesiaDetailTopAppBar(
    poesia: Poesia?,
    onNavigateBack: () -> Unit,
    onToggleFavorito: () -> Unit,
    onToggleLido: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Text(
                text = poesia?.titulo ?: stringResource(R.string.detalhes_da_poesia),
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.botao_voltar),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        actions = {
            poesia?.let { p ->
                IconButton(
                    onClick = onToggleFavorito,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = if (p.isFavorito) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
                ) {
                    Icon(
                        imageVector = if (p.isFavorito) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                        contentDescription = stringResource(if (p.isFavorito) R.string.desmarcar_favorito else R.string.marcar_favorito)
                    )
                }
                IconButton(
                    onClick = onToggleLido,
                    colors = IconButtonDefaults.iconButtonColors(contentColor = if (p.isLido) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant)
                ) {
                    Icon(
                        imageVector = if (p.isLido) Icons.Filled.DoneAll else Icons.Outlined.DoneAll,
                        contentDescription = stringResource(if (p.isLido) R.string.desmarcar_como_lido else R.string.marcar_como_lido)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
            scrolledContainerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(6.dp)
        ),
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun PoesiaDetailContent(
    poesia: Poesia,
    fontSizeMultiplier: Float
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val colorScheme = MaterialTheme.colorScheme
    val typography = MaterialTheme.typography
    val density = LocalDensity.current

    fun TextUnit.asPxInt(): Int = with(density) { this@asPxInt.toPx().toInt() }
    fun Dp.asPxInt(): Int = with(density) { this@asPxInt.toPx().toInt() }

    val corTextoHex = remember(colorScheme.onSurface) { colorScheme.onSurface.toHex() }
    val corLinkHex = remember(colorScheme.primary) { colorScheme.primary.toHex() }
    val corBordaBlocoCitacaoHex = remember(colorScheme.primary) { colorScheme.primary.copy(alpha = 0.5f).toHex() }
    val corTextoBlocoCitacaoHex = remember(colorScheme.onSurface) { colorScheme.onSurface.copy(alpha = 0.8f).toHex() }
    val blockquoteBgHex = remember(colorScheme.surfaceVariant) { colorScheme.surfaceVariant.copy(alpha = 0.2f).toHex() }
    val calloutInfoColor = remember(colorScheme.primary) { colorScheme.primary.toHex() }
    val calloutInfoBg = remember(colorScheme.primaryContainer) { colorScheme.primaryContainer.copy(alpha = 0.15f).toHex() }
    val calloutInfoBorder = remember(colorScheme.primary) { colorScheme.primary.copy(alpha = 0.4f).toHex() }
    val calloutWarningColorText = remember(colorScheme.onErrorContainer) {colorScheme.onErrorContainer.toHex()}
    val calloutWarningBg = remember(colorScheme.errorContainer) { colorScheme.errorContainer.copy(alpha = 0.35f).toHex() }
    val calloutWarningBorder = remember(colorScheme.error) { colorScheme.error.copy(alpha = 0.6f).toHex() }
    val corPoesiaLegendaTitulo = remember(colorScheme.tertiary) { colorScheme.tertiary.toHex() }
    val corBordaPoesiaLegendaTitulo = remember(colorScheme.outlineVariant) { colorScheme.outlineVariant.copy(alpha = 0.3f).toHex() }
    val corPoesiaLegenda = remember(colorScheme.secondary) { colorScheme.secondary.toHex() }
    val corPoesiaLegendaTexto = remember(colorScheme.onSurfaceVariant) { colorScheme.onSurfaceVariant.toHex() }
    val corPoesiaDestaque = remember(colorScheme.primary) { colorScheme.primary.toHex() }
    val imgPlaceholderBgHex = remember(colorScheme.surfaceVariant) { colorScheme.surfaceVariant.copy(alpha = 0.3f).toHex() }

    val fontFamilyCss = remember(typography.bodyLarge.fontFamily) {
        when (typography.bodyLarge.fontFamily) {
            FontFamily.SansSerif -> "sans-serif"
            FontFamily.Monospace -> "monospace"
            else -> "sans-serif"
        }
    }

    val baseBodyTextStyle = typography.bodyMedium
    val bodyFontSizePx = remember(baseBodyTextStyle.fontSize, fontSizeMultiplier, density) {
        (baseBodyTextStyle.fontSize.value * fontSizeMultiplier).sp.asPxInt()
    }
    val bodyLineHeightMultiplier = remember(baseBodyTextStyle.lineHeight, baseBodyTextStyle.fontSize) { // fontSizeMultiplier não afeta diretamente aqui
        val baseMultiplier = if (baseBodyTextStyle.fontSize != 0.sp && baseBodyTextStyle.lineHeight != TextUnit.Unspecified) {
            (baseBodyTextStyle.lineHeight.value / baseBodyTextStyle.fontSize.value)
        } else 1.5f
        baseMultiplier.coerceAtLeast(1.4f)
    }

    val h1TextStyle = typography.headlineLarge
    val h1FontSizePx = remember(h1TextStyle.fontSize, fontSizeMultiplier, density) { (h1TextStyle.fontSize.value * fontSizeMultiplier.coerceAtMost(1.2f)).sp.asPxInt() }
    val h1FontWeight = remember(h1TextStyle.fontWeight) { h1TextStyle.fontWeight?.weight ?: FontWeight.Bold.weight }
    val h1LineHeightMultiplier = remember(h1TextStyle.lineHeight, h1TextStyle.fontSize, density) {
        if (h1TextStyle.fontSize != 0.sp && h1TextStyle.lineHeight != TextUnit.Unspecified) (h1TextStyle.lineHeight.value / h1TextStyle.fontSize.value).coerceAtLeast(1.2f) else 1.3f
    }

    val imgBorderRadiusPx = remember(MaterialTheme.shapes.medium, density) { 8.dp.asPxInt() }

    val legendaTituloFontSizePx = remember(typography.titleMedium.fontSize, fontSizeMultiplier, density) { (typography.titleMedium.fontSize.value * fontSizeMultiplier.coerceAtMost(1.1f)).sp.asPxInt() }
    val legendaTituloFontWeight = remember(typography.titleMedium.fontWeight) { typography.titleMedium.fontWeight?.weight ?: FontWeight.SemiBold.weight }
    val legendaFontSizePx = remember(typography.labelLarge.fontSize, fontSizeMultiplier, density) { (typography.labelLarge.fontSize.value * fontSizeMultiplier).sp.asPxInt() }
    val legendaFontWeight = remember(typography.labelLarge.fontWeight) { typography.labelLarge.fontWeight?.weight ?: FontWeight.Medium.weight }
    val legendaTextoFontSizePx = remember(typography.bodySmall.fontSize, fontSizeMultiplier, density) { (typography.bodySmall.fontSize.value * fontSizeMultiplier).sp.asPxInt() }
    val legendaTextoFontWeight = remember(typography.bodySmall.fontWeight) { typography.bodySmall.fontWeight?.weight ?: FontWeight.Normal.weight }
    val destaqueFontSizePx = remember(typography.bodyLarge.fontSize, fontSizeMultiplier, density) { (typography.bodyLarge.fontSize.value * fontSizeMultiplier * 1.1f).sp.asPxInt() }
    val destaqueFontWeight = remember(typography.bodyLarge.fontWeight) { typography.bodyLarge.fontWeight?.weight?.plus(100)?.coerceAtMost(900) ?: FontWeight.SemiBold.weight }

    val cssStyle = remember(
        corTextoHex, corLinkHex, corBordaBlocoCitacaoHex, corTextoBlocoCitacaoHex,
        calloutInfoColor, calloutInfoBg, calloutInfoBorder, calloutWarningColorText, calloutWarningBg, calloutWarningBorder,
        corPoesiaLegendaTitulo, corBordaPoesiaLegendaTitulo, corPoesiaLegenda, corPoesiaLegendaTexto, corPoesiaDestaque,
        fontFamilyCss, bodyFontSizePx, bodyLineHeightMultiplier,
        h1FontSizePx, h1FontWeight, h1LineHeightMultiplier, imgBorderRadiusPx,
        legendaTituloFontSizePx, legendaTituloFontWeight, legendaFontSizePx, legendaFontWeight,
        legendaTextoFontSizePx, legendaTextoFontWeight, destaqueFontSizePx, destaqueFontWeight, blockquoteBgHex, imgPlaceholderBgHex
    ) {
        """
        <style type="text/css">
            body {
                color: #$corTextoHex; background-color: transparent !important; font-family: '$fontFamilyCss', sans-serif;
                font-size: ${bodyFontSizePx}px; line-height: $bodyLineHeightMultiplier;
                margin: 0 1rem; padding: 1px 0; word-wrap: break-word; -webkit-text-size-adjust: 100%;
            }
            a { color: #$corLinkHex; text-decoration: none; }
            a:hover { text-decoration: underline; }
            img {
                max-width: 100%; height: auto; display: block;
                margin-top: ${12.dp.asPxInt()}px; margin-bottom: ${12.dp.asPxInt()}px;
                border-radius: ${imgBorderRadiusPx}px; background-color: #${imgPlaceholderBgHex};
            }
            p, li, div {
                line-height: $bodyLineHeightMultiplier; font-size: ${bodyFontSizePx}px; color: #$corTextoHex;
                margin-top: 0.5em; margin-bottom: 0.5em;
            }
            p:first-child, div:first-child { margin-top: 0; }
            p:last-child, div:last-child { margin-bottom: 0; }
            h1 {
                font-size: ${h1FontSizePx}px; color: #$corTextoHex; font-weight: $h1FontWeight;
                line-height: $h1LineHeightMultiplier; margin-top: ${16.dp.asPxInt()}px; margin-bottom: ${8.dp.asPxInt()}px;
            }
            /* TODO: Mapear H2-H6 para typography.headlineMedium, titleLarge, etc., aplicando fontSizeMultiplier */
            blockquote {
                border-left: 3px solid #$corBordaBlocoCitacaoHex; padding: ${8.dp.asPxInt()}px ${12.dp.asPxInt()}px;
                margin: ${12.dp.asPxInt()}px 0; font-style: italic;
                color: #$corTextoBlocoCitacaoHex; background-color: #$blockquoteBgHex;
            }
            ul, ol { padding-left: ${24.dp.asPxInt()}px; margin-top: 0.5em; margin-bottom: 1em; }
            li { margin-bottom: 0.25em; }
            .bd-callout {
                padding: 1rem; margin-top: 1.25rem; margin-bottom: 1.25rem;
                border-left-width: .25rem; border-left-style: solid; border-radius: ${4.dp.asPxInt()}px;
            }
            .bd-callout-info { color: #$calloutInfoColor; background-color: #$calloutInfoBg; border-left-color: #$calloutInfoBorder; }
            .bd-callout-warning { color: #$calloutWarningColorText; background-color: #$calloutWarningBg; border-left-color: #$calloutWarningBorder; }
            .bd-callout-danger {
                color: #${colorScheme.onErrorContainer.toHex()};
                background-color: #${colorScheme.errorContainer.copy(alpha = 0.35f).toHex()};
                border-left-color: #${colorScheme.error.copy(alpha = 0.6f).toHex()};
            }
            p.poesia-legenda-titulo {
                text-align: left; font-style: normal; font-weight: $legendaTituloFontWeight; font-size: ${legendaTituloFontSizePx}px;
                font-variant: small-caps; color: #$corPoesiaLegendaTitulo; clear: left;
                border-bottom: 1px solid #$corBordaPoesiaLegendaTitulo;
                margin-top: 1.5em; margin-bottom: 0.5em; padding-bottom: 0.25em; padding-left: 0; padding-right: 0;
            }
            p.poesia-legenda {
                text-align: left; font-style: italic; font-weight: $legendaFontWeight; font-size: ${legendaFontSizePx}px;
                color: #$corPoesiaLegenda; margin-top: 0.25em; margin-bottom: 0.75em; padding: 0;
            }
            p.poesia-legenda-texto {
                font-style: italic; font-weight: $legendaTextoFontWeight; font-size: ${legendaTextoFontSizePx}px;
                color: #$corPoesiaLegendaTexto; margin-top: 0.25em; margin-bottom: 1.5em;
            }
            p.poesia-legenda-numero {
                margin: 0; 
                font-size: ${ (legendaTextoFontSizePx * 0.9).toInt().coerceAtLeast(8) }px;
                color: #$corPoesiaLegendaTexto;
            }
            p.poesia-destaque {
                margin-top: 1em; margin-bottom: 1em; font-weight: $destaqueFontWeight; font-size: ${destaqueFontSizePx}px;
                color: #$corPoesiaDestaque; padding: 0.5em;
                background-color: #${colorScheme.secondaryContainer.copy(alpha = 0.2f).toHex()};
                border-left: 3px solid #${colorScheme.secondary.toHex()}; padding-left: 0.75em;
            }
        </style>
        """.trimIndent()
    }

    val finalHtmlContent = remember(poesia.conteudo, cssStyle) {
        "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">$cssStyle</head><body>${poesia.conteudo}</body></html>"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(top = 8.dp, bottom = 32.dp, start = 16.dp, end = 16.dp)
    ) {
        poesia.imagem?.takeIf { it.isNotBlank() }?.let { imageUrl ->
            AsyncImage(
                model = imageUrl,
                contentDescription = stringResource(R.string.capa_da_poesia, poesia.titulo),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 180.dp, max = 320.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.ic_placeholder_image_poetry),
                error = painterResource(id = R.drawable.ic_placeholder_image_poetry)
            )
            Spacer(modifier = Modifier.height(20.dp))
        }

        Text(
            text = poesia.titulo,
            style = MaterialTheme.typography.displaySmall.copy(
                color = MaterialTheme.colorScheme.primary,
                lineHeight = MaterialTheme.typography.displaySmall.lineHeight * 0.9
            ),
            modifier = Modifier.padding(horizontal = 4.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = stringResource(id = poesia.categoria.displayNameResId),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )
            poesia.dataCriacao?.let { dataNaoNula ->
                Text(
                    text = "•  ${SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(dataNaoNula))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
        Spacer(modifier = Modifier.height(20.dp))

        Column(modifier = Modifier.fillMaxWidth()) {
            if (poesia.textoBase.isNotBlank()) {
                Text(
                    text = poesia.textoBase,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontStyle = FontStyle.Italic,
                        lineHeight = (MaterialTheme.typography.bodyLarge.lineHeight.value * 1.25f).sp.takeIf { MaterialTheme.typography.bodyLarge.lineHeight != TextUnit.Unspecified } ?: 22.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )
                if (poesia.conteudo.isNotBlank()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f), modifier = Modifier.padding(horizontal = 32.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                } else {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            if (poesia.conteudo.isNotBlank()) {
                AndroidView(
                    factory = { factoryContext ->
                        WebView(factoryContext).apply {
                            @SuppressLint("SetJavaScriptEnabled")
                            settings.javaScriptEnabled = false
                            settings.loadWithOverviewMode = true
                            settings.useWideViewPort = true
                            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                            settings.defaultTextEncodingName = "UTF-8"
                            settings.setNeedInitialFocus(false)
                            settings.blockNetworkImage = false
                            settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                            webViewClient = android.webkit.WebViewClient()
                            setBackgroundColor(Color.Transparent.toArgb())
                        }
                    },
                    update = { webView -> webView.post { webView.loadDataWithBaseURL(null, finalHtmlContent, "text/html", "UTF-8", null) } },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        val hasAdditionalInfo = poesia.campoAudio?.isNotBlank() == true || poesia.campoVideo?.isNotBlank() == true ||
                poesia.campoExtra?.isNotBlank() == true || poesia.campoUrl1?.isNotBlank() == true || poesia.campoUrl2?.isNotBlank() == true
        if (hasAdditionalInfo) {
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.informacoes_adicionais),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                poesia.campoAudio?.takeIf { it.isNotBlank() }?.let { InfoRow(label = stringResource(R.string.audio), value = it, context = context, uriHandler = uriHandler) }
                poesia.campoVideo?.takeIf { it.isNotBlank() }?.let { InfoRow(label = stringResource(R.string.video), value = it, context = context, uriHandler = uriHandler) }
                poesia.campoExtra?.takeIf { it.isNotBlank() }?.let { InfoRow(label = stringResource(R.string.notas), value = it, context = context, uriHandler = uriHandler) }
                poesia.campoUrl1?.takeIf { it.isNotBlank() }?.let { InfoRow(label = stringResource(R.string.link_1), value = it, isUrl = true, context = context, uriHandler = uriHandler) }
                poesia.campoUrl2?.takeIf { it.isNotBlank() }?.let { InfoRow(label = stringResource(R.string.link_2), value = it, isUrl = true, context = context, uriHandler = uriHandler) }
            }
        }
    }
}

@Composable
private fun InfoRow(
    label: String, value: String, isUrl: Boolean = false,
    context: android.content.Context, uriHandler: androidx.compose.ui.platform.UriHandler
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$label: ",
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(0.3f).padding(end = 4.dp)
        )
        if (isUrl) {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline),
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.weight(0.7f).clickable {
                    try { uriHandler.openUri(value) }
                    catch (e: Exception) { Toast.makeText(context, context.getString(R.string.erro_abrir_link_generico), Toast.LENGTH_SHORT).show() }
                }
            )
        } else {
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.weight(0.7f)
            )
        }
    }
}

@Composable
private fun ErrorStateView(errorMessage: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.ErrorOutline,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.ops_algo_deu_errado),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun Color.toHex(includeAlpha: Boolean = false): String {
    val colorInt = this.toArgb()
    return if (includeAlpha) String.format(Locale.ROOT, "%08X", colorInt)
    else String.format(Locale.ROOT, "%06X", 0xFFFFFF and colorInt)
}
