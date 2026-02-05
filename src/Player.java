import java.util.ArrayList;

public abstract class Player {
    protected ArrayList<Card> player = new ArrayList<Card>();
    protected boolean you;
    public void addCard(Card card){
        player.add(card);
    }
    public void addCards(ArrayList<Card> pot){
        player.addAll(pot);
    }
    public void removeCard(int start, int end){
        try {
            player.subList(start, end).clear();
        }catch(IndexOutOfBoundsException e){
            System.err.println("ami...");
        }
    }
    public Card getCard(int index){
        try{
            return player.get(index);
        }catch(IndexOutOfBoundsException e){
            System.err.println("ami x2");
        }
        return new Card();
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
    public ArrayList<Card> copy(){
        return new ArrayList<>(player);
    }
}
