package ru.nsu.ccfit.khudyakov.labs.lab2.commands;

import ru.nsu.ccfit.khudyakov.labs.lab2.source.Context;
import ru.nsu.ccfit.khudyakov.labs.lab2.source.Injection;

import java.util.Stack;

public class DIVIDE implements Command{
    @Injection(arg = Context.STACK)
    private Stack<Double> stack;

    @Injection(arg = Context.ARGUMENTS)
    private String[] arguments;

    @Override
    public void execute() {
        try {
            if (arguments.length != 1){
                System.out.println("Not valid command");
            } else if (stack.size() >= 2) {
                stack.push(stack.pop() / stack.pop());
            } else {
                System.out.println("Not enough elements in stack for this operation");
            }
        }
        catch (ArithmeticException ex){
            System.out.println("Error - division by zero\n");
        }
    }
}
