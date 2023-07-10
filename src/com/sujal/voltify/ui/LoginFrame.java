package com.sujal.voltify.ui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sujal.voltify.Application;
import com.sujal.voltify.ui.user.UserFrame;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.BoxLayout;

import java.awt.EventQueue;
import java.awt.Font;

public class LoginFrame extends JFrame implements ActionListener {
	private JTextField mUsernameField;
	private JPasswordField mPasswordField;
	private JButton mLoginButton;
	private JButton mSignUpButton;
	
	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public LoginFrame() {
		setTitle("Login - Voltify");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new GridBagLayout());
		JPanel mainPanel = new JPanel();
		rootPanel.add(mainPanel);
		add(rootPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		JPanel panel = new JPanel();
		mainPanel.add(panel);
		
		JLabel lblNewLabel = new JLabel("Voltify");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 23));
		panel.add(lblNewLabel);
		
		JPanel panel_1 = new JPanel();
		mainPanel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		JLabel lblNewLabel_1 = new JLabel("Username");
		panel_3.add(lblNewLabel_1);
		
		mUsernameField = new JTextField();
		mUsernameField.setColumns(12);
		panel_3.add(mUsernameField);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		
		JLabel lblNewLabel_2 = new JLabel("Password");
		panel_4.add(lblNewLabel_2);
		
		mPasswordField = new JPasswordField();
		mPasswordField.setColumns(12);
		panel_4.add(mPasswordField);
		
		JPanel panel_2 = new JPanel();
		mainPanel.add(panel_2);
		
		mLoginButton = new JButton("Log In");
		mLoginButton.addActionListener(this);
		panel_2.add(mLoginButton);
		
		mSignUpButton = new JButton("Sign Up");
		mSignUpButton.addActionListener(this);
		panel_2.add(mSignUpButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mSignUpButton) {
			SignUpFrame.start();
			dispose();
		}
		else if(e.getSource() ==mLoginButton) {
			String username = mUsernameField.getText();
			String passwordString = new String(mPasswordField.getPassword());
			try {
				if(Application.database.verifyAdmin(username,passwordString)) {
					System.out.println("ADMIN");
					dispose();
				}
				else if(Application.database.verifyUser(username,passwordString)) {
					UserFrame.start(username);
					dispose();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
