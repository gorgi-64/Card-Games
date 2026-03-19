import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import static java.lang.System.in;

class PlayerBJ extends Player {
    int condition;
    boolean dealer;
    int sum;
    @SuppressWarnings("unused")
    public PlayerBJ(){
        super(true);
    }
    public PlayerBJ(Card one, Card two, boolean deal, boolean ti) {
        super(ti);
        addCard(one);
        addCard(two);
        setDealer(deal);
        you = ti;
        condition = 1;
    }
    @SuppressWarnings("CopyConstructorMissesField")
    public PlayerBJ(PlayerBJ og){
        super(og.you);
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

    public boolean split(Card dealer) {
        if(this.player.get(0).getScore() != this.player.get(1).getScore() || this.dealer) return false;
        if (this.getYou()) {
            System.out.println("You can split. Do you want to split? \n1. No 2. Yes");
            return (new Scanner(System.in).nextInt() == 2);
        }
        int i = 0;
        if (player.get(i).getScore() == 11) return true;
        if (player.get(i).getScore() == 8) return true;
        if (player.get(i).getScore() == 9) {
            if (dealer.getScore() >= 2 && dealer.getScore() <= 9) return true;
        }
        if (player.get(i).getScore() == 7) {
            if (dealer.getScore() >= 2 && dealer.getScore() <= 7) return true;
        }
        if (player.get(i).getScore() == 6) {
            if (dealer.getScore() >= 2 && dealer.getScore() <= 6) return true;
        }
        if (player.get(i).getScore() == 4) {
            return dealer.getScore() == 5 || dealer.getScore() == 6;
        }
        return false;
    }
    public boolean doubleDown(Card dealer) {
        if (this.you) {
            System.out.println("Do yoy want to double down? (1. no, 2. yes)");
            return (new Scanner(System.in).nextInt() == 2);
        } else {
            if (player.get(0).getSymbol() == 'A' || player.get(1).getSymbol() == 'A') {
                if (this.suma() >= 13 && this.suma() <= 15 && (dealer.getScore() == 5 || dealer.getScore() == 6)) return true;
                else if (this.suma() >= 16 && this.suma() <= 17 && (dealer.getScore() >= 4 && dealer.getScore() <= 6)) return true;
                else return this.suma() == 18 && dealer.getScore() >= 3 && dealer.getScore() <= 6;
            } else {
                if (this.suma() == 11) return true;
                else if (this.suma() == 10 && dealer.getScore() >= 2 && dealer.getScore() <= 9) return true;
                else return this.suma() == 18 && dealer.getScore() >= 3 && dealer.getScore() <= 6;
            }
        }
    }
}

//hi

public class Blackjack {
    static PlayerBJ d;
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
    public static void twentyOne(boolean bust) {
        if(bust) System.out.println("Bust!");
        else System.out.println("21!");
    }
    public static double aggressiveLevel(){
        double choice = Math.random();
        if(choice < 1.0/6) return 0.5;
        if(choice < 2.0/6) return 1.0;
        if(choice < 3.0/6) return 1.5;
        if(choice < 4.0/6) return 1.75;
        if(choice < 5.0/6) return 2.0;
        return 1.25;
    }
    static void hand(PlayerBJ player) {
        Scanner input = new Scanner(in);

        if(player.suma() == 21 && player.sizes() == 2){
            System.out.println("Blackjack!");
            return;

        }
        if(player.getDealer() && player.suma() < 17) {
            System.out.println("Hit until 17");
            while(player.suma() < 17) {
                hit(player);
                if(player.suma() >= 21) {
                    twentyOne(player.suma() > 21);
                    return;
                }
                if(player.suma() >= 17) break;
            }
        }
        if(player.doubleDown(d.getCard(0))){
            System.out.println("Player doubles down!");
            hit(player);
            return;
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
                    double movementOpportunity = (21 - player.suma()) * aggressiveLevel();
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
    public static void gameLoop(int playerAmount) {

        PlayerBJ[] playerS = new PlayerBJ[playerAmount];
        PlayerBJ[] split = new PlayerBJ[playerAmount];
        int playerNumber = rand.nextInt(playerAmount);
        for(int i = 0; i < playerAmount; i++) {
            playerS[i] = new PlayerBJ(deck.get(i * 2), deck.get(i * 2 + 1), (i == playerAmount - 1), (i == playerNumber));

        }
        counter = (playerAmount * 2) - 1; //8 Cards, 0 index
        d = playerS[playerS.length - 1];

        System.out.println("Every player's Cards:" );
        for(int i = 0; i < playerAmount; i++) {
            System.out.print("Player " + i + ((i == playerNumber) ? "(you)" : "") + ((i == playerAmount - 1) ? "(dealer)" : "") + ": " + playerS[i].getCard(0).getAll());
            System.out.println(", " + ((i == playerAmount - 1) ? "?" : playerS[i].getCard(1).getAll()));
        }
        System.out.println("You are player " + playerNumber);
        General.pause();

        for(int i = 0; i < playerAmount; i++) {
            if(playerS[i].split(d.getCard(0))){
                split[i] = new PlayerBJ(playerS[i]);
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
            System.out.print("Player " + i + ((i == playerNumber) ? "(you)" : "") + ": ");
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
            gameLoop(6);
            System.out.println("Play again? (1: no, 2: yes)");
            int decision = input.nextInt();
            if(decision == 1) break;
        }
    }
}


