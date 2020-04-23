package Server;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {


	public static void main(String[] args) throws Exception {
		ServerSocket serverS = null;
		Socket withClient = null;
		serverS = new ServerSocket();
		serverS.bind(new InetSocketAddress("10.0.0.96", 9999));

		ArrayList<Socket> cList = new ArrayList<>(); // 확인용
		// ServerCenter sc = new ServerCenter();
		while (true) {
			System.out.println("서버대기중");
			withClient = serverS.accept();
			cList.add(withClient); // client들이 리스트에 잘 들어왔나 확인
			System.out.println(cList); // 확인용 리스트
			System.out.println(withClient.getInetAddress() + "클라이언트 접속함");
		//	ServerCenter sc = new ServerCenter(withClient);
		//	sc.start();
		}

	}
}