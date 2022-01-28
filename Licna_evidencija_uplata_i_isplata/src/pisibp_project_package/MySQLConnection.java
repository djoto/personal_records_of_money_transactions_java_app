package pisibp_project_package;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class MySQLConnection {
    
	FunkcijeEvidencija funkcije = new FunkcijeEvidencija();
	
	private Connection con = null;

    private String url = "jdbc:mysql://localhost:3306/evidencija_transakcija"; //set your url here
    private String username = "root"; //set your username here
    private String password = "password"; //set your password here
    
    public MySQLConnection() {

    }
    
    public MySQLConnection(String url, String username, String password) {
    	this.url = url;
    	this.username = username;
    	this.password = password;
    }
    
    public Connection createConnection(){
    	try {	
		    try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				funkcije.showWarning(e.getMessage());
				e.printStackTrace();
				return null;
			}
		    this.con = DriverManager.getConnection(this.url, this.username, this.password);
		    return this.con;
		    		    
    	} catch (SQLException ex) {
    		funkcije.showWarning(ex.getMessage());
    		//throw new Error("Error ", ex);
    		return null;
    	}
    }
    
    
    public void closeConnection() {
    	try {
            if (this.con != null) {
                this.con.close();
            }
        } catch (SQLException ex) {
        	funkcije.showWarning(ex.getMessage());
            //System.out.println(ex.getMessage());
        }
    }

}