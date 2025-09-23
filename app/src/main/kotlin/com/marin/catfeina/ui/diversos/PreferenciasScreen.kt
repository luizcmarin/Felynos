/*
 * Arquivo: com.marin.catfeina.ui.diversos.PreferenciasScreen.kt
 * @project Catfeina
 * @description
 * Composable que representa a tela de configurações de preferências do aplicativo,
 * permitindo ao usuário, por exemplo, alterar o tema visual.
 */
package com.marin.catfeina.ui.diversos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.marin.catfeina.ui.theme.ThemeChoice

/**
 * Tela de preferências onde o usuário pode configurar opções do aplicativo,
 * como a seleção do tema visual.
 *
 * @param viewModel Instância de [PreferenciasViewModel] injetada pelo Hilt,
 *                  usada para obter e atualizar as preferências de tema.
 */
@Composable
fun PreferenciasScreen(
    // modifier: Modifier = Modifier, // Se precisar passar modificador do NavHost
    viewModel: PreferenciasViewModel = hiltViewModel()
) {
    val currentTheme by viewModel.currentTheme.collectAsState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState()) // Para caso haja muitas opções de tema
    ) {
        Text(
            text = "Escolha o Tema do Aplicativo",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Selecione sua aparência visual preferida para o Catfeina.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Agrupa os RadioButtons para acessibilidade
        Column(Modifier.selectableGroup()) {
            ThemeChoice.entries.forEach { theme -> // .entries é a forma moderna desde Kotlin 1.9
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(56.dp) // Altura mínima recomendada para itens tocáveis
                        .selectable(
                            selected = (theme == currentTheme),
                            onClick = { viewModel.updateTheme(theme) },
                            role = Role.RadioButton // Para acessibilidade
                        )
                        .padding(horizontal = 16.dp), // Padding interno para o conteúdo da linha
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (theme == currentTheme),
                        onClick = null // onClick é tratado pelo Row pai com Modifier.selectable
                    )
                    Text(
                        text = theme.name.lowercase()
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}
