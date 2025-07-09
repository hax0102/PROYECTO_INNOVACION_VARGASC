
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class RecyclingApp extends JFrame {

    private JComboBox<String> materialComboBox;
    private JTextField userIdField;
    private JTextArea outputArea;
    private Map<String, Integer> userPoints;

    public RecyclingApp() {
        setTitle("Sistema de Reciclaje Automatizado");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        userPoints = new HashMap<>();

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        panel.add(new JLabel("Ingrese ID de usuario:"));
        userIdField = new JTextField();
        panel.add(userIdField);

        panel.add(new JLabel("Seleccione el material reciclado:"));
        String[] materiales = {"Plástico", "Vidrio", "Aluminio"};
        materialComboBox = new JComboBox<>(materiales);
        panel.add(materialComboBox);

        JButton submitButton = new JButton("Validar reciclaje y entregar cupón");
        panel.add(submitButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText().trim();
                String material = (String) materialComboBox.getSelectedItem();

                if (userId.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un ID de usuario.");
                    return;
                }

                int points = userPoints.getOrDefault(userId, 0);
                points += calculatePoints(material);
                userPoints.put(userId, points);

                String message = "Usuario: " + userId + "\nMaterial reciclado: " + material
                        + "\nPuntos acumulados: " + points
                        + "\nCupón entregado: " + generateCoupon(points) + "\n";
                outputArea.append(message + "\n");

                userIdField.setText("");
            }
        });
    }

    private int calculatePoints(String material) {
        switch (material) {
            case "Plástico":
                return 10;
            case "Vidrio":
                return 15;
            case "Aluminio":
                return 20;
            default:
                return 0;
        }
    }

    private String generateCoupon(int points) {
        if (points >= 100) {
            return "Cupón de $5.00";
        }
        if (points >= 50) {
            return "Cupón de $2.00";
        }
        return "Cupón simbólico (participación)";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RecyclingApp().setVisible(true));
    }
}
