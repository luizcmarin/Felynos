// =============================================================================
// Arquivo: com.marin.catfeina.ui.poesias.PoesiaScreen.kt
// Descrição: Define a tela de UI para a listagem de Poesias.
//            Esta tela interage com [PoesiaViewModel] para obter dados
//            e executar ações como exclusão, favoritar, marcar como lido
//            e navegação para detalhes.
// =============================================================================
package com.marin.catfeina.ui.poesias

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.marin.catfeina.R
import com.marin.catfeina.data.entity.Poesia
import com.marin.catfeina.dominio.UiEvent
import com.marin.catfeina.ui.Icones
import kotlinx.coroutines.flow.collectLatest


/**
 * Tela principal para listar Poesias.
 *
 * Exibe uma lista das poesias existentes e permite ações como excluir,
 * favoritar, marcar como lido e navegar para visualizar os detalhes de uma poesia.
 *
 * @param viewModel O [PoesiaViewModel] que fornece o estado e lida com a lógica de negócios.
 * @param onNavigateBack Callback para ser invocado quando a navegação para a tela anterior é solicitada.
 * @param onNavigateToPoesiaDetail Callback para navegar para os detalhes de uma poesia específica.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoesiaScreen(
    viewModel: PoesiaViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToPoesiaDetail: (poesiaId: Long) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    if (uiState.mostrarDialogoExclusao && uiState.itemParaExcluir != null) {
        AlertDialog(
            onDismissRequest = { viewModel.cancelarExclusao() },
            title = { Text(text = stringResource(R.string.dialogo_confirmar_exclusao_titulo)) },
            text = {
                Text(
                    text = stringResource(
                        R.string.dialogo_confirmar_exclusao_mensagem,
                        uiState.itemParaExcluir!!.titulo
                    )
                )
            },
            confirmButton = {
                TextButton(onClick = { viewModel.confirmarExclusao() }) {
                    Text(stringResource(R.string.botao_excluir))
                }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.cancelarExclusao() }) {
                    Text(stringResource(R.string.botao_cancelar))
                }
            }
        )
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    val message = if (event.args.isEmpty()) {
                        context.getString(event.messageResId)
                    } else {
                        context.getString(event.messageResId, *event.args.toTypedArray())
                    }
                    snackbarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
                }
                is UiEvent.ShowToast -> {
                    val message = if (event.args.isEmpty()) {
                        context.getString(event.messageResId)
                    } else {
                        context.getString(event.messageResId, *event.args.toTypedArray())
                    }
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
                UiEvent.NavigateUp -> onNavigateBack()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.titulo_poesias)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icones.Voltar,
                            contentDescription = stringResource(R.string.botao_voltar)
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
        PoesiaListContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            onExcluirClick = viewModel::prepararParaExcluir,
            onItemClick = { poesia -> onNavigateToPoesiaDetail(poesia.id) },
            onToggleFavorito = viewModel::toggleFavorito,
            onToggleLido = viewModel::toggleLido
        )
    }
}

@Composable
private fun PoesiaListContent(
    modifier: Modifier = Modifier,
    uiState: PoesiaListScreenUiState,
    onExcluirClick: (Poesia) -> Unit,
    onItemClick: (Poesia) -> Unit,
    onToggleFavorito: (Poesia) -> Unit,
    onToggleLido: (Poesia) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp), // Padding horizontal para a lista inteira
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (uiState.isLoading && uiState.poesias.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.globalErrorResId != null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Padding para mensagem de erro
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(uiState.globalErrorResId),
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        } else if (uiState.poesias.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Padding para mensagem de lista vazia
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.nenhuma_poesia_encontrada),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 8.dp, bottom = 16.dp) // Espaçamento no topo e no final da lista
            ) {
                items(
                    items = uiState.poesias,
                    key = { poesia -> poesia.id }
                ) { poesia ->
                    PoesiaListItem(
                        poesia = poesia,
                        onExcluirClick = { onExcluirClick(poesia) },
                        onItemClick = { onItemClick(poesia) },
                        onToggleFavorito = { onToggleFavorito(poesia) },
                        onToggleLido = { onToggleLido(poesia) }
                    )
                    if (uiState.poesias.last() != poesia) {
                        // Usar HorizontalDivider do Material 3
                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun PoesiaListItem(
    poesia: Poesia,
    onExcluirClick: () -> Unit,
    onItemClick: () -> Unit,
    onToggleFavorito: () -> Unit,
    onToggleLido: () -> Unit
) {
    Card(
        onClick = onItemClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 8.dp), // Padding horizontal para cada card
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        shape = RoundedCornerShape(12.dp) // Bordas um pouco mais arredondadas
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Exibir imagem da poesia
            if (poesia.imagem.isNotBlank()) {
                AsyncImage(
                    model = poesia.imagem,
                    contentDescription = stringResource(R.string.capa_da_poesia, poesia.titulo),
                    modifier = Modifier
                        .size(72.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.ic_placeholder_image),
                    error = painterResource(id = R.drawable.ic_placeholder_image)
                )
                Spacer(Modifier.width(16.dp))
            } else {
                Icon(
                    imageVector = Icones.SemImagem,
                    contentDescription = stringResource(R.string.imagem_nao_disponivel),
                    modifier = Modifier
                        .size(72.dp)
                        .padding(8.dp), // Padding dentro do ícone se não houver imagem
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.width(16.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = poesia.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = stringResource(id = poesia.categoria.displayNameResId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = poesia.textoBase.ifBlank { poesia.conteudo },
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(8.dp)) // Espaço antes da coluna de ícones

            // Coluna para ícones de ação alinhados verticalmente
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround // Para espaçar um pouco os botões
            ) {
                IconButton(onClick = onToggleFavorito, modifier = Modifier.size(40.dp)) {
                    Icon(
                        imageVector = if (poesia.isFavorito) Icones.Favorito else Icones.DesmarcaFavorito,
                        contentDescription = stringResource(if (poesia.isFavorito) R.string.desmarcar_favorito else R.string.marcar_favorito),
                        tint = if (poesia.isFavorito) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline
                    )
                }

                IconButton(onClick = onToggleLido, modifier = Modifier.size(40.dp)) {
                    Icon(
                        imageVector = if (poesia.isLido) Icones.Ver else Icones.NaoVer,
                        contentDescription = stringResource(if (poesia.isLido) R.string.desmarcar_como_lido else R.string.marcar_como_lido),
                        tint = if (poesia.isLido) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.outline
                    )
                }

                IconButton(onClick = onExcluirClick, modifier = Modifier.size(40.dp)) {
                    Icon(
                        imageVector = Icones.Excluir,
                        contentDescription = stringResource(R.string.excluir_poesia, poesia.titulo),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
