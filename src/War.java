import java.util.ArrayList;
import java.util.Random;

class PlayerW extends Player{
    private boolean finalBattle;
    public PlayerW(ArrayList<Card> cards){
        player = cards;
        you = false;
    }
    @Override
    public void printCards(){
        System.out.println("Ne moje v tazi igra :3");
    }
    public void removeCard(){
        player.remove(0);
    }
}
public class War {
    public static ArrayList<Card> deck = new ArrayList<>();
    public static void process(){
        //ArrayList<CardBJ> deck) {
        Random newrand = new Random(System.nanoTime());
        int[] array = new int[52];
        for(int i = 0; i < 52; i++) {
            array[i] = (i % 13) + 2;
        }
        int[] arraycopy = new int[52];
        for(int i = 0; i < 52; i++) {
            int newrandom;
            do {
                newrandom = newrand.nextInt(52);
            } while(arraycopy[newrandom] != 0);
            arraycopy[newrandom] = array[i];
        }
        for(int i = 0; i  < 52; i++) {
            CardBJ temp = new CardBJ(arraycopy[i]);
            deck.add(temp);
        }
    }
    public static ArrayList<Card> subArrayList(ArrayList<Card> one, int firstIndex, int secondIndex){
        ArrayList<Card> nov = new ArrayList<>();
        for(int i = firstIndex; i < secondIndex; i++){
            nov.add(one.get(i));
        }
        return nov;
    }

    public static int pushSize(int one, int two){
        return Math.max(Math.min(Math.min(one, two)- 1, 3), 1);
    }
    public static void war(PlayerW one, PlayerW two, ArrayList<Card> pot){
        pot.addAll(subArrayList(one.copy(), 0, pushSize(one.size(), two.size())));
        pot.addAll(subArrayList(two.copy(), 0, pushSize(one.size(), two.size())));

        int onefinal = one.getCard(pushSize(one.size(), two.size())).getScore();
        int twofinal = two.getCard(pushSize(one.size(), two.size())).getScore();

        one.removeCard(0, pushSize(one.size(), two.size()));
        two.removeCard(0, pushSize(one.size(), two.size()));
        if(Math.min(one.size(), two.size()) == 0){
            System.out.println("Player" +
                    (one.size() < two.size() ? "one" : "two")
                    + "has no cards and wins the war!");
            //house rules :3
            if(one.size() < two.size()){
                one.addCards(pot);
                return;
            }
            two.addCards(pot);
        }
        if(onefinal > twofinal){
            System.out.println("Player one wins the war!");
            one.addCards(pot);
        }
        if(onefinal < twofinal){
            System.out.println("Player two wins the war!");
            two.addCards(pot);
            return;
        }
        else war(one, two, pot);

    }
    public static void main(String[] args) {
        process();

        PlayerW one = new PlayerW(subArrayList(deck, 0, 25));
        PlayerW two = new PlayerW(subArrayList(deck, 26, 51));

        while(one.size() != 0 && two.size() != 0){
            Card p1 = one.getCard(0);
            Card p2 = two.getCard(0);
            one.removeCard(0, 0);
            two.removeCard(0, 0);
            System.out.println("Player one gives: " + p1.getSymbol());
            System.out.println("Player two gives: " + p2.getSymbol());
            if(p1.getScore() > p2.getScore()){
                System.out.println("Player one won the battle!");
                one.addCard(p1);
                one.addCard(p2);

            }
            else if(one.getCard(0).getScore() < two.getCard(0).getScore()){
                System.out.println("Player two won the battle!");
                two.addCard(p1);
                two.addCard(p2);
            }
            else{
                ArrayList<Card> pot = new ArrayList<>();
                pot.add(p1);
                pot.add(p2);
                System.out.println("War!");
                war(one, two, pot);
            }
            Blackjack.wait(250);
        }
        if(one.size() == 0){
            System.out.println("Player one won the game!");
        }
        else{
            System.out.println("Player two won the game!");
        }

    }
}
