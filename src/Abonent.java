import java.math.BigInteger;

class Abonent {
    BigInteger a;
    private BigInteger g;
    private BigInteger K;
    BigInteger p;
    Abonent(BigInteger g, BigInteger p){
        this.p = p;
        this.g = g;
        a = Utils.generatePrime(50);
    }

    BigInteger getPublicKey(){
        return Utils.modPow(g, a, p);
    }

    void setB(BigInteger b){
        this.K = Utils.modPow(b, a, p);
    }

    BigInteger getK(){
        return K;
    }
}
