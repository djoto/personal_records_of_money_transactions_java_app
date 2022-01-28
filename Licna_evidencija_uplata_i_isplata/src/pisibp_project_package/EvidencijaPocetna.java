package pisibp_project_package;

import java.awt.EventQueue;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.security.NoSuchAlgorithmException;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import java.awt.Color;


public class EvidencijaPocetna {

	private static String passwHash = null;
	private JFrame frmLicnaEvidencijaUplata;
	private JPasswordField inputPass;
	FunkcijeEvidencija funkcije = new FunkcijeEvidencija();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EvidencijaPocetna window = new EvidencijaPocetna();
					window.frmLicnaEvidencijaUplata.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EvidencijaPocetna() {
		passwHash = funkcije.getPasswordHash();
		initialize();
	}
	
	public JFrame getFrame() {
		return frmLicnaEvidencijaUplata;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLicnaEvidencijaUplata = new JFrame();
		ImageIcon img = new ImageIcon("icon-transaction.png");
		frmLicnaEvidencijaUplata.setIconImage(img.getImage());
		frmLicnaEvidencijaUplata.getContentPane().setBackground(new Color(173, 216, 230));
		frmLicnaEvidencijaUplata.setResizable(false);
		frmLicnaEvidencijaUplata.setTitle("Lična evidencija uplata i isplata - Prijava");
		frmLicnaEvidencijaUplata.setBounds(400, 170, 450, 300);
		frmLicnaEvidencijaUplata.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLicnaEvidencijaUplata.getContentPane().setLayout(null);
		
		inputPass = new JPasswordField();
		inputPass.setHorizontalAlignment(SwingConstants.CENTER);
		inputPass.setBounds(141, 79, 157, 25);
		frmLicnaEvidencijaUplata.getContentPane().add(inputPass);
		
		JButton btnDalje = new JButton("DALJE");
		btnDalje.setBackground(new Color(154, 205, 50));
		btnDalje.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					if(funkcije.hashWithSHA256(funkcije.getPasswordString(inputPass)).equals(passwHash)) {
						try {
							EvidencijaGlavno glavniProzor = new EvidencijaGlavno();
							glavniProzor.getFrame().setVisible(true);
						} catch (Exception e) {
							e.printStackTrace();
						}
						frmLicnaEvidencijaUplata.setVisible(false);
					}
					else {
						funkcije.showWarning("Neispravna šifra");
					}
				} catch (HeadlessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnDalje.setBounds(171, 121, 98, 30);
		//frmLicnaEvidencijaUplata.getContentPane().add(btnDalje);
		
		
		JButton btnNastavi = new JButton("NASTAVI BEZ ŠIFRE");
		btnNastavi.setBackground(new Color(154, 205, 50));
		btnNastavi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					EvidencijaGlavno glavniProzor = new EvidencijaGlavno();
					glavniProzor.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				frmLicnaEvidencijaUplata.dispose();
			}
		});
		btnNastavi.setBounds(121, 121, 198, 30);
		//frmLicnaEvidencijaUplata.getContentPane().add(btnNastavi);
		
		
		JLabel lblUnesiteSifru = new JLabel("Unesite šifru za pristup:");
		lblUnesiteSifru.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnesiteSifru.setBounds(141, 52, 157, 15);
		frmLicnaEvidencijaUplata.getContentPane().add(lblUnesiteSifru);
		
		
		JButton btnPromijeniSifru = new JButton("Promijeni šifru");
		btnPromijeniSifru.setBackground(new Color(154, 205, 50));
		btnPromijeniSifru.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PromjenaSifre promjenaSifre = new PromjenaSifre();
				promjenaSifre.setVisible(true);
				frmLicnaEvidencijaUplata.setVisible(false);
			}
		});
		btnPromijeniSifru.setBounds(158, 177, 128, 25);
		
		JButton btnPodesiSifru = new JButton("Dodaj šifru");
		btnPodesiSifru.setBackground(new Color(154, 205, 50));
		btnPodesiSifru.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DodavanjeSifre dodavanjeSifre = new DodavanjeSifre();
				dodavanjeSifre.setVisible(true);
				frmLicnaEvidencijaUplata.setVisible(false);
			}
		});
		btnPodesiSifru.setBounds(158, 177, 128, 25);
		
		
		if (passwHash == "") {
			frmLicnaEvidencijaUplata.getContentPane().add(btnPodesiSifru);
			frmLicnaEvidencijaUplata.getContentPane().add(btnNastavi);
		}
		else {
			frmLicnaEvidencijaUplata.getContentPane().add(btnPromijeniSifru);
			frmLicnaEvidencijaUplata.getContentPane().add(btnDalje);
		}
	
	}
	
	
}
