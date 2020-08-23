package ru.nsu.ccfit.khudyakov.labs.lab2.commands;

import ru.nsu.ccfit.khudyakov.labs.lab2.source.Context;
import ru.nsu.ccfit.khudyakov.labs.lab2.source.Injection;

import java.util.Map;
import java.util.Stack;


public class PUSH implements Command{
    @Injection(arg = Context.STACK)
    private Stack<Double> stack;

    @Injection(arg = Context.VARIABLES)
    private Map<String,Double> map;

    @Injection(arg = Context.ARGUMENTS)
    private String[] arguments;

    @Override
    public void execute() {
        try{
            if (arguments.length != 2){
                System.out.println("Not valid command");
                return;
            }
            stack.push(Double.parseDouble(arguments[1]));
        }
        catch (NumberFormatException ex) {
            if (map.containsKey(arguments[1]))
                stack.push(map.get(arguments[1]));
            else System.out.println("Invalid value for this operation");
        }
    }
}
