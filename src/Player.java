import java.util.ArrayList;

public abstract class Player {
    protected ArrayList<Card> player = new ArrayList<Card>();
    protected boolean you;
    public void addCard(Card card){
        player.add(card);
    }

    public Card getCard(int index){
        return player.get(index);
    }
    public int size(){
        return player.size();
    }
    public void printCards(){
        for(int i = 0; i < player.size(); i++) {
            System.out.print(player.get(i).getSymbol());
            if(i != player.size() - 1) System.out.print(", ");
        }
    }
}
