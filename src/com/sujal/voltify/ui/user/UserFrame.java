package com.sujal.voltify.ui.user;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.sujal.voltify.Application;

import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;

import javax.swing.SwingConstants;

public class UserFrame extends JFrame implements ActionListener {

	private JPanel contentPane;

	private JButton mUploadNewBillButton ;
	private JButton mCheckPastBillsButton;
	private JButton mViewProfileButton;
	private JButton mEstimateCostButton;

	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserFrame frame = new UserFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public UserFrame() {
		try {
			Application.database.getMeter(Application.username);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		setTitle("User - Voltify");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridBagLayout());
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 0));
		
		mUploadNewBillButton = new JButton("Upload New Bill");
		mUploadNewBillButton.addActionListener(this);
		panel_2.add(mUploadNewBillButton);
		
		mCheckPastBillsButton = new JButton("Check Past Bills");
		mCheckPastBillsButton.addActionListener(this);
		panel_2.add(mCheckPastBillsButton);
		
		mViewProfileButton = new JButton("View Profile");
		mViewProfileButton.addActionListener(this);
		panel_2.add(mViewProfileButton);
		
		mEstimateCostButton = new JButton("Estimate Cost");
		mEstimateCostButton.addActionListener(this);
		panel_2.add(mEstimateCostButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mViewProfileButton) {
			ProfileFrame.start();
		}
	}

}
