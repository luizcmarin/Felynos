# Catfeina  poetic_cat

[![Kotlin Version](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg?style=flat-square&logo=kotlin)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.6.0-4285F4.svg?style=flat-square&logo=android)](https://developer.android.com/jetpack/compose)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg?style=flat-square)](https://www.gnu.org/licenses/gpl-3.0)
<!-- Adicione mais badges se relevante (ex: build status, code coverage) -->

**Catfeina** é um aplicativo Android moderno, construído com as mais recentes tecnologias Jetpack, projetado para oferecer uma experiência elegante e agradável para amantes de poesia. Explore, descubra e interaja com um universo de conteúdo poético enriquecido visualmente.

<!-- Opcional: Adicionar um screenshot ou GIF do app aqui -->
<!-- <p align="center">
  <img src="path/to/your/screenshot.png" alt="Catfeina Screenshot" width="300"/>
</p> -->

## ✨ Funcionalidades

*   **Exploração de Poesias:** Navegue por uma coleção crescente de poesias.
*   **Detalhes Enriquecidos:** Visualize cada poesia com sua imagem de capa e galeria de imagens adicionais.
*   **Favoritos:** Marque suas poesias preferidas para acesso rápido.
*   **(Futuro) Marcar como Lido:** Acompanhe o que você já leu.
*   **Design Moderno:** Interface de usuário limpa e intuitiva construída inteiramente com Jetpack Compose.
*   **Temas Dinâmicos:** Personalize sua experiência visual escolhendo entre tema claro, escuro ou o padrão do sistema.
*   **Animação de Splash:** Uma agradável animação de boas-vindas com Lottie.
*   **Arquitetura Robusta:** Segue os princípios de Clean Architecture e MVVM, utilizando:
    *   **Room:** Para persistência de dados local.
    *   **Hilt:** Para injeção de dependências.
    *   **Kotlin Coroutines & Flow:** Para programação assíncrona e reativa.
    *   **Jetpack Navigation:** Para navegação entre telas.
    *   **Jetpack DataStore:** Para armazenamento de preferências.
    *   **Coil:** Para carregamento eficiente de imagens.
*   **População Inicial de Dados:** O conteúdo base é carregado localmente na primeira inicialização (a partir de um JSON interno).

## 🛠️ Tecnologias Utilizadas

*   **Linguagem:** [Kotlin](https://kotlinlang.org/)
*   **UI Toolkit:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
*   **Arquitetura:** MVVM com elementos de Clean Architecture
*   **Persistência de Dados:** [Room](https://developer.android.com/training/data-storage/room)
*   **Injeção de Dependência:** [Hilt](https://dagger.dev/hilt/)
*   **Programação Assíncrona:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) & [Flow](https://kotlinlang.org/docs/flow.html)
*   **Navegação:** [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation)
*   **Preferências:** [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore)
*   **Carregamento de Imagens:** [Coil](https://coil-kt.github.io/coil/)
*   **Animações:** [Lottie for Android](https://airbnb.io/lottie/#/android)
*   **Splash Screen API:** [AndroidX Core Splashscreen](https://developer.android.com/guide/topics/ui/splash-screen)
*   **Gradle Version Catalog (libs.versions.toml):** Para gerenciamento de dependências.

## 🚀 Como Começar (Build & Run)

1.  **Clone o repositório:**
```
git clone https://[URL_DO_SEU_REPOSITORIO_GIT]/Catfeina.git
    cd Catfeina
```

2.  **Abra no Android Studio:**
    *   Abra o Android Studio (versão recomendada: [última versão estável, ex: Iguana ou superior]).
    *   Selecione "Open an existing Android Studio project".
    *   Navegue até o diretório clonado `Catfeina` e selecione-o.

3.  **Sincronize o Gradle:**
    *   O Android Studio deve iniciar automaticamente a sincronização do Gradle. Se não, clique em "Sync Project with Gradle Files" (ícone de elefante na barra de ferramentas).

4.  **Execute o Aplicativo:**
    *   Selecione um emulador ou conecte um dispositivo físico.
    *   Clique no botão "Run 'app'" (ícone de play verde).

**Pré-requisitos:**
*   Android Studio ([versão compatível com AGP usado no projeto])
*   JDK 17 ou superior
## 🏗️ Estrutura do Projeto (Simplificada)

O projeto segue uma estrutura modular para promover a separação de responsabilidades:

    catfeina/
    ├── app/src/main/java/com/marin/catfeina/
    │   ├── data/                 # Camada de Dados: Repositórios, DAOs, Entidades Room, DataStore
    │   │   ├── dao/
    │   │   ├── entity/
    │   │   ├── repository/
    │   │   └── AppDatabase.kt
    │   │   └── PreferenciasRepository.kt
    │   ├── di/                   # Módulos Hilt para Injeção de Dependência
    │   ├── dominio/              # Camada de Domínio: Modelos de UI, UseCases/Actions, Enums
    │   ├── ui/                   # Camada de UI (Jetpack Compose): Screens, ViewModels, Themes, Ícones
    │   │   ├── poesias/
    │   │   ├── poesiadetail/
    │   │   ├── preferencias/
    │   │   ├── screens/          # Telas genéricas (Sobre, Política de Privacidade)
    │   │   └── theme/
    │   ├── MainActivity.kt       # Ponto de entrada da UI
    │   ├── MainScreen.kt         # Layout principal com navegação
    │   ├── Navigation.kt         # Definições de rotas e NavGraph
    │   └── CatfeinaApplication.kt # Classe Application (Hilt, inicializações)
    ├── app/src/main/assets/      # Recursos brutos (ex: futuras poesias_iniciais.json, fontes)
    ├── app/src/main/res/         # Recursos Android (layouts XML legados, drawables, strings, etc.)
    │   ├── drawable/             # Ícones e shapes
    │   ├── raw/                  # Animação Lottie (catfeina_pata.json)
    │   └── values/               # Strings, cores, temas
    └── build.gradle.kts (project e app), gradle/libs.versions.toml # Scripts de build e dependências

## 📝 Documentação Interna

Para entender melhor a colaboração com o assistente de IA e as decisões de arquitetura, consulte:

*   `GEMINI.MD`: Detalhes da colaboração com o Assistente Gemini, suas capacidades e limitações observadas.
*   `AGENT.MD`: Descreve as funcionalidades "inteligentes" ou automatizadas do Catfeina, como a população inicial de dados e o gerenciamento de tema.

## 🤝 Contribuições

Contribuições são bem-vindas! Se você tiver ideias para novas funcionalidades, melhorias ou correções de bugs:

1.Faça um Fork do projeto.
2.  Crie uma nova Branch (`git checkout -b feature/sua-feature-incrivel`).
3.  Faça commit de suas mudanças (`git commit -m 'Adiciona funcionalidade X'`).
4.  Faça Push para a Branch (`git push origin feature/sua-feature-incrivel`).
5.  Abra um Pull Request.

Por favor, siga as convenções de código e estilo do projeto.

## 🐛 Reportando Bugs

Se encontrar algum bug, por favor, abra uma [Issue](https://[URL_DO_SEU_REPOSITORIO_GIT]/Catfeina/issues) detalhando o problema, os passos para reproduzi-lo e a versão do aplicativo/Android.

## 📜 Licença

Este projeto é licenciado sob a **GNU General Public License v3.0** - veja o arquivo [LICENSE](LICENSE) para detalhes.

---

**Desenvolvido com ❤️ e Kotlin por [Seu Nome/Apelido] com a colaboração do Assistente Gemini.**

_Este README foi gerado com a ajuda do Assistente Gemini._
