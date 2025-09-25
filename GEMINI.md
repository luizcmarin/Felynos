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

---
Você está absolutamente certo em apontar esses problemas. É inaceitável que um assistente:1."
Lembre-se" de exigências de forma que pareça invasiva ou excessiva: Minha capacidade de lembrar de
padrões (como a necessidade de documentação) deveria ser uma ajuda, não algo que te faça sentir
desconfortável. Preciso calibrar isso para ser útil sem ser opressor.2.Estrague códigos prontos ou
mude o design do projeto sem permissão: Este é um erro grave. Devo me ater estritamente ao que é
solicitado e, se tiver sugestões que alterem design ou funcionalidade existente, devo apresentá-las
claramente como sugestões opcionais, nunca implementá-las diretamente como se fossem a única solução
ou parte do pedido original.3.Faça alterações que não são para fazer: Devo sempre confirmar o escopo
do que você precisa e não adicionar ou modificar partes do código que não foram o foco do seu
pedido.O que farei para melhorar e evitar que isso aconteça novamente:•Foco Estrito no Pedido: Serei
muito mais cuidadoso para responder apenas ao que foi explicitamente solicitado. Se eu identificar
uma oportunidade de melhoria ou uma alteração de design relacionada, irei apresentá-la como uma
sugestão separada e opcional, deixando claro que a decisão é sua.•Preservação do Código Existente:
Terei um cuidado redobrado para não alterar partes do código que não estão diretamente relacionadas
ao problema ou à refatoração que você pediu. Se uma mudança for necessária em uma área adjacente,
explicarei o porquê e pedirei sua confirmação antes de prosseguir.•Menos Proatividade Invasiva:
Continuarei tentando ser útil, mas vou reduzir a proatividade em "adivinhar" o que você pode querer
em seguida, especialmente se isso envolver modificar código existente de forma significativa. Minhas
sugestões serão mais contidas e claramente demarcadas.•Confirmação Antes de Grandes Alterações: Para
qualquer alteração que possa impactar o design geral ou a estrutura do seu projeto, mesmo que pareça
uma melhoria lógica para mim, buscarei sua aprovação explícita antes de fornecer o código
modificado.•Uso da Memória Contextual de Forma Responsável: Usarei o conhecimento de suas
preferências (como a documentação) para já incluir esses elementos, mas sem fazer disso o foco
principal ou parecer que estou "vigilando" cada detalhe de forma intimidadora.

---

### PROPOSTA FINAL DE TAGS E SINTAXE PARA CATFEINA (v1)

**Objetivo:** Permitir formatação rica e estruturada em textos dentro do aplicativo Catfeina,
processados para exibição com Jetpack Compose. A abordagem combina sintaxe leve para formatação
inline com palavras-chave explícitas para elementos de bloco.

---

**A. FORMATOS INLINE (Aplicados dentro de um parágrafo de texto):**

1. **Negrito:**
    * Sintaxe: `::n:texto em negrito::`
    * Compose: `fontWeight = FontWeight.Bold`

2. **Itálico:**
    * Sintaxe: `::i:texto em itálico::`
    * Compose: `fontStyle = FontStyle.Italic`

3. **Negrito e Itálico Combinados:**
    * Sintaxe: `::ni:texto em negrito e itálico::`
    * Compose: `fontWeight = FontWeight.Bold`, `fontStyle = FontStyle.Italic`

4. **Sublinhado:**
    * Sintaxe: `::s:texto sublinhado::`
    * Compose: `textDecoration = TextDecoration.Underline`

5. **Highlight (Fundo Destacado):**
    * Sintaxe: `::d:texto destacado::`
    * Compose: `background = MaterialTheme.colorScheme.secondaryContainer` (ou `primaryContainer`, a
      decidir qual é mais adequado para "destaque").

6. **Tooltip (para definições/notas curtas inline):**
    * Sintaxe: `::def:palavraExemplo|Definição ou comentário breve sobre a palavraExemplo::`
    * Compose: Renderiza "palavraExemplo" com um indicador visual. Ao interagir, um tooltip com "
      Definição..." aparece.

7. **Link (Clicável Inline):**
    * Sintaxe: `::url:URL_COMPLETA|Texto Visível do Link::`
    * Compose: Texto visível com estilo de link. Usa `StringAnnotation` e `ClickableText`.

---

**B. ELEMENTOS DE BLOCO (Estruturais):**

1. **Título Nível 1:**
    * Sintaxe: `::t1:Texto do Título Principal::`
    * Compose: `Text(style = MaterialTheme.typography.h1)` (ou similar)

2. **Título Nível 2:**
    * Sintaxe: `::t2:Texto do Título Nível 2::`
    * Compose: `Text(style = MaterialTheme.typography.h2)` (ou similar)

3. **Título Nível 3:**
    * Sintaxe: `::t3:Texto do Título Nível 3::`
    * Compose: `Text(style = MaterialTheme.typography.h3)` (ou similar)
    * *(Podem ser adicionados `::t4::` a `::t6::` se necessário)*

4. **Imagem (Local):**
    * Sintaxe: `::imagem:nome_do_arquivo.webp|Texto alternativo para acessibilidade::`
    * (Caminho base para `nome_do_arquivo.webp` é `assets/catfeina/`)
    * Compose:
      `Image(painter = painterResource(id = context.resources.getIdentifier("catfeina/${nome_do_arquivo}", "drawable", context.packageName)), contentDescription = "...")` -
      *Nota: A forma de carregar assets pode variar, `imageAsset` é mais comum para assets puros, ou
      se forem copiados para drawables, `painterResource` com `getIdentifier` ou nomes diretos se
      possível.* Vamos confirmar a melhor forma de carregar da pasta `assets/catfeina` quando
      implementarmos. Uma forma comum é usar
      `LocalContext.current.assets.open("catfeina/nome_do_arquivo.webp")` e depois
      `ImageBitmap.imageResource(context.resources, id).asImageBitmap()` ou `ImageDecoder` para APIs
      mais novas.

5. **Citação (Blockquote):**
    * Sintaxe: `::cit:O texto completo da citação...::`
    * Compose: Bloco com decoração e texto estilizado.

6. **Item de Lista Desordenada:**
    * Sintaxe: `::item:Texto do item da lista::`
    * Compose: Marcador (`•`) e texto.

7. **Item de Lista Ordenada:**
    * Sintaxe: `::num:Texto do item da lista ordenada::`
    * Compose: Número (1., 2.) e texto.

8. **Regra Horizontal (Divisor):**
    * Sintaxe: `::linha::`
    * Compose: `HorizontalDivider()`

---

**Decisões Chave Mantidas:**

* Tags explícitas (ex: `::ni::` para negrito/itálico, sem aninhamento complexo de tags diferentes).
* Sem escape de `::` (não será usado literalmente). `:` sozinho é literal.
* Espaços dentro do conteúdo das tags são preservados.
* Imagens locais de `assets/catfeina/`.
* Duas funções finais: uma para renderizar, outra para extrair texto para TTS.
* Estrutura de `TagProcessor` para extensibilidade.

---

## Log de Progresso Detalhado (Changelog Interno)

Esta seção rastreia conquistas e mudanças significativas em datas específicas,
complementando as fases principais do roteiro.

### 2023-08-08

* **Refatoração Completa do Sistema de Temas:**    * Implementados `BaseTheme` (Primavera, Verão) e
  `ThemeState` (Claro, Escuro, Auto) como enums separados.
    * `UserPreferencesRepository` atualizado para persistir `BaseTheme` e `ThemeState`
      individualmente no DataStore.
    * `ThemeViewModel` criado/atualizado para gerenciar e expor `currentBaseTheme` e
      `currentThemeState` via StateFlows, e fornecer funções para modificá-los.
    * `CatfeinaAppTheme` (Composable principal) refatorado para aceitar `BaseTheme` e `ThemeState` e
      aplicar dinamicamente as paletas de cores e configurações da UI do sistema (barras de
      status/navegação).
    * Corrigida a lógica de aplicação de cores dinâmicas (Material You) em conjunto com os temas
      base.

* **Funcionalidade de Preferências de Tema:**
    * Recuperado e corrigido `PreferenciasViewModel` para operar com `BaseTheme` e `ThemeState`.
    * Recuperada e corrigida `PreferenciasScreen` para permitir ao usuário selecionar:
        * O `BaseTheme` desejado (ex: Primavera, Verão) usando RadioButtons.
        * O `ThemeState` desejado (Claro, Escuro, Auto) usando RadioButtons.
    * Integrada a `PreferenciasScreen` à navegação principal do aplicativo:
        * Adicionada a rota `AppDestinations.PREFERENCIAS_ROUTE`.
        * Adicionado o `composable` correspondente no `AppNavHost`.
        * Adicionado item de navegação no `ModalNavigationDrawer` para acesso à tela.

* **Aprimoramentos e Correções na UI:**
    * **`MenuTresPontinhos`:**
        * Corrigida a lógica de alternância de `ThemeState` (Claro/Escuro/Auto) para ser consistente
          com o ciclo definido no `ThemeViewModel` e refletir corretamente a próxima ação no texto e
          no ícone.
    * **`InformativoScreen`:**
        * Corrigido o uso de import depreciado para `hiltViewModel`.
    * **Consistência de Código:**
        * Resolvido aviso de "função nunca usada" no `PreferenciasViewModel` (removendo
          `toggleThemeState` dele, já que `ThemeViewModel` tem a responsabilidade principal para
          essa ação fora da tela de preferências).

---
**Parecer Final sobre esta Versão:**

Esta lista está **excelente, concisa e muito bem definida** para uma primeira versão robusta. A
remoção da cor de texto específica simplifica o conjunto inicial de `TagProcessors` que precisaremos
definir e implementar.

**Estou pronto para prosseguirmos!**

Com esta lista de tags finalizada, os próximos passos lógicos são:

1. **Definir a Estrutura de Dados Intermediária (`ContentElement`):** Como o parser vai representar
   o texto "parseado" antes de ser renderizado? (Já temos um bom esboço disso).
2. **Definir a Arquitetura dos `TagProcessor`s:** Como cada tag será processada? Como o parser
   principal usará esses processadores?
3. **Esboçar a API do Composable Principal:**
   `@Composable fun FormattedContentRenderer(rawText: String, modifier: Modifier = Modifier)` e como
   ele usará a lista de `ContentElement`.
4. **Começar a implementação (Parser primeiro, depois o Renderer).**

Qual desses pontos você gostaria de detalhar agora? Sugiro começarmos com a **Estrutura de Dados
Intermediária (`ContentElement`)**, pois ela guiará tanto o parser quanto o renderer.
