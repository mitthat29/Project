package Training_Project;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
	static Connection con;
	public static Connection getConnection() {
		try {
			String oracleJDBCDriver="oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@localhost:1521/orcl.iiht.tech";
			String userName = "scott";
			String password = "tiger";
			Class.forName(oracleJDBCDriver);
			con = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			System.out.println("Connection Failed!");
		}

		return con;
	}
}
