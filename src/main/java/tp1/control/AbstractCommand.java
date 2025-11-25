package tp1.control;

import tp1.view.GameView;

public abstract class AbstractCommand implements Command {
    
    private final String name;
    private final String shortcut;
    private final String details;
    private final String help;

    protected AbstractCommand(String name, String shortcut, String details, String help) {
        this.name = name;
        this.shortcut = shortcut;
        this.details = details;
        this.help = help;
    }

    protected boolean matchCommand(String word) {
        return word != null && (word.toLowerCase().equalsIgnoreCase(name) || word.toLowerCase().equalsIgnoreCase(shortcut));
    }

    public String getDetails() { return details; }

    @Override
    public String helpText() {
        return details + " : " + help;
    }

}

    
