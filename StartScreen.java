import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class StartScreen {
    private JFrame startFrame;
    private JTextField playerAField;
    private JTextField playerBField;
    private String playerA; // Declarar como atributo de instancia
    private String playerB; // Declarar como atributo de instancia

    public StartScreen() {
        initialize();
    }

    private void initialize() {
        // Configuración de la ventana
        startFrame = new JFrame("Juego de los Botes de Oro - Inicio");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(600, 400); // Tamaño inicial de la ventana
        startFrame.setMinimumSize(new Dimension(700, 500)); // Tamaño mínimo
        startFrame.setResizable(true); // Permitir redimensionar la ventana
        startFrame.setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Panel principal con imagen de fondo
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("./fondo.png"); // Cambia la ruta a tu imagen
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Panel para los campos de texto y el botón
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        inputPanel.setOpaque(false); // Hacer el panel transparente para que se vea la imagen de fondo

        // Margen alrededor del panel de entrada
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Configuración de GridBagConstraints para posicionar los elementos
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10); // Espaciado entre elementos
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiquetas y campos de texto
        JLabel playerALabel = new RoundedLabel("Nombre del Jugador A:", 20, new Color(0, 0, 0, 230));
        playerALabel.setFont(new Font("Arial", Font.BOLD,16));
        playerALabel.setForeground(Color.WHITE); // Color del texto
        playerALabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margen interno

        playerAField = new RoundedTextField(15, 20, new Color(0, 0, 0, 150));
        playerAField.setFont(new Font("Arial", Font.BOLD, 15));
        playerAField.setOpaque(false); // Fondo transparente
        playerAField.setForeground(Color.YELLOW); // Color del texto
        playerAField.setBorder(new EmptyBorder(9, 10, 9, 10));
        String playerA = playerAField.getText().trim().toLowerCase();

        JLabel playerBLabel = new RoundedLabel("Nombre del Jugador B:", 20, new Color(0, 0, 0, 230));
        playerBLabel.setFont(new Font("Arial", Font.BOLD, 16));
        playerBLabel.setForeground(Color.WHITE); // Color del texto
        playerBLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Margen interno

        playerBField = new RoundedTextField(15, 20, new Color(0, 0, 0, 150));
        playerBField.setFont(new Font("Arial", Font.BOLD, 15));
        playerBField.setOpaque(false); // Fondo transparente
        playerBField.setForeground(Color.YELLOW); // Color del texto
        playerBField.setBorder(new EmptyBorder(9, 10, 9, 10));
        String playerB = playerBField.getText().trim().toLowerCase();

        // Agregar el KeyListener a los campos ya creados
        playerAField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                playerAField.setText(playerAField.getText().toUpperCase());
            }
        });

        playerBField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                playerBField.setText(playerBField.getText().toUpperCase());
            }
        });


        // Posicionar los elementos en el panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(playerALabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(playerAField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(playerBLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(playerBField, gbc);

        //Boton de inicio del juego
        RoundButton startButton = new RoundButton("INICIAR JUEGO",new Color(250, 180, 0), Color.WHITE);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound("./coin-sound.wav");
                String playerA = playerAField.getText().trim().toLowerCase();
                String playerB = playerBField.getText().trim().toLowerCase();

                if (playerA.isEmpty() || playerB.isEmpty()) {
                    JOptionPane.showMessageDialog(startFrame, "Por favor, ingresa los nombres de ambos jugadores.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    startFrame.dispose(); // Cierra la ventana de inicio
                    startGame(playerA, playerB); // Inicia el juego con los nombres ingresados
                }
            }
        });

        // Posicionar el botón en el centro
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        inputPanel.add(startButton, gbc);

        // Añadir el panel de entrada al panel principal
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // Añadir el panel principal a la ventana
        startFrame.add(mainPanel);
        startFrame.setVisible(true);

        // Enfoque automático en el primer campo
        playerAField.requestFocusInWindow();
    }

    private void startGame(String playerA, String playerB) {
        int[] coins = {20, 5, 4, 6, 8, 10, 2, 25}; // Arreglo de monedas
        GameLogic gameLogic = new GameLogic(coins);
        GUI gui = new GUI(gameLogic, playerA, playerB); // Pasa los nombres de los jugadores a la GUI
        //gui.show();
    }

    public static void main(String[] args) {
        new StartScreen(); // Inicia la ventana de inicio
    }

    // Clase para crear bordes redondeados
    private static class RoundBorder implements javax.swing.border.Border {
        private int radius;
        private Color color;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 1, this.radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }
    }

    private static class RoundedTextField extends JTextField {
        private int radius; // Radio para los bordes redondeados
        private Color backgroundColor; // Color de fondo semitransparente

        public RoundedTextField(int columns, int radius, Color backgroundColor) {
            super(columns); // Llama al constructor de JTextField
            this.radius = radius;
            this.backgroundColor = backgroundColor;
            setOpaque(false); // Hace que el JTextField sea transparente
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Margen interno
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Dibuja el fondo redondeado semitransparente
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            // Llama al metodo paintComponent de la clase padre para dibujar el texto
            super.paintComponent(g2d);
            g2d.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No dibujar el borde predeterminado
        }
    }

    public class RoundedLabel extends JLabel {
        private int radius; // Radio para los bordes redondeados
        private Color backgroundColor; // Color de fondo semitransparente

        public RoundedLabel(String text, int radius, Color backgroundColor) {
            super(text); // Llama al constructor de JLabel
            this.radius = radius;
            this.backgroundColor = backgroundColor;
            setOpaque(false); // Hace que el JLabel sea transparente
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Margen interno
        }

        @Override
        protected void paintComponent(Graphics g) {
            // Dibuja el fondo redondeado semitransparente
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            // Llama al metodo paintComponent de la clase padre para dibujar el texto
            super.paintComponent(g2d);
            g2d.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No dibujar el borde predeterminado
        }
    }

    public class RoundButton extends JButton {
        private final Color backgroundColor;
        private final Color textColor;
        private final int cornerRadius = 20; // Radio de las esquinas redondeadas

        public RoundButton(String text, Color backgroundColor, Color textColor) {
            super(text);
            this.backgroundColor = backgroundColor;
            this.textColor = textColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(textColor);
            setFont(new Font("Arial", Font.BOLD, 16));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(backgroundColor);
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            FontMetrics metrics = g2d.getFontMetrics(getFont());
            int x = (getWidth() - metrics.stringWidth(getText())) / 2;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
            g2d.setColor(textColor);
            g2d.drawString(getText(), x, y);

            g2d.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No dibujar el borde predeterminado
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(120, 50); // Tamaño del botón
        }
    }
    private void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error al reproducir el sonido: " + ex.getMessage());
        }

    }
}