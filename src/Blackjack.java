import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

import static java.lang.System.in;

class CardBJ extends Card{
    @Override
    public void setScore(int temp){
        if(temp <= 10) score = temp;
        else if(temp != 14) score = 10;
        else score = 14;
    }
    public CardBJ(int temp){
        super(temp);
        setScore(temp);
    }
}

class PlayerBJ extends Player {
    int condition;
    boolean dealer;
    int sum;
    public boolean getYou() {
        return you;
    }
    public int suma() {
        int s = 0;
        for(int i = 0; i < player.size(); i++) {
            if(player.get(i).getScore() > 10 || player.get(i).getScore() == 1) player.get(i).setScore(s + 11 <= 21);
            s += player.get(i).getScore();
        }
        sum = s;
        return s;
    }
    public int sizes() {
        return player.size();
    }

    public void setDealer(boolean temp) {
        dealer = temp;
    }
    public boolean getDealer() {
        return dealer;
    }
    public void constructor(CardBJ one, CardBJ two, boolean deal, boolean ti) {
        addCard(one);
        addCard(two);
        setDealer(deal);
        you = ti;
        condition = 1;
    }
    public void Split() {
        player.remove(1);
    }
}
//hi
public class Blackjack {
    static Random rand = new Random();
    static int counter = 0;
    static ArrayList<CardBJ> deck = new ArrayList<>();
    public static void pause() {
        Scanner input = new Scanner(in);
        input.nextLine();
    }
    public static void process(){
        //ArrayList<CardBJ> deck) {
        Random newrand = new Random(System.nanoTime());
        int[] array = new int[52];
        for(int i = 0; i < 52; i++) {
            array[i] = (i % 13) + 2;
        }
        int[] arraycopy = new int[52];
        for(int i = 0; i < 52; i++) {
            int newrandom = 0;
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
    public static void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void hit(PlayerBJ player) {
        counter++;
        player.addCard(deck.get(counter));
        System.out.println("Player hits and gets a " + deck.get(counter).getSymbol());
        wait(450);

    }
    public static void twentyone(boolean bust) {
        if(bust) System.out.println("Bust!");
        else System.out.println("21!");
    }
    public static double agressiveLevel(){
        double choice = Math.random();
        if(choice < 1/5) return 0.5;
        if(choice < 2/5) return 0.75;
        if(choice < 3/5) return 1.25;
        if(choice < 4/5) return 1.5;
        if(choice < 5/5) return 2.0;
        return 1.0;
    }
    public static void hand(PlayerBJ player) {
        Scanner input = new Scanner(in);
        player.printCards();
        if(player.getDealer() && player.suma() < 17) {
            System.out.println("Hit until 17");
            while(player.suma() < 17) {
                hit(player);
                if(player.suma() >= 21) {
                    twentyone(player.suma() > 21);
                    return;
                }
                if(player.suma() >= 17) break;
            }
        }
        while(true) {
            int choice = 0;
            if(player.getYou()) {
                player.printCards();
                System.out.println("hit: 1, fold: 2");
                choice = input.nextInt();
            }
            else {
                player.printCards();
                if (player.suma() <= 10) choice = 1;
                else if (player.suma() >= 19) choice = 2;
                else {
                    double movementOpportunity = (21 - player.suma()) * agressiveLevel();
                    double rng = rand.nextInt(11);
                    if(movementOpportunity > rng) choice = 1;
                    else choice = 2;
                }
            }
            if(choice == 1) hit(player);
            else {
                System.out.println("Player folds");
                return;
            }
            if(player.suma() > 21) {
                System.out.println("PLayer busts!");
                return;
            }
            if(player.suma() == 21) {
                System.out.println("21!");
                return;
            }
        }

    }
    public static void winConditions(PlayerBJ one, PlayerBJ dealer) {
        if(one.suma() > 21) {
            System.out.println("Bust!");
            return;
        }
        else if(one.suma() == dealer.suma()) {
            System.out.println("Push!");
            return;
        }

        else if(one.suma() == 21 && one.sizes() == 2) {
            System.out.println("Blackjack!");
            return;
        }

        else if(one.suma() > dealer.suma() && one.suma() < 21) {
            System.out.println("Win!");
            return;
        }

        else if(one.suma() <= 21 && dealer.suma() > 21) {
            System.out.println("Win!");
            return;
        }

        else System.out.println("Lose!");
    }
    public static void gameloop(int playerAmount) {
        process();
        PlayerBJ[] playerS = new PlayerBJ[playerAmount];
        PlayerBJ[] split = new PlayerBJ[playerAmount];
        int playnernumber = rand.nextInt(playerAmount);
        for(int i = 0; i < playerAmount; i++) {
            playerS[i] = new PlayerBJ();
            playerS[i].constructor(deck.get(i * 2), deck.get(i * 2 + 1), (i == playerAmount - 1), (i == playnernumber));
        }
        counter = (playerAmount * 2) - 1; //8 CardBJs, 0 index
        System.out.println("Every player's CardBJs:" );
        for(int i = 0; i < playerAmount; i++) {
            System.out.print("Player " + i + ((i == playnernumber) ? "(you)" : "") + ((i == playerAmount - 1) ? "(dealer)" : "") + ": " + playerS[i].getCard(0).getSymbol());
            System.out.println(", " + ((i == playerAmount - 1) ? "?" : playerS[i].getCard(1).getSymbol()));

        }
        System.out.println("You are player " + playnernumber);
        pause();
        for(int i = 0; i < playerAmount - 1; i++) {
            if(playerS[i].getCard(0).getScore() == playerS[i].getCard(1).getScore()) {
                int choice = 0;
                if(playerS[i].getYou()) {
                    System.out.println("You can split. Do you want to split? (1 for yes, 2 for no)");
                    Scanner input = new Scanner(System.in);
                    choice = input.nextInt();
                }
                else {
                    double choices = Math.random();
                    choice = ((choices <= 0.3) ? 2 : 1);
                }
                if(choice != 2){
                    System.out.println("Player " + i + " splits!");
                    split[i] = new PlayerBJ();
                    split[i].addCard(playerS[i].getCard(1));
                    playerS[i].Split();
                }
            }

            if(i != playerAmount - 1) System.out.print("PLayer " + i + ": ");
            else System.out.print("Dealer: ");
            hand(playerS[i]);
            System.out.print("\n");
            if(split[i] != null) hand(split[i]);
            System.out.println("Final score:" + playerS[i].suma());
            if(split[i] != null) System.out.println("Final split score: " + split[i].suma());
            pause();
        }
        boolean tie = false;
        for(int i = 0; i < playerAmount - 1; i++) {
            System.out.print("Player " + i + ((i == playnernumber) ? "(you)" : "") + ": ");
            winConditions(playerS[i], playerS[playerAmount - 1]);
            if(split[i] != null) {
                System.out.print("Split: ");
                winConditions(split[i], playerS[playerAmount - 1]);
            }
        }
    }
    public static void main(String[] args) {
        while(true) {
            rand = new Random(System.nanoTime());
            rand = new Random(System.nanoTime());
            gameloop(6);
            pause();
        }

    }
}


