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
  correspondentes.
  Implementar mappers (provavelmente em `data.entity`).
* [] 1.6. Integrar Hilt para fornecer DAOs, Repositórios, DataSources e o Database.
* [P] 1.7. **Critério de Conclusão:** Camadas de domínio e dados funcionais, carga inicial de
  dados OK, preferências podem ser salvas/lidas. Testes unitários para DAOs, Repositórios e
  DataSources.
* [] 1.9. **Colaboração com Gemini:** Revisão das definições de entidades e modelos, exemplos de
  implementação de Repositórios, uso do DataStore, e estratégias de teste para a camada de dados.

### Fase 2: Features Iniciais - Informativos e Configurações

* [] 2.1. Desenvolver Composable genérico para exibir conteúdo Markdown (ex: encapsulando
  Markwon) no pacote `ui.componentes`.
* [] 2.2. Desenvolver telas (Screens) e ViewModels no pacote `ui.informativo` para listar e
  exibir textos informativos.
* [X] 2.3. Desenvolver tela (Screen) e ViewModel no pacote `ui.preferencias` para permitir ao
  usuário alterar configurações, integrando com `Preferencias`.
* [P] 2.4. Integrar as rotas de navegação para as features `informativo` e `preferencias` no
  grafo de navegação.
* [] 2.5. Adicionar a API Splash Screen do AndroidX.
* [P] 2.6. **Critério de Conclusão:** Usuário pode navegar, visualizar informativos e alterar
  configurações. Splash screen funcional. Testes de UI básicos.
* [] 2.7. **Colaboração com Gemini:** Auxílio na criação do Composable de Markdown, exemplos de
  ViewModels, integração com DataStore, e configuração da navegação.

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
       /*
        * Arquivo: com.marin.catfeina.dominio.Icones.kt
        * @project Catfeina
        * @description
        * Ponto de entrada principal da aplicação Catfeina.
        * Define a Activity principal e a estrutura da UI raiz com Scaffold, NavigationDrawer e TopAppBar.
        */
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

---
