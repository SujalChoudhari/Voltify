package com.sujal.voltify.ui.user;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.sujal.voltify.Application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

public class UploadBillFrame extends JFrame implements ActionListener {

    private JPanel contentPane;
    private File selectedImageFile;
    private JTextField textField;
    private JLabel selectedImageLabel;
    private JComboBox<String> monthComboBox;

    public static void start() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UploadBillFrame frame = new UploadBillFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public UploadBillFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 400, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        
        JPanel panel = new JPanel();
        contentPane.add(panel);
        
        JLabel titleLabel = new JLabel("Upload New Bill");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        panel.add(titleLabel);
        
        JPanel panel_2 = new JPanel();
        contentPane.add(panel_2);
        panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.X_AXIS));
        
        JPanel panel_1 = new JPanel();
        panel_2.add(panel_1);
        panel_1.setLayout(new GridLayout(0, 2, 0, 0));
        
        JLabel monthLabel = new JLabel("Month:");
        panel_1.add(monthLabel);
        
        String[] monthOptions = {"January", "February", "March", "April", "May", "June", "July",
                "August", "September", "October", "November", "December"};
        monthComboBox = new JComboBox<>(monthOptions);
        panel_1.add(monthComboBox);
        
        JLabel unitsLabel = new JLabel("Units:");
        panel_1.add(unitsLabel);
        
        textField = new JTextField();
        panel_1.add(textField);
        
        JLabel imageLabel = new JLabel("Image:");
        panel_1.add(imageLabel);
        
        JButton uploadImageButton = new JButton("Upload Image");
        uploadImageButton.addActionListener(this);
        panel_1.add(uploadImageButton);
        
        selectedImageLabel = new JLabel("No image selected");
        panel_1.add(selectedImageLabel);


        JPanel buttonPanel = new JPanel();
        contentPane.add(buttonPanel);

        JButton generateBillButton = new JButton("Generate Bill");
        generateBillButton.addActionListener(this);
        buttonPanel.add(generateBillButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        buttonPanel.add(closeButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Close")) {
            dispose();
        } else if (e.getActionCommand().equals("Upload Image")) {
            uploadImage();
        } else if (e.getActionCommand().equals("Generate Bill")) {
            generateBill();
        }
    }

    private void uploadImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            selectedImageLabel.setText(selectedImageFile.getName());
        }
    }

    private void generateBill() {
        try {
            String month = (String) monthComboBox.getSelectedItem();
            double units = Double.parseDouble(textField.getText());
            double rate = 6.1; // Example rate of Rs/Unit

            // Retrieve user data from the database
            ResultSet userResultSet = Application.database.getMeter(Application.username);
            int meterId = userResultSet.getInt("id");
            String username = userResultSet.getString("username");
            String place = userResultSet.getString("place");
            String city = userResultSet.getString("city");
            int pincode = userResultSet.getInt("pincode");

            // Prepare bill data for display
            String companyName = "Voltify Pvt. Ltd.";
            String companyAddress = "123, Alpha Street, Beta City";
            double amount = units * rate;

            // Create and display the bill frame
            BillFrame billFrame = new BillFrame(meterId,username, companyName, companyAddress, place, city, pincode, month, units, rate, amount, selectedImageFile);
            Application.database.createBill(meterId, (float) units, (float) amount, false);
            billFrame.setVisible(true);
        } catch (NumberFormatException | SQLException e) {
            JOptionPane.showMessageDialog(this, "Invalid input for units!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private class BillFrame extends JFrame {

        public BillFrame(int id,String username, String companyName, String companyAddress, String place, String city,
                         int pincode, String month, double units, double rate, double amount, File imageFile) {
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setBounds(100, 100, 400, 300);
            setTitle("Invoice");

            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
            setContentPane(contentPane);
            contentPane.setLayout(new BorderLayout(0, 10));

            JLabel titleLabel = new JLabel("Invoice");
            titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
            titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentPane.add(titleLabel, BorderLayout.NORTH);

            JPanel detailsPanel = new JPanel();
            detailsPanel.setLayout(new GridLayout(0, 2, 10, 10));
            contentPane.add(detailsPanel, BorderLayout.CENTER);
            
            detailsPanel.add(new JLabel("Meter No:"));
            detailsPanel.add(new JLabel(String.valueOf(id)));

            detailsPanel.add(new JLabel("Username:"));
            detailsPanel.add(new JLabel(username));

            detailsPanel.add(new JLabel("Company Name:"));
            detailsPanel.add(new JLabel(companyName));

            detailsPanel.add(new JLabel("Company Address:"));
            detailsPanel.add(new JLabel(companyAddress));

            detailsPanel.add(new JLabel("User Address:"));
            detailsPanel.add(new JLabel(place + ", " + city + " - " + pincode));

            detailsPanel.add(new JLabel("Month:"));
            detailsPanel.add(new JLabel(month));

            detailsPanel.add(new JLabel("Units:"));
            detailsPanel.add(new JLabel(Double.toString(units)));

            detailsPanel.add(new JLabel("Rate:"));
            detailsPanel.add(new JLabel(Double.toString(rate)));

            detailsPanel.add(new JLabel("Amount:"));
            detailsPanel.add(new JLabel(Double.toString(amount)));

            if (imageFile != null) {
                JPanel imagePanel = new JPanel();
                imagePanel.setLayout(new BorderLayout());
                contentPane.add(imagePanel, BorderLayout.SOUTH);

                JLabel imageLabel = new JLabel(new ImageIcon(imageFile.getPath()));
                imagePanel.add(imageLabel, BorderLayout.CENTER);
            }

        }
    }
}
