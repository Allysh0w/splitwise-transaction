# splitwise-Transaction

Dado uma lista de tuplas T = [(k1 -> -v), (k2 -> +v), …], onde **k** é a chave identificadora de usuários de um sistema de pagamento instantâneo e **v** o valor de transações de crédito ou débito neste sistema.
 Considerando que as transações precisam ser enviadas para um sistema externo que cobra por transação processada,
 que cada usuário possui várias transações na lista T e que o sistema externo apenas aceita mensagens que representam uma transferência de valor de um usuário pagador para um usuário recebedor;
 escreva uma função que reduza as transações da lista T e gere uma nova lista T’ que contém as mensagens a serem enviadas ao sistema externo.
 
Exemplo de input:
T = [(u1 -> -10), (u2 -> +10), (u2 -> -3), (u3 -> +3), (u2 -> -1), (u1 -> +1)]
 
Exemplo de output:
T’ = [(u1, u2, 9), (u2, u3, 3)]
 
Implemente o algoritmo utilizando uma das seguintes linguagens: Scala, C, C++, GoLang ou Rust.
 
Apresente a complexidade de tempo (Big O) da sua solução.
