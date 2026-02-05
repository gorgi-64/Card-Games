public class Card {

    protected char symbol;
    protected int score;

    public Card(){
        symbol = '0';
        score = 0;
    }

    public Card(int temp) {
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
    }

    public void setScore(int temp){
        score = temp;
    }

    public int getScore() {
        return score;
    }
    public char getSymbol() {
        return symbol;
    }
    public void setScore(boolean ace) {
        if(ace) score = 11;
        else score = 1;
    }

}