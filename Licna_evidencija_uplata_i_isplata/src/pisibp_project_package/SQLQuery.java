package pisibp_project_package;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSetMetaData;

public class SQLQuery {
	
	FunkcijeEvidencija funkcije = new FunkcijeEvidencija();
	
	private MySQLConnection connection = new MySQLConnection();
	private Connection con = null;
	
	public SQLQuery() {}
	
	public double azurirajStanje() {
		
		this.con = connection.createConnection();
		
		double stanje = 0;
		
		String query = "select("+
				"(select ifNull(sum(iznos*(select kurs from Valuta where kod_valute=Transakcija.kod_valute)),0)"+
					"from Transakcija where tip='uplata')-"+
				"(select ifNull(sum(iznos*(select kurs from Valuta where kod_valute=Transakcija.kod_valute)),0)"+
					"from Transakcija where tip='isplata')"+
				") as stanje";
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	          stanje = rs.getDouble("stanje");
	        }
	     } catch (SQLException e) {
	    	  funkcije.showWarning(e.getMessage());
	    	  //JDBCTutorialUtilities.printSQLException(e);
	    	  stanje = 0;
	     }
	    
	    connection.closeConnection();
	    
	    return stanje;
		
	}
	
	
	public void updateExchangeRate(String[] valute, JSONObject trenutniKurseviKaUSD) throws SQLException, JSONException {
		
		DecimalFormat df = new DecimalFormat("#.####");
		
		this.con = connection.createConnection();
		
		int i;
		
		double kursRSDkaUSD = (double) trenutniKurseviKaUSD.get("RSD");
		
		PreparedStatement preparedStatement = this.con.prepareStatement("UPDATE Valuta SET kurs=? WHERE kod_valute = ?");
		
		for (i=0; i < valute.length; i++) {
			try {
				if(i==valute.length-1) {
					preparedStatement.setString(1, df.format(Double.valueOf(kursRSDkaUSD)));
				}
				else {
					preparedStatement.setString(1, df.format(Double.valueOf(kursRSDkaUSD / (double) trenutniKurseviKaUSD.get(valute[i]))));
				}
				preparedStatement.setString(2, valute[i]);
				int updateCount = preparedStatement.executeUpdate();
				//System.out.println(updateCount);
				//query = "update Valuta set kurs="+Double.valueOf((double) trenutniKurseviKaUSD.get(valute[i])).toString()+" where kod_valute="+valute[i];
			} catch (JSONException ex) {
				//funkcije.showWarning(ex.getMessage());
				funkcije.showWarning("Greška!");
			}
		}

		
		connection.closeConnection();
	}
	
	
	public String poslednjiVlasnikUTabeli(){

		Integer redni_br = 0;
			
		String query = "SELECT redni_br FROM Vlasnik ORDER BY redni_br DESC LIMIT 1";
		
	    try (Statement stmt = this.con.createStatement()) {
	        ResultSet rs = stmt.executeQuery(query);
	        while (rs.next()) {
	           redni_br = rs.getInt("redni_br");
	        }
	     } catch (SQLException e) {
	    	  funkcije.showWarning(e.getMessage());
	     }
	    //System.out.println(redni_br.getClass().getName());
	    //System.out.println(redni_br);
	    return redni_br.toString();		
	}
	
	public String checkUndefined(String str) {
		if (str.equals("")) {
			return "nedefinisano";
		}
		else {
			return str;
		}
	}
	
	public String setBrRacuna(String broj_racuna) {
		if (broj_racuna.equals("")) {
			Integer numUndef = 0;
			
			String query = "select count(br_racuna) as cnt from Racun where br_racuna LIKE \"undefined_%\"";
			
		    try (Statement stmt = this.con.createStatement()) {
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		           numUndef = rs.getInt("cnt");
		        }
		     } catch (SQLException e) {
		    	  funkcije.showWarning(e.getMessage());
		     }
		    return "undefined_"+(Integer.valueOf(numUndef+1)).toString();
		}
		
		return broj_racuna;
		
	}
	
	
	public boolean dodajTransakciju(String tip, Double iznos, String datum, String svrha, String valuta, String broj_racuna, String naziv_banke, String ime, String prezime, String e_mail, String adresa, String broj_telefona) {
		DecimalFormat df = new DecimalFormat("#.##");

		this.con = connection.createConnection();
		
		String stringIznos = df.format(iznos);
		
		boolean vlasnikInsertionOK;
		boolean racunInsertionOK;
		boolean transactionInsertionOK = false;
		String br_rac;
		
		String insertIntoVlasnik = "insert into Vlasnik (ime, prezime, e_mail, adresa, br_telefona) values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStmtVlasnik;
		try {
			preparedStmtVlasnik = con.prepareStatement(insertIntoVlasnik);
		    preparedStmtVlasnik.setString(1, checkUndefined(ime));
		    preparedStmtVlasnik.setString(2, checkUndefined(prezime));
		    preparedStmtVlasnik.setString(3, checkUndefined(e_mail));
		    preparedStmtVlasnik.setString(4, checkUndefined(adresa));
		    preparedStmtVlasnik.setString(5, checkUndefined(broj_telefona));
		    // execute the preparedstatement
		    preparedStmtVlasnik.execute();
		    vlasnikInsertionOK = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			vlasnikInsertionOK = false;
			funkcije.showWarning("ERROR! Neuspješno dodavanje vlasnika! "+e.getMessage());
			e.printStackTrace();
		}
		
		if (vlasnikInsertionOK == true) {
			String insertIntoRacun = "insert into Racun (br_racuna, naziv_banke, redni_br) values (?, ?, ?)";
			PreparedStatement preparedStmtRacun;
			try {
				preparedStmtRacun = con.prepareStatement(insertIntoRacun);
				br_rac = setBrRacuna(broj_racuna);
			    preparedStmtRacun.setString(1, br_rac);
			    preparedStmtRacun.setString(2, checkUndefined(naziv_banke));
			    preparedStmtRacun.setString(3, poslednjiVlasnikUTabeli());
			    // execute the preparedstatement
			    preparedStmtRacun.execute();
			    racunInsertionOK = true;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				br_rac = "undefined_err";
				racunInsertionOK = false;
				funkcije.showWarning("ERROR! Neuspješno dodavanje racuna! "+e.getMessage());
				e.printStackTrace();
			}
		
			
			if (racunInsertionOK == true) {
				String insertIntoTransakcija = "insert into Transakcija (tip, iznos, datum, svrha, kod_valute, br_racuna) values (?, ?, ?, ?, ?, ?)";
				PreparedStatement preparedStmtTransakcija;
				try {
					preparedStmtTransakcija = con.prepareStatement(insertIntoTransakcija);
				    preparedStmtTransakcija.setString(1, tip);
				    preparedStmtTransakcija.setString(2, df.format(iznos));
				    preparedStmtTransakcija.setString(3, datum);
				    preparedStmtTransakcija.setString(4, checkUndefined(svrha));
				    preparedStmtTransakcija.setString(5, valuta);
				    preparedStmtTransakcija.setString(6, br_rac);
				    // execute the preparedstatement
				    preparedStmtTransakcija.execute();
				    transactionInsertionOK = true;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					transactionInsertionOK = false;
					funkcije.showWarning("ERROR! Neuspješno dodavanje transakcije! "+e.getMessage());
					e.printStackTrace();
				}
			}
		
		}
		
		connection.closeConnection();
		
		return transactionInsertionOK;
		
	}
	
	
	public DefaultTableModel generateReport(String query) {
		DecimalFormat df = new DecimalFormat("#.##");

		this.con = connection.createConnection();
		
        Vector columnNames = new Vector();
        Vector data = new Vector();
		
        //String sql = "Select * from Vlasnik";
        Statement stmt;
		try {
			stmt = con.createStatement();
	        ResultSet rs = stmt.executeQuery( query );
	        ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
	        int columns = md.getColumnCount();

	        //  Get column names
	        for (int i = 1; i <= columns; i++) {
	            //columnNames.addElement( md.getColumnName(i) );
	            switch(md.getColumnName(i)) {
		            case "red_br":
		            	columnNames.addElement("ID");
		            	break;
		            case "tip":
		            	columnNames.addElement("TIP");
		            	break;
		            case "iznos":
		            	columnNames.addElement("IZNOS");
		            	break;
		            case "datum":
		            	columnNames.addElement("DATUM");
		            	break;
		            case "svrha":
		            	columnNames.addElement("SVRHA");
		            	break;
		            case "kod_valute":
		            	columnNames.addElement("VALUTA");
		            	break;
		            case "br_racuna":
		            	columnNames.addElement("BROJ RAČUNA");
		            	break;
		            case "naziv_banke":
		            	columnNames.addElement("NAZIV BANKE");
		            	break;
		            case "ime":
		            	columnNames.addElement("IME");
		            	break;
		            case "prezime":
		            	columnNames.addElement("PREZIME");
		            	break;
		            case "e_mail":
		            	columnNames.addElement("E_MAIL");
		            	break;
		            case "adresa":
		            	columnNames.addElement("ADRESA");
		            	break;
		            case "br_telefona":
		            	columnNames.addElement("BROJ TELEFONA");
		            	break;
		            default:
		            	columnNames.addElement(md.getColumnName(i));
	          }

	        }

	        //  Get row data
	        boolean empty = true;
	        while (rs.next()) {
	            Vector<Object> row = new Vector<Object>(columns);

	            for (int i = 1; i <= columns; i++) {
	                row.addElement( rs.getObject(i) );
	            }

	            data.addElement( row );

	            empty = false;
	        }
	        /*Vector<Object> row = new Vector<Object>(columns);
	        row.addElement("nesto");
	        data.addElement( row );*/
	        
	        if (empty) {
	        	return null;
	        }
	        
	        rs.close();
	        stmt.close();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
		
		connection.closeConnection();
		
		return model;
		
	}
	
	
	public void obrisiTransakciju(Integer id) {
		
		this.con = connection.createConnection();
		
		String updateStr = "delete from Transakcija where red_br="+id;
		
		PreparedStatement preparedStatement;
		int count = 0;
		try {
			preparedStatement = con.prepareStatement(updateStr);
			count = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			funkcije.showWarning("Neispravan unos!");
			e.printStackTrace();
		}
		
		if (count==0) {
			funkcije.showWarning("Ne postoji transakcija koja ima uneseni ID!");
		}
		
		connection.closeConnection();
		
	}


	
}
