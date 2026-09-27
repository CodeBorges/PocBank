---
name: study-quiz
description: Faz perguntas de revisão do PocBank (preparação para vaga Java sênior) com base no progresso de estudos, usando repetição espaçada. Fixa o que já foi aprendido, volta nos pontos com dúvida e ajusta score e próximos exercícios conforme as respostas. Usar quando o usuário pedir quiz, perguntas, revisão, "me testa", simulado de entrevista, ou no início de uma sessão de estudo.
---

# Quiz de revisão

Arquivos (ambos privados, em `study/`, fora do Git):
- `study/PROGRESS.md`: scores por conceito, dificuldades e trilha. Mantido
  pela skill `study-progress`. A **rubrica de score (0 a 5) está lá**, em
  `.claude/skills/study-progress/SKILL.md`. Use a mesma, não invente outra.
- `study/QUIZ.md`: banco de perguntas com histórico de respostas. Mantido
  por esta skill.

Leia os dois antes de começar.

## 1. Montar o quiz

Tamanho padrão: **5 perguntas**. O usuário pode pedir outro número, ou
"simulado de entrevista" (8 perguntas, só nível 3 e 4, sem dicas).

Escolha as perguntas nesta prioridade:
1. **Vencidas:** perguntas do banco com "Próxima revisão" ≤ hoje, caixa
   mais baixa primeiro.
2. **Pontos fracos sem pergunta:** conceitos com score ≤ 2, ou listados em
   "Revisar na próxima sessão", que ainda não têm pergunta no banco. Crie
   uma pergunta nova.
3. **Retenção:** 1 pergunta de um conceito com score ≥ 3, para confirmar
   que ele não foi esquecido.

Não repita a mesma pergunta duas vezes no mesmo quiz. Evite repetir o texto
exato de uma pergunta já respondida: reformule ou mude o exemplo, para medir
entendimento e não memorização da frase.

## 2. Nível da pergunta pelo score do conceito

| Score do conceito | Nível | Tipo de pergunta |
|---|---|---|
| 0 a 1 | 1. Reconhecer | "O que é...?", "Qual a diferença entre X e Y?" |
| 2 | 2. Prever | Mostrar um trecho curto de código: "o que acontece?", "esse teste passa?", "onde está o bug?" |
| 3 | 3. Justificar | "Por que...?", "O que quebra se...?", trade-off entre duas opções |
| 4 a 5 | 4. Entrevista | Pergunta aberta de entrevista sênior, cenário novo, fora do contexto do `Money` |

Prefira situações do próprio PocBank e erros que o usuário já cometeu (o
"Diário de sessões" lista os erros que viraram lição). Código nas perguntas:
no máximo 10 linhas.

## 3. Aplicar, uma pergunta por vez

Para cada pergunta:
1. Mostre só a pergunta, com o número (ex.: "Pergunta 2 de 5") e o conceito.
2. Peça também a **confiança**: 1 (chutei), 2 (acho que sim), 3 (tenho certeza).
3. Espere a resposta. Não dê dica antes de o usuário tentar. Se ele pedir
   dica, dê uma e registre que houve dica.
4. Avalie: **✓ certo** (acertou e sabe o porquê), **~ parcial** (ideia
   certa, mas faltou o porquê ou tem imprecisão) ou **✗ errado**.
5. Dê o feedback curto: o que estava certo, o que faltou e a resposta
   completa em poucas linhas. Se errou, explique como se fosse a primeira
   vez, com um exemplo diferente do usado antes.
6. Siga para a próxima.

**Atenção a "errado com certeza"** (✗ com confiança 3): isso indica uma
**ideia errada fixada**, que é mais grave do que não saber. Sempre explique
de onde vem o engano, e registre em "Dificuldades observadas".

## 4. Atualizar o banco (`study/QUIZ.md`)

Repetição espaçada com 5 caixas (sistema de Leitner):

| Caixa | Próxima revisão |
|---|---|
| 1 | na próxima sessão (amanhã) |
| 2 | em 3 dias |
| 3 | em 7 dias |
| 4 | em 14 dias |
| 5 | em 30 dias |

- ✓ com confiança 2 ou 3, sem dica: sobe 1 caixa.
- ✓ com confiança 1, ou com dica: fica na mesma caixa.
- ~ parcial: fica na mesma caixa, e volta para a caixa 1 se já era a segunda ~ seguida.
- ✗ errado: volta para a caixa 1.

Calcule a data com base em hoje, sempre como data absoluta (AAAA-MM-DD).
Acrescente o resultado ao histórico da pergunta: `✓`, `~` ou `✗`, com a
confiança, ex.: `✓3 ~2 ✗3`.

## 5. Atualizar o progresso (`study/PROGRESS.md`)

Com base nas respostas, e usando a rubrica da skill `study-progress`:
- **Score:** atualize os conceitos cobertos. A resposta do quiz é evidência
  (ex.: "Quiz 2026-09-28: explicou checked vs unchecked com exemplo, ✓3").
  Nível 4 de pergunta respondido com ✓, numa sessão diferente daquela em que
  o conceito foi aprendido, é a evidência que permite o score 5.
- **Dificuldades observadas:** registre padrões, não respostas isoladas.
  Ex.: "confunde o que o teste prova com o que o código faz (2 quizzes)".
- **Ajustes na trilha:** quando uma dificuldade aparecer em 2 quizzes ou
  mais, proponha como ela entra no próximo exercício. Ex.: "no `AccountId`,
  começar pelo teste negativo e fazer a prova do TDD antes do código".
  O mentor usa essa seção ao montar os próximos exercícios.
- **Revisar na próxima sessão:** remova o que ficou ✓ e inclua o que ficou ✗ ou ~.
- Recalcule as médias das áreas e o score geral.

## 6. Fechar o quiz

Mostre um resumo curto:
- Placar do quiz (ex.: 3 ✓, 1 ~, 1 ✗).
- Conceitos que subiram ou caíram de score (antes → depois).
- A dificuldade principal que apareceu, se houver, e como ela entra no próximo exercício.
- Quando é a próxima revisão.

## Formato de `study/QUIZ.md`

```markdown
# Banco de perguntas

| ID | Conceito | Nível | Pergunta | Caixa | Próxima revisão | Histórico |
|---|---|---|---|---|---|---|
| Q001 | Exceções checked vs unchecked | 1 | Qual a diferença entre... | 1 | 2026-09-28 | ✗3 ✓2 |
```

IDs são sequenciais e nunca são reaproveitados. Não guarde a resposta
esperada no arquivo: o usuário pode abri-lo.
