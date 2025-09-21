# Roteiro de Desenvolvimento: Felynos (Sabor Felynos)

**Colaborador:** Assistente Gemini
**Projeto:** Felynos - Aplicativo Android de Poesia (com o *flavor* inicial Felynos)
**Objetivo:** Criar um aplicativo moderno, robusto e escalável para amantes de poesia, utilizando Jetpack Compose e seguindo as melhores práticas de arquitetura e modularização definidas para o projeto Felynos. O primeiro *product flavor* a ser desenvolvido será o "Felynos", focado em poesias, personagens e textos informativos.

---

## Filosofia e Princípios Chave (Baseados na Arquitetura Felynos)

A arquitetura do Felynos será guiada pelos seguintes princípios, detalhados no "Roteiro de aprendizado sobre modularização" do projeto:

*   [ ] 1. **Modularização Estratégica:** Organizar o código em módulos Gradle distintos, seguindo a estratégia de baixo acoplamento e alta coesão, com separação clara entre módulos de `:app`, `:feature` e `:core`.
*   [ ] 2. **UI 100% com Jetpack Compose e Material 3:** Interface moderna, reativa e consistente, utilizando componentes do Material 3 e um sistema de design próprio (`:core:ui`).
*   [ ] 3. **Fluxo de Dados Unidirecional (UDF):** Adoção de UDF nos ViewModels das features, utilizando Kotlin Flows para gerenciar e expor o estado da UI.
*   [ ] 4. **Camada de Dados Robusta:** Implementação do padrão Repository, utilizando Room para persistência local (`:core:database`) e DataStore para preferências do usuário (`:core:datastore`). Os modelos de dados e a lógica de negócios residirão em `:core:dominio` e `:core:data`.
*   [ ] 5. **Injeção de Dependência com Hilt:** Gerenciamento claro e eficiente de dependências em todo o projeto.
*   [ ] 6. **Navegação com Navigation Compose:** Navegação type-safe e bem estruturada entre as diferentes telas e features do aplicativo.
*   [ ] 7. **Testabilidade:** Foco em criar código testável, com testes unitários, de integração e de UI em todos os módulos relevantes (`:core:testing` e testes específicos por módulo).
*   [ ] 8. **Foco na Experiência do Usuário:** Design limpo, funcionalidades úteis e performance otimizada para garantir a melhor experiência possível.
*   [ ] 9. **Suporte a Variações do Produto (Product Flavors):** A estrutura modular deve facilitar a criação de diferentes versões ou "sabores" do aplicativo (como o flavor "Felynos"), incluindo ou excluindo módulos de feature específicos.

---

## Estrutura de Módulos Proposta (Projeto Felynos)

A estrutura de módulos seguirá o modelo definido no "Roteiro de aprendizado sobre modularização" do Felynos:

*   [ ] **:app** (Módulo principal da aplicação)
*   [ ] **:core:dominio** (Modelos de dados puros, lógica de negócios central)
*   [ ] **:core:data** (Repositórios, orquestração de fontes de dados)
*   [ ] **:core:database** (DAOs, Entidades Room, configuração do banco)
*   [ ] **:core:network** (Configuração de API, fontes de dados remotas)
*   [ ] **:core:datastore** (Gerenciamento de preferências do usuário com DataStore)
*   [ ] **:core:ui** (Tema do aplicativo, componentes de UI reutilizáveis, design system)
*   [ ] **:core:testing** (Utilitários e dados para testes)
*   ---
*   Módulos de Feature para o *flavor* **Felynos**:
    *   [ ] **:feature:poesias** (Funcionalidades relacionadas a poesias)
    *   [ ] **:feature:personagens** (Funcionalidades relacionadas a personagens)
    *   [ ] **:feature:informativos** (Funcionalidades relacionadas a textos informativos)
    *   [ ] **:feature:configuracoes** (Configurações do aplicativo)
*   ---
*   [ ] **(Opcional Futuro) :sync** (Para sincronização de dados em background com WorkManager)

**(Ver o diagrama Mermaid no "Roteiro de aprendizado sobre modularização" do Felynos para a visualização das dependências)**

---

## Fases de Desenvolvimento (Foco no *Flavor* Felynos)

### Fase 0: Configuração Inicial e Estrutura de Módulos Base Felynos

*   [ ] 0.1. Configurar novo projeto Android Studio (Felynos) com Kotlin e Jetpack Compose.
*   [ ] 0.2. Estabelecer a estrutura de módulos Gradle inicial conforme definido para Felynos: `:app`, `:core:dominio`, `:core:data`, `:core:database`, `:core:network`, `:core:datastore`, `:core:ui`, `:core:testing`.
*   [ ] 0.3. Configurar Hilt para injeção de dependência básica no projeto Felynos.
*   [ ] 0.4. Configurar Jetpack Navigation Compose no módulo `:app` para a navegação principal.
*   [ ] 0.5. Definir o `FelynosTheme` (Material 3) inicial no `:core:ui`. Para o *flavor* Felynos, pode haver personalizações específicas.
*   [ ] 0.6. Criar um Composable placeholder simples em `:app` para verificação da estrutura e tema.
*   [ ] 0.7. Integrar este arquivo `GEMINI.MD` (roteiro de desenvolvimento) e o "Roteiro de aprendizado sobre modularização" no versionamento do projeto.
*   [ ] 0.8. **Critério de Conclusão:** Projeto Felynos compila, executa, estrutura de módulos base criada, Hilt e Navigation Compose básicos configurados, tema inicial aplicado.
*   [ ] 0.9. **Colaboração com Gemini:** Revisão da estrutura de módulos, exemplos de configuração `build.gradle.kts` (usando `libs.versions.toml`), e organização inicial dos módulos `core`.

### Fase 1: Camada de Dados Fundamental e Preferências (Core Felynos)

*   [ ] 1.1. No `:core:dominio`, definir modelos de dados puros iniciais (ex: `Poesia`, `Personagem`, `Informativo`, `PreferenciaUsuario`, `ResultWrapper`).
*   [ ] 1.2. No `:core:database`, definir entidades Room (`PoesiaEntity`, etc.) e DAOs básicos para cada entidade. Configurar o `FelynosDatabase`.
*   [ ] 1.3. No `:core:datastore`, implementar `PreferenciasUsuarioDataSource` para gerenciar preferências como tema, tamanho da fonte, etc.
*   [ ] 1.4. No `:core:network`, configurar `ApiService` inicial (mesmo que mockado ou com endpoints de placeholder).
*   [ ] 1.5. No `:core:data`, implementar Repositórios iniciais (ex: `OfflineFirstPoesiasRepository`) utilizando os DAOs, ApiService e DataStore. Implementar mappers entre Entidades de banco, DTOs de rede e Modelos de domínio.
*   [ ] 1.6. Implementar `AssetsDataSource` (provavelmente em `:core:data` ou como um utilitário) para ler dados iniciais de arquivos JSON/Markdown nos assets.
*   [ ] 1.7. Integrar Hilt para fornecer DAOs, Repositórios, DataSources e o Database.
*   [ ] 1.8. **Critério de Conclusão:** Camadas de domínio e dados funcionais, carga inicial de dados (se aplicável via assets) OK, preferências podem ser salvas/lidas. Testes unitários para DAOs, Repositórios e DataSources.
*   [ ] 1.9. **Colaboração com Gemini:** Revisão das definições de entidades e modelos, exemplos de implementação de Repositórios (com OfflineFirst strategy), uso do DataStore, e estratégias de teste para a camada de dados.

### Fase 2: Features Iniciais do *Flavor* Felynos - Informativos e Configurações

*   [ ] 2.1. Criar o módulo `:feature:informativos`.
*   [ ] 2.2. Desenvolver Composable genérico para exibir conteúdo Markdown (ex: encapsulando Markwon) no `:core:ui`.
*   [ ] 2.3. Em `:feature:informativos`, desenvolver tela (Screen) e ViewModel para listar os textos informativos (obtidos via `InformativosRepository`).
*   [ ] 2.4. Em `:feature:informativos`, desenvolver tela (Screen) e ViewModel para exibir o conteúdo de um texto informativo selecionado (usando o Composable de Markdown).
*   [ ] 2.5. Criar o módulo `:feature:configuracoes`.
*   [ ] 2.6. Em `:feature:configuracoes`, desenvolver tela (Screen) e ViewModel para permitir ao usuário alterar configurações (ex: tema claro/escuro, tamanho da fonte), integrando com o `PreferenciasUsuarioDataSource` de `:core:datastore`.
*   [ ] 2.7. Integrar as rotas de navegação para as features `informativos` e `configuracoes` no grafo de navegação principal no módulo `:app`.
*   [ ] 2.8. Adicionar a API Splash Screen do AndroidX no módulo `:app` (animação Lottie opcional).
*   [ ] 2.9. **Critério de Conclusão:** Usuário pode navegar para as telas de informativos, visualizar a lista e o conteúdo. Usuário pode acessar a tela de configurações e alterar preferências que são persistidas e refletidas no app. Splash screen funcional. Testes de UI básicos para as novas features.
*   [ ] 2.10. **Colaboração com Gemini:** Auxílio na criação do Composable de Markdown, exemplos de ViewModels para as features, integração das telas de configurações com DataStore, e configuração da navegação para as features.

### Fase 3: Feature Principal do *Flavor* Felynos - Poesias

*   [ ] 3.1. Criar o módulo `:feature:poesias`.
*   [ ] 3.2. Em `:feature:poesias`, desenvolver tela (Screen) e ViewModel para listar todas as poesias. Usar Coil para carregar imagens de capa (se houver).
*   [ ] 3.3. Em `:feature:poesias`, desenvolver tela (Screen) e ViewModel para exibir os detalhes de uma poesia (texto em Markdown, opção de favoritar). A funcionalidade de favoritar deve interagir com o `PoesiasRepository` e, possivelmente, com `PreferenciasUsuarioDataSource` ou uma tabela de favoritos.
*   [ ] 3.4. Integrar as rotas de navegação para a feature `poesias` no grafo principal em `:app`.
*   [ ] 3.5. Refinar componentes genéricos no `:core:ui` conforme necessário (ex: cards, botões de ação).
*   [ ] 3.6. **Critério de Conclusão:** Usuário pode listar, visualizar e favoritar poesias. A interface é responsiva e o carregamento de dados é eficiente. Testes para ViewModels e UI da feature.
*   [ ] 3.7. **Colaboração com Gemini:** Estrutura das telas de listagem e detalhes, lógica de favoritar (incluindo atualização de estado e persistência), sugestões para paginação ou virtualização de listas longas, e uso eficaz do Coil.

### Fase 4: Feature Adicional do *Flavor* Felynos - Personagens

*   [ ] 4.1. Criar o módulo `:feature:personagens`.
*   [ ] 4.2. Em `:feature:personagens`, desenvolver tela (Screen) e ViewModel para listar os personagens.
*   [ ] 4.3. Em `:feature:personagens`, desenvolver tela (Screen) e ViewModel para exibir os detalhes de um personagem (biografia em Markdown, imagem, lista de poesias associadas a ele – requer lógica no `PersonagensRepository` ou `PoesiasRepository`).
*   [ ] 4.4. Integrar as rotas de navegação para a feature `personagens` no grafo principal em `:app`.
*   [ ] 4.5. **Critério de Conclusão:** Usuário pode listar personagens e visualizar seus detalhes, incluindo poesias associadas. Testes para ViewModels e UI da feature.
*   [ ] 4.6. **Colaboração com Gemini:** Design das telas de personagens, lógica para buscar e exibir poesias associadas a um personagem, e otimização da apresentação de dados relacionados.

### Fase 5: Funcionalidades de UX Avançadas (Aplicável ao Felynos/Felynos)

*   [ ] 5.1. Implementar Text-to-Speech (TTS) para poesias na tela de detalhes (controles play/pause/stop), possivelmente como um utilitário em `:core:ui` ou uma classe específica em `:feature:poesias`.
*   [ ] 5.2. (Opcional) Animação do mascote "Cashito" com Lottie, oferecendo dicas contextuais ou como parte da identidade visual. Isso pode residir em `:core:ui`.
*   [ ] 5.3. Refinar animações de transição entre telas usando Navigation Compose.
*   [ ] 5.4. Melhorar acessibilidade em todo o aplicativo (descrições de conteúdo, navegação por foco, contraste de cores).
*   [ ] 5.5. **Critério de Conclusão:** TTS funcional para poesias. Animações de mascote (se implementadas) integradas. Transições de tela suaves e acessibilidade aprimorada.
*   [ ] 5.6. **Colaboração com Gemini:** Guia para implementação de TTS no Android com Jetpack Compose, ideias para integrar animações Lottie de forma sutil e eficaz, e melhores práticas para animações de navegação e acessibilidade.

### Fase 6: Polimento, Testes Abrangentes e Preparação para Release (Felynos - *Flavor* Felynos)

*   [ ] 6.1. Revisão completa da UI/UX em todos os módulos e fluxos do *flavor* Felynos.
*   [ ] 6.2. Escrever testes unitários, de integração e de UI mais abrangentes, cobrindo os principais fluxos de usuário, casos de borda, e migrações de schema do Room (se houver). Utilizar `:core:testing` para utilitários.
*   [ ] 6.3. Otimização de performance: monitorar e otimizar recomposições em Jetpack Compose, uso de memória, tempo de inicialização do app.
*   [ ] 6.4. Tratamento robusto de erros e estados de carregamento/vazio em todas as telas.
*   [ ] 6.5. Verificar e configurar regras Proguard/R8 para a build de release.
*   [ ] 6.6. Testar o *flavor* Felynos em diferentes dispositivos, tamanhos de tela e versões do Android.
*   [ ] 6.7. Gerar build de release assinado para o *flavor* Felynos.
*   [ ] 6.8. **Critério de Conclusão:** Aplicativo (flavor Felynos) estável, performático, visualmente polido, com alta cobertura de testes e pronto para ser distribuído.
*   [ ] 6.9. **Colaboração com Gemini:** Estratégias para testes de UI complexos, dicas de otimização de performance com Compose, checklist para preparação de release.

### Fase Pós-Lançamento (Ideias Futuras para Felynos)

*   [ ] 7.1. Implementar o módulo `:sync` para sincronização de conteúdo de uma API externa usando WorkManager (para buscar novas poesias, personagens, etc., automaticamente).
*   [ ] 7.2. Notificações push para novas poesias ou conteúdos relevantes.
*   [ ] 7.3. Funcionalidades de comunidade: permitir que usuários submetam poesias (requer backend e moderação).
*   [ ] 7.4. Mais opções de temas, coleções de poesias, ou filtros avançados.
*   [ ] 7.5. Desenvolvimento de outros *product flavors* do Felynos com diferentes conjuntos de features.

---

## Capacidades e Limitações do Assistente Gemini (Observadas)

*   **Capacidades:**
    *   [ ] Fornecer exemplos de código Kotlin/Compose.
    *   [ ] Sugerir estruturas de projeto e arquitetura (como neste roteiro).
    *   [ ] Explicar conceitos do Android, Jetpack e bibliotecas.
    *   [ ] Ajudar a refatorar código para melhor clareza ou performance.
    *   [ ] Sugerir bibliotecas e como adicioná-las (ex: via `libs.versions.toml`).
    *   [ ] Gerar documentação em Markdown.
    *   [ ] Auxiliar na escrita de testes.
*   **Limitações Observadas (e como lidar):**
    *   **Respostas Longas Truncadas:** Ao gerar arquivos ou respostas muito longas, especialmente contendo múltiplos blocos de código demarcados por 'três crases', a resposta pode ser cortada.
        *   **Solução:** Solicitar a resposta em partes menores, focar em seções específicas, ou pedir para evitar múltiplos blocos de código demarcados por 'três crases' em uma única resposta longa se o objetivo for um arquivo completo. Sinalizar o problema se ocorrer.
    *   [ ] **Acesso ao Código:** Não pode ver o código do projeto diretamente no IDE (a menos que seja explicitamente colado na conversa).
    *   [ ] **Execução e Teste:** Não pode executar ou testar o código fornecido.
    *   [ ] **Conhecimento Limitado:** O conhecimento é vasto, mas baseado em dados de treinamento até um certo ponto no tempo. Novas APIs ou bibliotecas muito recentes podem não ser totalmente conhecidas.
    *   [ ] **Interpretação de Markdown Complexo:** A presença excessiva de representações de blocos de código (usando 'três crases') ou estruturas Markdown muito aninhadas em prompts pode, ocasionalmente, confundir o processamento da solicitação.

---

## Instruções Gerais para Colaboração com Gemini (S E M P R E):

*   [ ] 1. **Consciência da Renderização:** Lembre-se que a interpretação interna do Markdown pelo Gemini pode diferir da visualização final no seu editor ou plataforma.
*   [ ] 2. **Clareza nos Pedidos:** Ser específico sobre o que é necessário. Se um exemplo de código for solicitado, especificar linguagem, bibliotecas e o problema a ser resolvido.
*   [ ] 3. **Blocos de Código:** Solicitar que todos os códigos venham dentro de um bloco de código demarcado por 'três crases' para facilitar a cópia. Evitar comentários excessivos e desnecessários *dentro* do código gerado, a menos que sejam cruciais para o entendimento imediato.
*   [ ] 4. **Idioma:** Comunicação em Português.
*   [ ] 5. **Nomenclatura no Código:** Preferência por nomes de variáveis, funções e classes em Português nos exemplos de código, quando fizer sentido para o contexto do projeto Felynos.
*   [ ] 6. **Gerenciamento de Dependências:** Ao sugerir bibliotecas, incluir a forma de adição via `libs.versions.toml`.
*   [ ] 7. **Formatação de Roteiros/Documentos:** Manter a formatação Markdown simples e clara em documentos como este para facilitar a leitura e edição.
*   [ ] 8. **Documentação de Código:** Seguir a convenção de incluir um cabeçalho descritivo no início de cada arquivo Kotlin, conforme o exemplo:
           // =============================================================================
           // Arquivo: com.marin.felynos.data.entity.TextoEntity.kt
           // Descrição: Entidade Room para a tabela 'textos', seu datao de domínio 'Texto',
           //            e funções de mapeamento entre eles.
           // =============================================================================
*   [ ] 9. **Gerar o conteúdo diário para o ChangeLog.md.
*   [ ] 10.

---

