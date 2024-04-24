Vamos dividir o roteiro em etapas claras e progressivas para facilitar o aprendizado do aluno. Aqui estão as etapas propostas:

### Etapa 1: Inicialização do projeto com o Spring Initializr

1. **Descrição**: Utilizar o [Spring Initializr](https://start.spring.io/) para gerar os arquivos de projeto básico (boilerplate).
2. **Conhecimentos Fundamentais**: Spring Framework, Spring Boot, MVC pattern.
3. **Produto Esperado**: Estrutura de projeto Spring Boot configurada com pacotes separados para controllers, models, views, repositories e services.

**Descrição:** Nesta etapa, vamos utilizar o Spring Initializr para gerar os arquivos de projeto básico (boilerplate) para a nossa aplicação Spring Boot. O Spring Initializr é uma ferramenta online que simplifica o processo de inicialização de projetos Spring Boot, permitindo-nos selecionar as dependências necessárias e configurar o projeto de acordo com nossas necessidades.

**Conhecimentos Fundamentais:**

- Spring Framework: Conjunto de bibliotecas que facilitam o desenvolvimento de aplicações Java empresariais.
- Spring Boot: Framework baseado no Spring Framework que simplifica o processo de configuração e desenvolvimento de aplicações Spring.
- MVC pattern: Padrão de arquitetura de software que separa os componentes de uma aplicação em Model, View e Controller.

**Produto Esperado:**
Estrutura de projeto Spring Boot configurada com pacotes separados para controllers, models, views, repositories e services.

---

#### Passo a Passo:

1. Acesse o [Spring Initializr](https://start.spring.io/).
2. Configure o projeto selecionando as seguintes opções:
   - **Project:** Maven Project
   - **Language:** Java
   - **Spring Boot:** Escolha a versão mais recente disponível.
   - **Project Metadata:** Preencha o Group, Artifact e Name conforme desejar.
   - **Dependencies:** Selecione as dependências necessárias para o seu projeto, como Spring Web para MVC.
3. Clique em "Generate" para baixar o arquivo ZIP contendo o projeto gerado pelo Spring Initializr.
4. Extraia o conteúdo do arquivo ZIP em um diretório de sua escolha.
5. Abra o projeto em sua IDE de desenvolvimento Java (Eclipse, IntelliJ, etc.).

![1713745564388](image/roteiro/1713745564388.png)

**Criar um controller inicial:**

```java
package network.webtech.labspringmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorld {
  
    @GetMapping("/")
    public String index() {
        return "Hello, World!";
    }
}
```

**Executar a aplicação:**
Para executar a aplicação Spring Boot, você pode usar o Maven para compilar e executar o projeto. Na raiz do projeto, execute o seguinte comando:

```bash
mvn spring-boot:run
```

**Exemplo de Estrutura de Projeto:**

```
meu-projeto
│   ├── src
│   │   ├── main
│   │   │   ├── java
│   │   │   │   └── com
│   │   │   │       └── exemplo
│   │   │   │           ├── controllers
│   │   │   │           │   └── MainController.java
│   │   │   │           ├── models
│   │   │   │           ├── repositories
│   │   │   │           └── services
│   │   │   └── resources
│   │   │       ├── static
│   │   │       └── templates
│   │   └── test
│   │       └── java
│   └── pom.xml
```

Nesta estrutura de projeto, os pacotes estão organizados da seguinte forma:

- **controllers:** Contém as classes responsáveis por receber as requisições HTTP e direcioná-las para os serviços apropriados.
- **models:** Contém as classes que representam as entidades de negócio da aplicação.
- **repositories:** Contém as interfaces de repositório para acessar os dados do banco.
- **services:** Contém as classes de serviço responsáveis por implementar a lógica de negócio da aplicação.

Com esta estrutura básica de projeto configurada, estamos prontos para avançar para as próximas etapas do desenvolvimento da nossa aplicação Spring Boot.

### Etapa 2: Configuração e Deploy do Ambiente de Desenvolvimento com Docker

**Descrição:** Nesta etapa, vamos configurar um ambiente de desenvolvimento local utilizando Docker para criar e gerenciar containers para o back end da aplicação. Além disso, vamos realizar o deploy da aplicação utilizando o GitHub e o Render, uma plataforma de hospedagem na nuvem.

**Conhecimentos Fundamentais:**

- Docker: Tecnologia de contêinerização que permite empacotar, distribuir e executar aplicações em ambientes isolados.
- Dockerfile e docker-compose: Arquivos de configuração utilizados para definir a construção e a execução de containers Docker.
- Git e GitHub: Ferramentas de controle de versão e hospedagem de código fonte, respectivamente.

**Produto Esperado:**
Ambiente de desenvolvimento configurado com Docker, repositório no GitHub integrado com o ambiente no Render.

---

#### Passo a Passo:

1. **Configuração do Ambiente de Desenvolvimento com Docker:**

Vamos criar um Dockerfile na raiz do projeto que define [dois estágios](https://docs.docker.com/build/building/multi-stage/), em que, no primeiro estágio, vamos utilizar uma imagem com o JDK completo, instalar o maven para compilar e gerar o pacote da aplicação (.jar). No segundo estágio, vamos montar o ambiente de execução apenas com o JRE e a nossa aplicação gerada no primeiro estágio. Utilize o código abaixo para montar o seu Dockerfile.

```Dockerfile
# Dockerfile para ambiente de build da aplicação
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app
COPY . .
RUN mvn clean package

# Segundo estágio do Dockerfile para montar o container de execução da aplicação
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/labspringmvc-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
```

Agora vamos começar a montar o nosso arquivo docker-compose.yaml que vai orquestrar toda a aplicação, definindo os diversos serviços e suas configurações.

```yaml
version: '3.8'

services:
  backend:
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
```

Para testar a configuração do ambiente baseado no Docker no ambiente local, execute o seguinte comando na raiz do projeto:

```bash
docker compose up --build -d
```

2. **Deploy da Aplicação Utilizando GitHub e Render:**
   Uma vez que o ambiente de desenvolvimento local com Docker está configurado e funcionando corretamente, vamos realizar o deploy da aplicação utilizando o GitHub e o Render. Siga os passos abaixo para realizar o deploy:

   a. Faça o commit e o push do código fonte para um repositório no GitHub.
   b. Crie uma conta no [Render](https://render.com/) e crie um novo Web Service, e escolha o deploy a partir de uma conexão com um repositório do GitHub.
   c. Em seguida, selecione o repositório do GitHub que contém o código da sua aplicação.
   d. Configure as opções de deploy, informado o branch a partir do qual será obtida a versão de publicação, escolha Docker como runtime e finalize a criação do serviço.
   e. Aguarde o processo de build e deploy ser concluído. Após isso, sua aplicação estará disponível em uma URL fornecida pelo Render.

Com isso, você terá configurado um ambiente de desenvolvimento local utilizando Docker e realizado o deploy da sua aplicação utilizando o GitHub e o Render. Este ambiente permitirá que você desenvolva e teste sua aplicação de forma isolada e também a disponibilize para acesso externo na nuvem.

### Etapa 3: Implementação de Controllers para Views

**Descrição:** Nesta etapa, vamos implementar controllers para lidar com as requisições HTTP voltadas para a obtenção de Views do padrão MVC e para acesso a endpoints REST para as operações CRUD. Utilizaremos o Spring MVC para criar controllers que suportem tanto views baseadas em templates como endpoints de uma API REST.

**Conhecimentos Fundamentais:**

- Spring MVC: Framework que facilita o desenvolvimento de aplicações web seguindo o padrão Model-View-Controller.
- APIs REST: Conjunto de padrões e princípios de arquitetura de software para a criação de serviços web que utilizam HTTP de forma consistente.

**Produto Esperado:**
Controllers implementados com suporte a views baseadas em templates e para endpoints de uma API REST que faz interfaces para operações de CRUD com entidades da aplicação.

---

#### Passo a Passo:

**Implementação dos Controllers para Views**

Para montar nossa camada de views, vamos utilizar um mecanismo de templates utilizado pelo Spring Boot que é o Thymeleaf. O mecanismo de templates promove a separação da camada de interface da lógica do sistema.

Para iniciar, adicione ao arquivo pom.xml uma dependência do Spring Boot o pacote do Thymeleaf.

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

Agora, crie um controller para lidar com as requisições relacionadas à interface de usuário (Views).

```java
package network.webtech.labspringmvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorld {
  
    // Requisição simples
    @GetMapping("/")
    public String index (Model model) {
	// Prepara um modelo com os dados a serem utilizados pelo template
        model.addAttribute("message", "Bem vindo ao Lab Spring MVC!");

        // Informa qual template será utilizado (home.html))
        return "home";
    }

    // Passando parâmetros no path da URL
    @GetMapping("/message/{msg}")
    public String message (@PathVariable (value="msg") String msg, Model model) {
        model.addAttribute("message", msg);
        return "home";
    }

}
```

Crie um template ThymeLeaf (por exemplo, `home.html`) na pasta `resources/templates` para renderizar a view.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Lab Spring MVC</title>
</head>
<body>
    <h1 th:text="${message}"></h1>
</body>
</html>
```

### Etapa 4: Implementação de Controllers para APIs Rest

**Descrição:** Nesta etapa, vamos implementar controllers para lidar com as requisições HTTP voltadas para acesso a endpoints REST para as operações CRUD. O Spring Boot já fornece todos os recursos para isso.

**Conhecimentos Fundamentais:**

* Spring MVC: Framework para desenvolvimento de aplicações web.
* APIs REST: Conjunto de padrões de arquitetura para comunicação entre sistemas.
* Object-Relational Mapping (ORM): Mapeamento de objetos para dados armazenados em um banco de dados relacional.
* JPA (Java Persistence API), Hibernate.
* Bancos de Dados Relacionais: Estruturas de dados que armazenam informações organizadas em tabelas relacionadas.

**Produto Esperado:**
Controllers implementados com suporte para endpoints de uma API REST que faz interfaces para operações de CRUD com entidades da aplicação.

---

#### Passo a Passo:

**Configuração do servidor PostgreSQL no Render.com**

Siga os passos descritos a seguir:

* Crie uma conta no [Render](https://render.com/) ou acesse sua conta existente.
* Crie um novo serviço e selecione "PostgreSQL" como o tipo de serviço.
* Siga as instruções para configurar e provisionar o servidor PostgreSQL.
* Anote as credenciais de acesso (host, porta, usuário, senha, nome do banco de dados).

##### Implementação das classes Model e Repository

Para iniciar, precisamos adicionar uma dependência importante relacionada ao suporte do Sprint Boot para o JPA.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
```

```
import javax.persistence.*;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private double preco;

    // getters e setters
}
```

**Implementação dos Controllers para Views**

Para montar nossa camada de views, vamos utilizar um mecanismo de templates utilizado pelo Spring Boot que é o Thymeleaf. O mecanismo de templates promove a separação da camada de interface da lógica do sistema.

##### Implementação dos Controllers para endpoints de uma API Rest

Nesse momento vamos montar uma API Rest fornecendo acesso a um recurso de produtos. Para iso, vamos criar um novo controller REST, anotado com `@RestController` para lidar com as requisições relacionadas à API.

```java
package network.webtech.labspringmvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class Produtos {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtoService.listarProdutos();
    }

    @PostMapping
    public Produto adicionarProduto(@RequestBody Produto produto) {
        return produtoService.adicionarProduto(produto);
    }

    // Implemente métodos para atualizar, deletar e buscar produtos...
}
```

d. Implemente os métodos no service correspondente para realizar as operações CRUD.

Com isso, você terá implementado controllers para lidar com requisições voltadas para a obtenção de views do padrão MVC e para acesso a endpoints REST para as operações CRUD com entidades da aplicação. Essa etapa é fundamental para definir a interação entre o front end e o back end da aplicação.
