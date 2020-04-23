package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	public static Connect c = null;
	private Connection conn;

	private Connect() {

	}

	public static Connect getInstance() {
		if (c == null) {
			c = new Connect();
		}
		return c;
	}

	public void orclelode() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Ŭ���� �ε� ����");
		} catch (ClassNotFoundException e) {
			System.out.println("Ŭ���� �ε� ����");
		}

	}

	public Connection connect() {
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			System.out.println("db���� ����");
		} catch (SQLException e) {
			System.out.println("����");
			e.printStackTrace();
		}
		return conn;
	}
}
