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

	public void update(Pong pong,int difficulty,Ball ball) {
		if(paddleNumber==1){
			if((ball.x < ((int)(0.5*pong.width)))&&(ball.motionX < 0)){
				float floatestimate = ((ball.motionY/ball.motionX)*(x - ball.x)) + (float)ball.y;
				int estimate = (int)floatestimate;
				settarget(estimate);
			}
		}
		if(paddleNumber==2){
			
		}
		
		// TODO Auto-generated method stub
		
	}

	private void settarget(int estimate) {
		
		if(estimate<y+5){
			move(true);
		}else{
			if(estimate>y+height-5){
				move(false);
			}
		}
		// TODO Auto-generated method stub
		
	}
}
