package pisibp_project_package;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class DefineReport {

	private JFrame frmDefinisanjeIzvjestaja;
	
	private FunkcijeEvidencija funkcije = new FunkcijeEvidencija();
	
	private JCheckBox chckbxIdTransakcije;
	private JCheckBox chckbxTip;
	private JCheckBox chckbxIznos;
	private JCheckBox chckbxDatum;
	private JCheckBox chckbxValuta;
	private JCheckBox chckbxSvrha;
	private JCheckBox chckbxBrojRacuna;
	private JCheckBox chckbxNazivBanke;
	private JCheckBox chckbxIme;
	private JCheckBox chckbxPrezime;
	private JCheckBox chckbxEmail;
	private JCheckBox chckbxAdresa;
	private JCheckBox chckbxBrojTelefona;
	
	private JComboBox comboBoxTip;
	private JTextField textFieldIznosOd;
	private JTextField textFieldIznosDo;
	private JDateChooser dateChooserOd;
	private JDateChooser dateChooserDo;
	private JComboBox comboBoxValuteFilter;
	private JTextField textFieldBrRacFilter;
	private JTextField textFieldImeFilter;
	private JTextField textFieldPrezimeFilter;
	private JTextField textFieldBrTelFilter;
	
	private String queryString;

	/**
	 * Create the application.
	 */
	public DefineReport() {
		initialize();
	}

	public JFrame getFrame() {
		return frmDefinisanjeIzvjestaja;
	}
	
	
	public String setQueryString() {
		
		String strSelect = "select t.red_br, t.tip, t.iznos, t.kod_valute, t.datum ";
		String strFrom = " from Transakcija as t, Racun as r, Vlasnik as v "; 
		String strWhere = " where t.br_racuna=r.br_racuna and r.redni_br = v.redni_br ";

		if (chckbxSvrha.isSelected()) {
			strSelect += ", t.svrha ";
		}
		if (chckbxBrojRacuna.isSelected()) {
			strSelect += ", r.br_racuna ";
		}
		if (chckbxNazivBanke.isSelected()) {
			strSelect += ", r.naziv_banke ";
		}
		if (chckbxIme.isSelected()) {
			strSelect += ", v.ime ";
		}
		if (chckbxPrezime.isSelected()) {
			strSelect += ", v.prezime ";
		}
		if (chckbxEmail.isSelected()) {
			strSelect += ", v.e_mail ";
		}
		if (chckbxAdresa.isSelected()) {
			strSelect += ", v.adresa ";
		}
		if (chckbxBrojTelefona.isSelected()) {
			strSelect += ", v.br_telefona ";
		}
		
		
		if (!comboBoxTip.getSelectedItem().toString().equals("SVE")) {
			strWhere += " and t.tip='"+comboBoxTip.getSelectedItem().toString().toLowerCase()+"' ";
		}
		if(!textFieldIznosOd.getText().equals("")) {
			strWhere += " and t.iznos>="+textFieldIznosOd.getText().toString()+" ";
		}
		if(!textFieldIznosDo.getText().equals("")) {
			strWhere += " and t.iznos<="+textFieldIznosDo.getText().toString()+" ";
		}
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date datumOd = (Date) dateChooserOd.getDate();
		Date datumDo = (Date) dateChooserDo.getDate();
		if (datumOd != null) {
			//System.out.println(dateFormat.format(datumOd));
			strWhere += " and t.datum>='"+dateFormat.format(datumOd)+"' ";
		}
		if (datumDo != null) {
			//System.out.println(dateFormat.format(datumDo));
			strWhere += " and t.datum<='"+dateFormat.format(datumDo)+"' ";
		}
		if (!comboBoxValuteFilter.getSelectedItem().toString().equals("Sve valute")) {
			strWhere += " and t.kod_valute='"+comboBoxValuteFilter.getSelectedItem().toString()+"' ";
		}
		if (!textFieldBrRacFilter.getText().equals("")) {
			strWhere += " and t.br_racuna like '"+textFieldBrRacFilter.getText().toString()+"%' ";
		}
		if (!textFieldImeFilter.getText().equals("")) {
			strWhere += " and v.ime like '"+textFieldImeFilter.getText().toString()+"%' ";
		}
		if (!textFieldPrezimeFilter.getText().equals("")) {
			strWhere += " and v.prezime like '"+textFieldPrezimeFilter.getText().toString()+"%' ";
		}
		if (!textFieldBrTelFilter.getText().equals("")) {
			strWhere += " and v.br_telefona like '"+textFieldBrTelFilter.getText().toString()+"%' ";
		}
		
		queryString = strSelect + strFrom + strWhere;
		
		return queryString;
	}
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDefinisanjeIzvjestaja = new JFrame();
		frmDefinisanjeIzvjestaja.getContentPane().setBackground(new Color(173, 216, 230));
		ImageIcon img = new ImageIcon("icon-transaction.png");
		frmDefinisanjeIzvjestaja.setIconImage(img.getImage());
		frmDefinisanjeIzvjestaja.setResizable(false);
		frmDefinisanjeIzvjestaja.setTitle("Definisanje izvještaja");
		frmDefinisanjeIzvjestaja.setBounds(300, 140, 673, 431);
		//frmDefinisanjeIzvjestaja.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDefinisanjeIzvjestaja.getContentPane().setLayout(null);
		
		JPanel panelFilteri = new JPanel();
		panelFilteri.setBackground(new Color(173, 216, 230));
		panelFilteri.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelFilteri.setBounds(325, 26, 319, 315);
		frmDefinisanjeIzvjestaja.getContentPane().add(panelFilteri);
		panelFilteri.setLayout(null);
		
		JLabel lblFilteri = new JLabel("FILTERI:");
		lblFilteri.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilteri.setBounds(0, 12, 319, 15);
		panelFilteri.add(lblFilteri);
		
		JLabel lblTip = new JLabel("Tip:");
		lblTip.setBounds(39, 44, 55, 15);
		panelFilteri.add(lblTip);
		lblTip.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JLabel lblIznosOd = new JLabel("Iznos:");
		lblIznosOd.setBounds(39, 75, 55, 15);
		panelFilteri.add(lblIznosOd);
		lblIznosOd.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JLabel lblDatum = new JLabel("Datum:");
		lblDatum.setBounds(39, 113, 55, 15);
		panelFilteri.add(lblDatum);
		lblDatum.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JLabel lblValuta = new JLabel("Valuta:");
		lblValuta.setBounds(39, 151, 55, 15);
		panelFilteri.add(lblValuta);
		lblValuta.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JLabel lblBrojRauna = new JLabel("Broj računa:");
		lblBrojRauna.setBounds(12, 185, 82, 15);
		panelFilteri.add(lblBrojRauna);
		lblBrojRauna.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JLabel lblIme = new JLabel("Ime:");
		lblIme.setBounds(39, 220, 55, 15);
		panelFilteri.add(lblIme);
		lblIme.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JLabel lblPrezime = new JLabel("Prezime:");
		lblPrezime.setBounds(39, 254, 55, 15);
		panelFilteri.add(lblPrezime);
		lblPrezime.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JLabel lblBrojTelefona = new JLabel("Broj telefona:");
		lblBrojTelefona.setBounds(12, 286, 82, 15);
		panelFilteri.add(lblBrojTelefona);
		lblBrojTelefona.setHorizontalAlignment(SwingConstants.TRAILING);
		
		comboBoxTip = new JComboBox();
		comboBoxTip.setModel(new DefaultComboBoxModel(new String[] {"SVE", "UPLATA", "ISPLATA"}));
		comboBoxTip.setBounds(99, 39, 96, 20);
		panelFilteri.add(comboBoxTip);
		
		JLabel lblOd = new JLabel("od");
		lblOd.setBounds(106, 75, 21, 15);
		panelFilteri.add(lblOd);
		
		JLabel lblIznosDo = new JLabel("do");
		lblIznosDo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblIznosDo.setBounds(203, 75, 21, 15);
		panelFilteri.add(lblIznosDo);
		
		textFieldIznosOd = new JTextField();
		textFieldIznosOd.setBounds(130, 73, 74, 19);
		panelFilteri.add(textFieldIznosOd);
		textFieldIznosOd.setColumns(10);
		
		textFieldIznosDo = new JTextField();
		textFieldIznosDo.setColumns(10);
		textFieldIznosDo.setBounds(233, 73, 74, 19);
		panelFilteri.add(textFieldIznosDo);
		
		JLabel lblDatumOd = new JLabel("od");
		lblDatumOd.setBounds(106, 113, 21, 15);
		panelFilteri.add(lblDatumOd);
		
		JLabel lblDatumDo = new JLabel("do");
		lblDatumDo.setHorizontalAlignment(SwingConstants.TRAILING);
		lblDatumDo.setBounds(203, 113, 21, 15);
		panelFilteri.add(lblDatumDo);
		
		dateChooserOd = new JDateChooser();
		dateChooserOd.setBounds(130, 109, 74, 19);
		panelFilteri.add(dateChooserOd);
		
		dateChooserDo = new JDateChooser();
		dateChooserDo.setBounds(233, 109, 74, 19);
		panelFilteri.add(dateChooserDo);
		
		comboBoxValuteFilter = new JComboBox();
		comboBoxValuteFilter.setModel(new DefaultComboBoxModel(new String[] {"Sve valute", "AUD", "BAM", "CAD", "CHF", "EUR", "GBP", "HRK", "RSD", "RUB", "SEK", "USD"}));
		comboBoxValuteFilter.setBounds(99, 148, 96, 20);
		panelFilteri.add(comboBoxValuteFilter);
		
		textFieldBrRacFilter = new JTextField();
		textFieldBrRacFilter.setBounds(99, 183, 132, 19);
		panelFilteri.add(textFieldBrRacFilter);
		textFieldBrRacFilter.setColumns(10);
		
		textFieldImeFilter = new JTextField();
		textFieldImeFilter.setColumns(10);
		textFieldImeFilter.setBounds(99, 218, 132, 19);
		panelFilteri.add(textFieldImeFilter);
		
		textFieldPrezimeFilter = new JTextField();
		textFieldPrezimeFilter.setColumns(10);
		textFieldPrezimeFilter.setBounds(99, 252, 132, 19);
		panelFilteri.add(textFieldPrezimeFilter);
		
		textFieldBrTelFilter = new JTextField();
		textFieldBrTelFilter.setColumns(10);
		textFieldBrTelFilter.setBounds(99, 284, 132, 19);
		panelFilteri.add(textFieldBrTelFilter);
		
		JPanel panelSadrzaj = new JPanel();
		panelSadrzaj.setBackground(new Color(173, 216, 230));
		panelSadrzaj.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelSadrzaj.setBounds(35, 26, 269, 315);
		frmDefinisanjeIzvjestaja.getContentPane().add(panelSadrzaj);
		panelSadrzaj.setLayout(null);
		
		JLabel lblOdaberitetae = new JLabel("Odaberite šta će izvještaj da sadrži:");
		lblOdaberitetae.setHorizontalAlignment(SwingConstants.CENTER);
		lblOdaberitetae.setBounds(0, 12, 269, 15);
		panelSadrzaj.add(lblOdaberitetae);
		
		chckbxIdTransakcije = new JCheckBox("ID transakcije");
		chckbxIdTransakcije.setBounds(8, 42, 112, 23);
		panelSadrzaj.add(chckbxIdTransakcije);
		chckbxIdTransakcije.setEnabled(false);
		chckbxIdTransakcije.setSelected(true);
		
		chckbxTip = new JCheckBox("Tip transakcije");
		chckbxTip.setBounds(8, 69, 112, 23);
		panelSadrzaj.add(chckbxTip);
		chckbxTip.setSelected(true);
		chckbxTip.setEnabled(false);
		
		chckbxIznos = new JCheckBox("Iznos");
		chckbxIznos.setBounds(8, 96, 112, 23);
		panelSadrzaj.add(chckbxIznos);
		chckbxIznos.setSelected(true);
		chckbxIznos.setEnabled(false);
		
		chckbxDatum = new JCheckBox("Datum");
		chckbxDatum.setBounds(8, 123, 112, 23);
		panelSadrzaj.add(chckbxDatum);
		chckbxDatum.setSelected(true);
		chckbxDatum.setEnabled(false);
		
		chckbxValuta = new JCheckBox("Valuta");
		chckbxValuta.setBounds(8, 150, 112, 23);
		panelSadrzaj.add(chckbxValuta);
		chckbxValuta.setSelected(true);
		chckbxValuta.setEnabled(false);
		
		chckbxSvrha = new JCheckBox("Svrha");
		chckbxSvrha.setBounds(138, 43, 112, 23);
		panelSadrzaj.add(chckbxSvrha);
		
		chckbxBrojRacuna = new JCheckBox("Broj računa");
		chckbxBrojRacuna.setBounds(138, 70, 112, 23);
		panelSadrzaj.add(chckbxBrojRacuna);
		
		chckbxNazivBanke = new JCheckBox("Naziv banke");
		chckbxNazivBanke.setBounds(138, 97, 112, 23);
		panelSadrzaj.add(chckbxNazivBanke);
		
		chckbxIme = new JCheckBox("Ime");
		chckbxIme.setBounds(138, 124, 112, 23);
		panelSadrzaj.add(chckbxIme);
		
		chckbxPrezime = new JCheckBox("Prezime");
		chckbxPrezime.setBounds(138, 151, 112, 23);
		panelSadrzaj.add(chckbxPrezime);
		
		chckbxEmail = new JCheckBox("E_mail");
		chckbxEmail.setBounds(138, 178, 112, 23);
		panelSadrzaj.add(chckbxEmail);
		
		chckbxAdresa = new JCheckBox("Adresa");
		chckbxAdresa.setBounds(138, 205, 112, 23);
		panelSadrzaj.add(chckbxAdresa);
		
		chckbxBrojTelefona = new JCheckBox("Broj telefona");
		chckbxBrojTelefona.setBounds(138, 232, 112, 23);
		panelSadrzaj.add(chckbxBrojTelefona);
		
		JButton btnPotvrdi = new JButton("POTVRDI");
		btnPotvrdi.setBackground(new Color(154, 205, 50));
		btnPotvrdi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String queryStr = setQueryString();
				ReportTable tabelaIzvjestaj = new ReportTable(queryStr);
				EvidencijaGlavno home;
				try {
					home = new EvidencijaGlavno();
					home.getFrame().setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (tabelaIzvjestaj.getModel() != null) {
					tabelaIzvjestaj.setVisible(true);
					tabelaIzvjestaj.setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
				else {
					funkcije.showWarning("Za Vaš upit nema podataka!");
				}
				getFrame().dispose();
			}
		});
		btnPotvrdi.setBounds(233, 358, 98, 25);
		frmDefinisanjeIzvjestaja.getContentPane().add(btnPotvrdi);
		
		JButton btnNazad = new JButton("NAZAD");
		btnNazad.setBackground(new Color(154, 205, 50));
		btnNazad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EvidencijaGlavno home;
				try {
					home = new EvidencijaGlavno();
					home.getFrame().setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				getFrame().dispose();
			}
		});
		btnNazad.setBounds(342, 358, 98, 25);
		frmDefinisanjeIzvjestaja.getContentPane().add(btnNazad);
	}
}
