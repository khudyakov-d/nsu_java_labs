package ru.nsu.ccfit.khudyakov.labs.lab2.commands;

import ru.nsu.ccfit.khudyakov.labs.lab2.source.Context;
import ru.nsu.ccfit.khudyakov.labs.lab2.source.Injection;

import java.util.Stack;

public class SQRT implements Command {
    @Injection(arg = Context.STACK)
    private Stack<Double> stack;

    @Injection(arg = Context.ARGUMENTS)
    private String[] arguments;

    @Override
    public void execute() {
        double value;

        if (arguments.length != 1) {
            System.out.println("Not valid command");
            return;
        }
        value = stack.pop();
        if (value >= 0) {
            stack.push(Math.sqrt(value));
        } else {
            System.out.println("Сan not take the root of a negative number");
        }

    }
}
