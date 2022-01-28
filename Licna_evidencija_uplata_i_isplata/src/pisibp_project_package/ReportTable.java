package pisibp_project_package;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ReportTable extends JFrame {

	private JPanel contentPane;
	
	private SQLQuery upit = new SQLQuery();
	
	private String upitZaIzvrsavanje;
	
	private DefaultTableModel reportModel;


	public ReportTable(String query) {
		setTitle("Izvještaj");
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		super.getContentPane().setBackground(new Color(173, 216, 230));
		ImageIcon img = new ImageIcon("table-icon.png");
		super.setIconImage(img.getImage());
		
		reportModel = upit.generateReport(query);
		
		JTable table = new JTable(reportModel);
		table.setDefaultEditor(Object.class, null);
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
		renderer.setHorizontalAlignment( SwingConstants.CENTER );
        JScrollPane scrollPane = new JScrollPane( table );
        getContentPane().add( scrollPane );
	}
	
	public DefaultTableModel getModel() {
		return reportModel;
	}
	
	
}
