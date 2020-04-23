package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberDAO {

	private static Connect c = Connect.getInstance();
	private Connection conn = c.connect();
	private Statement stmt;
	private ResultSet rs;
	private MemberDTO member = null;
	private static MemberDAO DAOobj;
	
	public MemberDAO() {
		
	}



	public static MemberDAO getInstance() {
		if (DAOobj == null) {
			DAOobj = new MemberDAO();
		}
		return DAOobj;
	}




	public int idchk(String id) {
		c.orclelode();
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * from member where id='" + id + "'";
			rs = stmt.executeQuery(sql);

			if (rs.next()) {
				return 1;
			} else {
				return 5;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 55;
	}


	
	
	  public MemberDTO loginchk(String id) {
		  c.orclelode();	   
	         try {
	            stmt = conn.createStatement();
	            String sql = "SELECT * from member where id='" + id + "'";
	            rs = stmt.executeQuery(sql);
	         //  System.out.println(2);
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
	            }catch (SQLException e) {
	            e.printStackTrace();
	        
	       
	         }
	      return member;

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

//		public boolean InsertMember(MemberDTO dto) {
//			boolean result = false;
//	
//			c.orclelode();
//			try {
//				String sql = "insert into member values(?,?,?,?,?,1)";
//				PreparedStatement psmt = conn.prepareStatement(sql);
//				psmt.setString(1, member.getId());
//				psmt.setString(2, member.getName());
//				psmt.setString(3, member.getPwd());
//				psmt.setString(4, member.getAdr());
//				psmt.setString(5, member.getCell());
//				int r = psmt.executeUpdate();
//
//					if (r > 0) {
//						result = true;
//					}
//					psmt.close();
//
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		
//
//			return result;
//		}

	  public boolean InsertMember(MemberDTO m) {
			boolean result = false;
			c.orclelode();
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
