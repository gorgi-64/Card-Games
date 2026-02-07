public class Card {

    protected char symbol;
    protected int score;
    protected int suit;

    public Card(){
        symbol = '0';
        score = 0;
    }

    public Card(int number){
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

    public void setScore(int temp){
        score = temp;
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

}