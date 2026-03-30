import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

@SuppressWarnings("unused")
class PlayerBJ extends Player {
    int condition;
    boolean dealer;
    int sum;
    boolean DD;
    double totalSum;
    double currentSum;

    public PlayerBJ(){
        super(true);
        totalSum = 100.0;
    }
    public PlayerBJ(boolean you){
        super(you);
        totalSum = 100;
    }
    public PlayerBJ(Card one, Card two, boolean deal, double obshtaSuma, boolean ti) {
        super(ti);
        totalSum = obshtaSuma;
        addCard(one);
        addCard(two);
        setDealer(deal);
        you = ti;
        condition = 1;
        DD = false;
        totalSum = obshtaSuma;
    }

    public void setDD(){
        DD = true;
    }
    public boolean getDD(){
        return DD;
    }
    public void setTotalSum(double totalSum){
        this.totalSum = totalSum;
    }
    public double getTotalSum(){
        return totalSum;
    }
    public void addSum(double amount){
        totalSum += amount;
    }
    public void setCurrentSum(int mode){
        if(this.dealer){
            currentSum = 0.0;
            return;
        }
        switch(mode){
            case 1:
                System.out.println("How much do you want to bet? (You have $" + totalSum + ")");
                currentSum = new Scanner(System.in).nextDouble();
                break;
            case 2:
                currentSum = totalSum * new int[]{1, 2, 2, 2, 3, 3, 4, 4, 5, 5, 3, 6, 7, 8, 9, 10}[General.rng(16)] / 10.0;
                //currentSum = 10.0;
                break;
            case 3:
                if(totalSum < currentSum){
                    System.out.println("Not enough money!");
                    return;
                }
                currentSum += totalSum;
                break;
            default:
                System.exit(1);
        }
        //totalSum -= currentSum;
        if(currentSum == totalSum) System.out.println("All in");
    }


    public double getCurrentSum(){
        return currentSum;
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
        if(this.player.get(0).getScore() != this.player.get(1).getScore() || this.dealer || (this.currentSum * 2) > this.totalSum) return false;
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
        if(this.dealer || this.player.size() < 2 || (this.currentSum * 2) > this.totalSum) {
            this.DD = false;
            return false;
        }
        if (this.you) {
            System.out.println("Do you want to double down? (1. no, 2. yes)");
            this.DD = (new Scanner(System.in).nextInt() == 2);
            return DD;
        } else {
            if (player.get(0).getSymbol() == 'A' || player.get(1).getSymbol() == 'A') {
                if (this.suma() >= 13 && this.suma() <= 15 && (dealer.getScore() == 5 || dealer.getScore() == 6)){
                    this.DD = true;
                    return true;
                }
                else if (this.suma() >= 16 && this.suma() <= 17 && (dealer.getScore() >= 4 && dealer.getScore() <= 6)){
                    this.DD = true;
                    return true;
                }
                else return this.suma() == 18 && dealer.getScore() >= 3 && dealer.getScore() <= 6;
            } else {
                if (this.suma() == 11){
                    this.DD = true;
                    return true;
                }
                else if (this.suma() == 10 && dealer.getScore() >= 2 && dealer.getScore() <= 9){
                    this.DD = true;
                    return true;
                }
                else{
                    this.DD = this.suma() == 18 && dealer.getScore() >= 3 && dealer.getScore() <= 6;
                    return DD;
                }
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
    static boolean withMoney;
    static int playerNumber;
    static PlayerBJ[] playerS = new PlayerBJ[playerNumber];

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

    static void hit(PlayerBJ player) {
        counter++;
        player.addCard(deck.get(counter));
        System.out.println("Player hits and gets a " + deck.get(counter).getAll());
        General.wait(650);
    }

    static void hand(PlayerBJ player) {
        Scanner input = new Scanner(System.in);
        player.printCards();
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
                if(player.size() >= 3) player.printCards();
                System.out.println("hit: 1, stand: 2");
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
                System.out.println("Player stands");
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
    public static void gameLoop(int playerAmount) {
        General.shuffle(playerS);
        PlayerBJ[] split = new PlayerBJ[playerAmount];
        int playerNumber = rand.nextInt(playerAmount);
        for(int i = 0; i < playerAmount; i++) {
            double temp = playerS[i].getTotalSum();
            playerS[i] = new PlayerBJ(deck.get(i * 2), deck.get(i * 2 + 1), (i == playerAmount - 1), temp, playerS[i].getYou());
            if(withMoney) playerS[i].setCurrentSum((playerS[i].getYou()) ? 1 : 2);
            //(Card one, Card two, boolean deal, double obshtaSuma, boolean ti)
        }
        counter = (playerAmount * 2) - 1; //8 Cards, 0 index
        d = playerS[playerS.length - 1];
        if(withMoney) {
            System.out.println("What every player bet: ");
            for (int i = 0; i < playerS.length - 1; i++) {
                System.out.println("Player " + i + ((playerS[i].getYou()) ? "(you)" : "") + ": $" + playerS[i].getCurrentSum());
            }
            General.pause();
            System.out.print("\n");
        }
        System.out.println("Every player's Cards:" );
        for(int i = 0; i < playerAmount; i++) {
            System.out.print("Player " + i + ((playerS[i].getYou()) ? "(you)" : "") + ((i == playerAmount - 1) ? "(dealer)" : "") + ": " + playerS[i].getCard(0).getAll());
            System.out.println(", " + ((i == playerAmount - 1) ? "?" : playerS[i].getCard(1).getAll()));
            if(playerS[i].getYou()) playerNumber = i;
        }
        System.out.println("You are player " + playerNumber);
        General.pause();

        for(int i = 0; i < playerAmount; i++) {
            if(playerS[i].split(d.getCard(0))){
                split[i] = new PlayerBJ(playerS[i]);

            }
            System.out.print("\n");
            if(i != playerAmount - 1) System.out.print("Player " + i + ": ");
            //if(i != playerAmount - 1) System.out.print("Player " + i + ": ");
            else System.out.print("Dealer: ");
            //System.out.println(playerS[i].getCard(0).print() + " " + ((playerS[i].size() > 1) ? playerS[i].getCard(1).print() : "")) ;

            hand(playerS[i]);
            System.out.print("\n");
            if(split[i] != null) hand(split[i]);
            System.out.println("Final score:" + playerS[i].suma() + (playerS[i].getDD() ? "(DD)" : ""));
            if(split[i] != null) System.out.println("Final split score: " + split[i].suma());
            General.pause();
        }
        if(withMoney) winWithMoney(playerS, split, playerNumber);
        else winWithoutMoney(playerS, split, playerNumber);
    }

    static double winConditions(PlayerBJ one, PlayerBJ dealer) {
        if (one.suma() > 21) {
            System.out.print("Bust!");
            return -1.0; // Bust - lose bet
        }
        else if (one.suma() == 21 && one.sizes() == 2) {
            System.out.print("Blackjack!");
            return 1.5; // Blackjack - pays 3:2
        }
        else if (one.suma() == dealer.suma()) {
            System.out.print("Push!");
            return 0.0; // Push - bet returned

        }
        else if (one.suma() > dealer.suma() && one.suma() <= 21) {
            System.out.print("Win!");
            return 1.0; // Win - pays 1:1
        }
        else if (one.suma() <= 21 && dealer.suma() > 21) {
            System.out.print("Win!");
            return 1.0; // Win (dealer bust) - pays 1:1
        }
        else {
            System.out.print("Lose!");
            return -1.0; // Lose - lose bet
        }
    }
    @SuppressWarnings("all")
    public static void winWithMoney(PlayerBJ[] playerS, PlayerBJ[] split, int playerNumber){
        double pot = 0;
        int playerAmount = playerS.length;
        int counter = 0;
        PlayerBJ[] nonbusted = new PlayerBJ[playerS.length];
        for(int i = 0; i < playerS.length - 1; i++) {
            System.out.print("Player " + i + ((i == playerNumber) ? "(you)" : "") + (playerS[i].getDD() ? "(DD)" : "") + ": ");
            double temp = playerS[i].getCurrentSum();
            double coefficient = winConditions(playerS[i], playerS[playerAmount - 1]) * (playerS[i].getDD() ? 2 : 1);
            //System.out.println(temp);
            double winnings = temp * coefficient;
            pot += (winnings <= 0 ? winnings : 0);
            if(split[i] != null) {
                System.out.print("\nSplit: ");
                coefficient = winConditions(split[i], playerS[playerAmount - 1]);
                playerS[i].addSum(split[i].getCurrentSum() * coefficient);
                winnings += (temp * coefficient);

            }
            System.out.println("\nWinnings: $" + winnings + "\n");
            playerS[i].addSum(winnings);
            pot += (winnings <= 0 ? winnings : 0);
            if(playerS[i].getTotalSum() == 0.0) continue;
            nonbusted[counter++] = playerS[i];
        }
        playerS[playerS.length - 1].addSum(Math.abs(pot));
        playerS = nonbusted;
    }


    @SuppressWarnings("all")
    public static void winWithoutMoney(PlayerBJ[] playerS, PlayerBJ[] split, int playerNumber){
         int playerAmount = playerS.length;
        for(int i = 0; i < playerS.length - 1; i++) {
            System.out.print("Player " + i + ((i == playerNumber) ? "(you)" : "") + ": ");
            winConditions(playerS[i], playerS[playerAmount - 1]);
            System.out.println('\n');
            if(split[i] != null) {
                System.out.print("\nSplit: ");
                winConditions(split[i], playerS[playerAmount - 1]);
                System.out.print('\n');
            }
        }
    }


    public static void main(String[] args) {
         System.out.println("Welcome to Blackjack!");
         int choice;
         do{
            System.out.println("Enter 1 for game with no money, 2 for a game with money and 3 for rules!");
            choice = input.nextInt();
            if(choice == 3){
                System.out.println("You are initially given two cards. You must either take more cards (hit) or give up (stand). ");
                System.out.println("your objective is to reach as close to 21 without going over 21 (bust)");
                System.out.println("Face cards (X, J, Q, K) are worth 10 points and an ace is worth either 1 or 11");
                System.out.println("If you have two identical cards, you can split (play 2 hands at once)");
                System.out.println("every hand you may double down (hit exactly once for double the bet)");
            }
         } while(choice == 3);
         if(!(choice == 1 || choice == 2)){
             throw new IllegalArgumentException("Invalid input!");
         }
         withMoney = (choice == 2);
         playerNumber = 6;
         playerS = new PlayerBJ[playerNumber];
         for(int i = 0; i < playerNumber; i++){
             playerS[i] = new PlayerBJ(i == 0);
         }
        while(true) {
            deck = new ArrayList<>();
            General.process(deck, 1);
            if(playerS[0].getTotalSum() == 0 && withMoney){
                System.out.println("You lost! Goodbye!");
                System.exit(0);
            }
            gameLoop(playerNumber);
            System.out.println("Play again? (1: no, 2: yes)");
            int decision = input.nextInt();
            if(decision == 1) break;
        }
    }
}