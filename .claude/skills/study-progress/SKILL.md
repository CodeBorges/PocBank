---
name: study-progress
description: Salva e consulta o progresso de estudos do PocBank (preparação para vaga Java sênior) com score de aprendizado por conceito, baseado em evidências. Usar quando o usuário pedir para salvar/ver progresso, encerrar ou iniciar uma sessão de estudo, perguntar "como estou", "meu score", "o que revisar", ou ao concluir um exercício da trilha.
---

# Progresso de estudos

O arquivo de progresso é `study/PROGRESS.md`, na raiz do repositório. Ele é a
fonte da verdade: leia antes de alterar e preserve o que já existe. A pasta
`study/` é privada (está no `.gitignore`): nunca a adicione ao Git.

As perguntas de revisão ficam com a skill `study-quiz` e o banco dela,
`study/QUIZ.md`. Esta skill cuida do placar, do diário e da trilha.

Esta skill tem três modos. Descubra pelo pedido qual usar.

## Modo 1: salvar progresso (fim de sessão ou fim de exercício)

1. Leia `study/PROGRESS.md` e revise o que aconteceu na sessão: código que o
   usuário escreveu, erros cometidos, perguntas respondidas, explicações que
   ele deu ou que precisou receber.
2. Atualize o score de cada conceito trabalhado usando a **rubrica** abaixo.
   Todo score precisa de **evidência concreta** da sessão (o que ele fez ou
   disse). Sem evidência, o score não muda.
3. Recalcule a média de cada área (uma casa decimal) e o score geral (média
   das áreas já iniciadas, ou seja, com pelo menos um conceito acima de 0).
4. Adicione uma entrada no topo do **Diário de sessões** (data absoluta, o
   que foi feito, erros que viraram lição, o que ficou pendente).
5. Atualize **Revisar na próxima sessão**: todo conceito com score ≤ 2, e
   também conceitos que caíram de score. Erros novos da sessão também viram
   perguntas no banco do `study-quiz` (caixa 1, revisão no dia seguinte).
6. Atualize **Onde paramos** com o próximo passo exato da trilha.
7. Mostre ao usuário um resumo curto: conceitos que subiram (antes → depois),
   o score geral e os 2 ou 3 itens de revisão. Não despeje o arquivo inteiro.

## Modo 2: início de sessão

1. Leia `study/PROGRESS.md`.
2. Rode um quiz curto de revisão com a skill `study-quiz` (3 perguntas, a
   não ser que o usuário peça outro número). É ela que atualiza scores,
   dificuldades e o banco de perguntas.
3. Antes de retomar a trilha a partir de "Onde paramos", leia
   "Ajustes na trilha" e aplique no exercício do dia.

## Modo 3: consulta ("como estou?")

Mostre o score geral, a tabela de áreas, os 3 conceitos mais fortes, os 3
mais fracos e o próximo passo. Não altere o arquivo.

## Rubrica (0 a 5)

| Score | Nível | Evidência exigida |
|---|---|---|
| 0 | Não visto | — |
| 1 | Apresentado | Recebeu a explicação, ainda não aplicou |
| 2 | Aplicado com ajuda | Fez funcionar depois de dicas ou correções |
| 3 | Aplicado sozinho | Fez certo sem dica direta |
| 4 | Explica o porquê | Justificou com as próprias palavras, incluindo trade-offs |
| 5 | Domina | Aplicou em contexto novo **ou** respondeu bem uma pergunta de entrevista sobre o tema sem consulta |

Regras:
- Seja honesto e conservador. O objetivo é preparar para entrevista, não
  agradar. Na dúvida entre dois scores, use o menor.
- Score 5 só com pergunta de entrevista respondida numa sessão **diferente**
  daquela em que o conceito foi aprendido (retenção, não memória de curto prazo).
- Explicação escrita pelo Claude a pedido do usuário conta como score 1 até
  que ele a explique de volta.

## Áreas

Use sempre estas áreas, para que o placar seja comparável entre sessões.
Conceitos novos entram na área que fizer mais sentido.

1. Fundamentos Java (linguagem, JVM, coleções, exceções, concorrência)
2. TDD e testes
3. Clean Code
4. SOLID
5. Design Patterns
6. DDD
7. Arquitetura (hexagonal, camadas, ADR)
8. Persistência e transações
9. Frameworks (Quarkus, CDI, REST)

## Formato de `study/PROGRESS.md`

Mantenha exatamente estas seções, nesta ordem:

```markdown
# Progresso de estudos

**Score geral:** X.X / 5  ·  **Última sessão:** AAAA-MM-DD

## Onde paramos
## Placar por área          (tabela: Área | Score | Conceitos)
## Conceitos                (uma tabela por área: Conceito | Score | Evidência | Atualizado)
## Revisar na próxima sessão
## Dificuldades observadas  (padrões que se repetem; mantido pelo study-quiz)
## Ajustes na trilha        (como as dificuldades entram nos próximos exercícios)
## Diário de sessões        (mais recente primeiro)
```
