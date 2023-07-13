package com.sujal.voltify.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.sujal.voltify.Application;
import com.sujal.voltify.database.Database;
import javax.swing.BoxLayout;

public class AdminFrame extends JFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField filterTextField;
	private JComboBox<String> filterComboBox;
	private JButton filterButton;
	private JTable billTable;
	private JButton markAsPaidButton;
	private JButton viewUsersButton;

	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminFrame frame = new AdminFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AdminFrame() {
		setTitle("Admin - Voltify");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 10));

		JLabel titleLabel = new JLabel("Admin Panel");
		titleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(titleLabel, BorderLayout.NORTH);

		JPanel filterHolderJPanel = new JPanel();
		JPanel filterPanel = new JPanel();
		
		filterPanel.setBorder(BorderFactory.createTitledBorder("Filter Bills"));
		filterHolderJPanel.add(filterPanel);
		contentPane.add(filterHolderJPanel,BorderLayout.WEST);
		filterPanel.setLayout(new BoxLayout(filterPanel, BoxLayout.PAGE_AXIS));
		
		filterTextField = new JTextField();
		filterPanel.add(filterTextField);

		filterComboBox = new JComboBox<>(new String[] { "Bill ID", "Created At", "Units", "Amount", "Paid" });
		filterPanel.add(filterComboBox);

		filterButton = new JButton("Filter");
		filterButton.addActionListener(this);
		filterPanel.add(filterButton);

		JPanel tablePanel = new JPanel();
		tablePanel.setLayout(new BorderLayout());
		contentPane.add(tablePanel, BorderLayout.CENTER);

		billTable = new JTable();
		JScrollPane scrollPane = new JScrollPane(billTable);
		tablePanel.add(scrollPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2, 10, 10));
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		markAsPaidButton = new JButton("Mark as Paid");
		markAsPaidButton.addActionListener(this);
		buttonPanel.add(markAsPaidButton);

		viewUsersButton = new JButton("View Users");
		viewUsersButton.addActionListener(this);
		buttonPanel.add(viewUsersButton);

		updateBillTable();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == filterButton) {
			filterBills();
		} else if (e.getSource() == markAsPaidButton) {
			markBillAsPaid();
		} else if (e.getSource() == viewUsersButton) {
			ViewUsersFrame.start();
		}
	}

	private void updateBillTable() {
		try {
			ResultSet billsResultSet = Application.database.getAllBills();

			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("Bill ID");
			model.addColumn("Created At");
			model.addColumn("Units");
			model.addColumn("Amount");
			model.addColumn("Paid");

			while (billsResultSet.next()) {
				Vector<String> row = new Vector<>();
				row.add(Integer.toString(billsResultSet.getInt("id")));
				row.add(billsResultSet.getDate("created_at").toString());
				row.add(Float.toString(billsResultSet.getFloat("units")));
				row.add(Float.toString(billsResultSet.getFloat("amount")));
				row.add(Boolean.toString(billsResultSet.getBoolean("paid")));
				model.addRow(row);
			}

			billTable.setModel(model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void filterBills() {
		String filterValue = filterTextField.getText();
		String filterColumn = (String) filterComboBox.getSelectedItem();

		try {
			ResultSet billsResultSet = Application.database.getFilteredBills(filterColumn, filterValue);

			DefaultTableModel model = new DefaultTableModel();
			model.addColumn("Bill ID");
			model.addColumn("Created At");
			model.addColumn("Units");
			model.addColumn("Amount");
			model.addColumn("Paid");

			while (billsResultSet.next()) {
				Vector<String> row = new Vector<>();
				row.add(Integer.toString(billsResultSet.getInt("id")));
				row.add(billsResultSet.getDate("created_at").toString());
				row.add(Float.toString(billsResultSet.getFloat("units")));
				row.add(Float.toString(billsResultSet.getFloat("amount")));
				row.add(Boolean.toString(billsResultSet.getBoolean("paid")));
				model.addRow(row);
			}

			billTable.setModel(model);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void markBillAsPaid() {
		int selectedRow = billTable.getSelectedRow();
		if (selectedRow != -1) {
			String billId = (String) billTable.getValueAt(selectedRow, 0);
			try {
				Application.database.updateBillStatus(Integer.parseInt(billId), true);
				JOptionPane.showMessageDialog(this, "Bill marked as paid successfully!", "Success",
						JOptionPane.INFORMATION_MESSAGE);
				updateBillTable();
			} catch (NumberFormatException | SQLException e) {
				JOptionPane.showMessageDialog(this, "Failed to mark bill as paid.", "Error",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(this, "Please select a bill to mark as paid.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
