//Bruna Romero 11913896
//Daniela Costa da Silva 14613625


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A classe InteractiveColorMixer implementa uma aplicação GUI interativa que permite
 * aos usuários misturar cores RGB (Vermelho, Verde, Azul) usando sliders.
 * 
 * <p>
 * <b>Event Propagation Path (Caminho de Propagação do Evento):</b><br>
 * O evento ChangeEvent segue o seguinte caminho de propagação:
 * <ol>
 *   <li><b>Origem:</b> O usuário interage com um JSlider (arrasta o knob ou clica na trilha)</li>
 *   <li><b>Disparo:</b> O JSlider dispara um ChangeEvent quando seu valor é alterado</li>
 *   <li><b>Notificação:</b> O JSlider notifica todos os ChangeListener registrados</li>
 *   <li><b>Captura:</b> O método stateChanged(ChangeEvent e) do listener é chamado automaticamente</li>
 *   <li><b>Identificação:</b> Dentro do listener, identificamos qual slider gerou o evento
 *       comparando a referência do objeto (e.getSource())</li>
 *   <li><b>Processamento:</b> Obtemos o novo valor do slider e atualizamos o display</li>
 * </ol>
 * Este padrão é conhecido como <b>Observer Pattern</b> (Padrão Observador), onde o slider
 * é o "sujeito" (observable) e o ChangeListener é o "observador" (observer).
 * 
 */
public class InteractiveColorMixer extends JFrame {
    
    // Declaração dos componentes da interface gráfica
    private JSlider redSlider;      // Slider para controlar o valor do Vermelho (0-255)
    private JSlider greenSlider;    // Slider para controlar o valor do Verde (0-255)
    private JSlider blueSlider;     // Slider para controlar o valor do Azul (0-255)
    
    private JLabel redLabel;        // Label para mostrar o valor atual do Vermelho
    private JLabel greenLabel;      // Label para mostrar o valor atual do Verde
    private JLabel blueLabel;       // Label para mostrar o valor atual do Azul
    
    private JPanel colorPanel;      // Painel que exibirá a cor misturada
    private JButton resetButton;    // Botão para resetar todos os sliders
    
    //constructor da classe, configura a janela, inicializa os componentes e organiza o layout
    public InteractiveColorMixer() {
        // Configuração da janela principal (JFrame)
        setTitle("Interactive Color Mixer");           // Define o título da janela
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fecha o programa quando a janela é fechada
        setSize(500, 400);                             // Define o tamanho da janela (largura=500, altura=400)
        setLayout(new BorderLayout(10, 10));           // Layout principal: BorderLayout com espaçamento de 10px
        
        // Inicializa todos os componentes
        initializeComponents();
        
        // Organiza os componentes na janela
        setupLayout();
        
        // Torna a janela visível
        setVisible(true);
    }
    
    //inicializa os componentes da interface gráfica, configura os sliders, labels, painel de cor e o botão reset, além de adicionar os listeners para os eventos
    private void initializeComponents() {
        // ========== CRIAÇÃO DOS SLIDERS ==========
        // Slider para cor Vermelha: valor inicial 128, mínimo 0, máximo 255
        redSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
        redSlider.setMajorTickSpacing(50);      // Tick maior a cada 50 unidades
        redSlider.setMinorTickSpacing(10);      // Tick menor a cada 10 unidades
        redSlider.setPaintTicks(true);          // Mostra os ticks (marcações)
        redSlider.setPaintLabels(true);         // Mostra os números nos ticks
        
        // Slider para cor Verde: valor inicial 128, mínimo 0, máximo 255
        greenSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
        greenSlider.setMajorTickSpacing(50);
        greenSlider.setMinorTickSpacing(10);
        greenSlider.setPaintTicks(true);
        greenSlider.setPaintLabels(true);
        
        // Slider para cor Azul: valor inicial 128, mínimo 0, máximo 255
        blueSlider = new JSlider(JSlider.HORIZONTAL, 0, 255, 128);
        blueSlider.setMajorTickSpacing(50);
        blueSlider.setMinorTickSpacing(10);
        blueSlider.setPaintTicks(true);
        blueSlider.setPaintLabels(true);
        
        // ========== CRIAÇÃO DOS LABELS ==========
        redLabel = new JLabel("Red: 128", SwingConstants.CENTER);
        greenLabel = new JLabel("Green: 128", SwingConstants.CENTER);
        blueLabel = new JLabel("Blue: 128", SwingConstants.CENTER);
        
        // ========== CRIAÇÃO DO PAINEL DE COR ==========
        colorPanel = new JPanel();
        colorPanel.setBackground(new Color(128, 128, 128)); // Cor inicial: cinza (R=128, G=128, B=128)
        colorPanel.setBorder(BorderFactory.createTitledBorder("Cor Misturada")); // Borda com título
        
        // ========== CRIAÇÃO DO BOTÃO RESET ==========
        resetButton = new JButton("Resetar para Cinza (128, 128, 128)");
        
        // ========== ADICIONA OS LISTENERS ==========
        // Adiciona ChangeListener ao slider vermelho
        redSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Este método é chamado automaticamente quando o slider é movido
                int redValue = redSlider.getValue();  // Pega o valor atual do slider vermelho
                updateColor();                         // Atualiza a cor do painel
                redLabel.setText("Red: " + redValue); // Atualiza o label
                logEvent("Red", redValue);             // Registra o evento no console
            }
        });
        
        // Adiciona ChangeListener ao slider verde
        greenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int greenValue = greenSlider.getValue();
                updateColor();
                greenLabel.setText("Green: " + greenValue);
                logEvent("Green", greenValue);
            }
        });
        
        // Adiciona ChangeListener ao slider azul
        blueSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int blueValue = blueSlider.getValue();
                updateColor();
                blueLabel.setText("Blue: " + blueValue);
                logEvent("Blue", blueValue);
            }
        });
        
        // Adiciona ActionListener ao botão reset
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Este método é chamado quando o botão é clicado
                resetSliders();  // Reseta todos os sliders para o valor 128
            }
        });
    }
    
    //set up layout para organizar os componentes na janela usando BorderLayout e GridLayout
    private void setupLayout() {
        // ========== PAINEL PARA OS SLIDERS (NORTE) ==========
        JPanel slidersPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        // GridLayout: 3 linhas, 2 colunas, espaçamento horizontal=10, vertical=10
        
        // Adiciona os componentes ao painel de sliders
        slidersPanel.add(new JLabel("Vermelho (Red):", SwingConstants.RIGHT));
        slidersPanel.add(redSlider);
        slidersPanel.add(new JLabel("Verde (Green):", SwingConstants.RIGHT));
        slidersPanel.add(greenSlider);
        slidersPanel.add(new JLabel("Azul (Blue):", SwingConstants.RIGHT));
        slidersPanel.add(blueSlider);
        
        // ========== PAINEL PARA OS LABELS ==========
        JPanel labelsPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        labelsPanel.add(redLabel);
        labelsPanel.add(greenLabel);
        labelsPanel.add(blueLabel);
        
        // ========== PAINEL COMBINADO PARA CONTROLES ==========
        JPanel controlPanel = new JPanel(new BorderLayout(10, 10));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margens
        controlPanel.add(slidersPanel, BorderLayout.CENTER);
        controlPanel.add(labelsPanel, BorderLayout.SOUTH);
        
        // ========== ADICIONA TUDO À JANELA PRINCIPAL ==========
        add(controlPanel, BorderLayout.NORTH);   // Controles no topo
        add(colorPanel, BorderLayout.CENTER);    // Painel de cor no centro
        add(resetButton, BorderLayout.SOUTH);    // Botão reset na parte inferior
    }
    
    //atualiza cor
    private void updateColor() {
        // Obtém os valores atuais de cada slider
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        
        // Cria um novo objeto Color com os valores RGB
        Color mixedColor = new Color(red, green, blue);
        
        // Aplica a cor como fundo do painel
        colorPanel.setBackground(mixedColor);
        
        // Força o redesenho do painel para mostrar a nova cor imediatamente
        colorPanel.repaint();
    }
    
    //impressão detalhada no console para cada evento de mudança nos sliders
    private void logEvent(String sliderName, int value) {
        // Imprime informações detalhadas no console
        System.out.println("[EVENTO] Slider " + sliderName + " foi movido para: " + value);
        System.out.println("        Cor atual: RGB(" + 
                          redSlider.getValue() + ", " + 
                          greenSlider.getValue() + ", " + 
                          blueSlider.getValue() + ")");
        System.out.println("        Timestamp: " + System.currentTimeMillis());
        System.out.println("----------------------------------------");
    }
    
    //reseta os sliders para o valor 128
    private void resetSliders() {
        // Define o valor de cada slider para 128
        redSlider.setValue(128);
        greenSlider.setValue(128);
        blueSlider.setValue(128);
        
        // Os labels e a cor serão atualizados automaticamente pelos listeners
        System.out.println("[RESET] Todos os sliders foram resetados para 128");
        System.out.println("----------------------------------------");
    }
    
    //main method para iniciar a aplicação
    public static void main(String[] args) {
        // Executa a criação da GUI na Event Dispatch Thread
        // Isso é uma boa prática em Swing para evitar problemas de concorrência
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InteractiveColorMixer();  // Cria e mostra a aplicação
            }
        });
    }
}