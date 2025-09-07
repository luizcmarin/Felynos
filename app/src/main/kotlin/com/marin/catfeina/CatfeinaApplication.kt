// =============================================================================
// Arquivo: com.marin.catfeina.CatfeinaApplication.kt
// Descrição: Classe Application customizada para o Catfeina.
//            Ponto de entrada principal para a inicialização de componentes
//            em nível de aplicativo, como Hilt e, futuramente, a lógica
//            de população inicial do banco de dados.
// =============================================================================
package com.marin.catfeina

import android.app.Application
import android.util.Log
import com.marin.catfeina.data.PreferenciasRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class CatfeinaApplication : Application() {

    /**
     * CoroutineScope personalizado para operações em nível de Application.
     * Usa [SupervisorJob] para que falhas em um filho não cancelem outros ou o próprio escopo.
     * Usa [Dispatchers.IO] como padrão para operações de longa duração ou I/O.
     */
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    /**
     * Repositório de preferências, injetado para verificar se os dados iniciais
     * já foram carregados.
     */
    @Inject
    lateinit var preferenciasRepository: PreferenciasRepository

    /**
     * Chamado quando o aplicativo está iniciando, antes que qualquer activity, service,
     * ou receiver objects (excluindo content providers) tenham sido criados.
     *
     * Implementações devem ser tão rápidas quanto possível para minimizar o impacto
     * no tempo de inicialização do aplicativo.
     */
    override fun onCreate() {
        super.onCreate()
        Log.d("CatfeinaApplication", "onCreate: Iniciando Catfeína.")
    }

}
