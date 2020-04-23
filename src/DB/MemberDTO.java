package DB;

public class MemberDTO {
	private String id;
	private String name;
	private String pwd;
	private String adr;
	private String cell;
	private int lv; 
	public int getLv() {
		return lv;
	}
	public void setLv(int lv) {
		this.lv = lv;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getAdr() {
		return adr;
	}
	public void setAdr(String adr) {
		this.adr = adr;
	}
	public String getCell() {
		return cell;
	}
	public void setCell(String cell) {
		this.cell = cell;
	}
	public String[] getArray() {
		String[] returnData = new String[5];
		returnData[0]=this.id;
		returnData[1]=this.name;
		returnData[2]=this.pwd;
		returnData[3]=this.adr;
		returnData[4]=this.cell;
		
		return returnData;
	}
}
