import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the faculty list
        FacultyList facultyList = new FacultyList();

        // Create the main window
        JFrame frame = new JFrame("Faculty Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Input field and button for adding
        JPanel addPanel = new JPanel();
        addPanel.setLayout(new FlowLayout());
        JTextField addField = new JTextField(20);
        JButton addButton = new JButton("Add");
        addPanel.add(new JLabel("Add Faculty:"));
        addPanel.add(addField);
        addPanel.add(addButton);

        // Input field and button for removing
        JPanel removePanel = new JPanel();
        removePanel.setLayout(new FlowLayout());
        JTextField removeField = new JTextField(20);
        JButton removeButton = new JButton("Remove");
        removePanel.add(new JLabel("Remove Faculty:"));
        removePanel.add(removeField);
        removePanel.add(removeButton);

        // Text area for displaying the list
        JTextArea listArea = new JTextArea(10, 30);
        listArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(listArea);

        // Add panels to the main panel
        panel.add(addPanel, BorderLayout.NORTH);
        panel.add(removePanel, BorderLayout.CENTER);
        panel.add(scrollPane, BorderLayout.SOUTH);

        // Add functionality to the buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = addField.getText().trim();
                if (!name.isEmpty()) {
                    facultyList.addFaculty(name);
                    updateList(listArea, facultyList);
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
                        JOptionPane.showMessageDialog(frame, name + " was removed.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, name + " not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    updateList(listArea, facultyList);
                    removeField.setText(""); // Clear the field
                }
            }
        });

        // Configure the window and make it visible
        frame.add(panel);
        frame.setVisible(true);
    }

    // Method to update the text area with the list of members
    private static void updateList(JTextArea listArea, FacultyList facultyList) {
        List<String> list = facultyList.getFacultyList();
        StringBuilder sb = new StringBuilder();
        for (String name : list) {
            sb.append(name).append("\n");
        }
        listArea.setText(sb.toString());
    }
}