import java.math.BigInteger;

class MiddleMan extends Abonent {
    private BigInteger K1;

    MiddleMan(BigInteger g, BigInteger p){
        super(g, p);
    }

    void setB1(BigInteger b){
        //K1 = b.modPow(a, p);
        K1 = Utils.modPow(b, a, p);
    }

    BigInteger getK1(){
        return K1;
    }
}
