package engine;

public interface IGameLogic extends DtUpdatable{
	void init();
	void processInput(Window window);
	void render(Window window);
}
