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
    static Integer[] box(int[] arr){
        Integer[] nov = new Integer[arr.length];
        for(int i = 0; i < nov.length; i++){
            nov[i] = arr[i];
        }
        return nov;

    }
    static int[] unbox(Integer[] arr){
        int[] nov = new int[arr.length];
        for(int i = 0; i < nov.length; i++){
            nov[i] = arr[i];
        }
        return nov;
    }
    static void process(ArrayList<Card> deck, int game){
        //Random newRand = new Random(System.nanoTime());
        int[] array = new int[52];
        for (int i = 0; i < 52; i++) {
            array[i] = i;
        }
        array = unbox(shuffle(box(array)));
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
    static <T> T[] shuffle(T[] array){
        for (int i = array.length - 1; i > 0; i--) {
            int j = rng(i + 1);
            T temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
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
