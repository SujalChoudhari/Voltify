package com.sujal.voltify.ui.user;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.sujal.voltify.Application;

import java.awt.GridLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.BoxLayout;
import java.awt.Font;
import javax.swing.JButton;

public class ProfileFrame extends JFrame {

	private JPanel contentPane;
	private JTextField mMeterText;
	private JTextField mUsernameText;
	private JTextField mPasswordText;
	private JTextField mPlaceText;
	private JTextField mCityText;
	private JTextField mPincodeText;


	public static void start(String username) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfileFrame frame = new ProfileFrame(username);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public ProfileFrame(String username) throws SQLException {
		
		ResultSet userResultSet = Application.database.getMeter(username); 
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new GridBagLayout());
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		JLabel lblNewLabel = new JLabel("Update Profile");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		panel_1.add(lblNewLabel);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel meterNoLabel = new JLabel("Meter No");
		panel_2.add(meterNoLabel);
		
		mMeterText = new JTextField();
		mMeterText.setText(userResultSet.getString("id"));
		mMeterText.setColumns(10);
		panel_2.add(mMeterText);
		
		JLabel usernameLabel = new JLabel("Username");
		panel_2.add(usernameLabel);
		
		mUsernameText = new JTextField();
		mUsernameText.setColumns(10);
		mUsernameText.setText(userResultSet.getString("username"));
		panel_2.add(mUsernameText);
		
		JLabel passwordLabel = new JLabel("Password");
		panel_2.add(passwordLabel);
		
		mPasswordText = new JTextField();
		mPasswordText.setColumns(10);
		mPasswordText.setText(userResultSet.getString("user_password"));
		panel_2.add(mPasswordText);
		
		JLabel placeLabel = new JLabel("Place");
		panel_2.add(placeLabel);
		
		mPlaceText = new JTextField();
		mPlaceText.setColumns(10);
		mPlaceText.setText(userResultSet.getString("place"));
		panel_2.add(mPlaceText);
		
		JLabel cityLabel = new JLabel("City");
		panel_2.add(cityLabel);
		
		mCityText = new JTextField();
		mCityText.setColumns(10);
		mCityText.setText(userResultSet.getString("city"));

		panel_2.add(mCityText);
		
		JLabel pincodeLabel = new JLabel("Pincode");
		panel_2.add(pincodeLabel);
		
		mPincodeText = new JTextField();
		mPincodeText.setText(userResultSet.getString("pincode"));
		mPincodeText.setColumns(10);
		panel_2.add(mPincodeText);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		
		JButton mUpdateButton = new JButton("Update");
		panel_3.add(mUpdateButton);
		
		JButton mCloseButton = new JButton("Close");
		panel_3.add(mCloseButton);
	}

}
