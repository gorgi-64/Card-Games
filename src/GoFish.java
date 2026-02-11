import java.util.ArrayList;
import java.util.Scanner;
class struct{
    public int player;
    public int card;
    public boolean has;
    public struct(int player, int card, boolean has){
        this.player = player;
        this.card = card;
        this.has = has;
    }
}
class PlayerGF extends Player{
    protected int personality;
    protected int pnumber;
    protected ArrayList<struct> knowledge;
    protected ArrayList<Character> quartet;
    public PlayerGF(ArrayList<Card> player, boolean you, int personality, int pnumber){
        super(you);
        this.personality = personality;
        this.knowledge = new ArrayList<>();
        this.pnumber = pnumber;
    }
    public int findMaxI(int[] arr){
        int maxI = 0;
        for(int i = 1; i < arr.length; i++){
            if(arr[maxI] < arr[i]) maxI = i;
        }
        return maxI;
    }
    public void addQuartet(char card){
        quartet.add(card);
    }
    public void processKnowledge(){
        for(int i = 0; i < player.size() - 1; i++){
            for(int j = i + 1; j < player.size(); j++){
                if(knowledge.get(i).player == knowledge.get(j).player && knowledge.get(i).card == knowledge.get(j).card){
                    if(knowledge.get(i).has != knowledge.get(j).has) knowledge.get(i).has = (Math.random() < 0.5);
                    knowledge.remove(j);
                    j--;
                }
            }
        }
    }
    public void addKnowledge(int player, char card, boolean has){
        knowledge.add(new struct(player, card, has));
    }
    public int has(int card){
        for(int i = 0; i < knowledge.size(); i++){
            if(knowledge.get(i).card == card && knowledge.get(i).has && knowledge.get(i).player != pnumber) return i;
        }
        return -1;

    }
    public void decidePlayer(int[] player, char[] request){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter a card to request and a player");
        request[0] = Card.rankSymbol(input.nextInt());
        player[0] = input.nextInt();
    }
    public int[] findArrayCards(){
        int[] cards = new int[13];
        for(Card card : player){
            cards[card.getScore() - 2]++;
        }
        return cards;
    }
    public void decideAI(int[] player, char[] request){
        for(Card card : this.player){
            for (struct struct : knowledge) {
                if (struct.card == card.getSymbol() && struct.has) {
                    request[0] = Card.rankSymbol(struct.card);
                    player[0] = struct.player;
                    return;
                }
            }
        }
        int[] cards = findArrayCards();
        int maxI  = findMaxI(cards);
        while(has(maxI) == -1){
            cards[maxI] = 0;
            maxI = findMaxI(cards);
        }
        if(cards.length != 0){
            request[0] = Card.rankSymbol(maxI);
            player[0] = has(maxI);
            return;
        }
        cards = findArrayCards();
        maxI = findMaxI(cards);
        request[0] = Card.rankSymbol(maxI);
        do{
            player[0] = (int) (Math.random() * 5);
        }while(player[0] == pnumber);
    }
    public void forget(){
        double probability = personality / 10.0;
        if(Math.random() >= personality){
            double decision = Math.random();
            //0 0.125 0.25 0.5 0.625 0.75 0.875 1
            if(decision * 8 < 4) return;
            int record = (int)(Math.random() * knowledge.size());
            if(decision * 8 >= 4 && decision * 8 < 5){ //forget entire record
                knowledge.remove(record);
            }
            else if(decision * 8 >= 5 && decision * 8 < 6){
                knowledge.get(record).card = (int)(Math.random() * 14);
            }
            else if(decision * 8 >= 6 && decision * 8 < 7){
                knowledge.get(record).player = (int)(Math.random() * 5);
            }
            else{
                addKnowledge((int)(Math.random() * 5), Card.rankSymbol ((int)(Math.random() * 13)), (Math.random() < 0.5));
            }
        }
    }
}
public class GoFish{
    static ArrayList<Card> deck = new ArrayList<>();
    public static void main(String[] args){
        General.process(deck, 0);
    }
}