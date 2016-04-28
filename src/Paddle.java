import java.awt.Color;
import java.awt.Graphics;

public class Paddle {
	public int paddleNumber;
	public int x,y, width=50, height=300;
	public static int speed = 5;
	
	public Paddle(Pong pong, int paddleNumber){
		this.paddleNumber = paddleNumber;
		
		if(paddleNumber == 1){
			this.x = 0;
		}
		else if(paddleNumber == 2){
			this.x = pong.width-this.width;
		}
		this.y = pong.height/2 - height/2;
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(x, y, width, height);
	}

	public void move(boolean up) {
		if(up){
			y -= speed;
			if(y<0)
				y = 0;
		}else{
			y += speed;
			if(y+height > Pong.pong.height)
				y = Pong.pong.height - height;
		}
	}
}
