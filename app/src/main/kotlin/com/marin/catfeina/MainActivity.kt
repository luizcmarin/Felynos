/*
 * Arquivo: com.marin.catfeina.MainActivity.kt
 * @project Catfeina
 * @description
 * Ponto de entrada principal da aplicação Catfeina.
 * Define a Activity principal e a estrutura da UI raiz com Scaffold, NavigationDrawer e TopAppBar.
 */
package com.marin.catfeina

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.marin.catfeina.dominio.Icones
import com.marin.catfeina.navigation.AppDestinations
import com.marin.catfeina.navigation.InformativosScreen
import com.marin.catfeina.navigation.InicioScreen
import com.marin.catfeina.navigation.PersonagemScreen
import com.marin.catfeina.navigation.PoesiasScreen
import com.marin.catfeina.ui.diversos.PreferenciasScreen
import com.marin.catfeina.ui.diversos.PreferenciasViewModel
import com.marin.catfeina.ui.theme.CatfeinaAppTheme
import com.marin.catfeina.ui.theme.ThemeChoice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CatfeinaApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatfeinaApp(
    navController: NavHostController = rememberNavController(),
    preferenciasViewModel: PreferenciasViewModel = hiltViewModel()
) {
    val currentThemeChoice by preferenciasViewModel.currentTheme.collectAsState()

    CatfeinaAppTheme(selectedTheme = currentThemeChoice) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        // Obtém a entrada atual da backstack para saber a rota ativa
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: AppDestinations.INICIO_ROUTE

        // Mapeia rotas para títulos amigáveis
        val currentScreenTitle = when (currentRoute) {
            AppDestinations.POESIAS_ROUTE -> "Poesias"
            AppDestinations.PERSONAGEM_ROUTE -> "Personagem"
            AppDestinations.INFORMATIVOS_ROUTE -> "Informativos"
            AppDestinations.PREFERENCIAS_ROUTE -> "Preferências"
            else -> "Catfeina" // Título padrão ou para a tela inicial
        }

        // Determina se o ícone de navegação deve ser o menu (para abrir drawer) ou voltar
        // Por enquanto, sempre será o menu, mas isso pode ser útil depois.
        val isTopLevelDestination = currentRoute == AppDestinations.INICIO_ROUTE ||
                currentRoute == AppDestinations.POESIAS_ROUTE ||
                currentRoute == AppDestinations.PERSONAGEM_ROUTE ||
                currentRoute == AppDestinations.INFORMATIVOS_ROUTE ||
                currentRoute == AppDestinations.PREFERENCIAS_ROUTE
        // (Você pode querer ajustar essa lógica se tiver telas de detalhes)

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen || isTopLevelDestination, // Habilita gestos apenas se o drawer estiver aberto ou em telas de nível superior
            drawerContent = {
                ModalDrawerSheet {
                    DrawerHeader()
                    Spacer(Modifier.height(12.dp))
                    NavigationDrawerItem(
                        label = { Text("Início") },
                        selected = currentRoute == AppDestinations.INICIO_ROUTE,
                        onClick = {
                            navController.navigate(AppDestinations.INICIO_ROUTE) {
                                // Pop até o início do grafo para evitar empilhar telas
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() }
                        }
                    )
                    NavigationDrawerItem(
                        icon = { Icon(Icones.Poesia, contentDescription = null) },
                        label = { Text("Poesias") },
                        selected = currentRoute == AppDestinations.POESIAS_ROUTE,
                        onClick = {
                            navController.navigate(AppDestinations.POESIAS_ROUTE) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Personagem") },
                        selected = currentRoute == AppDestinations.PERSONAGEM_ROUTE,
                        onClick = {
                            navController.navigate(AppDestinations.PERSONAGEM_ROUTE) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Informativos") },
                        selected = currentRoute == AppDestinations.INFORMATIVOS_ROUTE,
                        onClick = {
                            navController.navigate(AppDestinations.INFORMATIVOS_ROUTE) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() }
                        }
                    )
                    NavigationDrawerItem(
                        label = { Text("Preferências") },
                        selected = currentRoute == AppDestinations.PREFERENCIAS_ROUTE,
                        icon = { Icon(Icones.Preferencias, contentDescription = "Preferências") },
                        onClick = {
                            navController.navigate(AppDestinations.PREFERENCIAS_ROUTE) {
                                popUpTo(navController.graph.startDestinationId)
                                launchSingleTop = true
                            }
                            scope.launch { drawerState.close() }
                        }
                    )
                }
            }
        ) {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    TopAppBar(
                        title = { Text(currentScreenTitle) },
                        navigationIcon = {
                            // Se não for uma tela de nível superior e houver algo na backstack, mostrar "voltar"
                            // Caso contrário, mostrar o ícone do menu.
                            if (!isTopLevelDestination && navController.previousBackStackEntry != null) {
                                IconButton(onClick = { navController.navigateUp() }) {
                                    Icon(
                                        imageVector = Icones.Voltar,
                                        contentDescription = "Voltar"
                                    )
                                }
                            } else if (isTopLevelDestination) {
                                IconButton(onClick = {
                                    scope.launch {
                                        if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icones.Menu,
                                        contentDescription = "Abrir menu de navegação"
                                    )
                                }
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            ) { innerPadding ->
                AppNavHost(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Você pode adicionar uma imagem/logo aqui se desejar
        // Icon(Icones.LogoApp, contentDescription = "Logo Catfeina", modifier = Modifier.size(64.dp))
        // Spacer(Modifier.height(8.dp))
        Text(
            text = "Catfeina",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Seu app de poesias",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.INICIO_ROUTE, // Define a tela inicial
        modifier = modifier
    ) {
        composable(AppDestinations.INICIO_ROUTE) {
            InicioScreen()
        }
        composable(AppDestinations.POESIAS_ROUTE) {
            PoesiasScreen()
        }
        composable(AppDestinations.PERSONAGEM_ROUTE) {
            PersonagemScreen()
        }
        composable(AppDestinations.INFORMATIVOS_ROUTE) {
            InformativosScreen()
        }
        composable(AppDestinations.PREFERENCIAS_ROUTE) {
            PreferenciasScreen()
        }
        // Adicione outras telas aqui com composable("sua_rota") { SuaTelaComposable() }
    }
}


@Preview(showBackground = true)
@Composable
fun CatfeinaAppPreview() {
    CatfeinaAppTheme(selectedTheme = ThemeChoice.PADRAO) {
        CatfeinaApp()
    }
}

@Preview(showBackground = true, name = "Drawer Preview")
@Composable
fun DrawerPreview() {
    CatfeinaAppTheme(selectedTheme = ThemeChoice.PADRAO) {
        ModalDrawerSheet {
            DrawerHeader()
            Spacer(Modifier.height(12.dp))
            NavigationDrawerItem(
                icon = { Icon(Icones.Inicio, contentDescription = null) },
                label = { Text("Início") },
                selected = true,
                onClick = {},
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
            )
            // ... outros itens para um preview completo do drawer
        }
    }
}