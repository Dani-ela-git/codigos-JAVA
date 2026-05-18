import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ConversorGUI extends JFrame implements ActionListener {

    //componentes da interface
    private JLabel labelMilhas;
    private JTextField campoMilhas;
    private JButton buttonConverter;
    private JTextArea areaResultado;

    //construtor da classe
    public ConversorGUI() {

        //instanciar os componentes
        labelMilhas = new JLabel("Digite a distância em milhas:");
        campoMilhas = new JTextField(10); //10 colunas de largura
        buttonConverter = new JButton("Converter");
        areaResultado = new JTextArea(10, 20); //10 linhas e 20 colunas
        areaResultado.setEditable(false); //tornar a área de resultado não editável

        //adicionar um ContentPane com um layout
        Container cp = this.getContentPane();
        cp.setLayout(new BorderLayout());

        //painel superior (north) com FlowLayout
        JPanel painelSuperior = new JPanel(); //flowlayout por padrão
        painelSuperior.add(labelMilhas);
        painelSuperior.add(campoMilhas);
        painelSuperior.add(buttonConverter);
        cp.add(painelSuperior, BorderLayout.NORTH);

        //posicionando no centro o botão de converter com scroll
        cp.add(new JScrollPane(areaResultado), BorderLayout.CENTER);

        //adicionar ActionListener ao botão
        buttonConverter.addActionListener(this);

        //configurar o JFrame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    //sobrescrevendo o ActionPerformed para o callback do botão
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonConverter) {
            try {
                //lê a string do campo de texto e converte para double
                double milhas = Double.parseDouble(campoMilhas.getText());
                //converte milhas para quilômetros (1 milha = 1.60934 quilômetros)
                double quilometros = milhas * 2.540;
                //exibe o resultado na área de texto
                areaResultado.setText("Valor em centímetros: " + quilometros);
            } catch (NumberFormatException ex) {
                //trata o erro caso o usuário não digite um número válido
                areaResultado.setText("Erro: Por favor, digite um número válido.");
            }
        }
    }

    //método main para iniciar a aplicação
    public static void main(String[] args) {
        new ConversorGUI();
    }
}