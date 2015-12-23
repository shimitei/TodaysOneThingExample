package com.hinohunomi.todaysonething;

import java.util.Random;

public class RandomMessage implements ITakeMessage {
    private Random random = new Random();

    public String GetMessage() {
        int index = random.nextInt(messages.length);
        return messages[index];
    }

    private String messages[] = {
            "To be, or not to be ： that is the question.",
            "May the Force be with you.",
            "If you build it, he will come.",
            "Life was like a box of chocolates. You never know what you’re gonna get.",
            "With great power comes great responsibility.",
            "Records are made to be broken!",
            "Tomorrow is another day.",
    };
}
