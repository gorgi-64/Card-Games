import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

class knowledgeS {
    public int player;
    public int card;
    public boolean has;
    public knowledgeS(int player, int card, boolean has){
        this.player = player;
        this.card = card;
        this.has = has;
    }
}

class PlayerGF extends Player{
    protected int personality;
    protected int pnumber;
    protected ArrayList<knowledgeS> knowledge;
    protected ArrayList<Character> quartet;
    public PlayerGF(ArrayList<Card> player, boolean you, int personality, int pnumber){
        super(you);
        this.personality = personality;
        this.knowledge = new ArrayList<>();
        this.pnumber = pnumber;
        this.player = player;
    }
    @Override
    public void printCards(){
        for(int i = 0; i < player.size(); i++) {
            System.out.print(i + ": " + player.get(i).getAll());
            if(i != player.size() - 1) System.out.print(", ");
        }
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
    public void addCards(ArrayList<Card> that){
        player.addAll(that);
        player.sort(Comparator.comparing(Card::getScore));
    }
    public void processKnowledge(){
        if(knowledge.size() == 1) return;
        for(int i = 0; i < knowledge.size() - 1; i++){
            for(int j = i + 1; j < knowledge.size(); j++){
                if(knowledge.get(i).player == knowledge.get(j).player && knowledge.get(i).card == knowledge.get(j).card){
                    knowledge.get(i).has = knowledge.get(j).has;
                    knowledge.remove(j);
                    j--;
                }
            }
        }
    }
    public void addKnowledge(int player, char card, boolean has){
        knowledge.add(new knowledgeS(player, card, has));
        processKnowledge();
    }
    public int has(int card){
        for(int i = 0; i < knowledge.size(); i++){
            if(knowledge.get(i).card == card && knowledge.get(i).has && knowledge.get(i).player != pnumber) return i;
        }
        return -1;

    }
    public void decidePlayer(int[] player, char[] request){
        Scanner input = new Scanner(System.in);
        printCards();
        System.out.println("\nEnter a card to request and a player");
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
        for(knowledgeS that : knowledge){
            if(that.has && this.has(that.card) != -1){
                player[0] = that.player;
                request[0] = Card.rankSymbol(that.card);
                return;
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
        if(Math.random() >= probability){
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
    public static void requestMessage(int player, char card){
        System.out.println("Player requests " + card + " from player number " + player);
    }
    public ArrayList<Card> request(char request){
        ArrayList<Card> giveCards = new ArrayList<>();
        for(int i = 0; i < player.size(); i++){

            if(player.get(i).getSymbol() == request) giveCards.add(player.get(i));
            player.remove(i);
            i--;
        }
        if(giveCards.isEmpty()){
            return null;
        }
        System.out.println("Player gives: " + request);
        return giveCards;
    }
    public char removeFour(){
        for(int i = 0; i < player.size(); i++){
            if(i + 4 < player.size() && player.get(i).getSymbol() == player.get(i + 4).getSymbol()){
                addQuartet(player.get(i).getSymbol());
                player.subList(i, i + 5).clear();
                i--;
            }
        }
        return '0';
    }
}
public class GoFish{
    static ArrayList<Card> deck = new ArrayList<>();

    public static void main(String[] args){
        General.process(deck, 0);
        PlayerGF[] players = new PlayerGF[6];
        int counter = 0;
        for(int i = 0; i < 6; i++){
            PlayerGF temp = new PlayerGF(General.subArrayList(deck, counter, counter + 4), (i == 0), (int)(Math.random() * 15), i);
            players[i] = temp;
            counter += 4;
        }
        deck.subList(0, counter).clear();
        for(int i = 0;; i = (i + 1) % 6){
            System.out.println("Turn of player " + i);
            int[] playerR = new int[1];
            char[] request = new char[1];

            ArrayList<Card> add = new ArrayList<>();
            do{
                if(i == 0) players[i].decidePlayer(playerR, request);
                else players[i].decideAI(playerR, request);
                PlayerGF.requestMessage(playerR[0], request[0]);
                for(PlayerGF player : players){
                    player.addKnowledge(i, request[0], true);
                    player.addKnowledge(playerR[0], request[0], false);
                }
                ArrayList<Card> pot = players[playerR[0]].request(request[0]);
                if(pot == null){
                    System.out.println("Go fish!\n\n");
                    players[i].addCard(deck.get(0));
                    deck.remove(0);
                    players[i].addCards(add);
                    break;

                }
               add.addAll(pot);
            }while(true);
            for(PlayerGF player : players){
                player.forget();
            }
            char a = players[i].removeFour();
            if(a != '0') System.out.println("Player takes down " + a);
        }
    }
}
