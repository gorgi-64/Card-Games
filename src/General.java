import java.util.ArrayList;
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
        //Random newRand = new Random(System.nanoTime());
        int[] array = new int[52];
        for (int i = 0; i < 52; i++) {
            array[i] = i;
        }
        for (int i = 51; i > 0; i--) {
            int j = rng(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        if(deck == null) deck = new ArrayList<>();
        for(int i = 0; i  < 52; i++) {
            Card temp = new Card(array[i], game);
            deck.add(temp);
        }

    }
    static void pause() {
        Scanner input = new Scanner(in);
        input.nextLine();
    }
    static <T> ArrayList<T> subArrayList(ArrayList<T> one, int firstIndex, int secondIndex){
        ArrayList<T> nov = new ArrayList<>();
        for(int i = firstIndex; i < secondIndex; i++){
            nov.add(one.get(i));
        }
        return nov;
    }
    static int rng(int limit){
        return (int)(Math.random() * limit);
    }
}
