import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Ball {
	public static int x,y,motionX,motionY,width = 25,height = 25;
	public static Random random;
	public Ball(Pong pong){
		random =  new Random();
		this.x = pong.width/2 - width/2;
		this.y = pong.height/2 - height/2;
		
		
		int rand = random.nextInt(2);
		if(rand == 1){
			this.motionX = 5 + random.nextInt(4);
		}else{
			this.motionX = -(5 + random.nextInt(4));
		}

		
		int randy = random.nextInt(2);
 		if(randy == 1){
		this.motionY = 5 + random.nextInt(3);
 		}else{
 			this.motionY = -(5 + random.nextInt(3));
 	 	}
		while(motionX == motionY || motionX == -motionY){
			this.motionY = 5 + random.nextInt(3);
		}
		
	}
	public void update(Paddle paddle1,Paddle paddle2,Pong pong){
		checkCollision(paddle1);
		checkCollision(paddle2); 
		if((x + width + motionX > pong.width) ||(x+motionX < 0) ){
			motionX = -motionX;
			/*if(motionY>0){
				this.motionY = random.nextInt(8);
				if(motionY == 0){
					this.motionY = 3;
				}
			}else{
				this.motionY = -8 + random.nextInt(8);
				if(motionY == 0){
					this.motionY = 4;
				}
			}*/
		}
		if((y + height + motionY > pong.height) ||(y+motionY< 0) ){
			motionY = -motionY;
			//System.out.println("this_______________________************************************************************************");
			/*if(motionX>0){
				this.motionX = random.nextInt(8);
				if(motionX == 0){
					this.motionX = 8;
				}
			}else{
				this.motionX = -8 + random.nextInt(8);
				if(motionX == 0){
					this.motionX = 8;
				}
			}*/
		}
		this.x += motionX;
		this.y += motionY;
	}
	public void checkCollision(Paddle paddle){
		if(paddle.paddleNumber == 1){
			if((x+motionX) <= (paddle.x + paddle.width)){
				if(((y+motionY)>=(paddle.y-15))&&((y+motionY)<=(paddle.y+paddle.height+15))){
					motionX = -motionX;
					System.out.println("here in 3___________________");
				}else{
					if((((y+motionY+height) >= (paddle.y-5))&&((y + height)<=(paddle.y)))){
						motionY = -motionY;
						System.out.println("here in 1_____________");
					}
					if(((y+motionY)<=(paddle.y+paddle.height+5))&&((y)>=(paddle.y +paddle.height))){
						motionY=-motionY;
						System.out.println("here in 2_____________");
					}
				}
			}
		}else{if((x+width+motionX) >= (paddle.x )){
			if(((y+motionY)>=paddle.y-15)&&((y+motionY)<=(paddle.y + paddle.height+15))){
				motionX = -motionX;
			}else{
				if((((y+motionY+height) >= (paddle.y-5))&&((y + height)<=(paddle.y)))){
					motionY = -motionY;
					System.out.println("here in 1_____________");
				}
				if(((y+motionY)<=(paddle.y+paddle.height+5))&&((y)>=(paddle.y +paddle.height))){
					motionY=-motionY;
					System.out.println("here in 2_____________");
				}
			}
			}
			
		}
	}
	
	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
	}
	
}
