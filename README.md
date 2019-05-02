# Processadora
O sistema simula o saque de um cartão na visão do emissor. Realiza validações para autorizar ou não da transação.
Possui uma API que disponibiliza algumas funções, verificação de saldo do cartão, cartões disponíveis entre outras descritas abaixo.
> O cartão esta cadastrado em um banco psi, e o sistema não faz acesso direto a base para verificar o saldo do cartão. Então é
necessario descobrir a conta associada ao cartão para realizar a transação.

> O sistema esta disponibilizado em uma porta (socket TCP) recebe e retorna o json com status da transação pela mesma.

> O sistema permite conexão telnet através da porta 9876 (cadastrada) e multiplos clientes esta definido um limite de 50 clientes concorrentes.  
## Features
Simular uma resposta a um solicitação de saque.
- Receber um Json com a solicitação e responder um json com a resposta.
- API para consulta de saldo
## Tecnologias/Frameworks usados
Construido com:
- [Spring](https://spring.io/)
- [Java 8](https://www.oracle.com/technetwork/pt/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven](https://maven.apache.org/)
- [Lombok](https://projectlombok.org/)
- [Docker](https://docs.docker.com/install/)
- [Rabbitmq](https://www.rabbitmq.com/)
- [Flyway](https://flywaydb.org/)
- [Postgresql](https://www.postgresql.org/)
- ...
## Iniciando
Clone o repositório
```shell
git clone https://github.com/rhuambarbosa/withdraw-server.git
```
Subindo o Rabbitmq 
```shell
docker run -d --restart=always --name rabbitmq -p 5672:5762 -p 5671:5671 -p 15672:15672 rabbitmq:3-management
```
Subindo o banco de dados 
```shell
docker run -d --restart=always --name processadoradb -e POSTGRES_PASSWORD=processadoradb%#r2 -p 5433:5432 postgres:9.3
```
### Building
Local
```shell
mvn clean package
```
Docker
```shell
mvn clean package docker:build --batch-mode release:update-versions 
```
gera uma imagem docker rhuambarbosa/withdraw-server:<version>
## Tests
```shell
Subir o sistema de modo local ou em docker

prompt(digite):telnet localhost 9876
Informe o json com a solicitação da transação de saque.
ex: 
{"action": "withdraw","cardnumber":"1234567890123456","amount": "1,10"}

Será experado um json de resposta:
ex:
{

"action": "withdraw",

"code":"00",

"authorization_code": "123456"

}  
```
## Deploying
Local: Dentro da pasta raiz do projeto onde exite o maven
```shell
mvn spring-boot:run
``` 
Docker: docker run -d --restart=always -e "SPRING_PROFILES_ACTIVE=prod" --name withdraw-server -p 9876:9876 -p 8080:8080 rhuambarbosa/withdraw-server:<tag> 
## Funcionalidades API
```shell
| Verbo     | Funcionalidade    | Descrição |
| --------|---------|-------|
| Get | http://localhost:8080/balances/{cardnumber} | Extrato com as 10 ultimas transações |
| Get | localhost:8080/psi/accounts | Retorna a conta de um cartão, recebe como parametro o cardNumber e se esta ativo |
| Get | localhost:8080/psi/creditCard | Retorna o Id dos cartões, recebe como parametro o page e o size da paigna desejada |
| Get | http://localhost:8080/actuator/health |
``` 
Na pasta resources/static existe o arquivo a ser importado no postman com cada uma das funcionalidades.
## Cartões iniciais
```shell
| Cartão           | Saldo       |
| -----------------|-------------|
| 1234567890123456 | R$ 1.000,00 |
| 1234567774523456 | R$ 1.000,00 |
| 1234567899865034 | R$ 2.000,00 |
| 7450317890123456 | R$ 500,00   |
``` 
Os demais cartões podem ser consultadas através de funcionalidades da API
## Observações
Algumas maquina não reconhece o rabbit no localhost então é necessário informar o ip do rabbit no properties
Verificar o ip do container
```shell
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' rabbitmq
``` 