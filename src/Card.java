import java.util.Comparator;
@SuppressWarnings("all")
public class Card {

    protected char symbol;
    protected int score;
    protected int suit;

    public Card(int number, int game){
        score = number;
        suit = number % 4;
        score = setScore((number % 13) + 2, game);
        symbol = rankSymbol(number % 13 + 2);
    }
   public static Comparator<Card> rankSort = new Comparator<>() {
        @Override
        public int compare(Card card, Card t1) {
            return card.getScore() - t1.getScore();
        }
    };
    public static char rankSymbol(int number){
        char symbol = '0';
        if(number >= 2 && number < 10) symbol = (char) ('0' + number);
        else switch(number) {
            case 10: symbol = 'X'; break;
            case 11: symbol = 'J'; break;
            case 12: symbol = 'Q'; break;
            case 13: symbol = 'K'; break;
            case 14: symbol = 'A'; break;
        }
        return symbol;
    }
    public static char suitSymbol(int number){
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
        if(game == 1) return (temp > 10 && temp != 14) ? 10 : (temp == 14) ? 11 : temp; //blackjack
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
     public String print(){
        return symbol + "" + suitSymbol(suit);
     }
    /*public void setScore(boolean ace) {
        if(ace) score = 11;
        else score = 1;
    }*/
    /*public void setScore(int score){
        this.score = score;
    }*/
}