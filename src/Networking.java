/**
 * 
 */

/**
 //* @author Hrushi pc
 *
 */
import java.io.*;
import java.net.*;
import java.util.*;


public class Networking {


	/**
	 * @param args
	 */


	private static DatagramSocket socket;
	private static int portNum;
	private static ArrayList<String> IP_list = new ArrayList<String>();
	private static String Host;

	public Networking(String host){
		Host = host; 
	}

	public static void main(String[] args) throws Exception {
		final BufferedReader Input = new BufferedReader(new InputStreamReader(System.in));
		socket = new DatagramSocket(6543);
		run();
	}
	private static void run(){
		Runnable repeat = new Runnable(){
			@Override
			public void run(){
				byte[] SendData;
				byte[] ReceiveData;
				while(true){
					try{
						SendData = new byte[1024];
						ReceiveData = new byte[1024];
						DatagramPacket messagePacket = new DatagramPacket(ReceiveData,ReceiveData.length);

						socket.receive(messagePacket);
						System.out.println("message received");
						String message = new String(messagePacket.getData());
						System.out.println(message);
						if(message.toString().equals("addme")){
							System.out.println("here done");
							addplayer(messagePacket.getAddress().getHostAddress());
							System.out.println("here also");
						}else if(message.toString().equalsIgnoreCase("disconneted")){
							removeplayer(messagePacket.getAddress().getHostAddress());
						}

					}catch (UnknownHostException e){
						System.out.println("check here ");
					}
					catch(IOException e){
						System.out.println(e);
					}

				}
			}
		};
		Thread t = new Thread(repeat);
		t.start();
	}
	private static void addplayer(String IP){
		IP_list.add(IP);
		send("player added",IP);
	}
	private static void removeplayer(String IP){
		IP_list.remove(IP);
		send("removed",IP);
	}
	private static void send(final String message,final String Ip){
		Runnable send = new Runnable(){
			@Override
			public void run(){
				try {
					System.out.println("here now");
					byte[] SendData = new byte[1024];
					SendData = message.getBytes();
					InetAddress address = InetAddress.getByName(Ip);
					DatagramPacket sendPacket = new DatagramPacket(SendData,SendData.length,address,6533);
					socket.send(sendPacket);
					System.out.println("sent");
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (IOException e){
					System.out.println(e);
				}

			}
		};
		Thread t = new Thread(send);
		t.start();
		
	}
	private static void sendall(String message){
		for(final String IP: IP_list){
			send(message,IP);
		}
	}
}
