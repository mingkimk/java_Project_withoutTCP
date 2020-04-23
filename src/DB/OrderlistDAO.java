package DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class OrderlistDAO {
	
	private static Connect c = Connect.getInstance();
	private Connection conn = c.connect();
	private Statement stmt;
	private ResultSet rs;
	public static OrderlistDTO orlT =null;
	public static OrderlistDAO order; 

	
	public OrderlistDAO() {
		
	}
	public static OrderlistDAO getInstance() {
		if(order==null) {
			order= new OrderlistDAO();
			
		}
		return order;
		
	}
	public ArrayList<String[]> getList() {
		ArrayList<String[]> list = new ArrayList<String[]>();
		String sql = "SELECT * FROM basket";
		try {
			stmt = conn.createStatement();
			if (stmt != null) {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					BasketlistDTO dto = new BasketlistDTO();

					dto.setId(rs.getString("id"));
					dto.setCode(rs.getInt("code"));
					dto.setCname(rs.getString("cname"));
					dto.setCnt(rs.getInt("cnt"));
					dto.setPrice(rs.getInt("price"));
					list.add(dto.getArray());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}

}

