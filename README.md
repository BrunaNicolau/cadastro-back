# Cadastro BAckEnd

Este é um projeto Java de exemplo para cadastro proposto pelo Itaú.

## Pré-requisitos

Antes de começar, certifique-se de ter atendido aos seguintes requisitos:

- Importe o projeto na sua IDE de preferência.
- Certifique-se de ter pelo menos uma JDK disponível com a versão 11 do Java. Você pode verificar a versão do Java usando o seguinte comando no terminal:
 ```bash
   java -version
 ```
- Certifique-se que seja possível compilar o projeto com o Maven, é a ferramenta de gerenciamento de dependências que utilizaremos. Você pode verificar a versão do Java usando o seguinte comando no terminal:
```bash
   mvn clean install
 ```
## Executando o Projeto

O projeto foi construído em Spring (Boot e Data) e já está configurado para subir um banco de dados H2 em memória. No carregamento inicial, um script é executado criando o banco e inserindo registros padrão para facilitar sua compreensão.
Para iniciar a aplicação, execute o seguinte comando no terminal:

```bash
   mvn spring-boot:run
```
## Documentação
Este projeto utiliza o Springdoc OpenAPI para gerar a documentação da API. Você pode acessar a documentação da API em [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui/index.html#/)http://localhost:8080/swagger-ui/index.html#/ após iniciar a aplicação.

