import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

class PlayerW extends Player{
    private boolean finalBattle;
    public PlayerW(ArrayList<Card> cards, boolean you){
        super(you);
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
    public void addCards(ArrayList<Card> array){
        player.addAll(array);
    }
    public void removeCards(int start, int end){
        player.subList(start, end + 1).clear();
    }
    public ArrayList<Card> returnCards(int start, int end){
        return War.subArrayList(player, start, end);
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
    public static int potSz(int sz1, int sz2){
        return Math.min(Math.min(sz1, sz2), 3);

    }
    public static void war(PlayerW one, PlayerW two, ArrayList<Card> pot){
        System.out.println("War!");
        if(Math.min(one.size(), two.size()) == 0){
            if(one.size() == 0){
                one.addCards(pot);
                System.out.print("Player one ");
            }
            else{
                two.addCards(pot);
                System.out.print("Player two ");
            }
            System.out.println("has zero cards and wins the war!");
            //house rules
            return;
        }
        int potsz = potSz(one.size(), two.size()) - 1;
        Card lastOne = one.getCard(potsz);
        Card lastTwo = two.getCard(potsz);
        pot.addAll(one.returnCards(0, potsz));
        pot.addAll(two.returnCards(0, potsz));
        one.removeCards(0, potsz);
        two.removeCards(0, potsz);
        for(int i = 0; i < pot.size(); i++){
            if(i == 0) System.out.print("Player one gives: ");
            System.out.print(pot.get(i).getAll() + " ");
            if(i == (pot.size() - 1) / 2) System.out.print("\nPlayer two gives: ");
        }
        System.out.print("\n");
        if(lastOne.getScore() > lastTwo.getScore()){
            System.out.println("Player one wins the war!");
            one.addCards(pot);
            return;
        }
        if(lastOne.getScore() < lastTwo.getScore()){
            System.out.println("Player two wins the war!");
            two.addCards(pot);
            return;
        }
        else{
            war(one, two, pot);
        }

    }

    static ArrayList<Card> deck = new ArrayList<>();
    public static void pause() {
        Scanner input = new Scanner(System.in);
        input.nextLine();
    }
    public static void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            System.err.println("Emi...");
        }
    }
    public static void main(String[] args) {
        Card.process(deck, 0);
        PlayerW one = new PlayerW(subArrayList(deck, 0, 25), false);
        PlayerW two = new PlayerW(subArrayList(deck, 26, 51), false);
        while(one.size() != 0 && two.size() != 0){
            System.out.println("Player one gives: " + one.getCard(0).getAll());
            System.out.println("Player two gives: " + two.getCard(0).getAll());
            ArrayList<Card> pot = new ArrayList<>();
            int f = one.getCard(0).getScore();
            int s = two.getCard(0).getScore();
            pot.add(one.getCard(0));
            pot.add(two.getCard(0));
            one.removeCard();
            two.removeCard();
            if(f > s){
                System.out.println("Player one won the battle!");
                one.addCards(pot);
            }
            else if(f < s){
                System.out.println("Player two won the battle!");
                two.addCards(pot);
            }
            else{
                war(one, two, pot);
            }
        }
        one.printCards();
    }
}