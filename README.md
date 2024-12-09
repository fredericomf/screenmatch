# screenmatch
Projeto para o curso "Java trabalhando com lambdas e streams - Spring Framework"



# Banco de dados

## Postgres

Instalado no Docker para facilitar o desenvolvimento e não "sujar" o Sistema Operacional de trabalho.

_Nota: não criei um volume para esse container porque a aplicação é muito simples (somente estudo), não vejo necessidade de persistência_

### Rodar:
```bash
sudo docker run -p 5432:5432 -e POSTGRES_PASSWORD=admin -d postgres
```

Caso queira rodar com volume, eu preparei um arquivo do Docker Compose para rodar:
```bash
sudo docker compose up
```

# Utilidades

## Links

### Documentação sobre Derived Queries

https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

### Artigo sobre SQL Join

https://www.alura.com.br/artigos/join-e-seus-tipos