# Princípios de Arquitetura do Aplicativo Catfeina

Este documento descreve os princípios arquitetônicos chave que guiam o desenvolvimento do aplicativo
Catfeina, mesmo sendo construído como um projeto de módulo único (`:app`).

## Contexto do Projeto

O Catfeina é um aplicativo Android focado em poesias, personagem e textos informativos. Para
garantir um código manutenível, testável e escalável dentro de seu módulo único, seguimos os
seguintes princípios. O documento `GEMINI.MD` detalha o plano de desenvolvimento completo e a
estrutura de pacotes interna.

## Princípios Arquitetônicos Chave

Dentro do módulo `:app`, a organização e o design do código seguirão:

* **Separação de Preocupações por Pacotes:** O código será organizado em pacotes distintos para
  separar claramente as diferentes camadas e responsabilidades (ex: UI, ViewModel, Dados, Domínio).
  Isso visa alta coesão dentro dos pacotes e baixo acoplamento entre eles.
* **Interface de Usuário com Jetpack Compose:** A UI será construída inteiramente com Jetpack
  Compose e Material 3, promovendo uma abordagem declarativa e reativa.
* **Fluxo de Dados Unidirecional (UDF):** Os ViewModels seguirão o padrão UDF, utilizando Kotlin
  Flows para gerenciar e expor o estado da UI de forma previsível.
* **Camada de Dados Robusta:** Uma camada de dados bem definida, utilizando o padrão Repository,
  Room para persistência local e DataStore para preferências do usuário, garantirá o gerenciamento
  eficiente dos dados do aplicativo.
* **Injeção de Dependência com Hilt:** Hilt será utilizado para gerenciar as dependências em todo o
  aplicativo, simplificando a instanciação e o fornecimento de objetos.
* **Navegação com Navigation Compose:** A navegação entre as diferentes telas será gerenciada pelo
  Navigation Compose, garantindo uma navegação type-safe e bem estruturada.
* **Testabilidade:** O código será escrito com a testabilidade em mente, permitindo testes unitários
  para a lógica de negócios e ViewModels, e testes de instrumentação para a UI e fluxos de usuário.

Aderir a esses princípios ajudará a manter a qualidade e a organização do código à medida que o
aplicativo Catfeina evolui.

# Roteiro de Desenvolvimento: Catfeina

**Colaborador:** Assistente Gemini
**Projeto:** Catfeina - Aplicativo Android de Poesia
**Objetivo:** Criar um aplicativo moderno, robusto e escalável para amantes de poesia, utilizando
Jetpack Compose e seguindo as melhores práticas de arquitetura. O aplicativo será focado em poesias,
personagem e textos informativos, tudo contido em um único módulo `:app`.
---


# Roteiro de Desenvolvimento: Catfeina

## Filosofia e Princípios Chave (Aplicados no Módulo :app)

A arquitetura do Catfeina, dentro do módulo `:app`, será guiada pelos seguintes princípios:

* [X] 
    1. **Organização Interna Clara:** Embora em um único módulo, o código será organizado em
       pacotes distintos para separar preocupações (UI, ViewModel, Dados, Domínio), buscando alta
       coesão
       e baixo acoplamento entre essas camadas internas.
* [X] 
    2. **UI 100% com Jetpack Compose e Material 3:** Interface moderna, reativa e consistente,
       utilizando componentes do Material 3 e um sistema de design próprio (localizado em pacotes de
       UI
       dentro do `:app`).
* [X] 
    3. **Fluxo de Dados Unidirecional (UDF):** Adoção de UDF nos ViewModels, utilizando Kotlin
       Flows para gerenciar e expor o estado da UI.
* [X] 
    4. **Camada de Dados Robusta:** Implementação do padrão Repository, utilizando Room para
       persistência local e DataStore para preferências do usuário. Os modelos de dados, lógica de
       negócios, DAOs e entidades residirão em pacotes dedicados dentro do `:app`.
* [X] 
    5. **Injeção de Dependência com Hilt:** Gerenciamento claro e eficiente de dependências em
       todo o projeto.
* [X] 
    6. **Navegação com Navigation Compose:** Navegação type-safe e bem estruturada entre as
       diferentes telas do aplicativo.
* [X] 
    7. **Testabilidade:** Foco em criar código testável, com testes unitários, de integração e de
       UI para as diferentes camadas e funcionalidades dentro do `:app`.
* [X] 
    8. **Foco na Experiência do Usuário:** Design limpo, funcionalidades úteis e performance
       otimizada para garantir a melhor experiência possível.

---

## Estrutura de Pacotes Proposta (dentro do :app)

O módulo `:app` será organizado com a seguinte estrutura de pacotes (exemplo):

* `com.marin.catfeina`
    * `di` (Módulos Hilt/Room)
    * `data`
        * `dao` (DAOs do Room)
        * `entity` (Entidades Room)
        * `repository` (Implementações de Repositórios)
        * `datastore` (DataStore para preferências)
        * `remote` (Configuração do ApiService, DTOs - se houver API externa)
    * `dominio` (Classes públicas/utilitárias)
        * `usecase` (Casos de uso/Interactors)
    * `ui`
        * `poesias` (Screen e ViewModel)
        * `personagem` (Screen e ViewModel
        * `informativos` (Screen e ViewModel
        * `preferencias` (Screen e ViewModel)
        * `theme` (Tema do aplicativo, cores, tipografia)
        * `componentes` (Componentes de UI reutilizáveis)
    * `CatfeinaApplication.kt` (Classe Application)
    * `navigation` (Configuração do Navigation Compose, rotas)
    * `MainActivity.kt`

---

## Fases de Desenvolvimento (Aplicativo Catfeina - Módulo Único :app)

### Fase 0: Configuração Inicial e Estrutura de Pacotes Base

* [X] 0.1. Configurar novo projeto Android Studio (Catfeina) com Kotlin e Jetpack Compose (módulo
  `:app` único).
* [X] 0.2. Estabelecer a estrutura de pacotes inicial dentro do `:app` (ex:
  `data (dao, entity, repository)`,
  `dominio`,`di`, `ui (theme, diversos, notas, personagem, poesia)`, `componentes`).
* [X] 0.3. Configurar Hilt para injeção de dependência básica no projeto.
* [X] 0.4. Configurar Jetpack Navigation Compose para a navegação principal.
* [X] 0.5. Definir o `CatfeinaTheme` (Material 3) inicial no pacote `ui.theme`.
* [X] 0.6. Criar um Composable placeholder simples para verificação da estrutura e tema.
* [X] 0.7. Integrar este arquivo `GEMINI.MD` (roteiro de desenvolvimento) no versionamento do
  projeto.
* [X] 0.8. **Critério de Conclusão:** Projeto compila, executa, estrutura de pacotes base criada,
  Hilt e Navigation Compose básicos configurados, tema inicial aplicado.
* [X] 0.9. **Colaboração com Gemini:** Revisão da estrutura de pacotes, exemplos de configuração
  `build.gradle.kts` do `:app` (usando `libs.versions.toml`).

### Fase 1: Camada de Dados Fundamental e Preferências

* [] 1.1. No pacote `data.entity`, definir modelos de dados puros iniciais (ex: `Poesia`,
  `Personagem`, `Informativo`).
* [] 1.2. No pacote `data.dao`, definir entidades DAOs.
* [X] 1.3. No pacote `data.local`, implementar `Preferencias`.
* [X] 1.4. No pacote `data.remote`, configurar `ApiService` (DataStore) inicial.
* [P] 1.5. No pacote `data.repository`, implementar Repositórios iniciais e interfaces
  correspondentes. Implementar mappers (provavelmente em `data.entity`).
* [] 1.6. Integrar Hilt para fornecer DAOs, Repositórios, DataSources e o Database.
* [P] 1.7. **Critério de Conclusão:** Camadas de domínio e dados funcionais, carga inicial de
  dados OK, preferências podem ser salvas/lidas. Testes unitários para DAOs, Repositórios e
  DataSources.
* [] 1.9. **Colaboração com Gemini:** Revisão das definições de entidades e modelos, exemplos de
  implementação de Repositórios, uso do DataStore, e estratégias de teste para a camada de dados.

### Fase 2: Features Iniciais - Informativos e Configurações

* [] 2.1. Desenvolver Composable genérico no pacote `ui.componentes` para processar texto com **tags
  de formatação customizadas** (ex: `**negrito**`, `*italico*`) e renderizá-lo como
  `AnnotatedString`.
* [] 2.2. Desenvolver telas (Screens) e ViewModels no pacote `ui.informativo` para listar e exibir
  textos informativos, utilizando o Composable de texto formatado.
* [X] 2.3. Desenvolver tela (Screen) e ViewModel no pacote `ui.preferencias` para permitir ao
  usuário alterar configurações, integrando com `Preferencias`.
* [X] 2.4. Integrar as rotas de navegação para as features `informativo` e `preferencias` no grafo
  de navegação.
* [] 2.5. Adicionar a API Splash Screen do AndroidX.
* [P] 2.6. **Critério de Conclusão:** Usuário pode navegar, visualizar informativos com formatação
  básica e alterar configurações. Splash screen funcional. Testes de UI básicos.
* [] 2.7. **Colaboração com Gemini:** Auxílio na criação do Composable de processamento de **tags de
  formatação customizadas para `AnnotatedString`**, exemplos de ViewModels, integração com
  DataStore, e configuração da navegação.

### Fase 3: Feature Principal - Poesias

* [] 3.1. Desenvolver telas (Screens) e ViewModels no pacote `ui.poesias` para listar e exibir
  detalhes de poesias. Usar Coil para imagens.
* [] 3.2. Implementar funcionalidade de favoritar poesias, interagindo com o Repositório.
* [] 3.3. Integrar as rotas de navegação para a feature `poesias`.
* [] 3.4. Refinar componentes genéricos no `ui.componentes` conforme necessário.
* [] 3.5. **Critério de Conclusão:** Usuário pode listar, visualizar e favoritar poesias. Testes
  para ViewModels e UI.
* [] 3.6. **Colaboração com Gemini:** Estrutura das telas, lógica de favoritar, sugestões para
  listas longas, uso do Coil.

### Fase 4: Feature Adicional - personagem

* [] 4.1. Desenvolver telas (Screens) e ViewModels no pacote `ui.personagem` e
  `ui.viewmodel` para listar e exibir detalhes de personagem.
* [] 4.2. Implementar lógica para buscar e exibir poesias associadas a um personagem.
* [] 4.3. Integrar as rotas de navegação para a feature `personagem`.
* [] 4.4. **Critério de Conclusão:** Usuário pode listar personagem e visualizar seus detalhes.
  Testes para ViewModels e UI.
* [] 4.5. **Colaboração com Gemini:** Design das telas, lógica de dados relacionados.

### Fase 5: Funcionalidades de UX Avançadas

* [] 5.1. Implementar Text-to-Speech (TTS) para poesias.
* [] 5.2. (Opcional) Animação do mascote "Cashito" com Lottie.
* [] 5.3. Refinar animações de transição entre telas.
* [] 5.4. Melhorar acessibilidade.
* [] 5.5. **Critério de Conclusão:** TTS funcional, animações (se implementadas) integradas,
  transições suaves, acessibilidade aprimorada.
* [] 5.6. **Colaboração com Gemini:** Guia para TTS, Lottie, animações de navegação,
  acessibilidade.

### Fase 6: Polimento, Testes Abrangentes e Preparação para Release

* [] 6.1. Revisão completa da UI/UX.
* [] 6.2. Escrever testes unitários, de integração e de UI mais abrangentes.
* [] 6.3. Otimização de performance.
* [] 6.4. Tratamento robusto de erros e estados de carregamento/vazio.
* [] 6.5. Verificar e configurar regras Proguard/R8.
* [] 6.6. Testar em diferentes dispositivos.
* [] 6.7. Gerar build de release assinado.
* [] 6.8. **Critério de Conclusão:** Aplicativo estável, performático, polido, com alta cobertura
  de testes e pronto para ser distribuído.
* [] 6.9. **Colaboração com Gemini:** Estratégias para testes, otimização, checklist de release.

### Fase Pós-Lançamento (Ideias Futuras para Catfeina)

* [] 7.1. Sincronização de conteúdo de uma API externa usando WorkManager.
* [] 7.2. Notificações push.
* [] 7.3. Funcionalidades de comunidade (requer backend).
* [] 7.4. Mais opções de temas, coleções, filtros.

---

## Capacidades e Limitações do Assistente Gemini (Observadas)

* **Capacidades:**
    * [] Fornecer exemplos de código Kotlin/Compose.
    * [] Sugerir estruturas de projeto e arquitetura (como neste roteiro).
    * [] Explicar conceitos do Android, Jetpack e bibliotecas.
    * [] Ajudar a refatorar código para melhor clareza ou performance.
    * [] Sugerir bibliotecas e como adicioná-las (ex: via `libs.versions.toml`).
    * [] Gerar documentação em Markdown e fornecer dentro de bloco de código.
* **Limitações Observadas (e como lidar):**
    * **Respostas Longas Truncadas:** Ao gerar arquivos ou respostas muito longas, especialmente
      contendo múltiplos blocos de código demarcados por 'três crases', a resposta pode ser cortada.
        * **Solução:** NUNCA usar 'três crases' seguidas nas respostas. Solicitar a resposta em
          partes menores, focar em seções específicas, ou pedir
          para evitar múltiplos blocos de código demarcados por 'três crases' em uma única resposta
          longa se o objetivo for um arquivo completo. Sinalizar o problema se ocorrer.
    * [] **Conhecimento Limitado:** O conhecimento é vasto, mas baseado em dados de treinamento
      até um certo ponto no tempo. Novas APIs ou bibliotecas muito recentes podem não ser
      totalmente conhecidas.
    * [] **Interpretação de Markdown Complexo:** A presença excessiva de representações de blocos
      de código (usando 'três crases') ou estruturas Markdown muito aninhadas em prompts pode,
      ocasionalmente, confundir o processamento da solicitação.

---

## Instruções Gerais para o Assistente Gemini (S E M P R E):

*
    1. **Consciência da Renderização:** Lembre-se que a interpretação interna do Markdown pelo
       Gemini pode diferir da visualização final no seu editor ou plataforma.
*
    2. **Clareza nos Pedidos:** Ser específico sobre o que é necessário. Se um exemplo de código for
       solicitado, sempre será em kotlin, android studio, bibliotecas e o problema a ser resolvido.
*
    3. **Blocos de Código:** Evitar comentários excessivos e
       desnecessários *dentro* do código gerado e principalmente ao final das linhas de código (
       inline), a menos que sejam cruciais para o entendimento imediato.
*
    4. **Idioma:** Comunicação em Português.
*
    5. **Nomenclatura no Código:** Preferência por nomes de variáveis, funções e classes em
       Português nos exemplos de código. Inglês apenas para o que é próprio da linguagem.
*
    6. **Gerenciamento de Dependências:** Ao sugerir bibliotecas, incluir a forma de adição via
       `libs.versions.toml`.
*
    7. **Formatação de Roteiros/Documentos:** Manter a formatação Markdown simples e clara em
       documentos como este para facilitar a leitura e edição.
*
    8. **Documentação de Código:** Sempre incluir um cabeçalho descritivo no início
       de cada arquivo Kotlin, conforme esse modelo:

```
       /*
        * Arquivo: com.marin.catfeina.dominio.Icones.kt
        * @project Catfeina
        * @description
        * Ponto de entrada principal da aplicação Catfeina.
        * Define a Activity principal e a estrutura da UI raiz com Scaffold, NavigationDrawer e TopAppBar.
        */
```

*
    9. **Gerar o conteúdo diário para o ChangeLog.md.
*
    10. **Usar apenas gradle.** Não sugerir groovy.
*
    11. **Eu vou fornecer o layout das telas. **
*
    12. **NÃO SEJA TEIMOSO E FAÇA O QUE EU PEÇO.**
*
    13. **Leia o código do projeto diretamente no IDE. Não fique pedindo para colar códigos que
        estão abertos na IDE. LEIA!**
*
    14. **FORNEÇA OS CÓDIGOS ATUALIZADOS, NUNCA DEPRECATEDS! VERIFIQUE ANTES DE SUGERIR PARA VER SE
        É CÓDIGO VELHO E NÃO O USE. NÃO SUGIRA GAMBIARRAS. MEU IDE E BIBLIOTECAS ESTÃO TODAS
        ATUALIZADAS.**
*
    15. **EU direi a sequencia do roteiro, não vocÊ.**
*
    16. **NÃO PRECISA ASSUMIR o que eu tenho ou não tenho nos arquivos de código. VOCE PODE LER O
        ARQUIVO NA IDE E SOLICITAR QUAL ARQUIVO VC QUER E
        EU ABRO. FAÇA USANDO MODELOS REAIS !!!!! E POUPE TRABALHO PRA MIM!!!! É PRA ISSO UE CRIEI
        VOCE!!**

---

## Estratégia de Formatação de Texto com Tags Customizadas

Para implementar a formatação de partes específicas de texto comum (sem HTML ou Markdown nativo)
dentro do aplicativo Catfeina, utilizaremos uma abordagem baseada em tags personalizadas processadas
diretamente no Jetpack Compose para gerar uma `AnnotatedString`.

## Resumo do Processo

1. **Definição de Tags Personalizadas no Texto Fonte:**
    * No campo de conteúdo (ex: `informativo.conteudo`), o texto será armazenado como texto comum,
      mas com tags simples e legíveis embutidas para indicar a formatação desejada.
    * Exemplos de tags:
        * `**textoEmNegrito**`
        * `*textoEmItalico*`
        * `__textoSublinhado__`
        * `##textoComCorPrimaria##`
        * (Outras tags podem ser definidas conforme a necessidade)

2. **Criação de um Parser/Processador em Jetpack Compose:**
    * Será desenvolvida uma função Composable (ex: `ProcessarTextoComTags`) que recebe a string de
      texto contendo as tags personalizadas como entrada.
    * A principal responsabilidade desta função é converter a string de entrada em um objeto
      `AnnotatedString` do Jetpack Compose.

3. **Mapeamento de Tags para Estilos (`SpanStyle`):**
    * Dentro da função de processamento, um mapa ou uma estrutura lógica (ex: `when` clause)
      associará cada tag personalizada (ex: `"**"`) a um objeto `SpanStyle` específico.
    * O `SpanStyle` define os atributos de formatação, como `fontWeight` (para negrito),
      `fontStyle` (para itálico), `textDecoration` (para sublinhado), `color`, `fontSize`,
      `fontFamily`, etc.
    * Os estilos devem, preferencialmente, ser derivados de `MaterialTheme.colorScheme` (para cores)
      e `MaterialTheme.typography` (para fontes e pesos) para garantir consistência com o tema geral
      do aplicativo (Claro/Escuro).

4. **Lógica de Parsing (Expressões Regulares):**
    * A abordagem recomendada para identificar e extrair as tags e seu conteúdo da string de texto é
      o uso de Expressões Regulares (Regex).
    * Uma Regex será construída para encontrar todas as ocorrências das tags definidas (ex:
      `\*\*(.*?)\*\*` para capturar o conteúdo dentro de `**...**`). O `(.*?)` é um grupo de captura
      não guloso.
    * A função `findAll` da Regex será utilizada para iterar sobre todas as correspondências no
      texto.

5. **Construção Dinâmica da `AnnotatedString`:**
    * O builder `buildAnnotatedString { ... }` será utilizado para construir a `AnnotatedString`
      final.
    * Durante a iteração sobre as correspondências da Regex:
        * O texto que precede uma tag será anexado (usando `append()`) sem nenhum estilo especial.
        * O conteúdo capturado *dentro* de uma tag será anexado usando
          `withStyle(style = estiloDaTag) { append(conteudoDaTag) }`, onde `estiloDaTag` é o
          `SpanStyle` correspondente obtido no passo 3.
        * Um cursor ou índice será mantido para rastrear a posição atual no texto de entrada,
          garantindo que todo o texto seja processado e que as partes corretas sejam estilizadas.
    * Qualquer texto restante após a última tag processada também será anexado.

6. **Exibição no Composable `Text`:**
    * A `AnnotatedString` resultante da função de processamento será passada diretamente para um
      Composable `Text` padrão para renderização na UI.

## Benefícios da Abordagem

* **Controle Total e Personalizado:** Definição completa sobre quais tags são suportadas e como elas
  se traduzem visualmente.
* **Nativo do Jetpack Compose:** Utiliza exclusivamente APIs do Compose (`AnnotatedString`,
  `SpanStyle`, `Text`), evitando a necessidade de `AndroidView` ou interoperabilidade com o sistema
  de Views tradicional para esta funcionalidade.
* **Tematização Consistente:** Facilidade em alinhar os estilos de formatação com o `MaterialTheme`
  do aplicativo, garantindo que a formatação se adapte aos temas Claro e Escuro.
* **Legibilidade na Fonte de Dados:** As tags personalizadas no texto fonte são projetadas para
  serem simples e manter a legibilidade do conteúdo original.
* **Extensibilidade:** O sistema pode ser facilmente estendido para suportar novas tags e estilos de
  formatação no futuro, modificando o mapa de tags para estilos e, se necessário, a Regex.

## Considerações e Limitações

* **Complexidade da Regex:** Para cenários de formatação com tags muito complexas (ex: atributos
  dentro das tags) ou aninhamento profundo, a Regex pode se tornar significativamente complexa de
  escrever e manter.
* **Aninhamento de Tags:** A abordagem com uma Regex simples, como a exemplificada, geralmente não
  lida bem com o aninhamento arbitrário de tags (ex: `**negrito e *itálico dentro***`). Lidar com
  aninhamento robusto pode requerer um parser mais sofisticado (ex: recursivo descendente). Para as
  necessidades iniciais do Catfeina (negrito, itálico, sublinhado, cor), o não aninhamento ou
  aninhamento muito simples deve ser suficiente.
* **Escape de Tags:** Se houver a necessidade de exibir os próprios caracteres das tags como texto
  literal (ex: mostrar `**` ao invés de aplicar negrito), um mecanismo de escape para as tags (ex:
  `\*\*`) precisaria ser implementado no parser.


## Sistema de Tags de Formatação Catfeina v1.1 (Sintaxe Unificada: { })

**Objetivo:** Permitir formatação rica e estruturada em textos dentro do aplicativo Catfeina,
processados para exibição com Jetpack Compose. A sintaxe unificada é `{palavraChave|conteúdo}`
para tags com conteúdo e `{palavraChave}` para tags de bloco sem conteúdo direto (como `linha`).

---

**A. FORMATOS INLINE (Aplicados dentro de um parágrafo de texto ou item de lista):**

1.  **Negrito**
    *   **Palavra-Chave:** `n`
    *   **Sintaxe:** `{n|texto em negrito}`
    *   **Processador:** `ProcessadorEstiloSimples`
    *   **Resultado no Parser:** `AplicacaoSpanStyle(textoOriginal = "texto em negrito", fontWeight = FontWeight.Bold)`
    *   **Compose (Efeito):** `fontWeight = FontWeight.Bold`

2.  **Itálico**
    *   **Palavra-Chave:** `i`
    *   **Sintaxe:** `{i|texto em itálico}`
    *   **Processador:** `ProcessadorEstiloSimples`
    *   **Resultado no Parser:** `AplicacaoSpanStyle(textoOriginal = "texto em itálico", fontStyle = FontStyle.Italic)`
    *   **Compose (Efeito):** `fontStyle = FontStyle.Italic`

3.  **Negrito e Itálico Combinados (Não Implementado Diretamente no Processador Atual)**
    *   **Palavra-Chave (Proposta):** `ni`
    *   **Sintaxe (Proposta):** `{ni|texto em negrito e itálico}`
    *   **Processador:** Exigiria adição de "ni" às `palavrasChave` do `ProcessadorEstiloSimples` e lógica para aplicar ambos os estilos.
    *   **Resultado no Parser (Esperado):** `AplicacaoSpanStyle(textoOriginal = "texto em negrito e itálico", fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)`
    *   **Compose (Efeito):** `fontWeight = FontWeight.Bold`, `fontStyle = FontStyle.Italic`
    *   **Status Atual:** Palavra-chave "ni" não está no `ProcessadorEstiloSimples.kt`.

4.  **Sublinhado**
    *   **Palavra-Chave:** `s`
    *   **Sintaxe:** `{s|texto sublinhado}`
    *   **Processador:** `ProcessadorEstiloSimples`
    *   **Resultado no Parser:** `AplicacaoSpanStyle(textoOriginal = "texto sublinhado", textDecoration = TextDecoration.Underline)`
    *   **Compose (Efeito):** `textDecoration = TextDecoration.Underline`

5.  **Highlight (Fundo Destacado)**
    *   **Palavra-Chave:** `d`
    *   **Sintaxe:** `{d|texto destacado}`
    *   **Processador:** `ProcessadorEstiloSimples`
    *   **Resultado no Parser:** `AplicacaoSpanStyle(textoOriginal = "texto destacado", isDestaque = true)`
    *   **Compose (Efeito):** `background = MaterialTheme.colorScheme.secondaryContainer` (ou similar, definido no renderer).

6.  **Tooltip (Definição/Nota Inline)**
    *   **Palavra-Chave:** `tooltip` (Proposta original era `def`, mas `ProcessadorTooltip` usa `tooltip`)
    *   **Sintaxe:** `{tooltip|Texto do Tooltip|Texto Anotado}`
    *   **Processador:** `ProcessadorTooltip`
    *   **Resultado no Parser:** `AplicacaoAnotacaoTooltip(textoOriginal = "Texto Anotado", textoTooltip = "Texto do Tooltip")`
    *   **Compose (Efeito):** Renderiza "Texto Anotado" com indicador. Interação mostra tooltip com "Texto do Tooltip".

7.  **Link (Clicável Inline)**
    *   **Palavras-Chave:** `link`, `url`
    *   **Sintaxe:** `{link|URL_COMPLETA|Texto Visível do Link}`
    *   **Processador:** `ProcessadorLink`
    *   **Resultado no Parser:** `AplicacaoAnotacaoLink(textoOriginal = "Texto Visível do Link", url = "URL_COMPLETA")`
    *   **Compose (Efeito):** Texto visível com estilo de link, usando `StringAnnotation` e `ClickableText`.

---

**B. ELEMENTOS DE BLOCO (Estruturais):**

1.  **Cabeçalhos (Nível 1-6)**
    *   **Palavras-Chave:** `t1`, `t2`, `t3`, `t4`, `t5`, `t6`
    *   **Sintaxe:** `{t1|Texto do Título Principal}`
    *   **Processador:** `ProcessadorCabecalho`
    *   **Resultado no Parser:** `ElementoConteudo.Cabecalho(nivel = 1, texto = "Texto do Título Principal")` (o nível é inferido da palavra-chave)
    *   **Compose (Efeito):** `Text(style = MaterialTheme.typography.h1)` (ou `h2`, `h3`, etc.)

2.  **Imagem (Local)**
    *   **Palavra-Chave:** `imagem`
    *   **Sintaxe:** `{imagem|nome_do_arquivo.webp|Texto alternativo para acessibilidade}`
        *   (Caminho base para `nome_do_arquivo.webp` é `assets/catfeina/informativos/`)
    *   **Processador:** `ProcessadorImagem`
    *   **Resultado no Parser:** `ElementoConteudo.Imagem(nomeArquivo = "nome_do_arquivo.webp", textoAlternativo = "Texto alternativo...")`
    *   **Compose (Efeito):** Carrega e exibe a imagem do diretório de assets.

3.  **Citação (Blockquote)**
    *   **Palavra-Chave:** `cit`
    *   **Sintaxe:** `{cit|O texto completo da citação...}`
    *   **Processador:** `ProcessadorCitacao`
    *   **Resultado no Parser:** `ElementoConteudo.Citacao(texto = "O texto completo da citação...")`
    *   **Compose (Efeito):** Bloco com decoração específica (ex: barra lateral, fonte diferente) e texto estilizado.

4.  **Item de Lista (Desordenada/Ordenada - tipo definido pelo renderer ou tag separada)**
    *   **Palavra-Chave:** `item` (para item genérico)
    *   **Sintaxe:** `{item|Texto do item da lista}`
    *   **Processador:** `ProcessadorItemLista`
    *   **Resultado no Parser:** `ElementoConteudo.ItemLista(textoItem = "Texto do item da lista", aplicacoesEmLinha = emptyList())`
    *   **Compose (Efeito):** Marcador (`•` ou número) e texto.
        *   _Nota sobre Lista Ordenada:_ A proposta original tinha `{num|...}`. Atualmente, o `ProcessadorItemLista` usa apenas `item`. Para distinguir listas ordenadas de desordenadas, precisaríamos de uma tag diferente (ex: `num`) com seu processador, ou uma forma do renderer agrupar itens e aplicar numeração se eles estiverem dentro de um bloco de "lista ordenada" (não definido atualmente). Por ora, `{item|...}` cria um item de lista genérico. A formatação interna do item segue as mesmas regras (sub-parsing ou aplicações em linha).

5.  **Regra Horizontal (Divisor)**
    *   **Palavra-Chave:** `linha`
    *   **Sintaxe:** `{linha}` (sem conteúdo interno)
    *   **Processador:** `ProcessadorLinhaHorizontal`
    *   **Resultado no Parser:** `ElementoConteudo.LinhaHorizontal`
    *   **Compose (Efeito):** `HorizontalDivider()`

---

**Decisões Chave Refletidas:**

*   **Sintaxe Unificada:** `{palavraChave|conteúdo}` ou `{palavraChave}`.
*   **Processadores Implementados:** As tags listadas refletem os processadores existentes e suas palavras-chave.
*   **Negrito/Itálico (ni):** Marcado como "Não Implementado Diretamente" pois a tag `ni` não está no `ProcessadorEstiloSimples` atual.
*   **Tooltip:** Usa a palavra-chave `tooltip` do `ProcessadorTooltip` (a proposta original `def` foi ajustada).
*   **Itens de Lista:** `item` é a única tag implementada. A distinção entre ordenada/desordenada precisaria de mais trabalho (nova tag/processador ou lógica de renderização).
*   Imagens locais de `assets/catfeina/` (ou caminho base configurável).
*   Escape de `{` ou `}` não foi abordado; se necessário, exigiria lógica adicional no parser.
*   Espaços dentro do conteúdo das tags são geralmente preservados pelos processadores (ex: `trim()` é usado após `split`, mas o conteúdo em si não é trimado excessivamente antes do `split`).

