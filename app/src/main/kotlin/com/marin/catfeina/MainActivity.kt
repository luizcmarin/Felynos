/*
 * Arquivo: com.marin.catfeina.MainActivity.kt
 * @project Catfeina
 * @description
 * Ponto de entrada principal da aplicação Catfeina.
 * Define a Activity principal e a estrutura da UI raiz com Scaffold, NavigationDrawer e TopAppBar.
 */
package com.marin.catfeina

// import com.marin.catfeina.ui.theme.ThemeState // REMOVIDO - Não é mais usado aqui
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.marin.catfeina.dominio.Icones
import com.marin.catfeina.ui.diversos.PreferenciasScreen
import com.marin.catfeina.ui.informativo.InformativoScreen
import com.marin.catfeina.ui.theme.BaseTheme
import com.marin.catfeina.ui.theme.CatfeinaAppTheme
import com.marin.catfeina.ui.theme.ThemeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val themeViewModel: ThemeViewModel = hiltViewModel()
            // Coleta os estados de tema do ThemeViewModel conforme a nova estrutura
            val baseTheme by themeViewModel.currentBaseTheme.collectAsState()
            val isDarkTheme by themeViewModel.isDarkMode.collectAsState() // MUDANÇA: usa isDarkMode
            // REMOVIDO: val themeState by themeViewModel.currentThemeState.collectAsState()
            // REMOVIDO: val useDynamicColor by themeViewModel.dynamicColorPreference.collectAsState()

            CatfeinaAppTheme(
                selectedBaseTheme = baseTheme,
                useDarkTheme = isDarkTheme // MUDANÇA: passa isDarkTheme
                // REMOVIDO: selectedThemeState = themeState,
                // REMOVIDO: useDynamicColor = useDynamicColor
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Passa o themeViewModel para CatfeinaApp, pois ele pode ser usado
                    // pelo menu de três pontinhos ou pela navegação para PreferenciasScreen
                    CatfeinaApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatfeinaApp(
    navController: NavHostController = rememberNavController(),
) {
    // Não é mais necessário coletar baseTheme e themeState aqui,
    // pois CatfeinaAppTheme já está sendo configurado no nível da Activity.
    // O themeViewModel é passado para o caso de o menu ou outras partes da UI precisarem
    // interagir com as funções de mudança de tema (ex: toggleDarkMode, setBaseTheme).

    // REMOVIDO:
    // val baseTheme by themeViewModel.currentBaseTheme.collectAsState()
    // val themeState by themeViewModel.currentThemeState.collectAsState()
    // CatfeinaAppTheme(
    //     selectedBaseTheme = baseTheme,
    //     selectedThemeState = themeState
    // ) { ... }
    // O bloco CatfeinaAppTheme agora envolve CatfeinaApp na Activity.

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: AppDestinations.INICIO_ROUTE

    val rotaPoliticaDePrivacidade =
        AppDestinations.informativoDetalheComChave(AppDestinations.CHAVE_POLITICA_DE_PRIVACIDADE)

    val currentScreenTitle = when (currentRoute) {
        AppDestinations.INICIO_ROUTE -> "Catfeina"
        AppDestinations.POESIAS_ROUTE -> "Poesias"
        AppDestinations.PERSONAGEM_ROUTE -> "Personagem"
        rotaPoliticaDePrivacidade -> "Política de Privacidade"
        AppDestinations.PREFERENCIAS_ROUTE -> "Preferências"
        else -> "Catfeina"
    }

    val isTopLevelDestination = currentRoute == AppDestinations.INICIO_ROUTE ||
            currentRoute == AppDestinations.POESIAS_ROUTE ||
            currentRoute == AppDestinations.PERSONAGEM_ROUTE ||
            currentRoute == rotaPoliticaDePrivacidade ||
            currentRoute == AppDestinations.PREFERENCIAS_ROUTE

    val showUpButton = !isTopLevelDestination

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen || (isTopLevelDestination && currentRoute == AppDestinations.INICIO_ROUTE),
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                Spacer(Modifier.height(12.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icones.Inicio, contentDescription = "Início") },
                    label = { Text("Início") },
                    selected = currentRoute == AppDestinations.INICIO_ROUTE,
                    onClick = {
                        navController.navigate(AppDestinations.INICIO_ROUTE) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    icon = { Icon(Icones.Poesia, contentDescription = "Poesias") },
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
                    // Se Personagem tiver um ícone, adicione-o aqui
                    // icon = { Icon(Icones.Personagem, contentDescription = "Personagem") },
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
                    // Se Política de Privacidade tiver um ícone, adicione-o aqui
                    // icon = { Icon(Icones.Politica, contentDescription = "Política de Privacidade") },
                    label = { Text("Política de Privacidade") },
                    selected = currentRoute == rotaPoliticaDePrivacidade,
                    onClick = {
                        navController.navigate(rotaPoliticaDePrivacidade) { // Usa a variável rotaPoliticaDePrivacidade
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
                        if (showUpButton) {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icones.Voltar,
                                    contentDescription = "Voltar"
                                )
                            }
                        } else {
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
                    // Você pode adicionar um actions aqui para o menu de três pontinhos
                    // que usa o themeViewModel para alternar o tema.
                    // actions = {
                    //     MenuTresPontinhos(themeViewModel = themeViewModel) // Exemplo
                    // }
                )
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(innerPadding)
                // O themeViewModel não precisa ser passado para AppNavHost
                // a menos que alguma tela filha precise diretamente dele e não possa obtê-lo
                // via hiltViewModel() ou composição (ex: PreferenciasScreen).
            )
        }
    }
}

@Composable
fun DrawerHeader() { /* ... permanece o mesmo ... */ }

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppDestinations.INICIO_ROUTE,
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
        composable(
            route = AppDestinations.INFORMATIVO_DETALHE_ROUTE_TEMPLATE,
            arguments = listOf(navArgument(AppDestinations.INFORMATIVO_ARG_CHAVE) {
                type = NavType.StringType
            })
        ) {
            InformativoScreen(
                onNavegarParaTras = { navController.popBackStack() }
            )
        }
        composable(route = AppDestinations.PREFERENCIAS_ROUTE) {
            // A PreferenciasScreen agora usará o ThemeViewModel diretamente
            // para mostrar e alterar as preferências de tema.
            PreferenciasScreen(
                // onNavegarParaTras = { navController.popBackStack() } // Se necessário
                // O ThemeViewModel será obtido dentro de PreferenciasScreen via hiltViewModel()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CatfeinaAppPreview() {
    CatfeinaAppTheme(
        selectedBaseTheme = BaseTheme.PRIMAVERA,
        useDarkTheme = false // MUDANÇA: usaDarkTheme
        // REMOVIDO: selectedThemeState = ThemeState.CLARO
    ) {
        Text("Preview do CatfeinaApp com tema Primavera Claro")
    }
}

@Preview(showBackground = true, name = "Drawer Preview")
@Composable
fun DrawerPreview() {
    CatfeinaAppTheme(
        selectedBaseTheme = BaseTheme.PRIMAVERA,
        useDarkTheme = false // MUDANÇA: usaDarkTheme
        // REMOVIDO: selectedThemeState = ThemeState.CLARO
    ) {
        ModalDrawerSheet {
            // ... conteúdo do drawer para preview ...
        }
    }
}

