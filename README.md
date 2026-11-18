# Cidade em Dia de Chuva — Edição Web

Global Solution 2026.2 — Object-Oriented Programming and Java Web Development

---

## Integrantes

| Nome | RM |
|---|---|
| Roberta Barbosa da Silva | 562139 |
| Felipe Lima Gomes | 561752 |
| Isabelli dos Santos Rodrigues | 564027 |

---

## RM da equipe

562139 + 561752 + 564027 = **1687918**

---

## Parâmetros

| Parâmetro | Cálculo | Resultado |
|---|---|---|
| **P1** — Prefixo das tabelas | 1687918 % 1000 = 918 | **GS918** (GS918_BAIRRO, GS918_TRECHO, GS918_LEITURA) |
| **P2** — Limiar de alerta L | 10 + 2 * (1687918 % 4) = 10 + 2 * 2 = 14 | **L = 14 cm** (atenção a partir de 7 cm) |
| **P3** — Atributo extra de Bairro | (1687918 / 10) % 3 = 168791 % 3 = 2 | **possuiPiscinao** (Boolean) |
| **P4** — Ordenação da lista de trechos | (1687918 / 100) % 3 = 16879 % 3 = 1 | **Altitude crescente** e, no empate, logradouro |
| **P5** — Exclusão de trecho que tem leituras | (1687918 / 1000) % 2 = 1687 % 2 = 1 | **Cascata** (exclui as leituras do trecho e depois o trecho) |

---

## Versão escolhida

**Versão web (HTML):** controllers com `@Controller` e telas com Thymeleaf.

---

## Forma dos DTOs

Um único DTO por entidade (`BairroDTO`, `TrechoDTO` e `LeituraDTO`), escrito como **classe com Lombok**
(`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`), mais o `PainelItemDTO` para o painel do bairro.