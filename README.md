# O código funciona porque o número de invesões é sempre par quando ele te permite jogar

Para que um jogo desse estilo de quebra cabeça que desliza funcione, é preciso que o número de inversões seja par; as inversões acontecem quando um número maior está da esquerda pra direita à frente de um menor

Para preservar um padrão, já que a única maneira possível de ganhar é se uma combinação aleatória das peças permitir chegar nesse resultado:

1 - 2 - 3 - 4

5 - 6 - 7 - 8

9 - 10 -11 - 12

13 - 14 - 15 - X

ou seja, o número de inversões precisa ser par para que se preserve o padrão, no caso de vencer, haverá nenhuma inversão nesse estado atual, porém, se daí já houver inversões de número ímpar, se tornará impossível de resolver

E o código para isso é esse:

 private boolean ehSolucionavel(List<String> valores) {
        int inversoes = 0;
        int espacoVazioLinha = 0;
        // Conta o número de inversões
        for (int i = 0; i < valores.size(); i++) {
            if (valores.get(i).equals("")) {
                espacoVazioLinha = i / 4 + 1;
                continue;
            }
            for (int j = i + 1; j < valores.size(); j++) {
                if (valores.get(j).equals("")) {
                    continue;
                }
                if (Integer.parseInt(valores.get(i)) > Integer.parseInt(valores.get(j))) {
                    inversoes++;
                }
            }
        }
        // Para uma matriz 4x4, o quebra-cabeça é solucionável se:
        // O número de inversões é par e o espaço vazio está em uma linha ímpar (contando de baixo para cima)
        // O número de inversões é ímpar e o espaço vazio está em uma linha par (contando de baixo para cima)
        int linhaEspacoVazioDeBaixo = 4 - espacoVazioLinha + 1;
        return (inversoes % 2 == 0 && linhaEspacoVazioDeBaixo % 2 == 1) ||
               (inversoes % 2 == 1 && linhaEspacoVazioDeBaixo % 2 == 0);
    }
