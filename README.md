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
package com.exemplo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

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


### Etapa 3: Implementação de Controllers, Views e Endpoints REST

**Descrição:** Nesta etapa, vamos implementar controllers para lidar com as requisições HTTP voltadas para a obtenção de Views do padrão MVC e para acesso a endpoints REST para as operações CRUD. Utilizaremos o Spring MVC para criar controllers que suportem tanto views baseadas em templates como endpoints de uma API REST.

**Conhecimentos Fundamentais:**
- Spring MVC: Framework que facilita o desenvolvimento de aplicações web seguindo o padrão Model-View-Controller.
- APIs REST: Conjunto de padrões e princípios de arquitetura de software para a criação de serviços web que utilizam HTTP de forma consistente.

**Produto Esperado:**
Controllers implementados com suporte a views baseadas em templates e para endpoints de uma API REST que faz interfaces para operações de CRUD com entidades da aplicação.

---

#### Passo a Passo:

1. **Implementação dos Controllers para Views e Endpoints REST:**

   a. Crie um controller para lidar com as requisições relacionadas à interface de usuário (Views).

```java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/home")
    public String home(Model model) {
        // Lógica para carregar dados e passá-los para a view
        model.addAttribute("message", "Bem-vindo à nossa aplicação!");
        return "home"; // Retorna o nome do template a ser renderizado
    }

    // Outros métodos para lidar com outras páginas da aplicação...
}
```

b. Crie um template ThymeLeaf (por exemplo, `home.html`) na pasta `resources/templates` para renderizar a view.

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Minha Aplicação</title>
</head>
<body>
    <h1 th:text="${message}"></h1>
    <!-- Outros elementos HTML e lógica de template -->
</body>
</html>
```

c. Crie um controller REST para lidar com as requisições relacionadas à API.

```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

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


### Etapa 5: Definição dos Services, Models e Repositories integrados com o Banco de Dados

1. **Descrição**: Definir as entidades (models) da aplicação conectadas ao banco de dados, criar interfaces de repositório (repositories) para isolar a lógica de acesso aos dados e implementar classes de serviço para encapsular a lógica de negócio da aplicação..
2. **Conhecimentos Fundamentais**: Injeção de dependência, separação de responsabilidades, JPA (Java Persistence API), Hibernate, Padrão arquitetural MVC.
3. **Produto Esperado**: Entidades Java criadas e anotadas com as respectivas anotações do JPA, interfaces de repositório definidas para cada entidade. Classes de serviço criadas para cada entidade, com métodos para executar operações específicas de negócio.

### Etapa 6: Integração do Back End com o Front End

1. **Descrição**: Integrar o back end desenvolvido com o front end utilizando requisições HTTP.
2. **Conhecimentos Fundamentais**: Consumo de APIs REST, comunicação entre front end e back end.
3. **Produto Esperado**: Front end conectado ao back end, permitindo que as operações CRUD sejam realizadas de forma eficiente.

### Etapa 7: Implantação da Aplicação em Containers Docker

1. **Descrição**: Preparar a aplicação para implantação em ambiente de produção, utilizando containers Docker.
2. **Conhecimentos Fundamentais**: Implantação de aplicações em Docker, Dockerfile multi-stage, docker-compose para ambiente de produção.
3. **Produto Esperado**: Aplicação empacotada em containers Docker, pronta para ser implantada em um ambiente de produção ou em servidores na nuvem.

Ao seguir este roteiro, o aluno terá uma compreensão sólida dos conceitos fundamentais de desenvolvimento de software com Spring Boot MVC, integração de front end e back end, e implantação de aplicações em containers Docker. Cada etapa proporciona pequenas entregas que permitem ao aluno perceber sua progressão no aprendizado.
