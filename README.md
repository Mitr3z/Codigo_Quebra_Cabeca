# O código funciona porque o número de invesões é sempre par quando ele te permite jogar

Para que um jogo desse estilo de quebra cabeça que desliza funcione, é preciso que o número de inversões seja par; as inversões acontecem quando um número maior está da esquerda pra direita à frente de um menor

Para preservar um padrão, já que a única maneira possível de ganhar é se uma combinação aleatória das peças permitir chegar nesse resultado:

1 - 2 - 3 - 4

5 - 6 - 7 - 8

9 - 10 -11 - 12

13 - 14 - 15 - X

ou seja, o número de inversões precisa ser par para que se preserve o padrão, no caso de vencer, haverá nenhuma inversão nesse estado atual, porém, se daí já houver inversões de número ímpar, se tornará impossível de resolver
