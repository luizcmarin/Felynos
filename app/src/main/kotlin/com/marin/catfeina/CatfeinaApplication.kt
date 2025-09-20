// =============================================================================
// Arquivo: com.marin.catfeina.CatfeinaApplication.kt
// Descrição: Classe Application customizada para o Catfeina.
//            Ponto de entrada principal para a inicialização de componentes
//            em nível de aplicativo, como Hilt e, futuramente, a lógica
//            de população inicial do banco de dados.
// =============================================================================
package com.marin.catfeina

import android.app.Application
import com.marin.catfeina.data.db.AppDatabase // <<======== IMPORTAR AppDatabase
//import com.marin.catfeina.data.PreferenciasRepository
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope // <<======== IMPORTAR CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch // <<======== IMPORTAR launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class CatfeinaApplication : Application() {

    //    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO) // Você pode usar este se preferir

    // ----- INÍCIO DAS MODIFICAÇÕES -----
    @Inject // Hilt irá injetar a instância do AppDatabase aqui
    lateinit var appDatabase: AppDatabase
    // ----- FIM DAS MODIFICAÇÕES -----

    @Inject
//    lateinit var preferenciasRepository: PreferenciasRepository

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // Para builds de release, você pode querer plantar uma árvore
            // que envie logs para um serviço de crash reporting, por exemplo.
            // Ex: Timber.plant(CrashReportingTree())
            // Ou não plantar nada para não logar em release.
        }

        // ----- INÍCIO DAS MODIFICAÇÕES -----
        // Força a inicialização do banco de dados em uma coroutine de background
        // Use um CoroutineScope existente ou crie um novo.
        // Usar um SupervisorJob é bom para o escopo da aplicação, pois a falha
        // de uma coroutine não cancela as outras.
        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

        applicationScope.launch {
            // A simples chamada para obter a instância aqui (se ainda não foi criada)
            // acionará o Hilt a chamar o provideAppDatabase e, portanto,
            // a cópia do asset. Como estamos em Dispatchers.IO, isso
            // acontecerá em background.
            // Acessar openHelper.writableDatabase é uma forma comum de garantir
            // que o banco seja aberto/criado.
            Timber.d("Iniciando a inicialização do banco de dados em background...")
            try {
                appDatabase.openHelper.writableDatabase
                Timber.d("Banco de dados inicializado com sucesso em background.")
            } catch (e: Exception) {
                Timber.e(e, "Erro ao inicializar o banco de dados em background.")
            }
        }
        // ----- FIM DAS MODIFICAÇÕES -----
    }

}

