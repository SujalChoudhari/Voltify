package com.sujal.voltify.ui.user;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.sujal.voltify.Application;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class CheckPastBillsFrame extends JFrame implements ActionListener {

    private JPanel contentPane;
    private JTable billTable;

    public static void start() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CheckPastBillsFrame frame = new CheckPastBillsFrame();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CheckPastBillsFrame() throws SQLException {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.SOUTH);
        
        JButton mCloseButton = new JButton("Close");
        mCloseButton.addActionListener(this);
        panel.add(mCloseButton);

        JLabel titleLabel = new JLabel("Past Bills");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(titleLabel, BorderLayout.NORTH);

        JPanel tablePanel = new JPanel();
        contentPane.add(tablePanel, BorderLayout.CENTER);

        // Fetch bills data from the database
        ResultSet billsResultSet = Application.database.getMeter(Application.username);
        Vector<String> columnNames = new Vector<>();
        columnNames.add("Bill ID");
        columnNames.add("Created At");
        columnNames.add("Units");
        columnNames.add("Amount");
        columnNames.add("Paid");

        Vector<Vector<String>> data = new Vector<>();
        int meterId = billsResultSet.getInt("id");
        ResultSet billResultSet = Application.database.getAllBillsForMeter(meterId);
        while (billResultSet.next()) {
        	System.out.println("WORKS");
            Vector<String> row = new Vector<>();
            row.add(Integer.toString(billResultSet.getInt("id")));
            row.add(billResultSet.getDate("created_at").toString());
            row.add(Float.toString(billResultSet.getFloat("units")));
            row.add(Float.toString(billResultSet.getFloat("amount")));
            row.add(Boolean.toString(billResultSet.getBoolean("paid")));
            data.add(row);
        }
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.X_AXIS));

        billTable = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(billTable);
        tablePanel.add(scrollPane);
    }
    

	@Override
	public void actionPerformed(ActionEvent e) {
		dispose();
	}
}
