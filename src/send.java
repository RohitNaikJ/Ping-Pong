import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
public class send {

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		DatagramSocket Socket = new DatagramSocket(6533);
		byte[] SendData = new byte[1024];
		
		while(true){
			System.out.println("message to be sent");
			final BufferedReader Input = new BufferedReader(new InputStreamReader(System.in));
			SendData = Input.readLine().getBytes();
			System.out.println("enter host num");
			InetAddress Host = InetAddress.getByName(Input.readLine());
			DatagramPacket Packet = new DatagramPacket(SendData,SendData.length,Host,6543);
			System.out.println("Packet ready");
			Socket.send(Packet);
			System.out.println("packet sent");
			byte[] ReceiveData = new byte[1024];
			DatagramPacket Packet1 = new DatagramPacket(ReceiveData,ReceiveData.length);
			System.out.println("waiting");
			Socket.receive(Packet1);
			System.out.println("received");
			System.out.println(new String(Packet1.getData()));
		
		}
	}
}
