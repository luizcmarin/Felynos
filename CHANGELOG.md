# Catfeina - Registro de Atualizações (Change Log)


## [0.3.0] - 2025-09-24

**Novas Funcionalidades / Melhorias:**

*   **`ParserTextoFormatado.kt`:**
    *   **Melhoria na Lógica de Parsing de Tags:**
        *   Ajustada a lógica dentro do método `parse` para lidar corretamente com `ResultadoProcessamentoTag.ElementoBloco`. Agora, um parágrafo pendente é finalizado e adicionado à lista de elementos ANTES de adicionar o novo `ElementoBloco`. Isso garante a ordem correta dos elementos renderizados.
        *   Adicionado logging mais detalhado (`Timber.d`) para indicar quando um `ElementoBloco` é adicionado à lista final.
    *   **Refinamento do Tratamento de `AplicacaoTagEmLinha`:**
        *   Continuamos a refinar como as tags em linha (`AplicacaoTagEmLinha`) são processadas e adicionadas ao `acumuladorTextoParagrafo`.
        *   Asseguramos que o `intervalo` da aplicação em linha seja corretamente calculado e atualizado com base na sua posição dentro do texto acumulado do parágrafo.
        *   Adicionamos logs para depuração do cálculo de intervalo e para casos onde o texto original da aplicação é vazio.
    *   **Melhoria no Tratamento de Erros e Casos Não Consumidos:**
        *   Refinamos a lógica para quando um processador de tag retorna `ResultadoProcessamentoTag.NaoConsumido` ou `ResultadoProcessamentoTag.Erro`, garantindo que a tag original seja adicionada como texto literal ao parágrafo atual e que logs apropriados (warnings/errors) sejam emitidos.
        *   Tratamento para quando um processador não é encontrado para uma chave de tag, adicionando a tag literal ao output e logando um erro.
    *   **Robustez na Finalização de Parágrafos:**
        *   A função `finalizarParagrafoPendenteEAdicionar` foi revisada para garantir que parágrafos sejam corretamente criados e adicionados, mesmo no final do texto ou entre elementos de bloco.
    *   **Normalização de Quebras de Linha:**
        *   Implementada a substituição de `\r\n` por `\n` no início do método `parse` para normalizar as quebras de linha do texto de entrada.
    *   **Logging Aprimorado:**
        *   Adicionados e refinados diversos logs `Timber` (verbose, debug, warning, error) ao longo do processo de parsing para facilitar a depuração e o entendimento do fluxo de processamento das tags. Isso inclui logs para o texto de entrada, mapeamento de processadores, cada etapa do loop de parsing, resultados de processadores de tags e a finalização do parsing.
*   **Discussão e Planejamento (GEMINI.MD):**
    *   Revisamos o progresso da **Fase 2: Features Iniciais - Informativos e Configurações**.
    *   Confirmamos o foco no desenvolvimento do Composable genérico para processar texto com tags de formatação customizadas, que é o propósito central do `ParserTextoFormatado.kt`.

**Correções de Bugs:**

*   Indiretamente, o refinamento na lógica de finalização de parágrafos e adição de elementos de bloco preveniu potenciais bugs relacionados à ordem incorreta ou omissão de conteúdo.

## [0.2.0] - 2025-09-23

### Funcionalidades e Melhorias

*   **Sistema de Temas Refatorado:**
    *   Separada a lógica de seleção de tema em "Tema Base" (ex: Primavera, Verão) e "Estado do Tema" (Claro, Escuro, Automático do Sistema).
    *   Introduzidos os enums `BaseTheme` e `ThemeState` para gerenciar essas seleções.
    *   `UserPreferencesRepository` atualizado para salvar e carregar o tema base e o estado do tema como preferências distintas no DataStore.
    *   `ThemeViewModel` atualizado para expor `StateFlow`s separados para `currentBaseTheme` e `currentThemeState`, e para fornecer funções para modificar cada um independentemente.
    *   `CatfeinaAppTheme` (Composable principal do tema) modificado para aceitar `selectedBaseTheme` e `selectedThemeState` e aplicar a combinação correta de paletas de cores e tipografia.
    *   Corrigida a lógica de aplicação de cores dinâmicas (Material You) e temas base dentro do `CatfeinaAppTheme`.
    *   Melhorada a manipulação da barra de status no `CatfeinaAppTheme` para usar abordagens modernas, evitando APIs depreciadas e garantindo compatibilidade com o design de ponta a ponta (`enableEdgeToEdge`).

*   **Menu de Opções (`MenuTresPontinhos`):**
    *   Refatorado para desacoplar a lógica do menu da `InformativoScreen`.
    *   Movido o Composable `MenuTresPontinhos` para o pacote `com.marin.catfeina.dominio`.
    *   Atualizada a funcionalidade "Alterar Tema" dentro do menu para alternar o `ThemeState` (Claro, Escuro, Auto) através do `ThemeViewModel`.
    *   Ícone do item "Alterar Tema" agora reflete dinamicamente o `ThemeState` atual (Claro, Escuro, Auto).

### Correções de Bugs e Ajustes

*   **`InformativoScreen.kt`:**
    *   Corrigida a importação depreciada de `hiltViewModel` para usar `androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel`.
    *   Revisado o uso de `LocalContext.current` para garantir que seja mantido onde necessário (ex: `ImageRequest.Builder`).
    *   Clarificado o uso de `uiState` para garantir que todos os acessos sejam considerados pela IDE.
    *   Adicionada consistência na cor do ícone de navegação da `TopAppBar`.

*   **`MainActivity.kt`:**
    *   Atualizado o Composable `CatfeinaApp` para injetar e usar o `ThemeViewModel` modificado, coletando `baseTheme` e `themeState` separadamente e passando-os para `CatfeinaAppTheme`.
    *   Removida a dependência do `PreferenciasViewModel` para seleção de tema, se esta responsabilidade foi totalmente transferida para o `ThemeViewModel`.

### Documentação e Padrões

*   Adicionada e revisada documentação KDoc e cabeçalhos de arquivo para `ThemeViewModel.kt`, `UserPreferencesRepository.kt`, `MenuTresPontinhos.kt`, e `CatfeinaAppTheme.kt` para refletir as mudanças e manter a conformidade com os padrões do projeto.


## [0.1.0] - 2025-09-22

### Adicionado 🎉
- Implementada a funcionalidade completa de seleção de tema do aplicativo (Claro, Escuro, Padrão do Sistema).
- Criada a `PreferenciasScreen` permitindo ao usuário escolher e persistir o tema desejado.
- Adicionado `PreferenciasViewModel` para gerenciar o estado da seleção de tema.
- Implementado `UserPreferencesRepository` utilizando Jetpack DataStore para salvar e carregar as preferências de tema do usuário.
- Configurado `CatfeinaAppTheme` para aplicar dinamicamente o tema selecionado em todo o aplicativo.
- Integrada a tela de Preferências ao `NavigationDrawer` na `MainActivity`.
- Definida a estrutura inicial da `MainActivity` com `Scaffold`, `TopAppBar` dinâmica e `ModalNavigationDrawer`.
- Configuração inicial do Hilt para injeção de dependência.
- Configuração inicial do Jetpack Navigation Compose para navegação entre telas.

### Corrigido 🛠️
- Resolvido problema onde uma versão placeholder da `PreferenciasScreen` estava sendo incorretamente utilizada, impedindo a exibição das opções de tema. A tela de preferências funcional agora é corretamente invocada.

### Alterado ⚙️
- Roteiro de desenvolvimento (`GEMINI.MD`) atualizado para refletir o progresso nas Fases 0, 1 e 2.
- Refinada a lógica de navegação na `MainActivity` para interagir com o `NavigationDrawer` e `TopAppBar`.

---