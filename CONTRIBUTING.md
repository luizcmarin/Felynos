# Guia de Contribuição para o Catfeina

Primeiramente, gostaríamos de agradecer seu interesse em contribuir para o projeto **Catfeina**! Sua colaboração é valiosa para o nosso sucesso.

### Código de Conduta

Este projeto adota um [Código de Conduta](https://github.com/luizcmarin/catfeina/blob/main/CODE_OF_CONDUCT.md). Ao participar, você concorda em seguir este código. Por favor, reporte qualquer comportamento inaceitável para os [administradores](luizcmarin@gmail.com).

### Como Perguntar (Dúvidas e Suporte)

Se você tem uma dúvida sobre como usar **Catfeina** ou precisa de suporte, por favor, **não abra uma Issue no GitHub**. Em vez disso, você pode:

  * Perguntar em nossa [seção de Discussões no GitHub](https://github.com/luizcmarin/catfeina/discussions).
  * Procurar a resposta na nossa [wiki](https://github.com/luizcmarin/catfeina/wiki).

### Como Reportar um Bug

Antes de relatar um problema, por favor:

1.  **Verifique as Issues existentes:** Pode ser que o bug já tenha sido reportado ou resolvido. Você pode pesquisar em [Issues](https://github.com/luizcmarin/catfeina/issues).
2.  **Pesquise na documentação:** Às vezes, o comportamento pode ser intencional ou já documentado.

### Como Sugerir um Recurso (Feature - Pull requests)

Gostamos de novas ideias! Antes de sugerir um recurso:

1.  **Verifique PRs existentes:** Veja se a ideia já está sendo discutida em [Pull requests](https://github.com/luizcmarin/catfeina/pulls).
2.  **Abra uma nova Issue:** Descreva sua ideia com clareza.

### Desenvolvimento Local

Para configurar o ambiente de desenvolvimento local do **Catfeina** veja as instruções no arquivo [README.md](https://github.com/luizcmarin/catfeina/blob/main/README.md).

## Padrões de Codificação

Para manter a consistência, legibilidade e manutenibilidade do código no Catfeina, seguimos padrões de codificação claros. Ao contribuir, por favor, adote estas diretrizes:

  * **Linguagem:** PHP 8.1+ (ou a versão mínima que seu projeto exigir). Sempre utilize as funcionalidades e sintaxes modernas do PHP.

  * **Framework:** CodeIgniter 4. Sempre que possível, utilize as convenções e recursos do framework.

  * **Estilo de Código (PSR):**

      * Siga as diretrizes da **PSR-12 (Extended Coding Style)** para o estilo geral do código. Isso inclui regras sobre indentação (4 espaços, nunca tabs), namespaces, visibilidade de membros (`public`, `protected`, `private`), e declarações de tipos.
      * Utilize **`PHP CS Fixer`** ou **`PHP_CodeSniffer`** com o padrão PSR-12 para formatação automática. Configure seu editor de código (como VS Code com extensões PHP) para rodar estas ferramentas automaticamente ao salvar, garantindo que o código esteja sempre formatado corretamente.

  * **Nomeclatura:**

      * **Classes e Namespaces:** Use `PascalCase` (ex: `MyClassName`, `App\Controllers\Dashboard`).
      * **Métodos e Funções:** Use `camelCase` (ex: `myMethodName()`, `getData()`).
      * **Variáveis:** Use `camelCase` (ex: `$myVariable`, `$userName`).
      * **Constantes:** Use `UPPER_SNAKE_CASE` (ex: `MY_CONSTANT`).
      * **Arquivos:** Os nomes dos arquivos de classe devem corresponder ao nome da classe (ex: `MyClass.php` contém `class MyClass`).

  * **Declarações de Tipos (Type Hinting):** Sempre que possível, utilize **declarações de tipos** para parâmetros de funções/métodos, retornos e propriedades de classe. Isso melhora a legibilidade, ajuda na detecção de erros e auxilia as ferramentas de análise estática.

    ```php
    public function getUser(int $userId): ?UserEntity
    {
        // ...
    }
    ```

  * **Comentários e DocBlocks:**

      * Comente o código complexo ou a lógica não óbvia.
      * Utilize **DocBlocks** (comentários PHPDoc `/** ... */`) para documentar classes, métodos, funções e propriedades. Inclua `@param`, `@return`, `@throws` e uma breve descrição. Isso auxilia na geração de documentação e no autocompletar do editor.

  * **Testes:**

      * Toda nova funcionalidade e correção de bug (principalmente se for crítico) deve vir acompanhada de **testes de unidade** correspondentes.
      * Busque uma cobertura de testes alta, garantindo que as partes críticas do sistema estejam bem testadas. O CodeIgniter 4 usa o **PHPUnit** para testes.

  * **Controle de Versão (Git):**

      * Sempre trabalhe em um **branch separado** para cada nova funcionalidade ou correção de bug.
      * Use **mensagens de commit claras e descritivas** no imperativo (ex: "feat: Adiciona tela de login", "fix: Corrige bug de validação").

### Licença

Ao contribuir para este repositório, você concorda que suas contribuições serão licenciadas sob os termos da GNU General Public License v3.0.
Por favor, veja [`LICENSE`](https://github.com/luizcmarin/catfeina/blob/main/LICENSE) para mais informações.
