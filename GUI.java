import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {
    private GameLogic gameLogic;
    private String playerA;
    private String playerB;
    private JTextArea logArea;
    private JButton[] boteButtons; // Botones para los botes
    private JLabel playerACoinsLabel; // Etiqueta para las monedas del Jugador A
    private JLabel playerBCoinsLabel; // Etiqueta para las monedas del Jugador B

    public GUI(GameLogic gameLogic, String playerA, String playerB) {
        this.gameLogic = gameLogic;
        this.playerA = playerA;
        this.playerB = playerB;
        initializeGame();
        startGame();
    }

    private void initializeGame() {
        setTitle("Juego de los Botes de Oro");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Cargar la imagen de fondo
        ImageIcon backgroundIcon = new ImageIcon("./Botes/fondo-aqua.jpg");
        Image backgroundImg = backgroundIcon.getImage();

        // Crear un panel personalizado con la imagen de fondo
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BorderLayout()); // Para organizar los elementos encima

        // Panel para los nombres de los jugadores
        JPanel namePanel = new JPanel(new GridLayout(1, 2));
        namePanel.setOpaque(false); // Hacer el panel transparente para que se vea el fondo
        namePanel.setBorder(new EmptyBorder(20, 0, 0, 0)); // 20 píxeles de margen superior

        JLabel playerALabel = new JLabel(playerA.toUpperCase(), SwingConstants.CENTER);
        JLabel playerBLabel = new JLabel(playerB.toUpperCase(), SwingConstants.CENTER);
        playerALabel.setFont(new Font("Arial", Font.BOLD, 25));
        playerBLabel.setFont(new Font("Arial", Font.BOLD, 25));
        playerALabel.setForeground(Color.BLUE); // Color del texto
        playerBLabel.setForeground(Color.BLUE); // Color del texto

        namePanel.add(playerALabel);
        namePanel.add(playerBLabel);

        // Panel para mostrar las monedas de los jugadores
        JPanel coinsPanel = new JPanel(new GridLayout(1, 2));
        coinsPanel.setOpaque(false); // Hacer el panel transparente
        coinsPanel.setBorder(new EmptyBorder(10, 0, 10, 0)); // Margen interno

        // Crear etiquetas circulares para las monedas
        playerACoinsLabel = createCircularLabel("0");
        playerBCoinsLabel = createCircularLabel("0");

        coinsPanel.add(playerACoinsLabel);
        coinsPanel.add(playerBCoinsLabel);

        // Agregar el panel de nombres y monedas en la parte superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false); // Hacer el panel transparente
        topPanel.add(namePanel, BorderLayout.NORTH);
        topPanel.add(coinsPanel, BorderLayout.CENTER);

        backgroundPanel.add(topPanel, BorderLayout.NORTH);

        // Área de registro en el centro con scroll
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setOpaque(false); // Hacer el área de texto transparente
        logArea.setForeground(Color.WHITE); // Color del texto
        //JScrollPane scrollPane = new JScrollPane(logArea); // Agregar desplazamiento si el texto es largo
        //scrollPane.setOpaque(false); // Hacer el JScrollPane transparente
        //scrollPane.getViewport().setOpaque(false); // Hacer el viewport transparente
        //backgroundPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel para los botones de botes
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); // Hacer el panel de botones transparente
        buttonPanel.setLayout(new GridLayout(2, 4)); // Cambia según la cantidad de botes

        // Crear botones para cada bote
        int[] coins = gameLogic.getCoins();
        boteButtons = new JButton[coins.length];

        String[] imagePaths = {
                "./Botes/20.png", "./Botes/5.png", "./Botes/4.png", "./Botes/6.png",
                "./Botes/8.png", "./Botes/10.png", "./Botes/2.png", "./Botes/25.png"
        };

        for (int i = 0; i < coins.length; i++) {
            final int index = i; // Necesario para usar en el ActionListener

            // Dimensionar las imágenes como botones
            ImageIcon icon = new ImageIcon(imagePaths[i]);
            Image img = icon.getImage(); // Obtener la imagen del icono
            Image scaledImg = img.getScaledInstance(80, 90, Image.SCALE_SMOOTH); // Cambiar tamaño (ajusta según lo necesites)
            ImageIcon scaledIcon = new ImageIcon(scaledImg);

            boteButtons[i] = new JButton(scaledIcon);
            boteButtons[i].setOpaque(false); // Hacer el botón transparente
            boteButtons[i].setContentAreaFilled(false); // Sin relleno
            boteButtons[i].setBorderPainted(false); // Sin borde

            boteButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedCoins = gameLogic.chooseBote(index);
                    logArea.append((isPlayerATurn ? playerA : playerB) + " obtuvo " + selectedCoins + " monedas\n");

                    // Actualizar las monedas del jugador actual
                    if (isPlayerATurn) {
                        int currentCoins = Integer.parseInt(playerACoinsLabel.getText());
                        playerACoinsLabel.setText(String.valueOf(currentCoins + selectedCoins));
                    } else {
                        int currentCoins = Integer.parseInt(playerBCoinsLabel.getText());
                        playerBCoinsLabel.setText(String.valueOf(currentCoins + selectedCoins));
                    }

                    // Deshabilitar el botón después de seleccionarlo
                    boteButtons[index].setEnabled(false);

                    // Verificar si quedan botes disponibles
                    boolean allButtonsDisabled = true;
                    for (JButton button : boteButtons) {
                        if (button.isEnabled()) {
                            allButtonsDisabled = false;
                            break;
                        }
                    }

                    // Si todos los botes han sido elegidos, mostrar la pantalla final
                    if (allButtonsDisabled) {
                        showFinalScreen();
                    } else {
                        toggleTurn(); // Cambiar turno si aún quedan botes
                    }
                }
            });

            buttonPanel.add(boteButtons[i]);
        }

        backgroundPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Añadir el panel de fondo a la ventana
        add(backgroundPanel);

        setVisible(true);
    }

    private boolean isPlayerATurn = true; // Controla de quién es el turno

    private void toggleTurn() {
        isPlayerATurn = !isPlayerATurn; // Cambia entre jugador A y B
    }

    private void startGame() {
        logArea.setText(""); // Limpiar el área de texto antes de iniciar un nuevo juego
    }

    // Metodo para crear una etiqueta circular
    private JLabel createCircularLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int diameter = Math.min(getWidth(), getHeight()) - 1; // Reducir diámetro para dejar espacio
                int x = (getWidth() - diameter) / 2;
                int y = (getHeight() - diameter) / 2;

                // Dibujar el círculo amarillo
                g2d.setColor(new Color(255, 200, 0));
                g2d.fillOval(x, y, diameter, diameter);

                // Dibujar el texto centrado dentro del círculo
                g2d.setColor(Color.BLACK);
                FontMetrics fm = g2d.getFontMetrics();
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent()) / 2 - 4; // Ajuste fino para centrar

                g2d.drawString(getText(), textX, textY);
                g2d.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(90, 90); // Tamaño del círculo
            }
        };
        label.setFont(new Font("Arial", Font.BOLD, 25));
        label.setForeground(Color.BLACK); // Color del texto
        label.setOpaque(false);
        return label;
    }

    private void showFinalScreen() {
        int playerACoins = Integer.parseInt(playerACoinsLabel.getText());
        int playerBCoins = Integer.parseInt(playerBCoinsLabel.getText());

        String winner;
        int winningCoins;

        if (playerACoins > playerBCoins) {
            winner = playerA;
            winningCoins = playerACoins;
        } else if (playerBCoins > playerACoins) {
            winner = playerB;
            winningCoins = playerBCoins;
        } else {
            winner = "Empate";
            winningCoins = playerACoins;
        }

        new FinalScreen(winner, winningCoins);
    }

}