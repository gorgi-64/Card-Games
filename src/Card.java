public class Card {

    protected char symbol;
    protected int score;
    protected char suit;

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
        switch(number % 4){
            case 0:
                suit = '\u2665';
                break;
            case 1:
                suit = '\u2666';
                break;
            case 2:
                suit = '\u2660';
                break;
            case 3:
                suit = '\u2663';
                break;
            default:
                suit = '0';
                break;
        }
    }

    public void setScore(int temp){
        score = temp;
    }

    public int getScore() {
        return score;
    }
    public String getSymbol() {
        return "" + symbol + suit;
    }
    public void setScore(boolean ace) {
        if(ace) score = 11;
        else score = 1;
    }

}