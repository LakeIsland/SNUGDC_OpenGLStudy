package engine;

public interface IGameLogic extends DtUpdatable{
	void init();
	void processInput(Window window, float interval);
	void render(Window window);
}
