import java.util.ArrayList;

class Card8 extends Card{
    @Override
    public void setScore(int temp){
        if(temp == 8) score = 80;
        if(temp >= 11 && temp <= 13) score = 10;
        if(temp == 14) score = 1;
        score = temp;
    }
    public Card8(int temp){
        super(temp);
        setScore(temp);
    }
}

public class Crazy8 {
    static ArrayList<Card> deck;

    public static void main(String[] args) {

    }
}
