package engine;

import util.MyTimer;

public class FPSCounter implements DtUpdatable{
	private final MyTimer frameTimer;
	private int frames;
	
	FPSCounter(){
		frameTimer = new MyTimer(1.0f);
		frames = 0;
	}
	
	public void update(float dt){
		frameTimer.update(dt);
		if(frameTimer.isFinished()){
			frameTimer.rewind();
			System.out.println("FPS: " + frames);
			frames = 0;
		}
		frames++;
	}
	
}
