package tp1.control;

import java.util.Arrays;
import java.util.List;

import tp1.exceptions.CommandParseException;
import tp1.view.Messages;



public class CommandGenerator {

    private static final List<Command> AVAILABLE_COMMANDS = Arrays.asList(
        new ActionCommand(),
        new UpdateCommand(),
        new ResetCommand(),
        new HelpCommand(),
        new ExitCommand(),
        new AddObjectCommand()
    );


     public static Command parse(String[] words) throws CommandParseException{
        for(Command a : AVAILABLE_COMMANDS){
            Command parsed = a.parse(words);
            if(parsed != null){
                return parsed;
            }
        }
        throw new CommandParseException(Messages.UNKNOWN_COMMAND.formatted(words[0]));
    }

    public static String commandHelp(){
        StringBuilder sb = new StringBuilder();
        for (Command c : AVAILABLE_COMMANDS) {
            sb.append(c.helpText()).append(System.lineSeparator());
        }
        return sb.toString();
    }


}
