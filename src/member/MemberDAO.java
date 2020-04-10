package member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDAO {

	private Connection conn;
	private Statement stmt;
	private ResultSet rs;
	private MemberDTO d = null;
	private static MemberDAO DAOobj;
	
	public MemberDAO() {
		
	}

	
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("불러오기 성공");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("불러오기 실패");
		}
	}

	public static MemberDAO getInstance() {
		if (DAOobj == null) {
			DAOobj = new MemberDAO();
		}
		return DAOobj;
	}

	private boolean connect() {
		boolean result = false;
		try {
			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "system", "11111111");
			result = true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("???? ????:" + e.getMessage());
		}
		return result;
	}


	public Boolean idcheck(MemberDTO member) throws Exception {
		boolean result = false;
		if (connect()) {
			try {
				String sql = "SELECT * FROM member where id=?";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, member.getId());
				int r = psmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				psmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("DB연결 실패");
			System.exit(0);
		}
		return result;
	}

	
	
	   public MemberDTO loginchk(String id) {
		      if (connect()) {
		         try {
		            stmt = conn.createStatement();
		            String sql = "SELECT * from member where id='" + id + "'";
		            rs = stmt.executeQuery(sql);
		            System.out.println(2);
		            if (rs.next()) {
		               MemberDTO member = new MemberDTO();
		               member.setId(rs.getString("id"));
		               member.setName(rs.getString("name"));
		               member.setPwd(rs.getString("pwd"));
		               member.setAdr(rs.getString("adr"));
		               member.setCell(rs.getString("cell"));
		               member.setLv(rs.getInt("lv"));
		               return member;
		            }
		         } catch (SQLException e) {
		            e.printStackTrace();
		         }

		      } else {
		         System.out.println("DB연결 실패");
		         System.exit(0);
		      }
		      return d;

		   }
	
	

	
//		public Boolean Loginchk(MemberDTO member) throws Exception {
//			boolean result = false;
//			if (connect()) {
//				try {
//					String sql = "SELECT * from member where id=? and pwd=?";
//					PreparedStatement psmt = conn.prepareStatement(sql);
//					psmt.setString(1, member.getId());
//					psmt.setString(2, member.getPwd());
//					int r = psmt.executeUpdate();
//					
//
//					if (r > 0) {
//						result = true;
//					}
//					psmt.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			} else {
//				System.out.println("DB연결 실패");
//				System.exit(0);
//			}
//			return result;
//		}

			

	public boolean InsertMember(MemberDTO m) {
		boolean result = false;
		if (connect()) {
			try {
				String sql = "insert into member values(?,?,?,?,?,1)";
				PreparedStatement psmt = conn.prepareStatement(sql);
				psmt.setString(1, m.getName());
				psmt.setString(2, m.getId());
				psmt.setString(3, m.getPwd());
				psmt.setString(4, m.getAdr());
				psmt.setString(5, m.getCell());
			    int r = psmt.executeUpdate();

				if (r > 0) {
					result = true;
				}
				psmt.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("DB연결 실패");
			System.exit(0);
		}

		return result;
	}

	public int save(MemberDTO member) {
		// TODO Auto-generated method stub
		return 1;
	}

	public Boolean idchk(MemberDTO member) {
		// TODO Auto-generated method stub
		return false;
	}

}
