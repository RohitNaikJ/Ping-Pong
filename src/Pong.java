import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;
import javax.swing.JFrame;

public class Pong implements ActionListener, KeyListener{

	public static Pong pong;
	public int width = 700, height = 700;

	public Renderer renderer;
	public Paddle p1, p2;
	public Ball ball;
	
	public boolean bot = false, w = false, s = false, up = false, down = false;
	
	public Pong(){
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

	private void start() {
		p1 = new Paddle(this, 1);
		p2 = new Paddle(this, 2);
		ball = new Ball(this);
	}

	public void update(){
		if(w)
			p1.move(true);
		else if(s)
			p1.move(false);
		
		if(up)
			p2.move(true);
		else if(down)
			p2.move(false);
		ball.update(p1, p2, pong);
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
		update();
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
	
	public static void main(String args[]){
		pong = new Pong();
	}
}
