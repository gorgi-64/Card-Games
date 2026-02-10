import java.util.ArrayList;
import java.util.Scanner;
class PlayerC8 extends Player{
    static Scanner input = new Scanner(System.in);
    protected int personality;
    //0: you, 1: dumb, 2: chaotic, 3: aggressive, 4: cautious, 5: smart
    protected int points = 0;
    @Override
    public void printCards(){
        for(int i = 0; i < player.size(); i++){
            System.out.print("[" + i + ". ");
            System.out.print("\u001B[1m" +  player.get(i).getAll() + "\u001B[0m]; ");
        }
    }
    public void findPoints(){
        for(Card card : player){
            points += card.getScore();
        }
    }
    public int getPoints(){
        return points;
    }
    public PlayerC8(boolean you, int personality, ArrayList<Card> player){
        super(you);
        this.personality = personality;
        this.player = player;
    }
    public ArrayList<Card> returnPlayer(){
        return player;
    }
    public void removeCard(int index){
        player.remove(index);
    }
    public static boolean isPlayable(Card top, Card card, int suit){
        return ((card.getSymbol() == top.getSymbol()) || (card.getSuit(true) == suit) || (card.getSymbol() == '8'));
    }
    public boolean isPlayable(Card top, int suit){
        for(Card card : player){
            if(isPlayable(top, card, suit)) return true;
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
    public static int highestP(ArrayList<Card> cards, Card top, int suit){
        int highest = 0;
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i).getScore() > cards.get(highest).getScore() && isPlayable(cards.get(i), top, suit)) highest = i; //this
        }
        return highest;
    }
    public static int findMax(int[] arr){
        int maxI = 0, max = arr[0];
        for(int i = 1; i < arr.length; i++){
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return maxI;
    }
    public int changeSuit(int number){
        int[] arr = {0, 0, 0, 0};
        for (Card card : player) {
            arr[card.getSuit(true)]++;
        }
        int maxI = findMax(arr);
        for(int i = 2; i <= number; i++){
            arr[maxI] = -1;
            maxI = findMax(arr);
        }
        return maxI;
    }
    public int changeSuit(){
        int decision;
        if(personality == 0){
            System.out.print("Enter the suit you want to change to: ( ");
            for(int i = 0; i < 4; i++) System.out.print(i + ": " + Card.suitSymbol(i) + ((i == 3) ? ")" : ", "));
            decision = input.nextInt();
            //return decision - 1;
        }
         else if(personality == 1 || personality == 2){
            decision =  (int)(Math.random() * 4);
        }
        else if(personality != 4){
            decision =  changeSuit(1);
        }
        else{
            decision =  changeSuit((Math.random() < 0.5 ? 2 : 3));
        }
        System.out.println("Changed to " + Card.suitSymbol(decision));
        return decision;
    }


    public int getDecision(Card top, int suit){
        if(!isPlayable(top, suit)){ return -1; }
        ArrayList<Integer> playable = new ArrayList<>();
        for(int i = 0; i < player.size(); i++){
            if(isPlayable(top, player.get(i), suit)) playable.add(i);
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
            return playable.get((int)(Math.random() * playable.size()));
        }
        if(personality == 3){
            for(int i = 0; i < player.size(); i++){
                if(player.get(i).getSymbol() == '8') return i;
            }
            return highestP(player, top, suit);
        }

            if(personality == 4){
                System.out.println("In development");
                personality = 5;
            }
            if(personality == 5){
                if((player.size() == 2 || player.size() == 3) && contains('8', player) != -1) return contains('8', player);
                int play;
                do{
                    play = highestP(player, top, suit);
                }while(!isPlayable(player.get(play), top, suit) && player.get(play).getSymbol() != '8');
                return play;

            }
            return 0;
        }


}

public class Crazy8  {
    static ArrayList<Card> deck = new ArrayList<>();
    static boolean isEmpty(PlayerC8[] arr){
        boolean empty1 = false;
        for(PlayerC8 that : arr){
            if(that.size() != 0){
                if(empty1) return true;
                else empty1 = true;
            }
        }
        return false;
    }
    static boolean hasEmpty(PlayerC8[] players){
        for(PlayerC8 player : players){
            if(player.size() == 0) return true;
        }
        return false;
    }
    public static void main(String[] args) {
        General.process(deck, 2);
        int counter = 0;
        PlayerC8[] players = new PlayerC8[6];
        for(int i = 0; i < 6; i++){
            PlayerC8 temp;
                int personality = (int)(Math.random() * 5) + 1;
                temp = new PlayerC8(i == 0, ((i == 0) ? 0 : personality), General.subArrayList(deck, counter, counter + 6));
                counter += 7;
                players[i] = temp;
        }
        Card top = deck.get(counter);
        int suit = top.getSuit();

        deck.subList(0, counter + 2).clear();
        //boolean calculatePoints
       for(int i = 0; isEmpty(players); i = (i + 1) % 6){
           if(players[i].size() == 0) continue;
           if(players[i].getPoints() >= 100 && hasEmpty(players)){
               deck.addAll(players[i].returnPlayer());
               players[i].player.clear();
               System.out.println("Player's points are over 100 and loses automatically!");
           }
           System.out.println("Player number " + i);
           System.out.println("Top card: " + top.getAll());
            int decision = players[i].getDecision(top, suit);
            General.wait(500);
            while(decision == -1){
                System.out.println("No card, must draw!");
                System.out.println("Draw: " + deck.get(0).getAll());
                players[i].addCard(deck.get(0));
                deck.remove(0);
                decision = players[i].getDecision(top, suit);
            }
            deck.add(top);
            top = players[i].getCard(decision);
            suit = (top.getSymbol() == '8') ? players[i].changeSuit() : top.getSuit(true);
            players[i].removeCard(decision);
            System.out.println("Given card: " + top.getAll());
            System.out.println("\n");
            players[i].findPoints();
            General.wait(1000);
        }
    }
}
