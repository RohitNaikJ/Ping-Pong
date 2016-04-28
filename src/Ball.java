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
			System.out.println("this_______________________************************************************************************");
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
		checkCollision(paddle1);
		checkCollision(paddle2); 
		this.x += motionX;
		this.y += motionY;
	}
	public void checkCollision(Paddle paddle){
			if(paddle.paddleNumber == 1){
				if((x + motionX) < paddle.width){
					if(x > paddle.width){
							
					}else{
					}
						
				}
			}else
				if(paddle.paddleNumber == 2){
				
			}else System.out.println("paddle num wrong in Ball.checkCollision");
		/*	if(((paddle.paddleNumber == 1) && ((x + motionX)< (paddle.x+paddle.width))) ||((paddle.paddleNumber == 2) &&(x+motionX + width) > (paddle.x))){
				if((((y +height) > (paddle.y))&& ((y ) < (paddle.y+paddle.height)))&&
						(((paddle.paddleNumber == 1) && ( x > (paddle.x+paddle.width))) ||((paddle.paddleNumber == 2) &&(x + width) < (paddle.x)))){
					motionX = -motionX;
				}else{
					if((((y +height + motionY ) > paddle.y) && (y < paddle.y))|| (((y + motionY) < (paddle.y+paddle.height))&& (y>(paddle.y + paddle.height)))){
						System.out.println((((y +height + motionY) > paddle.y) && (y < paddle.y)) + "  from top");
						System.out.println((((y + motionY) < (paddle.y+paddle.height))&& (y>(paddle.y + paddle.height))) + " from bottom");
						System.out.println(motionY);
						motionY = -motionY;
						System.out.println(motionY);
					}
				}
			}*/
	
	}
	
	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval(x, y, width, height);
	}
	
}
