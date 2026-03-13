import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import static java.lang.System.in;

class PlayerBJ extends Player {
    int condition;
    boolean dealer;
    int sum;
    public PlayerBJ(){
        super(true);
    }
    public void splitConstructor(PlayerBJ og){
        this.dealer = og.dealer;
        this.you = og.you;
        this.addCard(og.getCard(1));
        og.remove(1);
    }
    @SuppressWarnings("all")
    private void remove(int i) {
        player.remove(i);
    }

    public boolean getYou() {
        return you;
    }
    public int suma() {
        int s = 0;
        int ace = 0;
        for (Card card : player) {
            s += card.getScore();
            if(card.getSymbol() == 'A') ace++;
        }
        while(s > 21 && ace > 0){
            s -= 10;
            ace--;
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
    public void constructor(Card one, Card two, boolean deal, boolean ti) {
        addCard(one);
        addCard(two);
        setDealer(deal);
        you = ti;
        condition = 1;
    }
}

//hi

public class Blackjack {
    static Random rand = new Random();
    static int counter = 0;
    static ArrayList<Card> deck = new ArrayList<>();
    static Scanner input = new Scanner(System.in);
     static void hit(PlayerBJ player) {
        counter++;
        player.addCard(deck.get(counter));
        System.out.println("Player hits and gets a " + deck.get(counter).getAll());
        General.wait(650);

    }
    public static void twentyone(boolean bust) {
        if(bust) System.out.println("Bust!");
        else System.out.println("21!");
    }
    public static double agressiveLevel(){
        double choice = Math.random();
        if(choice < 1.0/5) return 0.5;
        if(choice < 2.0/5) return 0.75;
        if(choice < 3.0/5) return 1.25;
        if(choice < 4.0/5) return 1.5;
        return 2.0;
    }
    static void hand(PlayerBJ player) {
        Scanner input = new Scanner(in);
        //player.printCards();
        if(player.suma() == 21 && player.sizes() == 2){
            System.out.println("Blackjack!");
            return;

        }
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
            int choice;
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
    static void winConditions(PlayerBJ one, PlayerBJ dealer) {
        if(one.suma() > 21) {
            System.out.println("Bust!");

        }
        else if(one.suma() == dealer.suma()) {
            System.out.println("Push!");
        }

        else if(one.suma() == 21 && one.sizes() == 2) {
            System.out.println("Blackjack!");

        }

        else if(one.suma() > dealer.suma() && one.suma() < 21) {
            System.out.println("Win!");

        }

        else if(one.suma() <= 21 && dealer.suma() > 21) {
            System.out.println("Win!");

        }

        else System.out.println("Lose!");
    }
    public static void gameloop(int playerAmount) {

        PlayerBJ[] playerS = new PlayerBJ[playerAmount];
        PlayerBJ[] split = new PlayerBJ[playerAmount];
        int playnernumber = rand.nextInt(playerAmount);
        for(int i = 0; i < playerAmount; i++) {
            playerS[i] = new PlayerBJ();
            playerS[i].constructor(deck.get(i * 2), deck.get(i * 2 + 1), (i == playerAmount - 1), (i == playnernumber));
        }
        counter = (playerAmount * 2) - 1; //8 Cards, 0 index
        System.out.println("Every player's Cards:" );
        for(int i = 0; i < playerAmount; i++) {
            System.out.print("Player " + i + ((i == playnernumber) ? "(you)" : "") + ((i == playerAmount - 1) ? "(dealer)" : "") + ": " + playerS[i].getCard(0).getAll());
            System.out.println(", " + ((i == playerAmount - 1) ? "?" : playerS[i].getCard(1).getAll()));

        }
        System.out.println("You are player " + playnernumber);
        General.pause();
        for(int i = 0; i < playerAmount; i++) {
            if(playerS[i].getCard(0).getScore() == playerS[i].getCard(1).getScore()) {
                int choice;
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
                    split[i].splitConstructor(playerS[i]);
                }
            }

            if(i != playerAmount - 1) System.out.print("Player " + i + ": ");
            else System.out.print("Dealer: ");
            hand(playerS[i]);
            System.out.print("\n");
            if(split[i] != null) hand(split[i]);
            System.out.println("Final score:" + playerS[i].suma());
            if(split[i] != null) System.out.println("Final split score: " + split[i].suma());
            General.pause();
        }
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
            deck = new ArrayList<>();
            General.process(deck, 1);
            gameloop(6);
            System.out.println("Play again? (1: no, 2: yes)");
            int decision = input.nextInt();
            if(decision == 1) break;
        }
    }
}


