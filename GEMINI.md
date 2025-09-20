# Roteiro de Desenvolvimento do Projeto Catfeina (XML)

## Fase 1: Fundação e Estrutura Base

1. **Configuração Inicial do Projeto:**
    *   [X] Criar projeto Android Studio com Kotlin.
    *   [ ] Configurar `build.gradle` com dependências necessárias:
        *   [X] Material Design 3.
        *   [X] Jetpack Navigation Component (Fragments com Safe Args).
        *   [X] Room Persistence Library (Runtime, Compiler, Kotlin Extensions - KTX).
        *   [X] Hilt para Injeção de Dependência.*   [X] Coroutines (Core, Android).
        *   [ ] Retrofit 2 e um conversor (Moshi/Gson/kotlinx.serialization) - *Para Atualização
            Dinâmica*.
        *   [ ] OkHttp 3 (Logging Interceptor) - *Para Atualização Dinâmica*.
        *   [ ] WorkManager KTX - *Para Atualização Dinâmica*.
        *   [X] Lottie.
        *   [ ] MediaPlayer (para sons ambientes).
        *   [ ] DataStore (para `ProgressTracker` e preferências do usuário).
        *   [X] Outras bibliotecas utilitárias (ex: Timber para logging).
    *   [X] Definir `minSdkVersion`, `targetSdkVersion`, `compileSdkVersion`.
    *   [X] Configurar `themes.xml` (Light/Dark) com Material 3 e atributos de cor.
    *   [X] Criar arquivo `GEMINI.MD`.

2. **Layouts e Navegação Principal:**
    *   [X] Criar layout da `MainActivity` (ex: com `DrawerLayout`, `NavigationView`,
        `CoordinatorLayout`, `AppBarLayout`, `Toolbar`).
    *   [X] Criar `FragmentContainerView` para hospedar os fragmentos.
    *   [X] Definir o grafo de navegação (`mobile_navigation.xml`) com destinos iniciais.
    *   [X] Configurar `Toolbar` com `NavigationUI` (`setupActionBarWithNavController`).
    *   [X] Configurar `NavigationView` (menu do drawer) com `NavigationUI` (
        `setupWithNavController`).
    *   [X] Implementar a tela "Sobre" (`SobreFragment`) e navegação para ela.

3. **Onboarding (Experiência de Primeiro Uso):**
    *   [ ] Definir fluxo de telas de apresentação do app e do universo das poesias.
    *   [ ] Criar layouts XML e Fragments para as telas de onboarding.
    *   [ ] Usar `ViewPager2` ou Navigation Component para a sequência.
    *   [ ] Lógica para exibir apenas na primeira inicialização (usar `DataStore` ou
        `SharedPreferences`).

## Fase 2: Banco de Dados Local com Room (Parcialmente CONCLUÍDO)

4. **Definição do Schema do Banco de Dados:**
    *   [ ] Criar classe de Entidade `@Entity data class Personagem(...)`.
        * `nome` como `NOT NULL` e com `@Index(unique = true)`.
    *   [ ] Criar classe de Entidade `@Entity data class Poesia(...)`.
        * `titulo` como `NOT NULL` e com `@Index(unique = true)`.
        * Coluna `eh_favorito` (INTEGER, 0 ou 1).
    *   [ ] Criar classe de Entidade `@Entity data class Nota(...)`.
    *   [ ] Criar classe de Entidade `@Entity data class Texto(...)`.
        * `chave` como `NOT NULL` e com `@Index(unique = true)`.
    *   [ ] Definir chaves primárias, tipos de coluna, outros índices conforme necessário.

5. **DAOs (Data Access Objects):**
    *   [ ] Criar interface `@Dao interface PersonagemDao` com métodos para `personagens`.
    *   [ ] Criar interface `@Dao interface PoesiaDao` com métodos para `poesias` (incluindo queries
        complexas para filtros, favoritos, contagens para conquistas).
    *   [ ] Criar interface `@Dao interface NotaDao` com métodos para CRUD de `notas`.
    *   [ ] Criar interface `@Dao interface TextoDao` com métodos para `textos` (ex:
        `getTextoPorChave(chave: String)`).
    *   [ ] Usar `Flow<List<T>>` ou `LiveData<List<T>>` para queries observáveis.
    *   [ ] Implementar métodos `@Upsert` onde apropriado (especialmente para Atualização Dinâmica e
        `Textos`).

6. **Classe AppDatabase:**
    *   [ ] Criar classe abstrata `@Database abstract class AppDatabase : RoomDatabase()` listando
        as entidades (`Personagem`, `Poesia`, `Nota`, `Texto`) e a versão.
    *   [ ] Fornecer métodos abstratos para acessar os DAOs.
    *   [ ] Implementar Singleton pattern para acesso à instância do banco de dados (geralmente via
        Hilt Module).

7. **Injeção de Dependência com Hilt para Room:**
    *   [X] Criar um `@Module object DatabaseModule` para fornecer instâncias do `AppDatabase` e dos
        DAOs.
    *   [X] Anotar a classe `Application` com `@HiltAndroidApp`.

8. **Carga Inicial de Dados (JÁ IMPLANTADO - REVISAR PARA NOVA ESTRUTURA):**
    *   [X] Preparar arquivos de dados iniciais (ex: JSON, TOML, ou `.db` pré-populado) na pasta
        `assets`, refletindo a nova estrutura de tabelas (`personagens`, `textos`).
    *   [X] Implementar/Ajustar lógica para popular o banco de dados na primeira criação:
        * Opção A: Usando `.createFromAsset("database/catfeina.db")` no builder do Room (se o `.db`
          for atualizado).
        * Opção B: Usando `RoomDatabase.Callback()` e no `onCreate()`, parsear os arquivos de assets
          e usar os DAOs para inserir os dados (executar em uma coroutine de IO).

9. **Migrations de Schema do Room:**
    *   [ ] Estar preparado para criar classes `Migration` e adicioná-las ao builder do
        `AppDatabase` sempre que a `version` do banco for incrementada devido a mudanças no schema.
    *   [ ] Testar as migrations.

## Fase 3: Implementação das Funcionalidades Principais

10. **CRUD para `Notas`:** (Conforme roteiro anterior)
    *   [ ] ViewModel, UI (Fragments XML para Lista e Add/Edit), Navegação.

11. **Listagem e Detalhes para `Personagens`:**
    *   [ ] ViewModel, UI (Fragments XML para Lista e Detalhes), Navegação.

12. **Listagem e Detalhes para `Poesias`:**
    *   [ ] **ViewModel:** `PoesiasViewModel`.
        * Expor dados, filtros (categoria), lógica para favorito, leitura, obter poesia aleatória.
    *   [ ] **UI (Fragments XML):**
        * `ListaPoesiasFragment`:
            * Layout com `RecyclerView`.
            * Adapter.
            * Filtro por `categoria` com `Spinner` ou `ChipGroup` (usando `DropdownMenu` não é um
              componente XML padrão, `Spinner` é o mais próximo ou um `PopupMenu` customizado).
            * Botões/ícones para favoritar.
            * Botão "Surpreenda-me" (Item 17).
        * `DetalhesPoesiaFragment`:
            * Layout para detalhes, `WebView` para `conteudo_html`.
            * Botão “Ler em voz alta” (Item 13).
            * Interações: Marcar como favorito/lido.
            * Exibição de "Explicações Contextuais" (Item 15).
            * Opção para "Ambientes Sonoros" (Item 14).
    *   [ ] Navegação.

## Fase 4: Recursos de Leitura Imersiva e Interação

13. **Leitura com Áudio (Text-to-Speech - TTS):**
    *   [ ] Integrar `TextToSpeech` engine do Android.
    *   [ ] Ler `conteudo_tts` da `Poesia` ou da tabela `Textos` (se aplicável).
    *   [ ] UI para Controles de Áudio (Play, Pause, Stop) na tela de detalhes da poesia.
        * Considerar barra de progresso simples ou indicação de fala.
    *   [ ] Lógica para enfileiramento (`QUEUE_FLUSH`, `QUEUE_ADD`) se houver leitura sequencial de
        múltiplos blocos.
    *   [ ] (Opcional Avançado) Configuração TTS (ajuste de voz, perfis de leitura) em uma tela de
        Configurações.
    *   [ ] (Opcional Avançado) Exportação de leitura TTS como arquivo de áudio (
        `synthesizeToFile`).

14. **Ambientes Sonoros:**
    *   [ ] Integrar `MediaPlayer` para tocar sons ambientes (ex: chuva, piano, vento) a partir de
        arquivos em `res/raw`.
    *   [ ] Adicionar controles na tela de detalhes da poesia para selecionar e tocar/pausar sons
        ambientes.
    *   [ ] Controle de volume para sons ambientes, separado do volume do TTS/mídia.

15. **Explicações Contextuais:**
    *   [ ] Identificar termos/referências em poesias que necessitam de explicação.
    *   [ ] Armazenar explicações na tabela `Textos` com chaves apropriadas.
    *   [ ] Na tela de detalhes da poesia, ao clicar em um termo destacado (pode requerer
        `SpannableString` ou lógica em `WebView`), exibir a explicação em um `AlertDialog`,
        `BottomSheetDialogFragment` ou `PopupWindow`.

## Fase 5: Mascote Interativo "Cashito" e Engajamento

16. **Componente Visual do Mascote "Cashito":**
    *   [ ] Criar um `View` customizado (ex: `MascoteView.xml` e `MascoteView.kt`) ou um layout
        incluído (`<include layout="@layout/mascote_layout" />`) para o mascote.
    *   [ ] Usar `LottieAnimationView` para a animação do Cashito.
    *   [ ] `TextView` para exibir as frases contextuais do Cashito.
    *   [ ] Lógica para exibir/ocultar ou animar discretamente o mascote em diferentes telas (ex:
        usando `AnimatedContent` não é um componente XML direto, mas transições como `Fade` ou
        `Slide` podem ser usadas com `TransitionManager` ou animações de View).

17. **Interatividade e Frases do Cashito:**
    *   [ ] Definir "estados" do Cashito (Filhote, Jovem, Mestre, Lendário) e associar diferentes
        animações Lottie ou visuais.
    *   [ ] Armazenar frases do Cashito na tabela `Textos` categorizadas por contexto (horário,
        progresso, tela atual, sentimento selecionado - *sentimento ainda não definido no roteiro*).
    *   [ ] Lógica no `MascoteView` ou `ViewModel` associado para:
        * Selecionar frases apropriadas com base no contexto.
        * Atualizar a `LottieAnimationView` e `TextView`.
        * Reagir a toques no mascote (mudar frase, pequena animação).
    *   [ ] Cashito sugere poesia aleatória ("Surpreenda-me" integrado com o mascote).
        * Botão "Surpreenda-me" na UI (`ListaPoesiasFragment` ou global).
        * Ao clicar, `PoesiasViewModel` seleciona uma poesia aleatória.
        * Cashito "apresenta" a poesia com uma frase temática antes de navegar para os detalhes.

18. **Conquistas Poéticas (Gamificação Leve):**
    *   [ ] **Lógica (`ProgressTracker`):**
        * Criar classe `ProgressTracker` (pode ser um `ViewModel` ou um serviço injetado por Hilt).
        * Usar `DataStore` para armazenar o progresso do usuário (nº de leituras, nº de favoritos,
          categorias exploradas, etc.) e quais conquistas foram desbloqueadas.
        * Detectar eventos (ex: `poesia.dataLeitura != null`, `poesia.eh_favorito == 1`) e atualizar
          o `ProgressTracker`.
        * Definir limiares para conquistas (ex: 10 leituras = "Leitor Voraz").
    *   [ ] **UI (Fragmento XML):**
        * `ConquistasFragment`:
            * Layout com `RecyclerView` para exibir conquistas (ícone, título, descrição, status
              bloqueado/desbloqueado).
            * Adapter para o `RecyclerView`.
            * Usar animações Lottie para celebrar o desbloqueio de uma conquista (pode ser em um
              `Dialog` ou animação na própria tela de conquistas).
            * Cashito aparece com frases de encorajamento relacionadas às conquistas.

## Fase 6: Atualização Dinâmica de Conteúdo

19. **Configuração da Fonte Remota:** (Conforme roteiro anterior)
    *   [ ] GitHub/Drive, arquivos JSON, `manifest_updates.json`.

20. **Módulo de Rede com Hilt:** (Conforme roteiro anterior)
    *   [ ] `OkHttpClient`, Retrofit, `ApiService`.

21. **Repositório de Atualização / UseCase:** (Conforme roteiro anterior)
    *   [ ] Lógica de download, parsing, upsert no Room, atualização de versões em `DataStore` (
        preferível a `SharedPreferences` para dados estruturados/tipados ou assíncronos).

22. **WorkManager para Tarefas em Background:** (Conforme roteiro anterior)
    *   [ ] `UpdateDataWorker`, agendamento, constraints.

23. **Interface do Usuário para Atualizações (Opcional):** (Conforme roteiro anterior)
    *   [ ] Verificação manual, `Snackbar`.

## Fase 7: Funcionalidades Adicionais e Polimento

24. **Busca e Filtros Avançados:**
    *   [ ] Barra de busca na `Toolbar` ou em tela dedicada para pesquisar poesias e personagens (
        títulos, conteúdo, nomes).
    *   [ ] Lógica no `ViewModel` e DAO para suportar a busca.

25. **Compartilhamento:**
    *   [ ] Na tela de detalhes da poesia, adicionar botão "Compartilhar".
    *   [ ] Usar `Intent.ACTION_SEND` para compartilhar o texto da poesia ou um link para ela (se
        aplicável).

26. **Notificações Diárias ("Poesia do Dia"):**
    *   [ ] Usar `WorkManager` para agendar uma tarefa diária.
    *   [ ] Lógica para selecionar uma "Poesia do Dia" (aleatória, ou baseada em critérios).
    *   [ ] Criar e exibir uma `Notification` (usando `NotificationManagerCompat`).
    *   [ ] Lidar com o clique na notificação para abrir a poesia no app.

27. **Módulo de Criação de Poesias pelo Usuário (Avançado/Opcional):**
    *   [ ] Nova seção no app para o usuário escrever suas próprias poesias.
    *   [ ] Formulário para título, texto, categoria (talvez uma categoria "Minhas Criações").
    *   [ ] Salvar as poesias do usuário no banco de dados Room (pode requerer uma nova tabela ou um
        campo diferenciador na tabela `Poesias`).
    *   [ ] Opções básicas de formatação se desejado.

28. **Acessibilidade:**
    *   [ ] Garantir bom contraste de cores.
    *   [ ] Fornecer `contentDescription` para imagens e botões icon-only para TalkBack.
    *   [ ] Testar a navegação com TalkBack.

29. **Configurações do Aplicativo:**
    *   [ ] Tela de Configurações (`PreferenceFragmentCompat`).
    *   [ ] Opções: frequência de atualização de conteúdo, tema do app, configurações de TTS,
        gerenciamento de notificações.

## Fase 8: Testes e Lançamento

30. **Testes:**
    *   [ ] Testes Unitários: ViewModels, Repositórios, UseCases, ProgressTracker, lógica de
        parsing.
    *   [ ] Testes de Instrumentação: DAOs do Room e migrations, WorkManager workers, fluxos de UI
        com Espresso.

31. **Otimização e Refinamento:**
    *   [ ] Profiling de performance.
    *   [ ] Revisão de UI/UX (conforme design fornecido).
    *   [ ] Tratamento de todos os casos de borda e erros.

32. **Preparação para Publicação:**
    *   [ ] Gerar APK/AAB assinado para release.
    *   [ ] Configurar listagem na Google Play Store.
    *   [ ] Considerar Proguard/R8.

---

## Decisões de Design e Padrões de Código (Adendo - {{HOJE_DATA}})

### 1. Estrutura de Arquivos para Entidades Room e Modelos de Domínio:

- Para cada tipo de dado persistido (Notas, Personagens, Poesias, Textos), será criado um arquivo
  Kotlin `.kt` dedicado no pacote apropriado (ex: `com.marin.catfeina.data.model` ou
  `com.marin.catfeina.data.entity`).- Cada um desses arquivos conterá:
    - A classe de dados `@Entity` do Room (ex: `NomeDaEntidadeEntity`).
    - A classe de dados do Modelo de Domínio correspondente (ex: `NomeDaEntidade`).
    - Funções de extensão para mapeamento bidirecional:
        - `fun NomeDaEntidadeEntity.toDomain(): NomeDaEntidade`
        - `fun NomeDaEntidade.toEntity(): NomeDaEntidadeEntity`
- **Justificativa:** Manter a coesão, facilitar a localização e promover uma clara separação entre a
  camada de persistência e a camada de domínio/negócios.

### 2. Nomenclatura de Entidades Room:

- Classes de entidade Room usarão o sufixo `Entity` (ex: `TextoEntity`, `PoesiaEntity`).
- O nome base da entidade será no singular (ex: `Texto` para a tabela `textos`).
- **Justificativa:** Clareza e distinção das classes de entidade em relação aos modelos de domínio
  ou DTOs. Segue uma convenção comum.

### 3. Cabeçalho de Documentação em Arquivos Kotlin:

- Todos os arquivos de código Kotlin gerados ou significativamente modificados devem incluir um
  cabeçalho de documentação padronizado no início do arquivo:
    - **Justificativa:** Melhorar a documentação do código, facilitar a compreensão e a manutenção.

### 4. Geração de `CHANGELOG.MD` e Adendos ao `GEMINI.MD`:

- Após discussões significativas sobre novas funcionalidades, alterações estruturais, ou decisões de
  design importantes, o assistente (Gemini) deve:
    - Gerar um adendo para o `GEMINI.MD` resumindo as decisões.
    - Propor uma entrada para o `CHANGELOG.MD` detalhando as mudanças.
- **Justificativa:** Manter um histórico claro das evoluções do projeto e das decisões tomadas.

### 5. Estrutura do Banco de Dados (Baseado no Dump SQL de {{HOJE_DATA}}):

- A estrutura das tabelas `notas`, `personagens`, `poesias`, e `textos` será baseada no dump SQL
  fornecido em {{HOJE_DATA}}. As entidades Room e modelos de domínio refletirão esta estrutura. (
  Pode-se anexar ou referenciar o dump SQL aqui se desejado).

### 6. Migrações do Room:

- A necessidade de migrações do Room será avaliada a cada alteração de schema.
- Se o app ainda não foi lançado e o schema está sendo definido/refinado, a versão do banco pode ser
  incrementada, e o Room pode recriar o banco (assumindo que a perda de dados de desenvolvimento é
  aceitável).
- Para alterações de schema após um lançamento ou quando dados precisam ser preservados, classes
  `Migration` apropriadas serão implementadas.

### 7. Funcionalidade da Tabela `Textos`:

- **Propósito:** Armazenar blocos de texto únicos (identificados por uma `chave`) para exibição em
  telas dedicadas (ex: Política de Privacidade, Termos de Uso).- **Campos Chave:** `id`, `chave`,
  `conteudo_html`, `conteudo_tts`.
- **Interação:** Itens de menu (ex: no Navigation Drawer) levarão a um Fragmento dedicado que
  renderizará o `conteudo_html` (provavelmente em um `WebView`).
- **Toolbar Comum:** Telas exibindo conteúdo de `Textos` (e outras tabelas principais) terão uma
  Toolbar com funcionalidades padronizadas:
    - Copiar (`conteudo_html` para `Textos`).
    - Compartilhar (`conteudo_html` para `Textos`).
    - Aumentar/Diminuir Tamanho da Fonte (aplicado ao `WebView`, preferência salva em
      `SharedPreferences`).
    - Seleção de Tema Claro/Escuro (aplicação global via `AppCompatDelegate.setDefaultNightMode()`).
    - Ouvir (aciona TTS para o `conteudo_tts`).

     
