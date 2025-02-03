import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * This class is responsible for creating and managing the GUI.
 */

public class GUIManager {
    private FacultyList facultyList;
    private CoordinatorPicker coordinatorPicker;

    public GUIManager(FacultyList facultyList, CoordinatorPicker coordinatorPicker) {
        this.facultyList = facultyList;
        this.coordinatorPicker = coordinatorPicker;
    }

    public void createAndShowGUI() {
        // Create the main window
        JFrame frame = new JFrame("Gerenciador de Docentes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600); // Adjusted size for better layout
        frame.setLocationRelativeTo(null); // Center the window on the screen

        // Main panel with GridBagLayout for precise control
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding
        panel.setBackground(new Color(240, 240, 240)); // Light gray background

        // Constraints for GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add spacing between components
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input field and button for adding
        JPanel addPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addPanel.setBackground(new Color(240, 240, 240));
        JTextField addField = new JTextField(20);
        JButton addButton = new JButton("Adicionar");
        addButton.setBackground(new Color(50, 150, 250)); // Blue background
        addButton.setForeground(Color.WHITE); // White text
        addButton.setFocusPainted(false); // Remove focus border
        addPanel.add(new JLabel("Adicionar Docente:"));
        addPanel.add(addField);
        addPanel.add(addButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(addPanel, gbc);

        // Input field and button for removing
        JPanel removePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        removePanel.setBackground(new Color(240, 240, 240));
        JTextField removeField = new JTextField(20);
        JButton removeButton = new JButton("Remover");
        removeButton.setBackground(new Color(250, 100, 100)); // Red background
        removeButton.setForeground(Color.WHITE); // White text
        removeButton.setFocusPainted(false); // Remove focus border
        removePanel.add(new JLabel("Remover Docente:"));
        removePanel.add(removeField);
        removePanel.add(removeButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(removePanel, gbc);

        // Text area for displaying the list
        JTextArea listArea = new JTextArea(10, 30);
        listArea.setEditable(false);
        listArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Monospaced font for better alignment
        JScrollPane scrollPane = new JScrollPane(listArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Lista de Docentes")); // Add a border with a title

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        // Coordinator and Vice-Coordinator display
        JPanel coordinatorPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        coordinatorPanel.setBackground(new Color(240, 240, 240));
        coordinatorPanel.setBorder(BorderFactory.createTitledBorder("Coordenadores")); // Add a border with a title
        JLabel coordinatorLabel = new JLabel("Coordenador: ");
        coordinatorLabel.setFont(new Font("SansSerif", Font.BOLD, 12)); // Bold font
        JLabel viceCoordinatorLabel = new JLabel("Vice-Coordenador: ");
        viceCoordinatorLabel.setFont(new Font("SansSerif", Font.BOLD, 12)); // Bold font
        coordinatorPanel.add(coordinatorLabel);
        coordinatorPanel.add(viceCoordinatorLabel);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        panel.add(coordinatorPanel, gbc);

        // Button to pick the next coordinator
        JButton pickButton = new JButton("Escolher Novo Coordenador");
        pickButton.setBackground(new Color(100, 200, 100)); // Green background
        pickButton.setForeground(Color.WHITE); // White text
        pickButton.setFocusPainted(false); // Remove focus border
        pickButton.setFont(new Font("SansSerif", Font.BOLD, 12)); // Bold font

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(pickButton, gbc);

        // Add functionality to the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = addField.getText().trim();
                if (!name.isEmpty()) {
                    facultyList.addFaculty(name);
                    updateList(listArea);
                    addField.setText(""); // Clear the field
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = removeField.getText().trim();
                if (!name.isEmpty()) {
                    boolean removed = facultyList.removeFaculty(name);
                    if (removed) {
                        JOptionPane.showMessageDialog(frame, name + " foi removido.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, name + " não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                    updateList(listArea);
                    removeField.setText(""); // Clear the field
                }
            }
        });

        pickButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!facultyList.getFacultyList().isEmpty()) {
                    coordinatorPicker.chooseCoordinator(facultyList.getFacultyList(), facultyList.getMembershipTime());
                    // Update labels with the new coordinator and vice-coordinator
                    coordinatorLabel.setText("Coordenador: " + coordinatorPicker.getCoordinator());
                    viceCoordinatorLabel.setText("Vice-Coordenador: " + coordinatorPicker.getViceCoordinator());
                } else {
                    JOptionPane.showMessageDialog(frame, "A lista de docentes está vazia. Adicione membros primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Update the list when GUI is created
        updateList(listArea);

        // Configure the window and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }

    // Method to update the text area with the list of members
    private void updateList(JTextArea listArea) {
        List<String> list = facultyList.getFacultyList();
        StringBuilder sb = new StringBuilder();
        for (String name : list) {
            sb.append(name).append("\n");
        }
        listArea.setText(sb.toString());
    }
}