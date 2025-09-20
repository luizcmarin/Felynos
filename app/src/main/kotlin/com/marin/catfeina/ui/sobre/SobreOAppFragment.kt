package com.marin.catfeina.ui.sobre

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.marin.catfeina.R
import com.marin.catfeina.databinding.FragmentSobreBinding

class SobreOAppFragment : Fragment() {

    private var _binding: FragmentSobreBinding? = null

    // Esta propriedade é válida apenas entre onCreateView e onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSobreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // setupToolbar() // Remova a chamada para a função antiga ou modifique-a
        configureActivityToolbar() // Nova função ou lógica inline
        setupVersaoApp()
        setupLinkPoliticaPrivacidade()

        // A animação Lottie é configurada para autoPlay e loop no XML.
    }

    // Função modificada ou lógica inline para configurar a Toolbar da Activity
    private fun configureActivityToolbar() {
        // O Navigation Component (com setupActionBarWithNavController na Activity)
        // geralmente cuida do título e do botão "Up" (voltar).
        // Se você precisar definir o título especificamente a partir do Fragment:
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.titulo_sobre)

        // O displayHomeAsUpEnabled (botão de voltar) também é geralmente tratado
        // pelo setupActionBarWithNavController na Activity, mostrando-o
        // para destinos que não são de nível superior.
        // (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    // A FUNÇÃO setupToolbar() ANTIGA DEVE SER REMOVIDA COMPLETAMENTE
    // JÁ QUE ELA FAZIA REFERÊNCIA A binding.toolbarSobre

    /*
    // FUNÇÃO ANTIGA - REMOVER
    private fun setupToolbar() {
        // Se a Activity hospedeira gerencia a Toolbar (recomendado com Navigation Component)
         (activity as? AppCompatActivity)?.setSupportActionBar(binding.toolbarSobre) // ERRO AQUI
         (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
         (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.titulo_sobre)

        // Se o Fragment gerencia sua própria Toolbar (menos comum com Nav Component, mas possível)
        binding.toolbarSobre.setNavigationOnClickListener { // ERRO AQUI
            // Ação de voltar, usando o Navigation Controller
            findNavController().navigateUp()
        }
        // O título já está definido no XML (app:title), mas pode ser definido programaticamente também:
        // binding.toolbarSobre.title = getString(R.string.titulo_sobre) // ERRO AQUI
    }
    */


    private fun setupVersaoApp() {
        try {
            val context = requireContext()
            val versionName =
                context.packageManager.getPackageInfo(context.packageName, 0).versionName
            binding.tvVersaoApp.text = getString(R.string.app_versao, versionName)
        } catch (e: PackageManager.NameNotFoundException) {
            binding.tvVersaoApp.text = getString(R.string.app_versao, "N/A")
            // Log.e("SobreOAppFragment", "Não foi possível obter a versão do app", e)
        }
    }

    private fun setupLinkPoliticaPrivacidade() {
        val politicaText = getString(R.string.titulo_politica_privacidade)
        val spannableString = SpannableString(politicaText)
        spannableString.setSpan(UnderlineSpan(), 0, politicaText.length, 0)
        binding.tvPoliticaPrivacidade.text = spannableString

        binding.tvPoliticaPrivacidade.setOnClickListener {
            navigateToPrivacyPolicy()
        }
    }

    private fun navigateToPrivacyPolicy() {
        val urlPolitica = "https://www.example.com/privacy" // MUDE ESTA URL
        try {
            val intent = Intent(Intent.ACTION_VIEW, urlPolitica.toUri())
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(
                context,
                getString(R.string.erro_abrir_link_generico),
                Toast.LENGTH_SHORT
            ).show()
            // Log.e("SobreOAppFragment", "Erro ao abrir link da política de privacidade", e)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}