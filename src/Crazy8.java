import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
    public PlayerC8(boolean you, int personality, ArrayList<Card> player){
        super(you);
        this.personality = personality;
        this.player = player;
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
    public static int contains(char rank, ArrayList<Card> playable){
        for(int i = 0; i < playable.size(); i++ ){
            Card card = playable.get(i);
            if(card.getSymbol() == rank) return i;
        }
        return -1;
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
            for(int i = 0; i < player.size(); i++){
                if(playable.contains(i)) suitCount[player.get(i).getSuit(true)]++;
            }
            if(personality == 4){
                System.out.println("In development");
                personality = 5;
            }
            if(personality == 5){
                if((player.size() == 2 || player.size() == 3) && contains('8', player) != -1) return contains('8', player);
                int play;
                do{
                    play = highestP(player, top);
                }while(!isPlayable(player.get(play), top) && player.get(play).getSymbol() != '8');
                return play;

            }

            return 0;
        }

}


public class Crazy8 {
    static ArrayList<Card> deck;

    public static void main(String[] args) {
        Card.process(deck, 2);
        int counter = 0;
        PlayerC8[] players = new PlayerC8[6];
        for(int i = 0; i < 6; i++){
            PlayerC8 temp;
                int personality = (int)(Math.random() * 5) + 1;
                temp = new PlayerC8(i == 0, ((i == 0) ? 0 : personality), War.subArrayList(deck, counter, counter + 7));
                counter += 7;
        }
    }
}
