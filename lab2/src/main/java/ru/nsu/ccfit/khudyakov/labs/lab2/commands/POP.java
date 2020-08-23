package ru.nsu.ccfit.khudyakov.labs.lab2.commands;

import ru.nsu.ccfit.khudyakov.labs.lab2.source.Context;
import ru.nsu.ccfit.khudyakov.labs.lab2.source.Injection;

import java.util.Stack;

public class POP implements Command{
    @Injection(arg = Context.STACK)
    Stack<Double> stack;

    @Injection(arg = Context.ARGUMENTS)
    private String[] arguments;

    @Override
    public void execute() {
        if (arguments.length != 1){
            System.out.println("Not valid command");
        } else if (stack.size() > 0)
            stack.pop();
        else {
            System.out.println("Stack is empty");
        }
    }
}
