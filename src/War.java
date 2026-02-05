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
    public static ArrayList<Card> subArrayList(ArrayList<Card> one, int firstIndex, int secondIndex){
        ArrayList<Card> nov = new ArrayList<>();
        for(int i = firstIndex; i < secondIndex; i++){
            nov.add(one.get(i));
        }
        return nov;
    }
    public static void main(String[] args) {
        Random newrand = new Random(System.nanoTime());
        int[] initialInt = new int[52];
        for (int i = 0; i < 52; i++) initialInt[i] = i;
        System.out.println(initialInt[1]);
        int[] arraycopy = new int[52];
        for (int i = 0; i < 52; i++) {
            int newrandom = 0;
            do {
                newrandom = newrand.nextInt(52);
            } while (arraycopy[newrandom] != 0);
            arraycopy[newrandom] = initialInt[i];
        }
        ArrayList<Card> deck = new ArrayList<>();
        for(int i = 0; i < 52; i++){
            Card temp = new Card(initialInt[i]);
            deck.add(temp);
        }
        PlayerW one = new PlayerW(subArrayList(deck, 0, 25));
        PlayerW two = new PlayerW(subArrayList(deck, 26, 51));
        while(one.size() != 0 && two.size() != 0){
            System.out.println("Player one gives: " + one.getCard(0));
            System.out.println("Player two gives: " + two.getCard(0));
            if(one.getCard(0).getScore() > two.getCard(0).getScore()){
                System.out.println("Player one won the battle!");
                one.addCard(two.getCard(0));
                two.removeCard();
            }
            else if(one.getCard(0).getScore() < two.getCard(0).getScore()){
                System.out.println("Player two won the battle!");
                two.addCard(two.getCard(0));
                one.removeCard();
            }
            else{

            }
        }

    }
}
