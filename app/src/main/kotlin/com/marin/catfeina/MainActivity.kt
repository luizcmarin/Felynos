package com.marin.catfeina

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat // Para fechar o drawer
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI // Import para onNavDestinationSelectedimport androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.marin.catfeina.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences // Para salvar a preferência do tema
    private lateinit var navController: NavController // Adicionado para fácil acesso

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences(packageName + "_preferences", MODE_PRIVATE)
        applyThemePreference()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView


        // ---- INÍCIO DA MODIFICAÇÃO SUGERIDA ----
        // Obtenha o NavHostFragment primeiro
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment

        // Então obtenha o NavController a partir do NavHostFragment
        navController = navHostFragment.navController
        // ---- FIM DA MODIFICAÇÃO SUGERIDA ----


//        navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_sobre_app // Se SobreOAppFragment for um destino de nível superior
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { menuItem ->
            val handledCustom = handleCustomNavigation(menuItem, drawerLayout)
            if (handledCustom) {
                true
            } else {
                val handledByUI = NavigationUI.onNavDestinationSelected(menuItem, navController)
                if (handledByUI) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                handledByUI
            }
        }
    }

    private fun handleCustomNavigation(menuItem: MenuItem, drawerLayout: DrawerLayout): Boolean {
        val args = Bundle()
        // O ID do ExibirTextoFragment no grafo de navegação
        val destinationId = R.id.exibirTextoFragment

        when (menuItem.itemId) {
            // IDs do seu menu XML do NavigationView (res/menu/your_drawer_menu.xml)
            R.id.nav_politica_privacidade -> { // Substitua pelo seu ID real
                args.putString("chave_texto", "politica_de_privacidade")
                navController.navigate(destinationId, args)
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.nav_termos_uso -> { // Substitua pelo seu ID real
                args.putString("chave_texto", "termos_de_uso")
                navController.navigate(destinationId, args)
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }
            R.id.nav_sobre_app -> { // EXEMPLO: Use um ID diferente se "Sobre o App" no menu leva a ExibirTextoFragment
                args.putString("chave_texto", "sobre_o_aplicativo")
                navController.navigate(destinationId, args)
                drawerLayout.closeDrawer(GravityCompat.START)
                return true
            }


            else -> return false
        }
    }

    private fun applyThemePreference() {
        val themePreferenceKey = getString(R.string.tema_preferido)
        val themeValue = sharedPreferences.getString(themePreferenceKey, "system")

        when (themeValue) {
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // ========== INÍCIO DA MODIFICAÇÃO SUGERIDA ==========
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Deixe o NavigationUI tentar lidar com o item SE ele for um destino de navegação
        // que você definiu na action bar (ex: itens do menu overflow) E NÃO for o botão "Up" padrão.
        // O botão "Up" (android.R.id.home) é tratado por onSupportNavigateUp().
        if (item.itemId != android.R.id.home && NavigationUI.onNavDestinationSelected(item, navController)) {
            return true
        }

        // Se não foi tratado pelo NavigationUI (ou se foi o botão "Up" e onSupportNavigateUp não tratou),
        // prossiga com a lógica de temas ou chame super.
        val themePreferenceKey = getString(R.string.tema_preferido)
        val editor = sharedPreferences.edit()

        return when (item.itemId) {
            R.id.tema_claro -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor.putString(themePreferenceKey, "light").apply()
                true
            }
            R.id.tema_escuro -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor.putString(themePreferenceKey, "dark").apply()
                true
            }
            R.id.tema_preferido -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                editor.putString(themePreferenceKey, "system").apply()
                true
            }
            // Se for android.R.id.home, onSupportNavigateUp já deve ter sido chamado e tratado.
            // Se por algum motivo chegou aqui (improvável se onSupportNavigateUp estiver correto),
            // super.onOptionsItemSelected(item) é a fallback correta.
            else -> super.onOptionsItemSelected(item)
        }
    }
    // ========== FIM DA MODIFICAÇÃO SUGERIDA ==========

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
