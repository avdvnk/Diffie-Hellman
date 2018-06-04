import java.io.IOException;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws IOException {
        BigInteger p = Utils.generateP(256);
        BigInteger g = Utils.getPRoot(p);
        Abonent abonent1 = new Abonent(g, p);
        Abonent abonent2 = new Abonent(g, p);
        abonent1.setB(abonent2.getPublicKey());
        abonent2.setB(abonent1.getPublicKey());
        Utils.writeKey("abonent1.txt", abonent1.getK());
        Utils.writeKey("abonent2.txt", abonent2.getK());
        System.out.println("Abonent1: " + abonent1.getK());
        System.out.println("Abonent2: " + abonent2.getK() + "\n");

        //man in the middle
        Abonent abonent3 = new Abonent(g, p);
        Abonent abonent4 = new Abonent(g, p);
        MiddleMan middleMan = new MiddleMan(g, p);
        abonent4.setB(middleMan.getPublicKey());
        abonent3.setB(middleMan.getPublicKey());
        middleMan.setB(abonent3.getPublicKey());
        middleMan.setB1(abonent4.getPublicKey());
        Utils.writeKey("abonent3.txt", abonent3.getK());
        Utils.writeKey("abonent4.txt", abonent4.getK());
        Utils.writeKey("middleManToAbonent3.txt", middleMan.getK());
        Utils.writeKey("middleManToAbonent4.txt", middleMan.getK1());

        System.out.println("Abonent3: " + abonent3.getK());
        System.out.println("MiddleMan to Abonent3: " + middleMan.getK());
        System.out.println("\nAbonent4: " + abonent4.getK());
        System.out.println("MiddleMan to Abonent4: " + middleMan.getK1());
    }
}
