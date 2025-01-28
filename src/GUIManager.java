import javax.swing.*;
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
        frame.setSize(400, 500); // Adjusted size to fit the new fields

        // Main panel with GridLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows for additional fields

        // Input field and button for adding
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new FlowLayout());
        JTextField addField = new JTextField(20);
        JButton addButton = new JButton("Adicionar");
        addPanel.add(new JLabel("Adicionar Docente:"));
        addPanel.add(addField);
        addPanel.add(addButton);

        // Input field and button for removing
        JPanel removePanel = new JPanel();
        removePanel.setLayout(new FlowLayout());
        JTextField removeField = new JTextField(20);
        JButton removeButton = new JButton("Remover");
        removePanel.add(new JLabel("Remover Docente:"));
        removePanel.add(removeField);
        removePanel.add(removeButton);

        // Text area for displaying the list
        JTextArea listArea = new JTextArea(10, 30);
        listArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(listArea);

        // Coordinator and Vice-Coordinator display
        JPanel coordinatorPanel = new JPanel();
        coordinatorPanel.setLayout(new GridLayout(2, 1));
        JLabel coordinatorLabel = new JLabel("Coordenador: ");
        JLabel viceCoordinatorLabel = new JLabel("Vice-Coordenador: ");
        coordinatorPanel.add(coordinatorLabel);
        coordinatorPanel.add(viceCoordinatorLabel);

        // Button to pick the next coordinator
        JButton pickButton = new JButton("Escolher Novo Coordenador");

        // Add panels to the main panel
        panel.add(addPanel);
        panel.add(removePanel);
        panel.add(scrollPane);
        panel.add(coordinatorPanel);
        panel.add(pickButton);

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
                    coordinatorPicker.chooseCoordinator(facultyList.getFacultyList());
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