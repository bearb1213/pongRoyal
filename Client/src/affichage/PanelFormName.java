package affichage;

import java.awt.*;
import javax.swing.*;
import serveur.Client;

public class PanelFormName extends JPanel {
    private JTextField nameField;
    private Client client;
    private FrameGame parentFrame;

    public PanelFormName(Client client, FrameGame parentFrame) {
        this.client = client;
        this.parentFrame = parentFrame;
        initComponents();
    }   
    
    private void initComponents() {
        // Layout principal avec espacement
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel pour les labels et champs
        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Labels
        JLabel nameLabel = new JLabel("Nom:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        
        // Champs de texte
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(150, 25));
        
        // Ajout au panel d'entrée
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        
        // Panel pour les boutons (optionnel)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton clearButton = new JButton("Effacer");
        clearButton.addActionListener(e -> clearFields());
        
        JButton validateButton = new JButton("Valider");
        validateButton.addActionListener(e -> validateInput());
        
        buttonPanel.add(clearButton);
        buttonPanel.add(validateButton);
        
        // Ajout des panels au panel principal
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Méthodes publiques pour accéder aux données
    
    public String getNameValue() {
        return nameField.getText().trim();
    }
    
    public void setNameValue(String name) {
        nameField.setText(name);
    }
    
    public void clearFields() {
        nameField.setText("");
    }
    
    // 
    public boolean validateInput() {
        String name = getNameValue();
        
        if (name.isEmpty()) {
            showMessage("Le champ 'Nom' est obligatoire", "Erreur", JOptionPane.ERROR_MESSAGE);
            nameField.requestFocus();
            return false;
        }
        try {
            // Exécuter sendMessage dans un thread séparé pour ne pas bloquer l'interface
            new Thread(() -> {
                try {
                    client.send("NAME " + name);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();

        } catch (Exception e) {
            showMessage("Erreur lors de la communication avec le serveur: " + e.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    
    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(
            this, 
            message, 
            title, 
            messageType
        );
    }
    
}