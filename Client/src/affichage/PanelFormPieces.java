package affichage;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import serveur.Client;

public class PanelFormPieces extends JPanel {
    // Champs pour chaque type de pièce
    private JSpinner nbPieceSpinner;
    private JSpinner pionPvSpinner;
    private JSpinner roiPvSpinner;
    private JSpinner reinePvSpinner;
    private JSpinner cavalierPvSpinner;
    private JSpinner fouPvSpinner;
    private JSpinner tourPvSpinner;
    
    private Client client;
    private JFrame parentFrame;

    public PanelFormPieces(Client client, JFrame parentFrame) {
        this.client = client;
        this.parentFrame = parentFrame;
        initComponents();
    }
    
    private void initComponents() {
        // Layout principal avec espacement
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Panel pour les champs avec un titre
        JPanel inputPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            "Configuration des Pièces",
            TitledBorder.CENTER,
            TitledBorder.DEFAULT_POSITION,
            new Font("Arial", Font.BOLD, 12),
            Color.DARK_GRAY
        ));
        
        // Création des spinners avec des valeurs int
        nbPieceSpinner = createIntSpinner(16, 1, 32, 1, "Nombre total de pièces par joueur");
        pionPvSpinner = createIntSpinner(1, 1, 100, 1, "Points de vie des pions");
        roiPvSpinner = createIntSpinner(10, 1, 100, 1, "Points de vie du roi");
        reinePvSpinner = createIntSpinner(8, 1, 100, 1, "Points de vie de la reine");
        cavalierPvSpinner = createIntSpinner(5, 1, 100, 1, "Points de vie des cavaliers");
        fouPvSpinner = createIntSpinner(5, 1, 100, 1, "Points de vie des fous");
        tourPvSpinner = createIntSpinner(5, 1, 100, 1, "Points de vie des tours");
        
        // Ajout des labels et spinners au panel
        addLabelAndSpinner(inputPanel, "Nombre total de pièces:", nbPieceSpinner);
        addLabelAndSpinner(inputPanel, "PV Pions:", pionPvSpinner);
        addLabelAndSpinner(inputPanel, "PV Roi:", roiPvSpinner);
        addLabelAndSpinner(inputPanel, "PV Reine:", reinePvSpinner);
        addLabelAndSpinner(inputPanel, "PV Cavaliers:", cavalierPvSpinner);
        addLabelAndSpinner(inputPanel, "PV Fous:", fouPvSpinner);
        addLabelAndSpinner(inputPanel, "PV Tours:", tourPvSpinner);
        
        // Panel pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton clearButton = new JButton("Réinitialiser");
        clearButton.setFont(new Font("Arial", Font.PLAIN, 12));
        clearButton.addActionListener(e -> resetToDefaults());
        
        JButton validateButton = new JButton("Valider");
        validateButton.setFont(new Font("Arial", Font.BOLD, 12));
        validateButton.addActionListener(e -> validateAndSend());
        
        buttonPanel.add(clearButton);
        buttonPanel.add(validateButton);
        
        // Ajout des panels au panel principal
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void addLabelAndSpinner(JPanel panel, String labelText, JSpinner spinner) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        panel.add(label);
        panel.add(spinner);
    }
    
    private JSpinner createIntSpinner(int initial, int min, int max, int step, String tooltip) {
        // Utilisation d'un modèle avec des Integer
        SpinnerNumberModel model = new SpinnerNumberModel(
            Integer.valueOf(initial), 
            Integer.valueOf(min), 
            Integer.valueOf(max), 
            Integer.valueOf(step)
        );
        
        JSpinner spinner = new JSpinner(model);
        
        // Personnalisation
        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();
        editor.getTextField().setFont(new Font("Arial", Font.PLAIN, 12));
        editor.getTextField().setHorizontalAlignment(JTextField.CENTER);
        spinner.setPreferredSize(new Dimension(80, 25));
        spinner.setToolTipText(tooltip);
        
        return spinner;
    }
    
    // ========== MÉTHODES GETTER AVEC CONVERSION EN INT ==========
    
    public int getNbPiece() {
        return ((Number) nbPieceSpinner.getValue()).intValue();
    }
    
    public int getPionPv() {
        return ((Number) pionPvSpinner.getValue()).intValue();
    }
    
    public int getRoiPv() {
        return ((Number) roiPvSpinner.getValue()).intValue();
    }
    
    public int getReinePv() {
        return ((Number) reinePvSpinner.getValue()).intValue();
    }
    
    public int getCavalierPv() {
        return ((Number) cavalierPvSpinner.getValue()).intValue();
    }
    
    public int getFouPv() {
        return ((Number) fouPvSpinner.getValue()).intValue();
    }
    
    public int getTourPv() {
        return ((Number) tourPvSpinner.getValue()).intValue();
    }
    
    // ========== MÉTHODES SETTER AVEC VALEURS INT ==========
    
    public void setNbPiece(int value) {
        nbPieceSpinner.setValue(Integer.valueOf(value));
    }
    
    public void setPionPv(int value) {
        pionPvSpinner.setValue(Integer.valueOf(value));
    }
    
    public void setRoiPv(int value) {
        roiPvSpinner.setValue(Integer.valueOf(value));
    }
    
    public void setReinePv(int value) {
        reinePvSpinner.setValue(Integer.valueOf(value));
    }
    
    public void setCavalierPv(int value) {
        cavalierPvSpinner.setValue(Integer.valueOf(value));
    }
    
    public void setFouPv(int value) {
        fouPvSpinner.setValue(Integer.valueOf(value));
    }
    
    public void setTourPv(int value) {
        tourPvSpinner.setValue(Integer.valueOf(value));
    }
    
    public void resetToDefaults() {
        setNbPiece(8);
        setPionPv(1);
        setRoiPv(10);
        setReinePv(8);
        setCavalierPv(5);
        setFouPv(5);
        setTourPv(5);
    }
    
    public boolean validateInput() {
        // Validation simple des valeurs
        if (getNbPiece() < 1) {
            showMessage("Le nombre de pièces doit être au moins 1", 
                       "Erreur", JOptionPane.ERROR_MESSAGE);
            nbPieceSpinner.requestFocus();
            return false;
        }
        
        // Validation que toutes les valeurs sont positives
        if (getPionPv() <= 0 || getRoiPv() <= 0 || getReinePv() <= 0 || 
            getCavalierPv() <= 0 || getFouPv() <= 0 || getTourPv() <= 0) {
            showMessage("Tous les points de vie doivent être supérieurs à 0", 
                       "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        return true;
    }
    
    public boolean validateAndSend() {
        if (!validateInput()) {
            return false;
        }
        
        try {
            // Préparation du message avec tous les int
            StringBuilder message = new StringBuilder("PIECES ");
            message.append(getNbPiece()).append(" ");
            message.append(getPionPv()).append(" ");
            message.append(getRoiPv()).append(" ");
            message.append(getReinePv()).append(" ");
            message.append(getCavalierPv()).append(" ");
            message.append(getFouPv()).append(" ");
            message.append(getTourPv());
            
            // Envoi au serveur dans un thread séparé pour ne pas bloquer l'interface
            new Thread(() -> {
                try {
                    client.send(message.toString());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();

            // Affichage des valeurs envoyées (pour débogage)
            System.out.println("Configuration envoyée: " + message.toString());
            
            
            
            return true;
            
        } catch (Exception e) {
            showMessage("Erreur d'envoi: " + e.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    // Méthode pour obtenir toutes les valeurs sous forme de tableau d'int
    public int[] getAllValuesAsIntArray() {
        return new int[] {
            getNbPiece(),
            getPionPv(),
            getRoiPv(),
            getReinePv(),
            getCavalierPv(),
            getFouPv(),
            getTourPv()
        };
    }
    
    // Méthode pour définir toutes les valeurs à partir d'un tableau d'int
    public void setAllValuesFromIntArray(int[] values) {
        if (values == null || values.length < 7) {
            throw new IllegalArgumentException("Le tableau doit contenir 7 valeurs int");
        }
        
        setNbPiece(values[0]);
        setPionPv(values[1]);
        setRoiPv(values[2]);
        setReinePv(values[3]);
        setCavalierPv(values[4]);
        setFouPv(values[5]);
        setTourPv(values[6]);
    }
    
    // Méthode utilitaire pour obtenir un résumé des valeurs
    public String getConfigurationSummary() {
        return String.format(
            "Configuration:\n" +
            "- Nombre pièces: %d\n" +
            "- PV Pions: %d\n" +
            "- PV Roi: %d\n" +
            "- PV Reine: %d\n" +
            "- PV Cavaliers: %d\n" +
            "- PV Fous: %d\n" +
            "- PV Tours: %d",
            getNbPiece(), getPionPv(), getRoiPv(), getReinePv(),
            getCavalierPv(), getFouPv(), getTourPv()
        );
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