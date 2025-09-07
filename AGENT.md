# AGENT.MD - Projeto Catfeina

## 1. Visão Geral do Projeto Catfeina

*   **Nome do Aplicativo:** Catfeina
*   **Descrição Curta:** Um aplicativo móvel Android para visualização e interação com uma coleção de poesias, permitindo aos usuários explorar, favoritar e (futuramente) ler conteúdo poético enriquecido com imagens.
*   **Objetivo Principal:** Oferecer uma plataforma agradável e organizada para leitura e apreciação de poesias, com recursos visuais e de personalização.
*   **Público-Alvo:** Amantes de poesia, leitores casuais em busca de inspiração, e qualquer pessoa que aprecie conteúdo literário bem apresentado em formato móvel.
*   **Tecnologias Chave (se relevante para "agentes"):**
    *   Kotlin (linguagem principal)
    *   Jetpack Compose (para a UI declarativa)
    *   Room (persistência de dados local para poesias e imagens)
    *   Hilt (injeção de dependências)
    *   Coroutines & Flow (programação assíncrona e reativa)
    *   Lottie (animação de splash screen)
    *   Jetpack DataStore (para preferências do usuário, como tema e estado de carga inicial)
    *   Coil (carregamento de imagens)

## 2. "Agentes" ou Funcionalidades Automatizadas no Catfeina

Aqui você listará e detalhará qualquer componente ou funcionalidade que atue de forma autônoma ou inteligente.

### 2.1. Agente/Funcionalidade: População Inicial do Banco de Dados
    *   **Propósito:** Garantir que o aplicativo tenha um conjunto inicial de poesias e imagens disponíveis para o usuário na primeira vez que o aplicativo é aberto, sem a necessidade de uma conexão de internet inicial para obter este conteúdo base.
    *   **Como Funciona (Alto Nível):**
        1.  Na inicialização do aplicativo (via `CatfeinaApplication#onCreate`), o sistema verifica uma flag nas preferências (DataStore) para determinar se os dados iniciais já foram carregados.
        2.  Se não foram, o sistema (futuramente) lê dados de um arquivo JSON pré-definido (localizado nos assets do aplicativo), que contém as informações das poesias e suas respectivas imagens.
        3.  Esses dados são então processados e inseridos no banco de dados local (Room) através da `InserirDadosIniciaisAction`.
        4.  Após a inserção bem-sucedida, a flag nas preferências é atualizada para indicar que os dados foram carregados, prevenindo execuções futuras.
    *   **Interação com o Usuário:** Indireta. O usuário não interage ativamente com este processo, mas se beneficia dele ao ter conteúdo disponível imediatamente após a instalação e primeira abertura. O processo é projetado para ser transparente.
    *   **Dados Utilizados (Entrada):**
        *   Flag booleana do DataStore (`dadosIniciaisCarregados`).
        *   (Futuramente) Um arquivo JSON nos assets contendo uma lista de objetos `DadosCompletosPoesiaParaCarga` (ou uma estrutura similar representando poesias e suas imagens).
    *   **Resultados Gerados (Saída):**
        *   Registros inseridos nas tabelas `poesia_entity` e `imagem_entity` do banco de dados Room.
        *   Atualização da flag `dadosIniciaisCarregados` no DataStore para `true`.
        *   Logs indicando o sucesso ou falha da operação.
    *   **Limitações Atuais:**
        *   A lógica de leitura e parsing do arquivo JSON ainda é um TODO.
        *   O arquivo JSON de dados iniciais ainda precisa ser criado e formatado.
        *   A `InserirDadosIniciaisAction` precisa ser finalizada para consumir os dados do JSON ou ter a lógica de carga embutida.
        *   Atualmente, a chamada na `CatfeinaApplication` pode não ter dados reais para passar para a action ou a action pode não ter como buscar os dados.
    *   **Ideias para o Futuro:**
        *   Permitir a atualização dos dados iniciais através de um mecanismo de versionamento no JSON e na flag.
        *   Buscar os dados iniciais de um servidor remoto como fallback ou alternativa.
        *   Fornecer feedback visual sutil ao usuário se a carga inicial for demorada (embora o objetivo seja que seja rápida).

### 2.2. Agente/Funcionalidade: Gerenciador de Preferências de Tema Dinâmico
    *   **Propósito:** Permitir que o usuário escolha entre tema claro, escuro ou o padrão do sistema, e aplicar essa preferência dinamicamente em todo o aplicativo para uma experiência visual consistente e personalizada.
    *   **Como Funciona (Alto Nível):**
        1.  O usuário seleciona a preferência de tema através de um menu na `TopAppBar` da `MainScreen`.
        2.  A seleção é persistida no Jetpack DataStore através do `PreferenciasRepository` e do `PreferenciasViewModel`.
        3.  Na `MainActivity`, o `PreferenciasViewModel` é observado para obter a preferência de tema atual.
        4.  Com base na preferência e no estado do tema do sistema (se "Padrão do Sistema" estiver selecionado), o tema apropriado (claro ou escuro) é aplicado ao `CatfeinaTheme`.
        5.  O `enableEdgeToEdge` também é configurado dinamicamente com base no tema aplicado para garantir a consistência das barras de sistema.
    *   **Interação com o Usuário:** Direta. O usuário seleciona explicitamente a opção de tema desejada no menu de configurações da `TopAppBar`.
    *   **Dados Utilizados (Entrada):**
        *   Seleção do usuário (PreferenciaTema: LIGHT, DARK, SYSTEM).
        *   Valor da preferência de tema armazenado no DataStore.
        *   Estado atual do tema do sistema (via `isSystemInDarkTheme()`).
    *   **Resultados Gerados (Saída):**
        *   Aplicação do tema visual (cores, estilos) em toda a interface do usuário do aplicativo.
        *   Configuração das barras de status e navegação para o modo edge-to-edge de acordo com o tema.
        *   Persistência da escolha do usuário no DataStore.
    *   **Limitações Atuais:**
        *   Nenhuma limitação funcional significativa identificada para esta funcionalidade.
    *   **Ideias para o Futuro:**
        *   Oferecer mais opções de temas (ex: temas de cores específicas, modo "True Black" para OLED).
        *   Sincronizar a preferência de tema com as configurações da conta do usuário (se houver backend).

## 3. Fluxos de Usuário Importantes Envolvendo Agentes (se aplicável)

*   **Fluxo de Primeira Inicialização:**
    1.  Usuário instala e abre o aplicativo pela primeira vez.
    2.  Splash screen do sistema é exibida.
    3.  Animação Lottie de splash é exibida.
    4.  Em paralelo (background), o "Agente de População Inicial do Banco de Dados" é acionado:
        *   Verifica se os dados precisam ser carregados.
        *   (Futuramente) Carrega dados de um JSON.
        *   Insere dados no banco Room.
        *   Atualiza a flag de "dados carregados".
    5.  Após a animação Lottie, o usuário é direcionado para a `MainScreen` (provavelmente a tela `INICIO` ou `POESIAS_LISTA`).
    6.  O conteúdo (poesias) já está disponível para visualização devido à ação do agente de população.
    7.  O "Agente de Gerenciamento de Preferências de Tema" aplica o tema padrão (provavelmente "Padrão do Sistema") na primeira inicialização.

*   **Fluxo de Alteração de Tema:**
    1.  Usuário está em qualquer tela principal do aplicativo.
    2.  Usuário abre o menu de três pontos na `TopAppBar`.
    3.  Usuário seleciona uma nova opção de tema (Claro, Escuro, Padrão do Sistema).
    4.  O "Agente de Gerenciamento de Preferências de Tema" é acionado:
        *   A preferência é salva no DataStore.
        *   O `CatfeinaTheme` recompõe com o novo tema.
        *   A UI é atualizada instantaneamente em todo o aplicativo para refletir a nova seleção.

## 4. Considerações de Design e UX para Agentes

*   **População Inicial do Banco de Dados:**
    *   **Transparência:** O processo deve ser, na maior parte, invisível para o usuário. Não deve bloquear a UI nem apresentar diálogos intrusivos.
    *   **Velocidade:** A carga de dados deve ser rápida para não atrasar a primeira interação do usuário com o conteúdo.
    *   **Feedback em Caso de Falha (Mínimo):** Se a carga inicial falhar criticamente (o que não deveria acontecer com dados locais), o aplicativo deve lidar graciosamente, talvez exibindo uma mensagem de "conteúdo indisponível" em vez de crashar. Erros devem ser logados para depuração.
*   **Gerenciador de Preferências de Tema:**
    *   **Feedback Imediato:** A mudança de tema deve ser aplicada instantaneamente após a seleção do usuário.
    *   **Clareza:** As opções de tema no menu devem ser claras e indicar qual está atualmente selecionada (já implementado com o ícone de "check").
    *   **Consistência:** O tema aplicado deve ser consistente em todas as telas e componentes do aplicativo.

## 5. Notas de Desenvolvimento e Manutenção

*   **População Inicial do Banco de Dados:**
    *   **Arquivo JSON:** A estrutura do arquivo `poesias_iniciais.json` (a ser criado) precisa ser bem definida e documentada. Qualquer alteração na estrutura do `PoesiaEntity` ou `ImagemEntity` pode exigir atualizações no JSON e na lógica de parsing.
    *   **`InserirDadosIniciaisAction`:** Deve ser robusta, lidar com possíveis erros de parsing do JSON (se a lógica estiver nela) e garantir a integridade dos dados durante a inserção no Room (uso de transações é fundamental).
    *   **Testes:**
        *   Testar a lógica de leitura e parsing do JSON isoladamente.
        *   Testar a `InserirDadosIniciaisAction` com dados mockados para verificar a correta inserção no banco.
        *   Testar o fluxo completo na `CatfeinaApplication` para garantir que a flag de "dados carregados" funcione e que a população ocorra apenas uma vez.
*   **Gerenciador de Preferências de Tema:**
    *   **`PreferenciasRepository` e `DataStore`:** A chave para a preferência de tema deve ser gerenciada cuidadosamente para evitar conflitos.
    *   **Testes:**
        *   Testar a gravação e leitura da preferência de tema no `PreferenciasRepository`.
        *   Testar a lógica na `MainActivity` que aplica o tema com base na preferência e no tema do sistema.
        *   Testes de UI para verificar visualmente se o tema é aplicado corretamente em diferentes telas e componentes.

---
