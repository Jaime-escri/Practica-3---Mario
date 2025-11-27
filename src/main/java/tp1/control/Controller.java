package tp1.control;

import tp1.view.GameView;
import tp1.exceptions.CommandException;
import tp1.logic.GameModel;

/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private GameModel model;
	private GameView view;
	

	public Controller(GameModel model, GameView view) {
		this.model = model;
		this.view = view;
	}

	public void run() {
		view.showWelcome();
		view.showGame();

		
		while(!model.isFinished()){
			String[] words = view.getPrompt();
			try {	
				Command command = CommandGenerator.parse(words);
				command.execute(model, view);
				
			} catch (CommandException e) {
				view.showError(e.getMessage());
				Throwable cause = e.getCause();
				String last = e.getMessage();
				while (cause != null) {
					if (cause.getMessage() != null && !cause.getMessage().equals(last)) {
						view.showError(cause.getMessage());
						last = cause.getMessage();
					}
					cause = cause.getCause();
				}
			}
			
		}

		view.showEndMessage();
	
	}
}

