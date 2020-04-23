package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class GoodsDAO {
	private static Connect c = Connect.getInstance();
	private Connection conn = c.connect();
	private Statement stmt;
	private ResultSet rs;

	public static GoodsDAO DAOobj;

	public GoodsDAO() {

	}



	public static GoodsDAO getInstance() {
		if (DAOobj == null) {
			DAOobj = new GoodsDAO();

		}
		return DAOobj;

	}

	public boolean Insert(GoodsDTO dto) {
//		GoodsDTO dto = new GoodsDTO();
//		String[] check = (String[]) obj;
//		int code = Integer.parseInt(check[0]);
//		dto.setCode(code);
//		dto.setCname(check[1]);
//		int cnt = Integer.parseInt(check[2]);
//		dto.setCnt(cnt);
//		int price = Integer.parseInt(check[3]);
//		dto.setPrice(price);
		boolean result = false;
		c.orclelode();
		try {
			String sql = "insert into goods values(?,?,?,?)";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, dto.getCode());
			psmt.setString(2, dto.getCname());
			psmt.setInt(3, dto.getCnt());
			psmt.setInt(4, dto.getPrice());
			int r = psmt.executeUpdate();

			if (r > 0) {
				result = true;
			}
			psmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public boolean Update(GoodsDTO dto) {
//		GoodsDTO dto = new GoodsDTO();
//		String[] check = (String[]) obj;
//		int code = Integer.parseInt(check[0]);
//		dto.setCode(code);
//		dto.setCname(check[1]);
//		int cnt = Integer.parseInt(check[2]);
//		dto.setCnt(cnt);
//		int price = Integer.parseInt(check[3]);
//		dto.setPrice(price);
		boolean result = false;
		c.orclelode();
		try {
			String sql = "update goods set code=?,cname=?,cnt=?,price=? where code=?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, dto.getCode());
			psmt.setString(2, dto.getCname());
			psmt.setInt(3, dto.getCnt());
			psmt.setInt(4, dto.getPrice());

			int r = psmt.executeUpdate();
			System.out.println(dto.getCode() + "," + dto.getCname() + "," + dto.getCnt() + "," + dto.getPrice());
			if (r > 0) {
				result = true;
			}
			psmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public ArrayList<String[]> getList() {
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			String sql = "SELECT * FROM goods";
			c.orclelode();
			stmt = conn.createStatement();
			if (stmt != null) {
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					GoodsDTO dto = new GoodsDTO();
					dto.setCode(rs.getInt("code"));
					dto.setCname(rs.getString("cname"));
					dto.setCnt(rs.getInt("cnt"));
					dto.setPrice(rs.getInt("price"));
					list.add(dto.getArray());
					return list;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.exit(0);
		return list;

	}

	public boolean delinfo(GoodsDTO dto) {
		boolean result = false;
	//	GoodsDTO dto = new GoodsDTO();

	
		c.orclelode();
		try {
			String sql = "delete goods where code=?";
			PreparedStatement psmt = conn.prepareStatement(sql);
			psmt.setInt(1, dto.getCode());
			int r = psmt.executeUpdate();
			System.out.println(dto.getCode() + "," + dto.getCname() + "," + dto.getCnt() + "," + dto.getPrice());
			if (r > 0) {
				result = true;
			}
			psmt.close();

		} catch (SQLException e) {
			System.out.println("삭제실패");
		}
		return result;

	}

}
