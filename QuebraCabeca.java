package quebracabeca;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class QuebraCabeca extends JFrame implements ActionListener {
    
    // Matriz 4x4 de botões
    private JButton[][] botoes;
    
    // Posição do espaço vazio
    private int espacoVazioLinha;
    private int espacoVazioColuna;
    
    // Painel principal
    private JPanel painelPrincipal;
    
    // Construtor
    public QuebraCabeca() {
        super("Tiles Puzzle");
        
        // Configurações do JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        
        // Inicializa o painel principal
        painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridLayout(4, 4, 5, 5));
        
        // Inicializa a matriz de botões
        botoes = new JButton[4][4];
        
        // Cria os botões e adiciona ao painel
        criarBotoes();
        
        // Adiciona o painel ao frame
        add(painelPrincipal);
        
        // Embaralha os botões
        embaralhar();
        
        // Exibe o frame
        setVisible(true);
    }
    
    // Método para criar os botões
    private void criarBotoes() {
        int contador = 1;
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i == 3 && j == 3) {
                    // O último botão é o espaço vazio
                    botoes[i][j] = new JButton("");
                    botoes[i][j].setBackground(Color.WHITE);
                    espacoVazioLinha = i;
                    espacoVazioColuna = j;
                } else {
                    // Cria os botões numerados
                    botoes[i][j] = new JButton(String.valueOf(contador++));
                    botoes[i][j].setBackground(new Color(245, 231, 0));
                }
                
                // Adiciona o ActionListener
                botoes[i][j].addActionListener(this);
                
                // Adiciona o botão ao painel
                painelPrincipal.add(botoes[i][j]);
            }
        }
    }
    
    // Método para embaralhar os botões
    public void embaralhar() {
        // Lista para armazenar os valores dos botões
        List<String> valores = new ArrayList<>();
        
        // Adiciona os valores à lista
        for (int i = 1; i <= 15; i++) {
            valores.add(String.valueOf(i));
        }
        valores.add(""); // Espaço vazio
        
        // Embaralha a lista
        randomizarBotoes(valores);
        
        // Atualiza os botões com os valores embaralhados
        int indice = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String valor = valores.get(indice++);
                botoes[i][j].setText(valor);
                
                // Atualiza a posição do espaço vazio
                if (valor.equals("")) {
                    espacoVazioLinha = i;
                    espacoVazioColuna = j;
                    botoes[i][j].setBackground(Color.WHITE);
                } else {
                    botoes[i][j].setBackground(new Color(245, 231, 0));
                }
            }
        }
        
        // Verifica se o quebra-cabeça é solucionável
        while (!ehSolucionavel(valores)) {
            // Se não for solucionável, embaralha novamente
            Collections.shuffle(valores);
            
            // Atualiza os botões com os novos valores
            indice = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    String valor = valores.get(indice++);
                    botoes[i][j].setText(valor);
                    
                    // Atualiza a posição do espaço vazio
                    if (valor.equals("")) {
                        espacoVazioLinha = i;
                        espacoVazioColuna = j;
                        botoes[i][j].setBackground(Color.WHITE);
                    } else {
                        botoes[i][j].setBackground(new Color(245, 231, 0)); // Azul claro
                    }
                }
            }
        }
    }
    
    // Método para randomizar os botões
    public void randomizarBotoes(List<String> valores) {
        Collections.shuffle(valores);
    }
    
    // Método para verificar se o quebra-cabeça é solucionável
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
        // 1. O número de inversões é par e o espaço vazio está em uma linha ímpar (contando de baixo para cima)
        // 2. O número de inversões é ímpar e o espaço vazio está em uma linha par (contando de baixo para cima)
        int linhaEspacoVazioDeBaixo = 4 - espacoVazioLinha + 1;
        
        return (inversoes % 2 == 0 && linhaEspacoVazioDeBaixo % 2 == 1) ||
               (inversoes % 2 == 1 && linhaEspacoVazioDeBaixo % 2 == 0);
    }
    
    // Método para mover um botão
    public void moverBotao(int linha, int coluna) {
        // Verifica se o botão está adjacente ao espaço vazio
        if ((Math.abs(linha - espacoVazioLinha) == 1 && coluna == espacoVazioColuna) ||
            (Math.abs(coluna - espacoVazioColuna) == 1 && linha == espacoVazioLinha)) {
            
            // Troca o texto do botão com o espaço vazio
            botoes[espacoVazioLinha][espacoVazioColuna].setText(botoes[linha][coluna].getText());
            botoes[linha][coluna].setText("");
            
            // Troca as cores
            botoes[espacoVazioLinha][espacoVazioColuna].setBackground(new Color(245, 231, 0));
            botoes[linha][coluna].setBackground(Color.WHITE);
            
            // Atualiza a posição do espaço vazio
            espacoVazioLinha = linha;
            espacoVazioColuna = coluna;
            
            // Verifica se o jogador venceu
            if (verificarVitoria()) {
                JOptionPane.showMessageDialog(this, "Parabéns! Você venceu!", "Vitória", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    // Método para verificar se o jogador venceu
    public boolean verificarVitoria() {
        int contador = 1;
        
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                // Verifica se o último botão é o espaço vazio
                if (i == 3 && j == 3) {
                    if (!botoes[i][j].getText().equals("")) {
                        return false;
                    }
                } else {
                    // Verifica se os outros botões estão na ordem correta
                    if (!botoes[i][j].getText().equals(String.valueOf(contador++))) {
                        return false;
                    }
                }
            }
        }
        
        return true;
    }
    
    // Implementação do ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        // Encontra o botão clicado
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (e.getSource() == botoes[i][j]) {
                    // Move o botão
                    moverBotao(i, j);
                    return;
                }
            }
        }
    }
    
    // Método principal
    public static void main(String[] args) {
        new QuebraCabeca();
    }
}