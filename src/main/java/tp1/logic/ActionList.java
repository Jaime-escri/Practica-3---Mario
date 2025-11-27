package tp1.logic;


import java.util.ArrayList;
import java.util.List;

import tp1.exceptions.ActionParseException;
import tp1.view.Messages;

public class ActionList {
    private List<Action> actions = new ArrayList<>();

    public  void addStringAction(String[] command) throws ActionParseException{
        int num = command.length;
        if(num > 5){
            num = 5;
        }

        for(int i = 1; i < num; i++){
            Action act = Action.stringToAction(command[i]);
            if(act == null)throw new ActionParseException(Messages.UNKNOWN_ACTION.formatted(command[i]));
            actions.add(act);
        }
    }

    public void add(Action act){
        if(act != null){
            actions.add(act);
        }
        
    }


    public List<Action> getActions(){
        return actions;
    }

    public void clear(){
        actions.clear();
    }
}
