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

		ArrayList<Socket> cList = new ArrayList<>(); // Ȯ�ο�
		// ServerCenter sc = new ServerCenter();
		while (true) {
			System.out.println("���������");
			withClient = serverS.accept();
			cList.add(withClient); // client���� ����Ʈ�� �� ���Գ� Ȯ��
			System.out.println(cList); // Ȯ�ο� ����Ʈ
			System.out.println(withClient.getInetAddress() + "Ŭ���̾�Ʈ ������");
		//	ServerCenter sc = new ServerCenter(withClient);
		//	sc.start();
		}

	}
}