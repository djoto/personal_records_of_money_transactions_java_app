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
import java.security.NoSuchAlgorithmException;
import java.awt.event.ActionEvent;

public class PromjenaSifre extends JFrame {

	private JPanel contentPane;
	FunkcijeEvidencija funkcije = new FunkcijeEvidencija();
	DodavanjeSifre dodavanje = new DodavanjeSifre();
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JLabel lblUnesiteeljenuNovu;
	private JButton btnPotvrdi;
	private JButton btnIzai;

	/**
	 * Create the frame.
	 */
	public PromjenaSifre() {
		setResizable(false);
		setTitle("Promjena šifre");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 170, 450, 274);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.getContentPane().setBackground(new Color(173, 216, 230));
		ImageIcon img = new ImageIcon("icon-transaction.png");
		super.setIconImage(img.getImage());
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.setBounds(144, 50, 160, 26);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField_1.setBounds(144, 124, 160, 26);
		contentPane.add(passwordField_1);
		
		JLabel lblUnesiteStaruifru = new JLabel("Unesite staru šifru:");
		lblUnesiteStaruifru.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnesiteStaruifru.setBounds(144, 28, 160, 15);
		contentPane.add(lblUnesiteStaruifru);
		
		lblUnesiteeljenuNovu = new JLabel("Unesite željenu novu šifru:");
		lblUnesiteeljenuNovu.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnesiteeljenuNovu.setBounds(144, 101, 160, 15);
		contentPane.add(lblUnesiteeljenuNovu);
		
		btnPotvrdi = new JButton("POTVRDI");
		btnPotvrdi.setBackground(new Color(154, 205, 50));
		btnPotvrdi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					changePassword();
					EvidencijaPocetna pocetna = new EvidencijaPocetna();
					pocetna.getFrame().setVisible(true);
					closeFrame();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnPotvrdi.setBounds(103, 184, 98, 25);
		contentPane.add(btnPotvrdi);
		
		btnIzai = new JButton("IZAĐI");
		btnIzai.setBackground(new Color(154, 205, 50));
		btnIzai.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EvidencijaPocetna pocetna = new EvidencijaPocetna();
				pocetna.getFrame().setVisible(true);
				closeFrame();
			}
		});
		btnIzai.setBounds(243, 184, 98, 25);
		contentPane.add(btnIzai);
	}
	
	public void closeFrame(){
	    super.dispose();
	}
	
	private void changePassword() throws NoSuchAlgorithmException { 
		String staraSifraHash = funkcije.getPasswordHash();
		String unesenaStaraSifra = funkcije.getPasswordString(passwordField);
		String unesenaNovaSifra = funkcije.getPasswordString(passwordField_1);
		if (staraSifraHash.equals(funkcije.hashWithSHA256(unesenaStaraSifra))) {
			dodavanje.setPassword(unesenaNovaSifra);
		}
		else {
			funkcije.showWarning("Neispravna šifra");
		}
	}

}
