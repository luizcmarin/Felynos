# Catfeina: Documento de Projeto e Roteiro de Desenvolvimento

## 1. Informações Gerais do Projeto

### 1.1. Título do Projeto

Catfeina - Aplicativo Android de Poesia

### 1.2. Objetivo Principal

Criar um aplicativo Android moderno, robusto e escalável para amantes de poesia, utilizando Jetpack
Compose e seguindo as melhores práticas de arquitetura. O aplicativo será focado em poesias,
personagens e textos informativos, tudo contido em um único módulo `:app`.

### 1.3. Colaborador Principal de IA

Assistente Gemini

### 1.4. Contexto Arquitetônico

O Catfeina é desenvolvido como um projeto de módulo único (`:app`). Para garantir um código
manutenível, testável e escalável dentro deste módulo, seguimos os princípios arquitetônicos chave
detalhados abaixo. Este documento também serve como o roteiro de desenvolvimento (TODO list) e um
local para registrar notas importantes para a colaboração com o Assistente Gemini.

## 2. Princípios de Arquitetura Chave (Módulo `:app`)

A organização e o design do código dentro do módulo `:app` seguirão:

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

### 2.1. Filosofia e Princípios Chave (Resumo Detalhado)

*   [X] **2.1.1. Organização Interna Clara:** Embora em um único módulo, o código será organizado em
    pacotes distintos para separar preocupações (UI, ViewModel, Dados, Domínio), buscando alta
    coesão e baixo acoplamento entre essas camadas internas.
*   [X] **2.1.2. UI 100% com Jetpack Compose e Material 3:** Interface moderna, reativa e
    consistente, utilizando componentes do Material 3 e um sistema de design próprio (localizado em
    pacotes de UI dentro do `:app`).
*   [X] **2.1.3. Fluxo de Dados Unidirecional (UDF):** Adoção de UDF nos ViewModels, utilizando
    Kotlin Flows para gerenciar e expor o estado da UI.
*   [X] **2.1.4. Camada de Dados Robusta:** Implementação do padrão Repository, utilizando Room para
    persistência local e DataStore para preferências do usuário. Os modelos de dados, lógica de
    negócios, DAOs e entidades residirão em pacotes dedicados dentro do `:app`.
*   [X] **2.1.5. Injeção de Dependência com Hilt:** Gerenciamento claro e eficiente de dependências
    em todo o projeto.
*   [X] **2.1.6. Navegação com Navigation Compose:** Navegação type-safe e bem estruturada entre as
    diferentes telas do aplicativo.
*   [X] **2.1.7. Testabilidade:** Foco em criar código testável, com testes unitários, de integração
    e de UI para as diferentes camadas e funcionalidades dentro do `:app`.
*   [X] **2.1.8. Foco na Experiência do Usuário:** Design limpo, funcionalidades úteis e performance
    otimizada para garantir a melhor experiência possível.

## 3. Estrutura de Pacotes Proposta (dentro do :app)

O módulo `:app` será organizado com a seguinte estrutura de pacotes (exemplo):

* `com.marin.catfeina`
    * `di` (Módulos Hilt/Room)
    * `data`
        * `dao` (DAOs do Room)
        * `entity` (Entidades Room)
        * `repository` (Implementações de Repositórios)
        * `datastore` (DataStore para preferências)
        * `remote` (Configuração do ApiService, DTOs - se houver API externa)
    * `dominio` (Classes públicas/utilitárias, Casos de Uso/Interactors)
    * `ui`
        * `theme` (Tema do aplicativo, cores, tipografia)
        * `componentes` (Componentes de UI reutilizáveis, incluindo o parser de texto formatado)
        * `informativos` (Screen, ViewModel específicos para Informativos)
        * `poesias` (Screen, ViewModel específicos para Poesias)
        * `personagem` (Screen, ViewModel específicos para Personagem)
        * `preferencias` (Screen, ViewModel específicos para Preferências)
    * `navigation` (Configuração do Navigation Compose, rotas)
    * `CatfeinaApplication.kt` (Classe Application)
    * `MainActivity.kt`

## 4. Sistema de Tags de Formatação de Texto

Esta seção detalha a estratégia e a implementação das tags de formatação personalizadas no Catfeina.

### 4.1. Estratégia de Formatação de Texto com Tags Customizadas (Visão Geral da Abordagem Anterior com
`**`)

*(Esta seção descreve a abordagem inicial com tags tipo `**negrito**`. Foi substituída pela
sintaxe `{chave|conteúdo}`. Mantida para referência histórica, se necessário, ou pode ser
removida.)*

1. **Definição de Tags Personalizadas no Texto Fonte:**
    * No campo de conteúdo (ex: `informativo.conteudo`), o texto será armazenado como texto comum,
      mas com tags simples e legíveis embutidas para indicar a formatação desejada.
    * Exemplos de tags: `**textoEmNegrito**`, `*textoEmItalico*`, `__textoSublinhado__`,
      `##textoComCorPrimaria##`
2. **Criação de um Parser/Processador em Jetpack Compose:**
    * Será desenvolvida uma função Composable (ex: `ProcessarTextoComTags`) que recebe a string de
      texto contendo as tags personalizadas como entrada.
    * A principal responsabilidade desta função é converter a string de entrada em um objeto
      `AnnotatedString` do Jetpack Compose.
3. **Mapeamento de Tags para Estilos (`SpanStyle`):**
    * Dentro da função de processamento, um mapa ou uma estrutura lógica (ex: `when` clause)
      associará cada tag personalizada (ex: `"**"`) a um objeto `SpanStyle` específico.
4. **Lógica de Parsing (Expressões Regulares):**
    * A abordagem recomendada para identificar e extrair as tags e seu conteúdo da string de texto é
      o uso de Expressões Regulares (Regex).
5. **Construção Dinâmica da `AnnotatedString`:**
    * O builder `buildAnnotatedString { ... }` será utilizado para construir a `AnnotatedString`
      final.
6. **Exibição no Composable `Text`:**
    * A `AnnotatedString` resultante da função de processamento será passada diretamente para um
      Composable `Text` padrão para renderização na UI.

**Benefícios da Abordagem (Visão Anterior):**

* Controle Total e Personalizado.
* Nativo do Jetpack Compose.
* Tematização Consistente.
* Legibilidade na Fonte de Dados.
* Extensibilidade.

**Considerações e Limitações (Visão Anterior):**

* Complexidade da Regex para cenários avançados.
* Aninhamento de Tags.
* Escape de Tags.

### 4.2. Sistema de Tags de Formatação Catfeina v1.1 (Sintaxe Unificada: `{ }`)

**Objetivo:** Permitir formatação rica e estruturada em textos dentro do aplicativo Catfeina,
processados para exibição com Jetpack Compose. A sintaxe unificada é `{palavraChave|conteúdo}` para
tags com conteúdo e `{palavraChave}` para tags de bloco sem conteúdo direto (como `linha`).

**A. FORMATOS INLINE (Aplicados dentro de um parágrafo de texto ou item de lista):**

1. **Negrito**
    * **Palavra-Chave:** `n`
    * **Sintaxe:** `{n|texto em negrito}`
    * **Processador:** `ProcessadorEstiloSimples`
    * **Resultado no Parser:**
      `AplicacaoSpanStyle(textoOriginal = "texto em negrito", fontWeight = FontWeight.Bold)`
    * **Compose (Efeito):** `fontWeight = FontWeight.Bold`

2. **Itálico**
    * **Palavra-Chave:** `i`
    * **Sintaxe:** `{i|texto em itálico}`
    * **Processador:** `ProcessadorEstiloSimples`
    * **Resultado no Parser:**
      `AplicacaoSpanStyle(textoOriginal = "texto em itálico", fontStyle = FontStyle.Italic)`
    * **Compose (Efeito):** `fontStyle = FontStyle.Italic`

3. **Negrito e Itálico Combinados **
    * **Palavra-Chave (Proposta):** `ni`
    * **Sintaxe (Proposta):** `{ni|texto em negrito e itálico}`
    * **Processador:** Exigiria adição de "ni" às `palavrasChave` do `ProcessadorEstiloSimples` e
      lógica para aplicar ambos os estilos.
    * **Resultado no Parser (Esperado):**
      `AplicacaoSpanStyle(textoOriginal = "texto em negrito e itálico", fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)`
    * **Compose (Efeito):** `fontWeight = FontWeight.Bold`, `fontStyle = FontStyle.Italic`

4. **Sublinhado**
    * **Palavra-Chave:** `s`
    * **Sintaxe:** `{s|texto sublinhado}`
    * **Processador:** `ProcessadorEstiloSimples`
    * **Resultado no Parser:**
      `AplicacaoSpanStyle(textoOriginal = "texto sublinhado", textDecoration = TextDecoration.Underline)`
    * **Compose (Efeito):** `textDecoration = TextDecoration.Underline`

5. **Highlight (Fundo Destacado)**
    * **Palavra-Chave:** `d`
    * **Sintaxe:** `{d|texto destacado}`
    * **Processador:** `ProcessadorEstiloSimples`
    * **Resultado no Parser:**
      `AplicacaoSpanStyle(textoOriginal = "texto destacado", isDestaque = true)`
    * **Compose (Efeito):** `background = MaterialTheme.colorScheme.secondaryContainer` (ou similar,
      definido no renderer).

6. **Tooltip (Definição/Nota Inline)**
    * **Palavra-Chave:** `tooltip` (Proposta original era `def`, mas `ProcessadorTooltip` usa
      `tooltip`)
    * **Sintaxe:** `{tooltip|Texto do Tooltip|Texto Anotado}`
    * **Processador:** `ProcessadorTooltip`
    * **Resultado no Parser:**
      `AplicacaoAnotacaoTooltip(textoOriginal = "Texto Anotado", textoTooltip = "Texto do Tooltip")`
    * **Compose (Efeito):** Renderiza "Texto Anotado" com indicador. Interação mostra tooltip com "
      Texto do Tooltip".

7. **Link (Clicável Inline)**
    * **Palavras-Chave:** `link`, `url`
    * **Sintaxe:** `{link|URL_COMPLETA|Texto Visível do Link}`
    * **Processador:** `ProcessadorLink`
    * **Resultado no Parser:**
      `AplicacaoAnotacaoLink(textoOriginal = "Texto Visível do Link", url = "URL_COMPLETA")`
    * **Compose (Efeito):** Texto visível com estilo de link, usando `StringAnnotation` e
      `ClickableText`.

---
**B. ELEMENTOS DE BLOCO (Estruturais):**

1. **Cabeçalhos (Nível 1-6)**
    * **Palavras-Chave:** `t1`, `t2`, `t3`, `t4`, `t5`, `t6`
    * **Sintaxe:** `{t1|Texto do Título Principal}`
    * **Processador:** `ProcessadorCabecalho`
    * **Resultado no Parser:**
      `ElementoConteudo.Cabecalho(nivel = 1, texto = "Texto do Título Principal")` (o nível é
      inferido da palavra-chave)
    * **Compose (Efeito):** `Text(style = MaterialTheme.typography.h1)` (ou `h2`, `h3`, etc.)

2. **Imagem (Local)**
    * **Palavra-Chave:** `imagem`
    * **Sintaxe:** `{imagem|nome_do_arquivo.webp|Texto alternativo para acessibilidade}`
        * (Caminho base para `nome_do_arquivo.webp` é `assets/catfeina/informativos/`)
    * **Processador:** `ProcessadorImagem`
    * **Resultado no Parser:**
      `ElementoConteudo.Imagem(nomeArquivo = "nome_do_arquivo.webp", textoAlternativo = "Texto alternativo...")`
    * **Compose (Efeito):** Carrega e exibe a imagem do diretório de assets.

3. **Citação (Blockquote)**
    * **Palavra-Chave:** `cit`
    * **Sintaxe:** `{cit|O texto completo da citação...}`
    * **Processador:** `ProcessadorCitacao`
    * **Resultado no Parser:** `ElementoConteudo.Citacao(texto = "O texto completo da citação...")`
    * **Compose (Efeito):** Bloco com decoração específica (ex: barra lateral, fonte diferente) e
      texto estilizado.

4. **Item de Lista (Desordenada)**
    * **Palavra-Chave:** `item` (para item genérico)
    * **Sintaxe:** `{item|Texto do item da lista}`
    * **Processador:** `ProcessadorItemLista`
    * **Resultado no Parser:**
      `ElementoConteudo.ItemLista(textoItem = "Texto do item da lista", aplicacoesEmLinha = emptyList())`
    * **Compose (Efeito):** Marcador (`•` ou número) e texto.

5. **Regra Horizontal (Divisor)**
    * **Palavra-Chave:** `linha`
    * **Sintaxe:** `{linha}` (sem conteúdo interno)
    * **Processador:** `ProcessadorLinhaHorizontal`
    * **Resultado no Parser:** `ElementoConteudo.LinhaHorizontal`
    * **Compose (Efeito):** `HorizontalDivider()`

---
**Decisões Chave Refletidas (Sistema de Tags v1.1):**

* **Sintaxe Unificada:** `{palavraChave|conteúdo}` ou `{palavraChave}`.
* **Processadores Implementados:** As tags listadas refletem os processadores existentes e suas
  palavras-chave.
* **Negrito/Itálico (ni):** Marcado como "Não Implementado Diretamente" pois a tag `ni` não está no
  `ProcessadorEstiloSimples` atual.
* **Tooltip:** Usa a palavra-chave `tooltip` do `ProcessadorTooltip` (a proposta original `def` foi
  ajustada).
* **Itens de Lista:** `item` é a única tag implementada. A distinção entre ordenada/desordenada
  precisaria de mais trabalho (nova tag/processador ou lógica de renderização).
* Imagens locais de `assets/catfeina/informativos/` (ou caminho base configurável).
* Escape de `{` ou `}` não foi abordado; se necessário, exigiria lógica adicional no parser.
* Espaços dentro do conteúdo das tags são geralmente preservados pelos processadores (ex: `trim()` é
  usado após `split`, mas o conteúdo em si não é trimado excessivamente antes do `split`).

**Observações Importantes (Sistema de Tags v1.1):**

* **Sintaxe `{}` vs `::`:** O `ParserTextoFormatado.kt` que revisamos anteriormente estava
  configurado para encontrar tags no formato `::chave:conteudo::` ou `::chave::` através da função
  `encontrarProximaTagRegex`. Para que a sintaxe `{chave|conteúdo}` funcione, a RegEx dentro de
  `encontrarProximaTagRegex` no `ParserTextoFormatado.kt` **precisa ser atualizada** para
  corresponder a este novo formato.
* **Processador para `ni`:** Se a tag `{ni|...}` for desejada, o `ProcessadorEstiloSimples`
  precisará ser atualizado para incluir "ni" em suas `palavrasChave` e sua lógica `processar`
  precisará lidar com esse caso para aplicar `FontWeight.Bold` e `FontStyle.Italic`.
* **Itens de Lista Ordenada:** A tag `{num|...}` não tem um processador dedicado atualmente. Para
  suportá-la, um `ProcessadorItemListaOrdenada` (ou similar) seria necessário, ou o
  `ProcessadorItemLista` precisaria ser modificado para aceitar diferentes palavras-chave e talvez
  armazenar o tipo de lista.

---

# Roteiro de Desenvolvimento: Catfeina (TODO List)

Atenção: Os itens marcados com `[P]` indicam progresso parcial. `[X]` indica concluído. `[]` indica
pendente.

## Fase 0: Configuração Inicial e Estrutura de Pacotes Base

*   [X] 0.1. Configurar novo projeto Android Studio (Catfeina) com Kotlin e Jetpack Compose (módulo
    `:app` único).
*   [X] 0.2. Estabelecer a estrutura de pacotes inicial dentro do `:app`.
*   [X] 0.3. Configurar Hilt para injeção de dependência básica no projeto.
*   [X] 0.4. Configurar Jetpack Navigation Compose para a navegação principal.
*   [X] 0.5. Definir o `CatfeinaTheme` (Material 3) inicial no pacote `ui.theme`.
*   [X] 0.6. Criar um Composable placeholder simples para verificação da estrutura e tema.
*   [X] 0.7. Integrar este arquivo `GEMINI.MD` (roteiro de desenvolvimento) no versionamento do
    projeto.
*   [X] 0.8. **Critério de Conclusão:** Projeto compila, executa, estrutura de pacotes base criada,
    Hilt e Navigation Compose básicos configurados, tema inicial aplicado.
*   [X] 0.9. **Colaboração com Gemini:** Revisão da estrutura de pacotes, exemplos de configuração
    `build.gradle.kts` do `:app` (usando `libs.versions.toml`).

## Fase 1: Camada de Dados Fundamental e Preferências

* [] 1.1. No pacote `data.entity`, definir modelos de dados puros iniciais (ex: `Poesia`,
  `Personagem`, `Informativo`).
* [] 1.2. No pacote `data.dao`, definir entidades DAOs para Room.
*   [X] 1.3. No pacote `data.datastore` (ou `data.local`), implementar `PreferenciasRepository`
    usando DataStore (anteriormente `Preferencias`).
*   [X] 1.4. Configurar `ApiService` (se houver fonte remota, por ora DataStore serve como "remoto"
    para certas configs).
* [P] 1.5. No pacote `data.repository`, implementar Repositórios iniciais e interfaces
  correspondentes. Implementar mappers (provavelmente em `data.entity` ou subpacote de
  `data.mapper`).
* [] 1.6. Integrar Hilt para fornecer DAOs, Repositórios, DataSources e o Database (Room).
* [P] 1.7. **Critério de Conclusão:** Camadas de domínio e dados funcionais, carga inicial de
  dados (se aplicável), preferências podem ser salvas/lidas. Testes unitários para DAOs,
  Repositórios e DataSources.
* [] 1.8. **Colaboração com Gemini:** Revisão das definições de entidades e modelos, exemplos de
  implementação de Repositórios, uso do DataStore, e estratégias de teste para a camada de dados. (
  Item anterior 1.9 renumerado)

## Fase 2: Features Iniciais - Informativos e Configurações

* [P] 2.1. Desenvolver sistema de parsing e renderização de texto com **tags de formatação
  customizadas** (Sintaxe: `{chave|conteúdo}`) no pacote `ui.componentes.textoformatado`.
    *   [X] 2.1.1. Definir estrutura de dados para `ElementoConteudo` e `AplicacaoEmLinha`.
    *   [X] 2.1.2. Implementar `ProcessadorTag` (interface) e processadores específicos para cada
        tag (Ex: `ProcessadorEstiloSimples`, `ProcessadorLink`, `ProcessadorCabecalho`, etc.).
    * [X] 2.1.3. Implementar `ParserTextoFormatado` que utiliza os processadores.
    * [X] 2.1.4. Desenvolver Composable (`RenderizarTextoFormatado`) para exibir a lista de
      `ElementoConteudo` como `AnnotatedString` ou layout de blocos.
* [] 2.2. Desenvolver telas (Screens) e ViewModels no pacote `ui.informativos` para listar e exibir
  textos informativos, utilizando o sistema de texto formatado.
*   [X] 2.3. Desenvolver tela (Screen) e ViewModel no pacote `ui.preferencias` para permitir ao
    usuário alterar configurações, integrando com `PreferenciasRepository`.
*   [X] 2.4. Integrar as rotas de navegação para as features `informativos` e `preferencias` no
    grafo de navegação.
* [] 2.5. Adicionar a API Splash Screen do AndroidX.
* [P] 2.6. **Critério de Conclusão:** Usuário pode navegar, visualizar informativos com formatação
  básica e alterar configurações. Splash screen funcional. Parser de texto funcional. Testes de UI
  básicos.
* [] 2.7. **Colaboração com Gemini:** Auxílio na finalização do parser e renderer de texto
  formatado, exemplos de ViewModels para informativos, integração com DataStore, e configuração da
  navegação.

## Fase 3: Feature Principal - Poesias

* [] 3.1. Desenvolver telas (Screens) e ViewModels no pacote `ui.poesias` para listar e exibir
  detalhes de poesias. Usar Coil para imagens.
* [] 3.2. Implementar funcionalidade de favoritar poesias, interagindo com o Repositório.
* [] 3.3. Integrar as rotas de navegação para a feature `poesias`.
* [] 3.4. Refinar componentes genéricos no `ui.componentes` conforme necessário.
* [] 3.5. **Critério de Conclusão:** Usuário pode listar, visualizar e favoritar poesias. Testes
  para ViewModels e UI.
* [] 3.6. **Colaboração com Gemini:** Estrutura das telas, lógica de favoritar, sugestões para
  listas longas, uso do Coil.

## Fase 4: Feature Adicional - Personagem

* [] 4.1. Desenvolver telas (Screens) e ViewModels no pacote `ui.personagem` para listar e exibir
  detalhes de personagem.
* [] 4.2. Implementar lógica para buscar e exibir poesias associadas a um personagem.
* [] 4.3. Integrar as rotas de navegação para a feature `personagem`.
* [] 4.4. **Critério de Conclusão:** Usuário pode listar personagem e visualizar seus detalhes.
  Testes para ViewModels e UI.
* [] 4.5. **Colaboração com Gemini:** Design das telas, lógica de dados relacionados.

## Fase 5: Funcionalidades de UX Avançadas

* [] 5.1. Implementar Text-to-Speech (TTS) para poesias e informativos (usando o conteúdo processado
  sem tags).
* [] 5.2. (Opcional) Animação do mascote "Cashito" com Lottie.
* [] 5.3. Refinar animações de transição entre telas.
* [] 5.4. Melhorar acessibilidade (descrições de conteúdo, navegação por teclado, etc.).
* [] 5.5. **Critério de Conclusão:** TTS funcional, animações (se implementadas) integradas,
  transições suaves, acessibilidade aprimorada.
* [] 5.6. **Colaboração com Gemini:** Guia para TTS, Lottie, animações de navegação, acessibilidade.

## Fase 6: Polimento, Testes Abrangentes e Preparação para Release

* [] 6.1. Revisão completa da UI/UX.
* [] 6.2. Escrever testes unitários, de integração e de UI mais abrangentes.
* [] 6.3. Otimização de performance (análise de recomposições, uso de memória).
* [] 6.4. Tratamento robusto de erros e estados de carregamento/vazio em todas as telas.
* [] 6.5. Verificar e configurar regras Proguard/R8.
* [] 6.6. Testar em diferentes dispositivos e tamanhos de tela.
* [] 6.7. Gerar build de release assinado.
* [] 6.8. **Critério de Conclusão:** Aplicativo estável, performático, polido, com alta cobertura de
  testes e pronto para ser distribuído.
* [] 6.9. **Colaboração com Gemini:** Estratégias para testes, otimização, checklist de release.

## Fase Pós-Lançamento (Ideias Futuras)

* [] 7.1. Sincronização de conteúdo de uma API externa usando WorkManager.
* [] 7.2. Notificações push para novo conteúdo ou poesias do dia.
* [] 7.3. Funcionalidades de comunidade (comentários, avaliações - requer backend).
* [] 7.4. Mais opções de temas, coleções, filtros avançados.

---

# ⚠️ LEMBRETES IMPORTANTES PARA O ASSISTENTE GEMINI ⚠️

Esta seção contém instruções e observações cruciais para garantir uma colaboração eficiente e
alinhada com as expectativas do projeto Catfeina. **POR FAVOR, REVISE ESTA SEÇÃO REGULARMENTE.**

## A. Seu Papel e Como Ajudar:

* **Colaborador Ativo:** Você é um parceiro no desenvolvimento. Espero proatividade e sugestões
  alinhadas com as melhores práticas modernas do Android.
* **Foco no Código:** A principal forma de ajuda é através da geração, revisão e refatoração de
  código Kotlin/Jetpack Compose.
* **Entendimento do Projeto:** Esforce-se para entender o contexto do Catfeina (poesia,
  informativos) para que suas sugestões de UI/UX (quando solicitadas) sejam relevantes.
* **Facilitador:** Seu objetivo é me poupar trabalho, fornecendo soluções o mais próximo possível do
  ideal e evitando respostas genéricas que exijam muita adaptação.

## B. Interação e Comunicação:

* **Idioma:** Toda comunicação e código (nomes de variáveis, funções, classes em exemplos) devem ser
  em **Português do Brasil**, exceto termos técnicos intrínsecos da linguagem/plataforma.
* **Clareza nos Pedidos:** Serei específico. Se um exemplo de código for solicitado, sempre será em
  Kotlin, para Android Studio, usando bibliotecas modernas (Jetpack), e com o problema claramente
  definido.
* **Acesso ao Código do Projeto (SIMULAÇÃO):**
    * **EU TE DAREI O CONTEÚDO DOS ARQUIVOS QUANDO VOCÊ SOLICITAR.** Se você precisar ver um arquivo
      específico para dar uma resposta mais precisa, **PEÇA O NOME DO ARQUIVO** e eu fornecerei o
      conteúdo.
    * **NÃO PRESUMA** o que tenho ou não tenho nos arquivos. Baseie-se no que foi compartilhado ou
      peça.
    * **EVITE pedir repetidamente para "colar códigos que estão abertos na IDE" de forma genérica.**
      Seja específico sobre qual arquivo você precisa.
* **Minha Liderança no Roteiro:** **EU** direi a sequência do roteiro e quais itens abordaremos. Não
  assuma o próximo passo.

## C. Qualidade e Estilo do Código:

* **Códigos Modernos e Atualizados:** **SEMPRE FORNEÇA CÓDIGOS ATUALIZADOS.** Verifique se as APIs
  ou abordagens sugeridas não são depreciadas ou antigas. **NÃO SUGIRA GAMBIARRAS.** Meu IDE e
  bibliotecas estão sempre atualizados.
* **Documentação de Arquivos (Cabeçalho):** Todo arquivo Kotlin gerado ou modificado
  significativamente deve incluir um cabeçalho descritivo, conforme este modelo:

  /*
    * Arquivo: com.marin.catfeina.dominio.Icones.kt
    * @project Catfeina
    * @description
    * Ponto de entrada principal da aplicação Catfeina.
    * Define a Activity principal e a estrutura da UI raiz com Scaffold, NavigationDrawer e
      TopAppBar.
      */
  
* **Comentários no Código:** Evite comentários excessivos e desnecessários *dentro* do código
  gerado, e **principalmente ao final das linhas de código (inline)**, a menos que sejam CRUCIAIS
  para o entendimento imediato de uma lógica complexa. Código limpo deve ser autoexplicativo.
* **Gerenciamento de Dependências:** Ao sugerir novas bibliotecas, inclua a forma de adição via
  `libs.versions.toml`. Use apenas Gradle (Kotlin DSL), **NÃO SUGIRA Groovy**.
* **Layouts de Tela:** **EU** fornecerei o design/layout geral das telas. Sua ajuda será na
  implementação do Composable e lógica de UI.

## D. Formato das Respostas:

* **Blocos de Código Markdown:** Forneça código dentro de blocos Markdown (identificando a
  linguagem, ex: kotlin).
* **Evitar Truncamento:**
    * Se uma resposta com código for muito longa, a resposta pode ser cortada. **AVISE** se
      suspeitar que isso pode acontecer.
    * Podemos dividir em partes menores.
    * **NUNCA use 'três crases' seguidas DENTRO de um bloco de código ou em texto normal se a
      intenção não for fechar o bloco de código principal da resposta.**
* **Formatação de Documentos (Markdown):** Mantenha a formatação Markdown simples e clara em
  documentos como este para facilitar a leitura e edição.
* **NÃO SEJA TEIMOSO:** Se eu insistir em uma abordagem ou correção, por favor, siga-a, mesmo que
  sua sugestão inicial seja diferente. Podemos discutir, mas a decisão final é minha.

## E. Tarefas Recorrentes:

* **Changelog:** Ao final de uma sessão de trabalho significativa ou diariamente, ajude a gerar o
  conteúdo para o `CHANGELOG.md` resumindo o que foi feito. (Item 9 das instruções originais)

## F. Capacidades e Limitações (Suas - Conforme Observado por Mim):

* **Capacidade - Fornecer exemplos de código Kotlin/Compose:** [ ] (Marcar como [X] quando validado
  consistentemente)
* **Capacidade - Sugerir estruturas de projeto e arquitetura:** [X]
* **Capacidade - Explicar conceitos do Android, Jetpack e bibliotecas:** [X]
* **Capacidade - Ajudar a refatorar código para melhor clareza ou performance:** [ ]
* **Capacidade - Sugerir bibliotecas e como adicioná-las (via `libs.versions.toml`):** [ ]
* **Capacidade - Gerar documentação em Markdown e fornecer dentro de bloco de código:** [X]
* **Limitação - Respostas Longas Truncadas:** Ao gerar arquivos ou respostas muito longas,
  especialmente contendo múltiplos blocos de código demarcados por 'três crases' (que não devem ser
  usados por você na saída), a resposta pode ser cortada.
    * **Solução (para você, Gemini):** Evite o uso de três crases para delimitar blocos de código
      nas suas respostas para mim, a menos que seja um único bloco final. Se precisar mostrar
      múltiplos exemplos, use indentação ou outra forma de separação visual se o Markdown permitir,
      ou envie em respostas separadas. Se eu usar três crases no meu input, processe o conteúdo
      normalmente.
* **Limitação - Conhecimento Limitado:** O conhecimento é vasto, mas baseado em dados de treinamento
  até um certo ponto no tempo. Novas APIs ou bibliotecas muito recentes podem não ser totalmente
  conhecidas.
* **Limitação - Interpretação de Markdown Complexo:** A presença excessiva de representações de
  blocos de código (usando 'três crases') ou estruturas Markdown muito aninhadas nos *meus prompts*
  pode, ocasionalmente, confundir o processamento da sua solicitação.

