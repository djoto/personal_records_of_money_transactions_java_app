package pisibp_project_package;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import org.json.JSONException;
import org.json.JSONObject;

public class FunkcijeEvidencija {
	
	public FunkcijeEvidencija() {}
	
	public void showWarning(String warning) {
		JFrame frame  = new JFrame("Upozorenje");
		ImageIcon img = new ImageIcon("icon-transaction.png");
		frame.setIconImage(img.getImage());
		JOptionPane.showMessageDialog(frame, warning);
	}
	
	public String hashWithSHA256(String textToHash) throws NoSuchAlgorithmException{
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(textToHash.getBytes(StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
	    for (byte b : hash) {
	        sb.append(String.format("%02X ", b));
	    }
		return sb.toString().replaceAll(" ", "");
	}
	
	public String getPasswordHash() {
		String passwd = "";
		try {
		      File passFile = new File("pass_file.txt");
		      Scanner myReader = new Scanner(passFile);
		      while (myReader.hasNext()) {
		        passwd = myReader.next();
		      }
		      myReader.close();
		} catch (FileNotFoundException e) {
	    	passwd = "";
		}
		return passwd;
	}
	
	public String getPasswordString(JPasswordField passField) {
		return new String(passField.getPassword());
	}
	
	public void azurirajKursValuta() throws SQLException {
		String[] listaValuta = {"AUD", "BAM", "CAD", "CHF", "EUR", "GBP", "HRK", "RSD", "RUB", "SEK", "USD"};

		JsonReader jsonReader = new JsonReader();
		JSONObject json;
		try {
			json = jsonReader.readJsonFromUrl("https://openexchangerates.org/api/latest.json?app_id=3e5fed026ac34f6083e9749f5349be0d");
			JSONObject trenutniKurseviKaUSD = (JSONObject) json.get("rates");
			
			SQLQuery upit = new SQLQuery();
			upit.updateExchangeRate(listaValuta, trenutniKurseviKaUSD);
			
		} catch (IOException | JSONException e) {
			// TODO Auto-generated catch block
			showWarning("Greška pri azuriranju kurseva valuta\nKursevi valuta neće biti najnovoji!");
			//e.printStackTrace();
		}	
		
	
	}
	
	
}
