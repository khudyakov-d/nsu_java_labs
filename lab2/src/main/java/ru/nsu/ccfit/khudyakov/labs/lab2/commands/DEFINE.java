package ru.nsu.ccfit.khudyakov.labs.lab2.commands;

import ru.nsu.ccfit.khudyakov.labs.lab2.source.Context;
import ru.nsu.ccfit.khudyakov.labs.lab2.source.Injection;

import java.util.Map;
import java.util.Stack;

public class DEFINE implements Command {
    @Injection(arg = Context.STACK)
    private Stack<Double> stack;

    @Injection(arg = Context.VARIABLES)
    private Map<String,Double> map;

    @Injection(arg = Context.ARGUMENTS)
    private String[] arguments;


    @Override
    public void execute() {
        try{
            if (arguments.length != 3){
                System.out.println("Not valid command");
                return;
            }
            map.put(arguments[1],Double.parseDouble(arguments[2]));
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid value for this operation");
        }
    }
}
