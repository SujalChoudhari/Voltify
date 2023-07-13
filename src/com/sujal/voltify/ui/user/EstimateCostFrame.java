package com.sujal.voltify.ui.user;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EstimateCostFrame extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTextField unitsTextField;
    private JLabel costLabel;
    
    private double mRate = 6.1;


    public static void start() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    EstimateCostFrame frame = new EstimateCostFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public EstimateCostFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 589, 171);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(contentPane);
        contentPane.setLayout(new GridLayout(3, 1));

        JLabel titleLabel = new JLabel("Estimate Cost of Electricity");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(titleLabel);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        contentPane.add(inputPanel);

        JLabel unitsLabel = new JLabel("Units:");
        inputPanel.add(unitsLabel);

        unitsTextField = new JTextField();
        unitsTextField.setColumns(10);
        inputPanel.add(unitsTextField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        contentPane.add(buttonPanel);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(this);
        buttonPanel.add(calculateButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(this);
        buttonPanel.add(closeButton);

        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        contentPane.add(resultPanel);

        JLabel rateLabel = new JLabel("Rate:("+mRate+") Rs/Unit");
        resultPanel.add(rateLabel);

        costLabel = new JLabel("Estimated Cost: ");
        resultPanel.add(costLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Close")) {
            dispose();
        } else if (e.getActionCommand().equals("Calculate")) {
            calculateCost();
        }
    }

    private void calculateCost() {
        try {
            double units = Double.parseDouble(unitsTextField.getText());
            double cost = units * mRate;
            costLabel.setText("Estimated Cost: Rs " + cost);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input for units!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
