package manager;

public class GoodsDTO {


		private String code;
		private String cname;
		private String cnt;
		private String price;
	


		public String getCode() {
			return code;
		}



		public void setCode(String code) {
			this.code = code;
		}





		public String getCname() {
			return cname;
		}



		public void setCname(String cname) {
			this.cname = cname;
		}




		public String getCnt() {
			return cnt;
		}



		public void setCnt(String cnt) {
			this.cnt = cnt;
		}



		public String getPrice() {
			return price;
		}



		public void setPrice(String price) {
			this.price = price;
		}



		public String[] getArray() {
			String[] returnData = new String[3];
			returnData[0]=this.code;
			returnData[1]=this.cname;
			returnData[2]=this.cnt;
			returnData[3]=this.price;
			return returnData;
		}
}
		


	