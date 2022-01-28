package pisibp_project_package;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.awt.event.ActionEvent;

public class EvidencijaGlavno {

	private JFrame frameEvidencijaGlavno;
	FunkcijeEvidencija funkcije = new FunkcijeEvidencija();
	private double stanje;
	/**
	 * Create the application.
	 * @throws SQLException 
	 */
	public EvidencijaGlavno() throws SQLException {
		funkcije.azurirajKursValuta();
		SQLQuery upit = new SQLQuery();
		this.stanje = upit.azurirajStanje();
		initialize();
	}
	
	public JFrame getFrame() {
		return frameEvidencijaGlavno;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frameEvidencijaGlavno = new JFrame();
		frameEvidencijaGlavno.getContentPane().setBackground(new Color(173, 216, 230));
		ImageIcon img = new ImageIcon("icon-transaction.png");
		frameEvidencijaGlavno.setIconImage(img.getImage());
		frameEvidencijaGlavno.setFont(new Font("Dialog", Font.PLAIN, 14));
		frameEvidencijaGlavno.setResizable(false);
		frameEvidencijaGlavno.setTitle("Lična evidencija uplata i isplata");
		frameEvidencijaGlavno.setBounds(400, 170, 423, 358);
		frameEvidencijaGlavno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameEvidencijaGlavno.getContentPane().setLayout(null);
		
		DecimalFormat df = new DecimalFormat("#.##");
		
		JLabel lblStanje = new JLabel("STANJE:");
		lblStanje.setVerticalAlignment(SwingConstants.BOTTOM);
		lblStanje.setHorizontalAlignment(SwingConstants.CENTER);
		lblStanje.setFont(new Font("Dialog", Font.BOLD, 16));
		lblStanje.setBounds(31, 36, 370, 26);
		lblStanje.setText("STANJE: "+df.format(this.stanje)+" RSD");
		frameEvidencijaGlavno.getContentPane().add(lblStanje);
		
		JButton btnDodajTransakciju = new JButton("DODAJ TRANSAKCIJU");
		btnDodajTransakciju.setBackground(new Color(154, 205, 50));
		btnDodajTransakciju.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					DodavanjeTransakcije dodavanje = new DodavanjeTransakcije();
					dodavanje.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				frameEvidencijaGlavno.dispose();
			}
		});
		btnDodajTransakciju.setBounds(119, 88, 192, 32);
		frameEvidencijaGlavno.getContentPane().add(btnDodajTransakciju);
		
		JButton btnGenerisiIzvjestaj = new JButton("GENERIŠI IZVJEŠTAJ");
		btnGenerisiIzvjestaj.setBackground(new Color(154, 205, 50));
		btnGenerisiIzvjestaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DefineReport izvjestaj = new DefineReport();
				izvjestaj.getFrame().setVisible(true);
				getFrame().dispose();
			}
		});
		btnGenerisiIzvjestaj.setBounds(119, 150, 192, 32);
		frameEvidencijaGlavno.getContentPane().add(btnGenerisiIzvjestaj);
		
		JButton btnObrisi = new JButton("OBRIŠI TRANSAKCIJU");
		btnObrisi.setBackground(new Color(154, 205, 50));
		btnObrisi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DeleteTransaction brisanje = new DeleteTransaction();
				brisanje.setVisible(true);
				getFrame().dispose();
			}
		});
		btnObrisi.setBounds(119, 210, 192, 32);
		frameEvidencijaGlavno.getContentPane().add(btnObrisi);
		
		JButton btnNewButton = new JButton("NAZAD");
		btnNewButton.setBackground(new Color(154, 205, 50));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EvidencijaPocetna pocetna = new EvidencijaPocetna();
				pocetna.getFrame().setVisible(true);
				getFrame().dispose();
			}
		});
		btnNewButton.setBounds(164, 273, 100, 32);
		frameEvidencijaGlavno.getContentPane().add(btnNewButton);
	}
}
