package com.sujal.voltify.ui;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sujal.voltify.Application;

public class SignUpFrame extends JFrame implements ActionListener{

	private JPanel mContentPane;
	private JTextField mUsernameTextField;
	private JTextField mPasswordTextField;
	private JTextField mPlaceTextField;
	private JTextField mCityTextField;
	private JTextField mPincodeTextField;
	private JTextField mMeterNoTextField;
	private JButton mCreateButton;
	private JButton mLoginButton;

	public static void start() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUpFrame frame = new SignUpFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SignUpFrame() {
		setTitle("Signup - Voltify");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		mContentPane = new JPanel();
		mContentPane.setLayout(new GridBagLayout());
		mContentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mContentPane);
		
		JPanel panel = new JPanel();
		mContentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JLabel createAccountLabel = new JLabel("Create a new account?");
		createAccountLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		panel_1.add(createAccountLabel);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel meterNoLabel = new JLabel("Meter No");
		panel_2.add(meterNoLabel);
		
		mMeterNoTextField = new JTextField();
		panel_2.add(mMeterNoTextField);
		mMeterNoTextField.setColumns(10);
		
		JLabel usernameLabel = new JLabel("Username");
		panel_2.add(usernameLabel);
		
		mUsernameTextField = new JTextField();
		panel_2.add(mUsernameTextField);
		mUsernameTextField.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password");
		panel_2.add(passwordLabel);
		
		mPasswordTextField = new JTextField();
		panel_2.add(mPasswordTextField);
		mPasswordTextField.setColumns(10);
		
		JLabel placeLabel = new JLabel("Place");
		panel_2.add(placeLabel);
		
		mPlaceTextField = new JTextField();
		panel_2.add(mPlaceTextField);
		mPlaceTextField.setColumns(10);
		
		JLabel cityLabel = new JLabel("City");
		panel_2.add(cityLabel);
		
		mCityTextField = new JTextField();
		panel_2.add(mCityTextField);
		mCityTextField.setColumns(10);
		
		JLabel pincodeLabel = new JLabel("Pincode");
		panel_2.add(pincodeLabel);
		
		mPincodeTextField = new JTextField();
		panel_2.add(mPincodeTextField);
		mPincodeTextField.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		mCreateButton = new JButton("Create");
		mCreateButton.addActionListener(this);
		mCreateButton.setBackground(new Color(255, 255, 255));
		panel_3.add(mCreateButton);
		
		mLoginButton = new JButton("Already have an account?");
		mLoginButton.addActionListener(this);
		mLoginButton.setBackground(SystemColor.menu);
		mLoginButton.setFont(new Font("Tahoma", Font.PLAIN, 8));
		panel_3.add(mLoginButton);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == mLoginButton) {
			LoginFrame.start();
			dispose();
		}else if(e.getSource() == mCreateButton) {
			try {
				Application.database.createMeter(
						Integer.parseInt(mMeterNoTextField.getText()),
						mUsernameTextField.getText(),
						mPasswordTextField.getText(),
						mPlaceTextField.getText(),
						mCityTextField.getText(),
						Integer.parseInt(mPincodeTextField.getText())
						);
				dispose();
				LoginFrame.start();
			} catch (NumberFormatException | SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

}
