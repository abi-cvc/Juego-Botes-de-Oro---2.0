import javax.swing.*;
import java.awt.*;

public class FinalScreen extends JFrame {
    private Image backgroundImg;

    public FinalScreen(String winnerName, int coins) {
        setTitle("¡Fin del Juego!");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Cargar la imagen de fondo
        ImageIcon backgroundIcon = new ImageIcon("./Botes/fondo-aqua.jpg");
        backgroundImg = backgroundIcon.getImage();

        // Crear un panel personalizado con la imagen de fondo
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImg, 0, 0, getWidth(), getHeight(), this);
            }
        };
        backgroundPanel.setLayout(new BoxLayout(backgroundPanel, BoxLayout.Y_AXIS));

        // Crear etiquetas con estilos
        JLabel winnerLabel = new JLabel("¡" + winnerName.toUpperCase() + " ha ganado!", SwingConstants.CENTER);
        winnerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        winnerLabel.setForeground(Color.BLUE);
        winnerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel coinsLabel = new JLabel("Monedas: " + coins, SwingConstants.CENTER);
        coinsLabel.setFont(new Font("Arial", Font.BOLD, 20));
        coinsLabel.setForeground(Color.YELLOW);
        coinsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón para cerrar la ventana
        JButton closeButton = new JButton("Cerrar");
        closeButton.setFont(new Font("Arial", Font.BOLD, 16));
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        closeButton.addActionListener(e -> dispose());

        // Agregar componentes con espaciado
        backgroundPanel.add(Box.createVerticalStrut(40));
        backgroundPanel.add(winnerLabel);
        backgroundPanel.add(Box.createVerticalStrut(15));
        backgroundPanel.add(coinsLabel);
        backgroundPanel.add(Box.createVerticalStrut(20));
        backgroundPanel.add(closeButton);

        // Agregar el panel de fondo a la ventana
        setContentPane(backgroundPanel);
        setVisible(true);
    }
}
