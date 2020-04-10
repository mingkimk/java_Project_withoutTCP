package manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;



public class GoodsDAO {
	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	
	public static GoodsDAO DAOobj;
	
	public GoodsDAO() {
		
	}
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("클래스 로드 성공");
		} catch (ClassNotFoundException e) {
		System.out.println("클래스 로드 실패");
		
		}
	}
	public static GoodsDAO getInstance() {
		if(DAOobj== null) {
			DAOobj=new GoodsDAO();
			
		}
		return DAOobj;			
			
	}
	private boolean connect() {
		boolean result=false;
		try {
			conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			result=true;
		} catch (Exception e) {
			// TODO: handle exception
		System.out.println("연결 실패");
		}
		return result;
		
		}
	
	
	


public ArrayList<String[]> getList(){
	ArrayList<String[]> list= new ArrayList();
	String sql="SELECT * FROM Product";
	if(connect()) {
		try {
			stmt=conn.createStatement();
			if(stmt != null) {
				rs = stmt.executeQuery(sql);  // rs는 sql을 실행한 튜플 단위로 가져온다.
				while(rs.next()) {
					GoodsDTO Goods = new GoodsDTO();
					
					Goods.setCode(rs.getString("code"));
					Goods.setCname(rs.getString("cname"));
					Goods.setCnt(rs.getString("cnt"));
					Goods.setPrice(rs.getString("price"));
					
					list.add(Goods.getArray());
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}else {
		System.out.println("DB연결 실패");
		System.exit(0);
	}
	return list;
}
public void InsertGoods(GoodsDTO newDTO) {
	// TODO Auto-generated method stub
	
}



}


