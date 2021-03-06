import java.awt.BasicStroke;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.*;

import javax.swing.Timer;
import javax.swing.JFrame;

public class Pong implements ActionListener, KeyListener{

	public static Pong pong;
	public int width = 700, height = 700;

	public Renderer renderer;
	public Paddle p1, p2;
	public Ball ball;
	public int difficulty = 1;
	public int GroupPort = 9875;
	public DatagramSocket socket;
	public ArrayList<String> IP_list;
	public String firsthost = "10.208.23.243";
	public boolean bot = true, w = false, s = false, up = false, down = false,play=false;
	private static Random random;
	
	public Pong() throws IOException{
		Timer timer = new Timer(20, this);
		JFrame jFrame = new JFrame("Pong");
		renderer = new Renderer();
		
		jFrame.setSize(width+15, height + 45);
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.add(renderer);
		jFrame.addKeyListener(this);
		
		start();
		
		timer.start(); 
	}

	private void start() throws IOException {
		p1 = new Paddle(this, 1);
		p2 = new Paddle(this, 2);
		ball = new Ball(this,this.width/2 - (25/2),this.height/2 - (25/2));
		random = new Random();
		int rand = random.nextInt(2);
		if(rand == 1){
			ball.motionX = 5 + random.nextInt(4);
		}else{
			ball.motionX = -(5 + random.nextInt(4));
		}

		
		int randy = random.nextInt(2);
 		if(randy == 1){
		ball.motionY = 5 + random.nextInt(3);
 		}else{
 			ball.motionY = -(5 + random.nextInt(3));
 	 	}
		while(ball.motionX == ball.motionY || ball.motionX == -ball.motionY){
			ball.motionY = 5 + random.nextInt(3);
		}
		IP_list = new ArrayList<String>();
		socket = new DatagramSocket(GroupPort);
		System.out.println("socket started");
		Send("addme",firsthost);
		
		StartNetwork();
		
		
	}

	private void Send(String string, String firsthost2) throws IOException {
		byte[] SendData = new byte[1024];
		SendData = string.getBytes();
		InetAddress InetAddr;
			InetAddr = InetAddress.getByName(firsthost2);
		
		DatagramPacket SendPacket = new DatagramPacket(SendData,SendData.length,InetAddr,GroupPort);
		socket.send(SendPacket);
		System.out.println("sent:"+string);
		// TODO Auto-generated method stub
		
	}

	private void StartNetwork() throws SocketException {
		// TODO Auto-generated method stub
		Runnable networkThread = new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
			while(true){
				try {
					byte[] ReceiveData = new byte[1024];
					DatagramPacket ReceivePacket = new DatagramPacket(ReceiveData,ReceiveData.length);
					socket.receive(ReceivePacket);
					System.out.println("received:" + (new String(ReceivePacket.getData())).trim());
					
					if(IP_list.isEmpty()){
						String hostAddress = ReceivePacket.getAddress().getHostAddress();
						IP_list.add(hostAddress);
						bot = false;
						play = true;
						//connection();
					}
					String received = (new String(ReceivePacket.getData())).trim();
					if(received.equals("addme")){
						Send("ball "+ball.x+" "+ ball.y+" "+ball.motionX+" "+ball.motionY+" "+p1.y+" "+p2.y);
					//	try {
							//Thread.sleep(8);
						//} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							//e.printStackTrace();
						//}
						play = true;
					}
					if(received.equals("true")){
						p1.move(true);
					}
					if(received.equals("false")){
						p1.move(false);
					}
					if(received.startsWith("ball")){
						String args[] = received.split(" ");
						int x =pong.width - Integer.parseInt(args[1]);
						int y = Integer.parseInt(args[2]);
						ball = new Ball(pong,x,y);
						int mX = -Integer.parseInt(args[3]);
						int mY = Integer.parseInt(args[4]);
						ball.motionX=mX;
						ball.motionY=mY;
						int pad1Y=Integer.parseInt(args[5]);
						int pad2Y=Integer.parseInt(args[6]);
						p2.y=pad1Y;
						p1.y=pad2Y;
						play = true;
					}
					if(received.startsWith("BIall")){
						String args[] = received.split(" ");
						int x =pong.width - Integer.parseInt(args[1]);
						int y = Integer.parseInt(args[2]);
						ball = new Ball(pong,x,y);
						int mX = -Integer.parseInt(args[3]);
						int mY = Integer.parseInt(args[4]);
						ball.motionX=mX;
						ball.motionY=mY;
						}
					if(received.startsWith("paddle")){
						String args[] = received.split(" ");
						p1.y = Integer.parseInt(args[3]);
						p2.y = Integer.parseInt(args[2]);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
			}

		};
		Thread t = new Thread(networkThread);
		t.start();
		
	}
	private void connection() {
		// TODO Auto-generated method stub
		Runnable continuous = new Runnable(){

			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while(true){
				// TODO Auto-generated method stub
					Send("ball "+ball.x+" "+ ball.y+" "+ball.motionX+" "+ball.motionY/*+" "+p1.y+" "+p2.y*/);
					try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				}
			
		};
		Thread t = new Thread(continuous);
		t.start();
	}

	public void update() throws IOException{
		///if(play) ball.update(p1, p2, pong);
		if(bot) {
			p1.update(pong, difficulty, ball);
			if(up) p2.move(true);
			if(down) p2.move(false);
		}else{
			if(up){
				if(!IP_list.isEmpty()){
					Send("true");
					p2.move(true);
				}
			}else if(down){
				if(!IP_list.isEmpty()){
					Send("false");
					p2.move(false);
				}
			}
		}
		/*if(w)
			p1.move(true);
		else if(s)
			p1.move(false);
		*/
	}
	
	private void Send(String string) {
		// TODO Auto-generated method stub
		final String data = string;
		Runnable sendthread = new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(final String Addr : IP_list){
					try {
						
						byte[] SendData = new byte[1024];
						SendData = data.getBytes();
						InetAddress InetAddr;
							InetAddr = InetAddress.getByName(Addr);
						
						DatagramPacket SendPacket = new DatagramPacket(SendData,SendData.length,InetAddr,GroupPort);
						socket.send(SendPacket);
						System.out.println("Sent:"+ new String(SendData).trim());
						
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}catch(IOException e){
						e.printStackTrace();
					}
				
				}
			}
			
		};
		Thread t = new Thread(sendthread);
		t.start();
	}

	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);

		g.setColor(Color.WHITE);g.setStroke(new BasicStroke(4));
		g.drawLine(width/2, 0, width/2, height);
		g.drawOval(width/2 - 75, height/2 - 75, 150, 150);
		
		p1.render(g);
		p2.render(g);
		ball.render(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			update();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		renderer.repaint(); 
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int id = e.getKeyCode();
		
		switch(id){
			case KeyEvent.VK_W:
				w = true;
				break;
				
			case KeyEvent.VK_S:
				s = true;
				break;
				
			case KeyEvent.VK_UP:
				up = true;
				break;
				
			case KeyEvent.VK_DOWN:
				down = true;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int id = e.getKeyCode();
		
		switch(id){
			case KeyEvent.VK_W:
				w = false;
				break;
				
			case KeyEvent.VK_S:
				s = false;
				break;
				
			case KeyEvent.VK_UP:
				up = false;
				break;
				
			case KeyEvent.VK_DOWN:
				down = false;
				break;
		}
	}
	
	public static void main(String args[]) throws IOException{
		pong = new Pong();
	}
}
