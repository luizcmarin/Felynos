// =============================================================================
// Arquivo: com.marin.catfeina.Navigation.kt
// Descrição: Gráfico de navegação, definições de rota e itens de menu.
// =============================================================================
package com.marin.catfeina

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.marin.catfeina.ui.Icones
import com.marin.catfeina.ui.poesias.PoesiaDetailScreen
import com.marin.catfeina.ui.poesias.PoesiaScreen
import com.marin.catfeina.ui.screens.PrivacyPolicyScreen
import com.marin.catfeina.ui.screens.SobreScreen

const val ARG_POESIA_ID = "poesiaId"

object NavDestinations {
    const val INICIO = "inicio"
    const val POESIAS_LISTA = "poesias_lista"
    const val POESIA_DETALHE_ROTA_BASE = "poesia_detalhe"
    const val POESIA_DETALHE = "$POESIA_DETALHE_ROTA_BASE/{$ARG_POESIA_ID}"

    // const val CATEGORIAS = "poesias" // Removido/Comentado
    // const val FORMA_PAGAMENTO = "forma_pagamento" // Removido
    // const val TAREFAS = "tarefas" // Removido
    const val SOBRE = "sobre"
    const val POLITICA_PRIVACIDADE = "politica_privacidade"
}

data class MenuItemData(
    val route: String,
    @StringRes val titleResId: Int,
    val icon: ImageVector
)

object AppMenuItems {
    val bottomNavigationItems = listOfNotNull(
        MenuItemData(
            route = NavDestinations.INICIO,
            titleResId = R.string.titulo_inicio,
            icon = Icones.Inicio
        ),
        MenuItemData(
            route = NavDestinations.POESIAS_LISTA,
            titleResId = R.string.titulo_poesias,
            icon = Icones.Poesia
        )
    )

    val drawerNavigationItems = listOfNotNull(
        MenuItemData(
            route = NavDestinations.INICIO,
            titleResId = R.string.titulo_inicio,
            icon = Icones.Inicio
        ),
        MenuItemData(
            route = NavDestinations.POESIAS_LISTA,
            titleResId = R.string.titulo_poesias,
            icon = Icones.Poesia
        ),
        MenuItemData(
            route = NavDestinations.SOBRE,
            titleResId = R.string.titulo_sobre,
            icon = Icones.Sobre
        )
    )
}


// --- Gráfico de Navegação ---
@Composable
fun CatfeinaNavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = NavDestinations.INICIO
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(NavDestinations.INICIO) { PlaceholderScreen("Início") }

        composable(NavDestinations.POESIAS_LISTA) {
            PoesiaScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPoesiaDetail = { poesiaId ->
                    navController.navigate("${NavDestinations.POESIA_DETALHE_ROTA_BASE}/$poesiaId")
                }
            )
        }

        composable(
            route = NavDestinations.POESIA_DETALHE,
            arguments = listOf(navArgument(ARG_POESIA_ID) { type = NavType.LongType })
        ) {
            PoesiaDetailScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }

        composable(NavDestinations.SOBRE) {
            SobreScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToPrivacyPolicy = { navController.navigate(NavDestinations.POLITICA_PRIVACIDADE) }
            )
        }

        composable(NavDestinations.POLITICA_PRIVACIDADE) {
            PrivacyPolicyScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

// --- Tela de Exemplo ---
@Composable
fun PlaceholderScreen(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Bem-vindo à tela de $text")
    }
}

