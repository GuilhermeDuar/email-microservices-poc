# 📦 POC - Microservice Email

Este projeto é uma **prova de conceito** de um microsserviço para **envio de emails**, implementado em Java e executado em um container Docker.  
A aplicação aguarda o recebimento de uma **mensagem via RabbitMQ**, vinda de outro microsserviço, contendo as informações necessárias para realizar a **notificação via e-mail**. Por fim registra no banco de dados o status e conteúdo enviado.

---

## ⚙️ Tecnologias Utilizadas

### 🧑‍💻 Linguagem
- **Java 21**

### 🚀 Frameworks e Bibliotecas
- **Spring Boot**:
    - `spring-boot-starter-web` – Criação da API REST
    - `spring-boot-starter-amqp` – Integração com o RabbitMQ
    - `spring-boot-starter-data-jpa` – Acesso a dados com JPA
    - `spring-boot-starter-validation` – Validação de dados de entrada
- **PostgreSQL Driver**
- **Lombok** – Redução de código boilerplate
- **Maven** – Gerenciamento de dependências e build

### 🗄 Banco de Dados
- **PostgreSQL**:
    - Executado via Docker
    - Volume persistente para manter os dados entre reinicializações

### 📬 Mensageria
- **RabbitMQ**:
    - Utilizado como broker de mensagens
    - Configurado com **exchange do tipo `direct`** para envio de mensagens a filas específicas (no caso, a default).

---

## ▶️ Como Executar o Projeto

1. **Configuração dos arquivos:**
    - **docker-compose.yml** para orquestrar a imagens, volume e containers:
       ```yaml
        services:
          app:
            build: {caminho para o Dockerfile}
            ports:
              - "{origem}:{destino}"
            depends_on:
              - {nome do container de banco}
          postgres:
            image: postgres:{versão}
            container_name: {nome do container}
            environment:
              POSTGRES_DB: {nome do banco}
              POSTGRES_USER: {usuário}
              POSTGRES_PASSWORD: {senha}
            ports:
                - "{origem}:{destino}"
            volumes:
                - {nome do volume}:/var/lib/postgresql/data
        volumes:
          user_data:
            name: {nome do volume}
       ```

    - **Dockerfile** para compilar utilizando maven e gerar a imagem da aplicação
      ```Dockerfile
      FROM maven:{versão do maven} AS build
      WORKDIR /app
      COPY . .
      RUN mvn clean package
 
      FROM {versão da jdk}
      COPY --from=build /app/target/*.jar app.jar
      ENTRYPOINT ["java", "-jar", "/app.jar"]
      ```
        - Versões disponíveis no docker hub:
            - Maven: https://hub.docker.com/_/maven
            - Eclipse Temurin JDK: https://hub.docker.com/_/eclipse-temurin

    - **application.yml** para configurar:
        - Aplicação
        - Banco de dados
        - Configurações do RabbitMQ (URL, filas)
          ```yaml
          server:
            port: 8080
     
          spring:
            application:
            name: user
          datasource:
            url: jdbc:postgresql://{servidor}:5432/ms-user
            username: {usuário}
            password: {senha}
          jpa:
            hibernate:
            ddl-auto: update
          rabbitmq:
            addresses: {amqps url}
          mail:
           host: {smtp desejado}
           port: {porta}
           username: {email do remetente}
           password: {senha de API}
           properties:
            mail:
              smtp:
                auth: true
                starttls:
                  enable: true
     
          broker:
            queue:
              email:
                name: {nome da fila}
          ```
2. **Clone o repositório**
    - Aqui mesmo no GitHub você pode achar um botão "<> Code" no topo da página, nele é possível baixar o repositório.

3. **Executar o docker-compose**
    ```bash
    docker-compose up
    ```
### **Aplicação configurada!🎉**

