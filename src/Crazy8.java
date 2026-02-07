import java.util.ArrayList;
import java.util.Scanner;


class Card8 extends Card{
    @Override
    public void setScore(int temp){
        if(temp == 8) score = 80;
        if(temp >= 11 && temp <= 13) score = 10;
        if(temp == 14) score = 1;
        score = temp;
    }
    public Card8(int temp){
        super(temp);
        setScore(temp);
    }
}
class PlayerC8 extends Player{
    static Scanner input = new Scanner(System.in);
    protected int personality;
    //0: you, 1: dumb, 2: chaotic, 3: aggressive, 4: cautious, 5: smart
    @Override
    public void printCards(){
        for(int i = 0; i < player.size(); i++){
            System.out.print(i + ". " + player.get(i).getAll() + " ");
        }

    }
    public PlayerC8(boolean you, int personality){
        super(you);
        this.personality = personality;
    }
    public static boolean isPlayable(Card top, Card card){
        return card.getSymbol() == top.getSymbol() || card.getSuit() == top.getSuit() || card.getSymbol() == '8';
    }
    public boolean isPlayable(Card top){
        for(Card card : player){
            if(isPlayable(top, card)) return true;
        }
        return false;
    }
    public static int highestP(ArrayList<Card> cards, Card top){
        int highest = 0;
        for(int i = 0; i < cards.get(0).getScore(); i++){
            if(cards.get(i).getScore() > cards.get(highest).getScore() && isPlayable(cards.get(i), top)) highest = i;
        }
        return highest;
    }
    public int getDecision(Card top){
        if(!isPlayable(top)) return -1;
        ArrayList<Integer> playable = new ArrayList<>();
        for(int i = 0; i < player.size(); i++){
            if(isPlayable(top, player.get(i))) playable.add(i);
        }
        if(personality == 0){
            System.out.print("Your hand: ");
            printCards();
            System.out.print("\n");
            return input.nextInt();
        }
        if(personality == 1){
            return playable.get(0);
        }
        if(personality == 2){
            boolean eight = (Math.random() < 0.5);
            if(eight){
                for(int i = 0; i < player.size(); i++){
                    if(player.get(i).getSymbol() == '8') return i;
                }
            }
            return playable.get((int)(Math.random() * playable.size()) + 1);
        }
        if(personality == 3){
            for(int i = 0; i < player.size(); i++){
                if(player.get(i).getSymbol() == '8') return i;
            }
            return highestP(player, top);
        }

        int[] suitCount = {0, 0, 0, 0};
        for (Card card : player) {
            suitCount[card.getSuit()]++;
        }
        return 0;
    }
}
public class Crazy8 {
    static ArrayList<Card> deck;

    public static void main(String[] args) {

    }
}
