//estudando GUI

import javax.swing.*;
import java.awt.event.*;

//classse que implementa a interface ActionListener
public class EstudandoGUI extends JFrame implements ActionListener {
    private JButton button;

    // construtor da classe
    public EstudandoGUI() {

        // alocando button na memoria
        button = new JButton("Clique aqui!");

        // adicionando o ActionListener ao button
        button.addActionListener(this);

        // adicionando o button ao JFrame sem o ContentPane
        //add(button);

        // configurando o JFrame
        this.getContentPane().add(button);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300, 200);
        this.pack();
        this.setVisible(true);
    }

    // sobreescrevendo o método ActionPerformed da interface ActionListener
    // esse é o callback que será chamado quando o button for clicado
    @Override
    public void actionPerformed(ActionEvent e) {
        // verificando se o evento foi gerado pelo button
        if (e.getSource() == button) {
            // exibindo uma mensagem quando o button for clicado
            System.out.println("Button clicado!");
            JOptionPane.showMessageDialog(this, "Hello Word GUI!");
        }
    }

    // método para iniciar a aplicação
    public static void main(String[] args) {
        new EstudandoGUI();
    }
}
