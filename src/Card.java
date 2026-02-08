import java.util.Random;
import java.util.ArrayList;
public class Card {

    protected char symbol;
    protected int score;
    protected int suit;

    public Card(){
        symbol = '0';
        score = 0;
    }

    public Card(int number, int game){
        int temp = (number % 13) + 2;
        score = temp;
        if(temp < 10) symbol = (char) ('0' + temp);
        else switch(temp) {
            case 10:
                symbol = 'X';
                break;
            case 11:
                symbol = 'J';
                break;
            case 12:
                symbol = 'Q';
                break;
            case 13:
                symbol = 'K';
                break;
            case 14:
                symbol = 'A';
                break;
        }
        suit = number % 4;
        score = setScore(temp, game);
    }
    public char suitSymbol(int number){
        switch(number){
            case 0:
                return '♥';
            case 1:
                return '♦';
            case 2:
                return '♠';
            case 3:
                return '♣';
            default:
                return '0';
        }
    }
    public int setScore(int temp, int game){
        if(game == 0) return temp; //general
        if(game == 1) return ((temp > 10 && temp != 14) ? 10 : temp); //blackjack
        if(game == 2){ //crazy 8
            if(temp == 8) return 80;
            if(temp >= 11 && temp <= 13) return 10;
            if(temp == 14) return 1;
            else return temp;
        }
        return 0;
    }
    public int getScore() {
        return score;
    }
    public String getAll() {
        return "" + symbol + suitSymbol(suit);
    }
    public char getSymbol(){
        return symbol;
    }
    public char getSuit(){
        return suitSymbol(suit);
    }
    public int getSuit(boolean that){
        if(that) return suit;
        return 0;
    }
    public void setScore(boolean ace) {
        if(ace) score = 11;
        else score = 1;
    }
    public void setScore(int score){
        this.score = score;
    }
    public static void process(ArrayList<Card> deck, int game){
        Random newrand = new Random(System.nanoTime());
        int[] array = new int[52];
        for(int i = 0; i < 52; i++) {
            array[i] = i;
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
            Card temp = new Card(arraycopy[i], game);
            deck.add(temp);
        }


    }
}