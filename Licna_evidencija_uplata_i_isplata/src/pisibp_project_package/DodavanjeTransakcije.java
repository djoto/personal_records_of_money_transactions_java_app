package pisibp_project_package;


import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class DodavanjeTransakcije {

	private JFrame frmDodavanje;
	private JTextField textFieldIznos;
	private JTextField textFieldBrRacuna;
	private JTextField textFieldNazivBanke;
	private JTextField textFieldIme;
	private JTextField textFieldPrezime;
	private JTextField textFieldEmail;
	private JTextField textFieldAdresa;
	private JTextField textFieldBrTelefona;
	private JDateChooser dateChooser;
	
	FunkcijeEvidencija funkcije = new FunkcijeEvidencija();
	SQLQuery upit = new SQLQuery();

	/**
	 * Create the application.
	 */
	public DodavanjeTransakcije() {
		initialize();
	}
	
	public JFrame getFrame() {
		return frmDodavanje;
	}

	
	public boolean provjeriPodatkeZaDodavanje(String iznos, String broj_racuna, Date datum){
		
		try {  
		    Double.parseDouble(iznos);  
		  } catch(NumberFormatException e){
			funkcije.showWarning("Neispravno ste unijeli iznos!");
		    return false;  
		  }  
		
		if (!broj_racuna.equals("")) {
			try {  
			    Long.parseLong(broj_racuna);
			  } catch(NumberFormatException e){
				funkcije.showWarning("Neispravno ste unijeli broj računa!");
			    return false;  
			  } 
		}

		
		if (datum == null) {
			funkcije.showWarning("Niste unijeli datum transakcije!");
		    return false;  
		}
		return true;
	}
		
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDodavanje = new JFrame();
		frmDodavanje.getContentPane().setBackground(new Color(173, 216, 230));
		ImageIcon img = new ImageIcon("icon-transaction.png");
		frmDodavanje.setIconImage(img.getImage());
		frmDodavanje.setResizable(false);
		frmDodavanje.setTitle("Dodavanje transakcije");
		frmDodavanje.setBounds(300, 140, 749, 437);
		//frmDodavanje.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDodavanje.getContentPane().setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"UPLATA", "ISPLATA"}));
		comboBox.setBounds(73, 64, 104, 19);
		frmDodavanje.getContentPane().add(comboBox);
		
		textFieldIznos = new JTextField();
		textFieldIznos.setBounds(73, 101, 104, 19);
		frmDodavanje.getContentPane().add(textFieldIznos);
		textFieldIznos.setColumns(10);
		
		dateChooser = new JDateChooser();
		dateChooser.setBounds(73, 182, 104, 19);
		frmDodavanje.getContentPane().add(dateChooser);
		
		JComboBox comboBoxValute = new JComboBox();
		comboBoxValute.setModel(new DefaultComboBoxModel(new String[] {"RSD", "AUD", "BAM", "CAD", "CHF", "EUR", "GBP", "HRK", "RUB", "SEK", "USD"}));
		comboBoxValute.setBounds(73, 140, 104, 19);
		frmDodavanje.getContentPane().add(comboBoxValute);
		
		JLabel lblTip = new JLabel("Tip:");
		lblTip.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTip.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTip.setBounds(46, 66, 21, 17);
		frmDodavanje.getContentPane().add(lblTip);
		
		JLabel lblIznos = new JLabel("Iznos:");
		lblIznos.setVerticalAlignment(SwingConstants.BOTTOM);
		lblIznos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIznos.setBounds(35, 101, 34, 17);
		frmDodavanje.getContentPane().add(lblIznos);
		
		JLabel lblValuta = new JLabel("Valuta:");
		lblValuta.setVerticalAlignment(SwingConstants.BOTTOM);
		lblValuta.setHorizontalAlignment(SwingConstants.RIGHT);
		lblValuta.setBounds(24, 140, 45, 17);
		frmDodavanje.getContentPane().add(lblValuta);
		
		JLabel lblDatum = new JLabel("Datum:");
		lblDatum.setVerticalAlignment(SwingConstants.BOTTOM);
		lblDatum.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDatum.setBounds(24, 184, 45, 17);
		frmDodavanje.getContentPane().add(lblDatum);
		
		JLabel lblSvrha = new JLabel("Svrha:");
		lblSvrha.setVerticalAlignment(SwingConstants.BOTTOM);
		lblSvrha.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSvrha.setBounds(29, 230, 38, 17);
		frmDodavanje.getContentPane().add(lblSvrha);
		
		JScrollPane scrollPaneSvrha = new JScrollPane();
		//scrollPaneRacun.setViewportBorder(new LineBorder(new Color(124, 252, 0), 3));
		scrollPaneSvrha.setBounds(73, 231, 215, 67);
		frmDodavanje.getContentPane().add(scrollPaneSvrha);
		
		JTextArea textAreaSvrha = new JTextArea();
		scrollPaneSvrha.setViewportView(textAreaSvrha);
		
		JLabel lblPodaciOTransakciji = new JLabel("Podaci o transakciji:");
		lblPodaciOTransakciji.setFont(new Font("Dialog", Font.BOLD, 14));
		lblPodaciOTransakciji.setHorizontalAlignment(SwingConstants.CENTER);
		lblPodaciOTransakciji.setBounds(22, 18, 206, 24);
		frmDodavanje.getContentPane().add(lblPodaciOTransakciji);
		
		JLabel lblPodaciORacunu = new JLabel("<html>Podaci o računu sa kojeg Vam se<br/>uplaćuje / na koji isplaćujete:</html>", SwingConstants.CENTER);
		lblPodaciORacunu.setBounds(242, 13, 246, 35);
		frmDodavanje.getContentPane().add(lblPodaciORacunu);
		
		textFieldBrRacuna = new JTextField();
		textFieldBrRacuna.setBounds(347, 74, 141, 19);
		frmDodavanje.getContentPane().add(textFieldBrRacuna);
		textFieldBrRacuna.setColumns(10);
		
		textFieldNazivBanke = new JTextField();
		textFieldNazivBanke.setBounds(347, 111, 141, 19);
		frmDodavanje.getContentPane().add(textFieldNazivBanke);
		textFieldNazivBanke.setColumns(10);
		
		JLabel lblBrojRacuna = new JLabel("Broj računa:");
		lblBrojRacuna.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBrojRacuna.setBounds(260, 76, 81, 15);
		frmDodavanje.getContentPane().add(lblBrojRacuna);
		
		JLabel lblNazivBanke = new JLabel("Naziv banke:");
		lblNazivBanke.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNazivBanke.setBounds(260, 113, 81, 15);
		frmDodavanje.getContentPane().add(lblNazivBanke);
		
		JLabel lblPodaciOOsobi = new JLabel("<html>Podaci o osobi koja Vam<br/>uplaćuje / kojoj isplaćujete:</html>", SwingConstants.CENTER);
		lblPodaciOOsobi.setBounds(531, 17, 206, 35);
		frmDodavanje.getContentPane().add(lblPodaciOOsobi);
		
		textFieldIme = new JTextField();
		textFieldIme.setBounds(608, 74, 104, 19);
		frmDodavanje.getContentPane().add(textFieldIme);
		textFieldIme.setColumns(10);
		
		textFieldPrezime = new JTextField();
		textFieldPrezime.setBounds(608, 111, 104, 19);
		frmDodavanje.getContentPane().add(textFieldPrezime);
		textFieldPrezime.setColumns(10);
		
		textFieldEmail = new JTextField();
		textFieldEmail.setBounds(608, 149, 104, 19);
		frmDodavanje.getContentPane().add(textFieldEmail);
		textFieldEmail.setColumns(10);
		
		textFieldAdresa = new JTextField();
		textFieldAdresa.setBounds(608, 187, 104, 19);
		frmDodavanje.getContentPane().add(textFieldAdresa);
		textFieldAdresa.setColumns(10);
		
		textFieldBrTelefona = new JTextField();
		textFieldBrTelefona.setBounds(608, 224, 104, 19);
		frmDodavanje.getContentPane().add(textFieldBrTelefona);
		textFieldBrTelefona.setColumns(10);
		
		JLabel lblIme = new JLabel("Ime:");
		lblIme.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIme.setBounds(547, 76, 55, 15);
		frmDodavanje.getContentPane().add(lblIme);
		
		JLabel lblPrezme = new JLabel("Prezime:");
		lblPrezme.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPrezme.setBounds(547, 113, 55, 15);
		frmDodavanje.getContentPane().add(lblPrezme);
		
		JLabel lblEmail = new JLabel("e-mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(547, 151, 55, 15);
		frmDodavanje.getContentPane().add(lblEmail);
		
		JLabel lblAdresa = new JLabel("Adresa:");
		lblAdresa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAdresa.setBounds(547, 189, 55, 15);
		frmDodavanje.getContentPane().add(lblAdresa);
		
		JLabel lblBrojTelefona = new JLabel("Broj telefona:");
		lblBrojTelefona.setHorizontalAlignment(SwingConstants.RIGHT);
		lblBrojTelefona.setBounds(515, 226, 87, 15);
		frmDodavanje.getContentPane().add(lblBrojTelefona);
		
		JButton btnNewButton = new JButton("DODAJ");
		btnNewButton.setBackground(new Color(154, 205, 50));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Date datum = (Date) dateChooser.getDate();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				if (provjeriPodatkeZaDodavanje(textFieldIznos.getText(), textFieldBrRacuna.getText(), datum)) {
					if(upit.dodajTransakciju(comboBox.getSelectedItem().toString(), Double.valueOf(textFieldIznos.getText()), dateFormat.format(datum), textAreaSvrha.getText(), comboBoxValute.getSelectedItem().toString(), textFieldBrRacuna.getText(), textFieldNazivBanke.getText(), textFieldIme.getText(), textFieldPrezime.getText(), textFieldEmail.getText(), textFieldAdresa.getText(), textFieldBrTelefona.getText())) {
						JFrame frame  = new JFrame("Status");
						JOptionPane.showMessageDialog(frame, "Uspješno ste dodali transakciju!");
					}
					else {
						funkcije.showWarning("Dodavanje transakcije nije uspjelo!");
					}
					try {
						EvidencijaGlavno glavniProzor = new EvidencijaGlavno();
						glavniProzor.getFrame().setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
					frmDodavanje.dispose();
				}
				
			}
		});
		btnNewButton.setBounds(226, 350, 98, 25);
		frmDodavanje.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("NAZAD");
		btnNewButton_1.setBackground(new Color(154, 205, 50));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					EvidencijaGlavno glavniProzor = new EvidencijaGlavno();
					glavniProzor.getFrame().setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				frmDodavanje.dispose();
			}
		});
		btnNewButton_1.setBounds(399, 350, 98, 25);
		frmDodavanje.getContentPane().add(btnNewButton_1);
		
		JLabel label = new JLabel("*");
		label.setVerticalAlignment(SwingConstants.BOTTOM);
		label.setFont(new Font("Dialog", Font.BOLD, 14));
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		label.setForeground(Color.RED);
		label.setBounds(13, 58, 28, 24);
		frmDodavanje.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("*");
		label_1.setVerticalAlignment(SwingConstants.BOTTOM);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Dialog", Font.BOLD, 14));
		label_1.setBounds(12, 95, 22, 24);
		frmDodavanje.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("*");
		label_2.setVerticalAlignment(SwingConstants.BOTTOM);
		label_2.setHorizontalAlignment(SwingConstants.TRAILING);
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Dialog", Font.BOLD, 14));
		label_2.setBounds(2, 135, 21, 24);
		frmDodavanje.getContentPane().add(label_2);
		
		JLabel label_3 = new JLabel("*");
		label_3.setVerticalAlignment(SwingConstants.BOTTOM);
		label_3.setHorizontalAlignment(SwingConstants.TRAILING);
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Dialog", Font.BOLD, 14));
		label_3.setBounds(2, 177, 21, 24);
		frmDodavanje.getContentPane().add(label_3);
		
		JLabel lblNapomenaPoljaOznaena = new JLabel("Napomena: Polja označena sa");
		lblNapomenaPoljaOznaena.setBounds(226, 310, 172, 15);
		frmDodavanje.getContentPane().add(lblNapomenaPoljaOznaena);
		
		JLabel label_3_1 = new JLabel("*");
		label_3_1.setVerticalAlignment(SwingConstants.BOTTOM);
		label_3_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_3_1.setForeground(Color.RED);
		label_3_1.setFont(new Font("Dialog", Font.BOLD, 14));
		label_3_1.setBounds(399, 302, 21, 24);
		frmDodavanje.getContentPane().add(label_3_1);
		
		JLabel lblSuObavezna = new JLabel("su obavezna!");
		lblSuObavezna.setBounds(419, 310, 87, 15);
		frmDodavanje.getContentPane().add(lblSuObavezna);
	}
}
