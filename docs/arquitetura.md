## 🧱 Arquitetura

Este projeto utiliza arquitetura em camadas (Layered), separando:

- Controllers
- Services
- Repositories

```
Estrutura:

/controllers → recebe requisição
/services → regras de negócio
/repositories → banco de dados
/models → entidades
```

## 🥇 Melhor escolha

_Java + Spring Boot_

- O ecossistema do Spring Boot praticamente empurra você pra usar arquitetura em camadas:
  - Controller → Service → Repository → Model

- Integra fácil com padrões como:
  - DAO / Repository
  - Service Layer
  - Factory, Singleton, Strategy

- Forte tipagem → ajuda a organizar melhor o projeto
- Muito usado em faculdade e mercado → professores costumam gostar 😅

👉 Se o objetivo é aprender arquitetura de verdade, Java é uma escolha muito sólida.
