# Exemplo Carros - CRUD com Migrations (Liquibase)

Este projeto é uma aplicação simples de CRUD (Create, Read, Update, Delete) de carros, desenvolvida com o objetivo principal de demonstrar o uso e a importância de **Migrations** no ciclo de desenvolvimento de software.

## 🚀 Sobre o Projeto

O foco aqui não é gestão de carros, mas sim como as alterações na estrutura do banco de dados (como criação de tabelas e adição de colunas) são gerenciadas de forma versionada e segura.

## 📂 O que são Migrations e Liquibase?

### O que são Migrations?
Em projetos de software, o banco de dados evolui junto com o código. **Migrations** são como um "controle de versão" para o seu banco de dados. Elas permitem que você descreva as mudanças no banco de dados em arquivos de script, que podem ser aplicados em qualquer ambiente (desenvolvimento, teste, produção) de forma consistente.

### O que é o Liquibase?
O **Liquibase** é uma ferramenta de código aberto que automatiza essas migrações. Em vez de rodar scripts SQL manualmente, você define as mudanças em formatos como XML, YAML, JSON ou SQL, e o Liquibase se encarrega de verificar o que já foi executado e o que ainda precisa ser aplicado no banco de dados.

Ele utiliza uma tabela especial chamada `DATABASECHANGELOG` para registrar quais scripts (chamados de *changeSets*) já foram rodados, garantindo que nenhum script seja executado duas vezes por engano.

## ⚙️ Configuração do Banco de Dados

As configurações de conexão estão localizadas no arquivo `src/main/resources/application.properties`. Abaixo estão os dados utilizados para este exemplo (ambiente local):

- **URL:** `jdbc:mariadb://localhost:3306/exemplo_carros`
- **Usuário:** `user`
- **Senha:** `senha123`
- **Arquivo Mestre do Liquibase:** `src/main/resources/db/changelog/db.changelog-master.xml`

> **Nota:** Estes dados são apenas para fins de exemplo local e não devem ser usados em ambientes produtivos reais sem a devida segurança.

## 💻 Comandos Liquibase (Modo Shell)

Caso você precise utilizar o Liquibase diretamente via linha de comando (Shell), aqui estão os principais comandos explicados detalhadamente. 

Para rodar esses comandos, você geralmente precisará apontar para o driver do banco de dados e fornecer as credenciais. Exemplo de estrutura básica:

```bash
liquibase \
  --url=jdbc:mariadb://localhost:3306/exemplo_carros \
  --username=user \
  --password=senha123 \
  --changelog-file=src/main/resources/db/changelog/db.changelog-master.xml \
  [COMANDO]
```

### Principais Comandos:

1.  **`update`**
    *   **O que faz:** É o comando mais comum. Ele lê o seu arquivo de changelog e aplica todas as mudanças que ainda não foram executadas no banco de dados.
    *   **Para o leigo:** "Ei Liquibase, veja o que tem de novo nos meus arquivos e atualize meu banco de dados para a versão mais recente."

2.  **`status`**
    *   **O que faz:** Lista quantas mudanças (*changeSets*) ainda não foram aplicadas ao banco de dados.
    *   **Para o leigo:** "Verifique se o meu banco de dados está atualizado ou se ainda faltam scripts para rodar."

3.  **`rollback [tag/count/date]`**
    *   **O que faz:** Desfaz as alterações feitas no banco de dados. Você pode desfazer por uma quantidade de mudanças (ex: voltar 1 passo) ou até uma data específica.
    *   **Para o leigo:** "Eu cometi um erro! Por favor, desfaça a última alteração que eu fiz no banco de dados."

4.  **`validate`**
    *   **O que faz:** Verifica se o seu arquivo de changelog está escrito corretamente e se não há erros de sintaxe.
    *   **Para o leigo:** "Confira se eu escrevi meus scripts de migração de forma correta antes de eu tentar rodar."

5.  **`diff`**
    *   **O que faz:** Compara dois bancos de dados (ou seu código e o banco) e mostra as diferenças entre eles.
    *   **Para o leigo:** "Compare o meu banco de dados atual com o que deveria ser e me mostre o que está diferente."

6.  **`future-rollback-sql`**
    *   **O que faz:** Gera o código SQL que seria necessário para desfazer as mudanças que você *ainda vai* aplicar. É uma medida de segurança.
    *   **Para o leigo:** "Antes de eu atualizar o banco, me mostre como eu poderia desfazer essa atualização caso algo dê errado."

7.  **`changelog-sync`**
    *   **O que faz:** Marca todas as mudanças no seu arquivo de changelog como "já executadas" no banco de dados, sem realmente rodar o SQL.
    *   **Para o leigo:** "Finja que você já rodou tudo o que está nos meus arquivos. Use isso quando o banco já estiver com as tabelas prontas e você só quiser que o Liquibase saiba disso (estabelecer uma **baseline**)."
> **Nota Importante:** O caminho definido para o primeiro changelog é muito importante. Conforme desse projeto: "spring.liquibase.change-log=classpath:/db/changelog/db.changelog-master.xml" ele traz o caminho relativo do arquivo inicial sugerido
> "db.changelog-master.xml", este caminho precisa ser o mesmo em scripts do liquibase como no plugin do maven(pom.xml) ou gradle(build.gradle) e até mesmo usando o caminho completo em stages de pipeline. Caso o caminho seja diferente, teremos um erro na hora de rodar a aplicação com a migration
> informando que as tabelas, ou atributos já existem e não podem ser criadas novamente.
8.  **`update-sql`**
    *   **O que faz:** Em vez de aplicar as mudanças no banco, ele apenas mostra na tela (ou gera um arquivo) o código SQL puro que seria executado.
    *   **Para o leigo:** "Não altere nada ainda, apenas me mostre o código SQL que você gerou a partir do meu XML/YAML para que eu possa revisar ou entregar para um DBA."

## 🔌 Plugins e Facilitadores (Scaffolding)

No ecossistema Java/Spring, não precisamos escrever cada linha do XML de migração manualmente. Existem ferramentas que automatizam esse processo (**scaffolding**):

*   **Liquibase Maven Plugin:** Permite rodar todos os comandos acima diretamente pelo Maven (ex: `mvn liquibase:update`). Isso facilita a integração em pipelines de CI/CD.
*   **JPA Buddy (IntelliJ Plugin):** Esta é uma das ferramentas mais poderosas. Ela analisa suas entidades Java (classes com `@Entity`) e compara com o banco de dados real, gerando o arquivo de migration (XML, YAML ou SQL) automaticamente para você.

## 🌐 Paralelo com o Mundo .NET Core

Se você vem do C# / .NET Core, o conceito de Migrations é muito similar, mas as ferramentas mudam:

| Característica | Java (Spring Boot) | .NET Core |
| :--- | :--- | :--- |
| **Ferramenta Principal** | Liquibase / Flyway | EF Core Migrations |
| **Configuração** | XML, YAML, JSON ou SQL | Classes C# (Code-First) |
| **Interface** | Shell / Maven / Gradle | Package Manager Console / dotnet CLI |
| **Scaffolding** | JPA Buddy / Spring Data | `dotnet ef migrations add` |

No .NET, o comando `dotnet ef database update` equivale ao `update` do Liquibase. O plugin **Entity Framework Core Power Tools** no Visual Studio faz um papel semelhante ao JPA Buddy, facilitando a engenharia reversa e a geração de diagramas e migrações.

## 🛠️ Exemplo Prático: Adicionando o atributo "Cor"

Imagine que precisamos adicionar a cor do carro. Usando o **JPA Buddy** ou o **Liquibase Maven Plugin**, o fluxo seria:

1.  **Alteração no Código:** Você adiciona `private String cor;` na sua classe `Carro.java`.
2.  **Geração da Migration:** O plugin detecta que a classe tem um campo novo que não existe no banco e gera algo assim:
    ```xml
    <changeSet id="12345-add-cor" author="felipe">
        <addColumn tableName="carro">
            <column name="cor" type="varchar(50)"/>
        </addColumn>
    </changeSet>
    ```
3.  **Execução:** Ao subir a aplicação ou rodar o `update`, o Liquibase cria a coluna automaticamente.

### ⏪ E o Rollback nesse cenário?

Se você perceber que a coluna "cor" não era necessária, o Liquibase facilita o retorno:

*   **Comando:** `liquibase rollback-count 1`
*   **O que acontece:** O Liquibase executa o comando inverso (`dropColumn`) para remover a coluna "cor" e remove o registro da tabela `DATABASECHANGELOG`.
*   **Ajuda dos Plugins:** Ferramentas como o JPA Buddy já sugerem a estrutura do rollback automaticamente, garantindo que você tenha um caminho de volta seguro caso a migração cause problemas.

---
*Este projeto foi estruturado para fins educacionais sobre arquitetura de software e persistência de dados.*
