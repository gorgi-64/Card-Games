import java.util.ArrayList;

public abstract class Player {
    protected ArrayList<Card> player;
    protected boolean you;
    public Player(boolean you){
        player = new ArrayList<>();
        this.you = you;
    }
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
            System.out.print(player.get(i).getAll());
            if(i != player.size() - 1) System.out.print(", ");
        }
        System.out.print('\n');
    }
}