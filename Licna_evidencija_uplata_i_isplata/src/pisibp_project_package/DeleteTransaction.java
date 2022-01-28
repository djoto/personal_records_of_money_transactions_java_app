package pisibp_project_package;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class DeleteTransaction extends JFrame {

	private JPanel contentPane;
	
	SQLQuery upit = new SQLQuery();
	FunkcijeEvidencija funkcije = new FunkcijeEvidencija();

	/**
	 * Create the frame.
	 */
	public DeleteTransaction() {
		setResizable(false);
		setTitle("Brisanje transakcije");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(450, 190, 348, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		super.getContentPane().setBackground(new Color(173, 216, 230));
		ImageIcon img = new ImageIcon("icon-transaction.png");
		super.setIconImage(img.getImage());
		
		JLabel lblUnesiteIdTransakcije = new JLabel("Unesite ID transakcije koju želite da obrišete:");
		lblUnesiteIdTransakcije.setHorizontalAlignment(SwingConstants.CENTER);
		lblUnesiteIdTransakcije.setBounds(12, 12, 314, 15);
		contentPane.add(lblUnesiteIdTransakcije);
		
		JSpinner spinnerIDBrisanje = new JSpinner();
		spinnerIDBrisanje.setFont(new Font("Dialog", Font.BOLD, 14));
		spinnerIDBrisanje.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		spinnerIDBrisanje.setBounds(136, 39, 65, 25);
		contentPane.add(spinnerIDBrisanje);
		
		JButton btnObrisi = new JButton("OBRIŠI");
		btnObrisi.setBackground(new Color(154, 205, 50));
		btnObrisi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int input = JOptionPane.showConfirmDialog(null, "Želite li obrisati transakciju?", "Potvrda brisanja",JOptionPane.YES_NO_OPTION);
				if(input==0) {
					upit.obrisiTransakciju((Integer) spinnerIDBrisanje.getValue());
				}
				EvidencijaGlavno home;
				try {
					home = new EvidencijaGlavno();
					home.getFrame().setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				closeWindow();
			}
		});
		btnObrisi.setBounds(61, 81, 98, 25);
		contentPane.add(btnObrisi);
		
		JButton btnIzadji = new JButton("IZAĐI");
		btnIzadji.setBackground(new Color(154, 205, 50));
		btnIzadji.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				EvidencijaGlavno home;
				try {
					home = new EvidencijaGlavno();
					home.getFrame().setVisible(true);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				closeWindow();
			}
		});
		btnIzadji.setBounds(187, 81, 98, 25);
		contentPane.add(btnIzadji);
	}
	
	public void closeWindow() {
		super.dispose();
	}
}
