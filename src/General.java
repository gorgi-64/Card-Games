import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.in;

public interface General {
    static void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.err.println("Emi...");
        }
    }
    static void process(ArrayList<Card> deck, int game){
        Random newrand = new Random(System.nanoTime());
        int[] array = new int[52];
        for(int i = 0; i < 52; i++) {
            array[i] = i;
        }
        int[] arraycopy = new int[52];
        for(int i = 0; i < 52; i++) {
            int newrandom;
            do {
                newrandom = newrand.nextInt(52);
            } while(arraycopy[newrandom] != 0);
            arraycopy[newrandom] = array[i];
        }
        if(deck == null) deck = new ArrayList<>();
        for(int i = 0; i  < 52; i++) {
            Card temp = new Card(arraycopy[i], game);
            deck.add(temp);
        }
    }
    static void pause() {
        Scanner input = new Scanner(in);
        input.nextLine();
    }
}
