# Felynos  poetic_cat

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg?style=flat-square&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.7-4285F4.svg?style=flat-square&logo=android)](https://developer.android.com/jetpack/compose) <!-- Atualize para a versão do Compose que você está usando -->
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=flat-square)](https://www.gnu.org/licenses/gpl-3.0)
<!-- Adicione mais badges se relevante (ex: build status, code coverage, versão do app) -->

**Felynos** é um aplicativo Android moderno, construído inteiramente com Jetpack Compose e as mais recentes tecnologias Jetpack. Ele foi projetado para oferecer uma experiência elegante, fluida e agradável para amantes de poesia. Explore, descubra e interaja com um universo de conteúdo poético enriquecido visualmente e com formatação rica através de Markdown.

<!-- Opcional: Adicionar um screenshot ou GIF do app aqui -->
<!-- <p align="center">
  <img src="path/to/your/screenshot.png" alt="Felynos Screenshot" width="300"/>
</p> -->

## ✨ Funcionalidades Principais

*   **Exploração de Poesias:** Navegue por uma coleção crescente de poesias, apresentadas de forma clara e legível.
*   **Detalhes Enriquecidos:** Visualize cada poesia com sua imagem de capa (carregada via Coil) e informações adicionais.
*   **Conteúdo Formatado com Markdown:** Textos como biografias de personagens, detalhes de poesias e textos informativos (Sobre, Política de Privacidade) são renderizados a partir de Markdown, permitindo formatação rica.
*   **Favoritos:** Marque suas poesias preferidas para acesso rápido.
*   **(Futuro) Marcar como Lido:** Acompanhe o que você já leu.
*   **Design Moderno com Material 3:** Interface de usuário limpa e intuitiva construída inteiramente com Jetpack Compose e os princípios do Material Design 3.
*   **Temas Dinâmicos:** Personalize sua experiência visual escolhendo entre tema claro, escuro ou o padrão do sistema (suporte a cores dinâmicas do Material You em dispositivos compatíveis).
*   **Animação de Splash:** Uma agradável animação de boas-vindas utilizando a API oficial de Splash Screen do Android e Lottie.
*   **Arquitetura Robusta:** Segue os princípios de Clean Architecture e MVVM, utilizando:
    *   **Room:** Para persistência de dados local.
    *   **Hilt:** Para injeção de dependências.
    *   **Kotlin Coroutines & Flow:** Para programação assíncrona e reativa.
    *   **Jetpack Navigation Compose:** Para uma navegação fluida e type-safe entre telas.
    *   **Jetpack DataStore:** Para armazenamento de preferências do usuário (como tema e tamanho da fonte).
    *   **Coil:** Para carregamento eficiente e moderno de imagens.
*   **População Inicial de Dados:** O conteúdo base é carregado localmente na primeira inicialização (a partir de um JSON interno contendo Markdown).
*   **(Opcional) Mascote Interativo "Cashito":** Um mascote animado com Lottie que oferece dicas e sugestões.

## 🛠️ Tecnologias Utilizadas

*   **Linguagem Principal:** [Kotlin](https://kotlinlang.org/) (incluindo Coroutines e Flow)
*   **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose) com [Material 3](https://m3.material.io/)
*   **Arquitetura:** MVVM com elementos de Clean Architecture
*   **Persistência de Dados:** [Room](https://developer.android.com/training/data-storage/room)
*   **Injeção de Dependência:** [Hilt](https://dagger.dev/hilt/)
*   **Navegação:** [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
*   **Preferências:** [Jetpack DataStore (Preferences)](https://developer.android.com/topic/libraries/architecture/datastore)
*   **Carregamento de Imagens:** [Coil](https://coil-kt.github.io/coil/)
*   **Renderização de Markdown:** [Markwon](https://noties.io/Markwon/) (para exibir conteúdo formatado em Composables)
*   **Animações:**
    *   [Lottie for Android (Compose)](https://airbnb.io/lottie/#/android-compose)
    *   Animações nativas do Jetpack Compose
*   **Splash Screen API:** [AndroidX Core Splashscreen](https://developer.android.com/guide/topics/ui/splash-screen)
*   **Gerenciamento de Dependências:** [Gradle Version Catalog (libs.versions.toml)](https://developer.android.com/build/migrate-to-catalogs)

**Pré-requisitos de Ambiente para Desenvolvimento:**
*   Android Studio Iguana (ou mais recente)
*   JDK 17 ou superior configurado para o Android Studio

## 🚀 Como Começar (Build & Run)

1.  **Clone o repositório:**
    Se você ainda não tem o código do projeto, clone o repositório do GitHub (ou do sistema de controle de versão que você está usando) para a sua máquina local. Abra um terminal ou prompt de comando e use o seguinte comando, substituindo a URL pelo link correto do seu repositório:
    ```
    bash git clone [https://github.com/luizcmarin/Felynos.git](https://github.com/luizcmarin/Felynos)
    ```
    Após clonar, navegue para o diretório do projeto:
    ```
    bash cd Felynos
    ```
2.  **Abra no Android Studio:**    *   Abra o Android Studio (versão recomendada: **Android Studio Iguana | 2023.2.1** ou superior).
    *   Na tela de boas-vindas do Android Studio, selecione "Open" (ou "Open an Existing Project").
    *   Navegue até o diretório onde você clonou o projeto `Felynos` e selecione-o. Clique em "OK" ou "Open".

3.  **Sincronize o Gradle:**
    *   O Android Studio deve iniciar automaticamente a sincronização do Gradle assim que o projeto for aberto. Este processo baixa as dependências necessárias e configura o ambiente de build.
    *   Se a sincronização não iniciar automaticamente, ou se você precisar refazê-la, você pode clicar em "Sync Project with Gradle Files" (geralmente representado por um ícone de elefante na barra de ferramentas superior) ou navegar via menu: `File > Sync Project with Gradle Files`.
    *   Aguarde a conclusão da sincronização. Você pode acompanhar o progresso na aba "Build" na parte inferior do Android Studio.

4.  **Execute o Aplicativo:**
    *   Após a sincronização bem-sucedida do Gradle, você estará pronto para executar o aplicativo.
    *   Certifique-se de ter um emulador Android configurado ou um dispositivo físico conectado ao seu computador com a depuração USB ativada.
    *   Na barra de ferramentas superior do Android Studio, selecione o dispositivo/emulador desejado no menu dropdown de dispositivos.
    *   Clique no botão "Run 'app'" (o ícone de play verde ▶️) ou use o atalho `Shift + F10` (no Windows/Linux) ou `Control + R` (no macOS).
    *   O Android Studio compilará o projeto, instalará o APK no dispositivo/emulador selecionado e iniciará o aplicativo.

**Pré-requisitos de Ambiente para Desenvolvimento:**
*   Android Studio Iguana (ou mais recente)
*   JDK 17 ou superior configurado para o Android Studio
*   Git instalado na sua máquina (para clonar o repositório)


## 📝 Documentação Interna e Decisões de Projeto

Para entender melhor a colaboração com o assistente de IA, as decisões de arquitetura e a evolução do projeto:

*   `GEMINI.MD`: Detalhes da colaboração com o Assistente Gemini, prompts utilizados, limitações observadas e como a IA auxiliou no desenvolvimento e refatoração.
*   _(Opcional: Adicionar um `ARCHITECTURE.md` se quiser detalhar mais as decisões de arquitetura)_

## 🤝 Contribuições

Contribuições são muito bem-vindas! Se você tiver ideias para novas funcionalidades, melhorias ou correções de bugs, por favor, siga estes passos:

1.  Faça um Fork do projeto (se estiver contribuindo de um repositório externo).
2.  Crie uma nova Branch para sua feature ou correção (`git checkout -b feature/sua-feature-incrivel` ou `fix/corrige-bug-xyz`).
3.  Faça commit de suas mudanças com mensagens claras (`git commit -m 'Adiciona funcionalidade X'`).
4.  Faça Push para a sua Branch (`git push origin feature/sua-feature-incrivel`).
5.  Abra um Pull Request detalhando suas alterações.

Por favor, tente seguir as convenções de código e estilo do projeto e certifique-se de que os testes (se aplicável) ainda passam.

## 🐛 Reportando Bugs

Se encontrar algum bug, por favor, abra uma [Issue](https://github.com/luizcmarin/Felynos/Felynos/issues) no repositório do projeto (se ele for público/compartilhado). Inclua:

*   Uma descrição clara e concisa do bug.
*   Passos para reproduzir o comportamento.
*   Qual comportamento você esperava.
*   Qual comportamento realmente aconteceu.
*   Screenshots ou GIFs, se ajudar a ilustrar o problema.
*   Versão do aplicativo (se souber), versão do Android e modelo do dispositivo.

## 📜 Licença

Este projeto é licenciado sob a **GNU General Public License v3.0**. Veja o arquivo `LICENSE` para mais detalhes.

---

**Desenvolvido com ❤️, Kotlin & Jetpack Compose por Luiz Marin com a colaboração e assistência de Caroline Marin.**
