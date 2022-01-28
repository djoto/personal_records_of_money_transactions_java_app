package pisibp_project_package;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class DodavanjeSifre extends JFrame {

	private JPanel contentPane;
	private JPasswordField passwordField;
	FunkcijeEvidencija funkcije = new FunkcijeEvidencija();

	/**
	 * Create the frame.
	 */
	public DodavanjeSifre() {
		setTitle("Dodavanje šifre");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 170, 450, 193);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.getContentPane().setBackground(new Color(173, 216, 230));
		ImageIcon img = new ImageIcon("icon-transaction.png");
		super.setIconImage(img.getImage());
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(140, 39, 165, 26);
		contentPane.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("Unesite željenu šifru:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(140, 12, 165, 15);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("POTVRDI");
		btnNewButton.setBackground(new Color(154, 205, 50));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String pass = funkcije.getPasswordString(passwordField);
					setPassword(pass);
					EvidencijaPocetna pocetna = new EvidencijaPocetna();
					pocetna.getFrame().setVisible(true);
					closeFrame();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(98, 88, 101, 25);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("IZAĐI");
		btnNewButton_1.setBackground(new Color(154, 205, 50));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EvidencijaPocetna pocetna = new EvidencijaPocetna();
				pocetna.getFrame().setVisible(true);
				closeFrame();
			}
		});
		btnNewButton_1.setBounds(242, 88, 101, 25);
		contentPane.add(btnNewButton_1);
	}
	
	public void closeFrame(){
	    super.dispose();
	}
	
	public void setPassword(String pass) throws NoSuchAlgorithmException{
		
		if (pass.equals("") || pass.equals(" ")) {
			funkcije.showWarning("Neispravan format šifre!");
		}
		else {
			try {
			      File passFile = new File("pass_file.txt");
			      passFile.createNewFile();
			      passFile.setWritable(true);
			      FileWriter myWriter = new FileWriter(passFile);
			      myWriter.write(funkcije.hashWithSHA256(pass));
			      myWriter.close();
			      passFile.setWritable(false);
			} catch (IOException e) {
				funkcije.showWarning("Pojavila se greška!");
			}
		}
	}	

}
