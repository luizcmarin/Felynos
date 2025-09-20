// =============================================================================
// Arquivo: com.marin.catfeina.ui.texto.ExibirTextoFragment.kt
// Descrição: Fragmento para exibir o conteúdo de um Texto específico (HTML)
//            em um WebView, com funcionalidades adicionais na Toolbar.
// =============================================================================

package com.marin.catfeina.ui.texto

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.*
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.marin.catfeina.R
import com.marin.catfeina.databinding.FragmentExibirTextoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Locale
import androidx.core.content.edit

@AndroidEntryPoint
class ExibirTextoFragment : Fragment(), TextToSpeech.OnInitListener {

    private var _binding: FragmentExibirTextoBinding? = null
    private val binding get() = _binding!! // This property is only valid between onCreateView and onDestroyView.

    private val viewModel: ExibirTextoViewModel by viewModels()

    private var tts: TextToSpeech? = null
    private var isTtsInitialized = false
    private var currentTtsText: String? = null

    // Preferências de tamanho de fonte (exemplo, você precisará salvar/carregar isso)
    private var currentFontSizePercent = 100 // Default 100%

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tts = TextToSpeech(requireContext(), this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentExibirTextoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        observeUiState()
        loadFontSizePreference() // Carregar preferência de tamanho
        applyFontSizeToWebView()
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.exibir_texto_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_copiar_texto -> {
                        copiarConteudoParaClipboard()
                        true
                    }
                    R.id.action_compartilhar_texto -> {
                        compartilharConteudo()
                        true
                    }
                    R.id.action_aumentar_fonte -> {
                        ajustarTamanhoFonte(10) // Aumenta em 10%
                        true
                    }
                    R.id.action_diminuir_fonte -> {
                        ajustarTamanhoFonte(-10) // Diminui em 10%
                        true
                    }
                    R.id.action_ouvir_texto -> {
                        falarTexto()
                        true
                    }
                    R.id.action_trocar_tema -> {
                        trocarTemaAplicativo()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is ExibirTextoUiState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.webViewTexto.visibility = View.GONE
                            binding.textViewError.visibility = View.GONE
                            binding.buttonTentarNovamente.visibility = View.GONE
                        }
                        is ExibirTextoUiState.Success -> {
                            binding.progressBar.visibility = View.GONE
                            binding.webViewTexto.visibility = View.VISIBLE
                            binding.textViewError.visibility = View.GONE
                            binding.buttonTentarNovamente.visibility = View.GONE

                            // Carrega o HTML no WebView
                            // Para garantir a melhor renderização e evitar problemas de codificação:
                            binding.webViewTexto.loadDataWithBaseURL(
                                null, // baseUrl, pode ser null se não houver recursos locais referenciados no HTML
                                state.texto.conteudoHtml,
                                "text/html",
                                "UTF-8",
                                null // historyUrl
                            )
                            currentTtsText = state.texto.conteudoTts

                            // Atualiza o título da Activity/Toolbar (opcional)
                            activity?.title = state.texto.chave // Ou um título mais amigável
                        }
                        is ExibirTextoUiState.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.webViewTexto.visibility = View.GONE
                            binding.textViewError.visibility = View.VISIBLE
                            binding.buttonTentarNovamente.visibility = View.VISIBLE

                            binding.textViewError.text = state.mensagem ?: getString(R.string.erro_carregar_texto_padrao)
                            binding.buttonTentarNovamente.setOnClickListener {
                                viewModel.tentarNovamenteCarregarTexto()
                            }
                        }
                    }
                }
            }
        }
    }

    // --- Implementação das Ações da Toolbar ---

    private fun copiarConteudoParaClipboard() {
        val uiState = viewModel.uiState.value
        if (uiState is ExibirTextoUiState.Success) {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            // Idealmente, copiar o texto puro, não o HTML bruto, se disponível.
            // Se o conteudoTts for uma versão limpa, use-o. Senão, precisaria de uma forma de extrair texto do HTML.
            // Para simplificar, vamos usar o conteudoTts, assumindo que é texto puro.
            val clip = ClipData.newPlainText("texto_copiado", uiState.texto.conteudoTts)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(requireContext(), getString(R.string.texto_copiado_sucesso), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), getString(R.string.nada_para_copiar), Toast.LENGTH_SHORT).show()
        }
    }

    private fun compartilharConteudo() {
        val uiState = viewModel.uiState.value
        if (uiState is ExibirTextoUiState.Success) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                // Novamente, idealmente texto puro.
                putExtra(Intent.EXTRA_TEXT, uiState.texto.conteudoTts)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        } else {
            Toast.makeText(requireContext(), getString(R.string.nada_para_compartilhar), Toast.LENGTH_SHORT).show()
        }
    }

    private fun ajustarTamanhoFonte(percentChange: Int) {
        currentFontSizePercent += percentChange
        if (currentFontSizePercent < 50) currentFontSizePercent = 50 // Mínimo
        if (currentFontSizePercent > 200) currentFontSizePercent = 200 // Máximo
        applyFontSizeToWebView()
        saveFontSizePreference() // Salvar a preferência
    }

    private fun applyFontSizeToWebView() {
        binding.webViewTexto.settings.textZoom = currentFontSizePercent
    }

    private fun saveFontSizePreference() {
        // Implementar salvamento em SharedPreferences
        // Ex:
        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        prefs.edit { putInt("webview_font_size", currentFontSizePercent) }
        Timber.d("Tamanho da fonte salvo: $currentFontSizePercent%")
    }

    private fun loadFontSizePreference() {
        // Implementar carregamento de SharedPreferences
        // Ex:
        val prefs = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        currentFontSizePercent = prefs.getInt("webview_font_size", 100)
        Timber.d("Tamanho da fonte carregado: $currentFontSizePercent%")
    }


    private fun falarTexto() {
        if (isTtsInitialized && !currentTtsText.isNullOrEmpty()) {
            if (tts?.isSpeaking == true) {
                tts?.stop() // Para a fala atual se estiver falando
            } else {
                tts?.speak(currentTtsText, TextToSpeech.QUEUE_FLUSH, null, "TextoParaFalar")
            }
        } else if (currentTtsText.isNullOrEmpty() && viewModel.uiState.value is ExibirTextoUiState.Success) {
            Toast.makeText(requireContext(), getString(R.string.sem_texto_para_tts), Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(requireContext(), getString(R.string.tts_nao_disponivel), Toast.LENGTH_SHORT).show()
            // Tenta inicializar novamente se não estiver pronto
            if (tts == null) {
                tts = TextToSpeech(requireContext(), this)
            }
        }
    }

    private fun trocarTemaAplicativo() {
        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        if (currentNightMode == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        // A Activity será recriada, então o tema será aplicado.
    }


    // --- TextToSpeech.OnInitListener ---
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val ptBrLocale = Locale.Builder()
                .setLanguage("pt")
                .setRegion("BR")
                .build()
            val result = tts?.setLanguage(ptBrLocale)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Timber.e("TTS: Linguagem Português (BR) não suportada ou dados faltando.")
                // Tenta um fallback para o Locale padrão do dispositivo
                val defaultLocale = Locale.getDefault()
                val fallbackResult = tts?.setLanguage(defaultLocale)
                if (fallbackResult == TextToSpeech.LANG_MISSING_DATA || fallbackResult == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Timber.e("TTS: Linguagem Padrão do Dispositivo (${defaultLocale.displayName}) também não suportada.")
                    Toast.makeText(requireContext(), getString(R.string.tts_linguagem_nao_suportada), Toast.LENGTH_LONG).show()
                } else {
                    isTtsInitialized = true
                    Timber.i("TTS: Inicializado com sucesso usando o Locale padrão do dispositivo.")
                }
            } else {
                isTtsInitialized = true
                Timber.i("TTS: Inicializado com sucesso em Português (BR).")
            }
        } else {
            Timber.e("TTS: Falha na inicialização. Status: $status")
            Toast.makeText(requireContext(), getString(R.string.tts_falha_inicializacao), Toast.LENGTH_LONG).show()
            isTtsInitialized = false
        }
    }

    override fun onDestroyView() {
        // Limpar o ViewBinding
        _binding = null
        // Parar e liberar o TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
            tts = null // Importante para evitar memory leaks e crashes
        }
        isTtsInitialized = false
        super.onDestroyView()
    }
}
