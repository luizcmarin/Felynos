# Catfeina - Registro de Atualizações

Todas as mudanças notáveis neste projeto serão documentadas neste arquivo.

O formato é baseado em [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
e este projeto adere ao [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased] - 19/09/2025

### Novo

- **Definição da Estrutura Inicial do Banco de Dados:**
    - Definidas as tabelas `notas`, `personagens`, `poesias`, e `textos` via dump SQL.
    - Campos e índices especificados para cada tabela.
- **Padrões de Código para Entidades e Modelos de Domínio:**
    - Estabelecido padrão de arquivo único para Entidade Room (`NomeEntity`), Modelo de Domínio (
      `Nome`) e mapeadores (`toDomain()`, `toEntity()`).
    - Adotada nomenclatura `NomeEntity` para classes de entidade Room.
    - Implementação de cabeçalho de documentação padronizado para arquivos Kotlin.
- **Funcionalidade da Tabela `Textos`:**
    - Especificado o uso da tabela `textos` para armazenar conteúdos como Política de Privacidade e
      Termos de Uso.
    - Definida a navegação e a exibição desses textos em `WebView`.
    - Esboçada a Toolbar comum com funcionalidades de cópia, compartilhamento, ajuste de fonte,
      mudança de tema e TTS.
- **Processo de Documentação:**
    - Combinado o registro de decisões de design no `GEMINI.MD` e o rastreamento de mudanças no
      `CHANGELOG.MD`.
