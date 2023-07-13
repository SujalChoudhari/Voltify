package com.sujal.voltify.ui;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.sujal.voltify.Application;

public class ViewUsersFrame extends JFrame {

	private JPanel contentPane;
	private JTable usersTable;

	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ViewUsersFrame frame = new ViewUsersFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ViewUsersFrame() {
		setTitle("View Users - Voltify");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10));

		JLabel titleLabel = new JLabel("View Users");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(titleLabel, BorderLayout.NORTH);

		JPanel tablePanel = new JPanel();
		contentPane.add(tablePanel, BorderLayout.CENTER);

		usersTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(usersTable);
		tablePanel.add(scrollPane);

		updateUsersTable();
	}

	private void updateUsersTable() {
		try {
			ResultSet usersResultSet = Application.database.getAllMeters();

			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("Meter ID");
			model.addColumn("Username");
			model.addColumn("Place");
			model.addColumn("City");
			model.addColumn("Pincode");

			while (usersResultSet.next()) {
				Vector<String> row = new Vector<>();
				row.add(Integer.toString(usersResultSet.getInt("id")));
				row.add(usersResultSet.getString("username"));
				row.add(usersResultSet.getString("place"));
				row.add(usersResultSet.getString("city"));
				row.add(Integer.toString(usersResultSet.getInt("pincode")));
				model.addRow(row);
			}

			usersTable.setModel(model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
