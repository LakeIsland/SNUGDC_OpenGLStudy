package util;

public class MyTimer {
	private float timer;
	private float expireTime;
	
	public MyTimer(float expireTime){
		timer = 0;
		this.expireTime = expireTime;
	}
	
	public void update(float dt){
		timer += dt;
	}
	
	public boolean isFinished(){
		return (this.timer >= this.expireTime);
	}
	
	public void rewind(){
		this.timer -= this.expireTime;
	}
}
