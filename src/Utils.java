import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

class Utils {
    static void writeKey(String filename, BigInteger value) throws IOException {
        FileOutputStream fos = new FileOutputStream(new File(filename));
        fos.write(value.toString().getBytes());
    }

    static BigInteger modPow(BigInteger value, BigInteger exponent, BigInteger m) {
        if (exponent.equals(BigInteger.ZERO))
            return BigInteger.ONE;

        BigInteger z = modPow(value, exponent.shiftRight(1), m);
        BigInteger zPowTwo = z.pow(2).mod(m);
        if (exponent.remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
            return zPowTwo;
        return zPowTwo.multiply(value).mod(m);
    }

    static BigInteger generatePrime (int bitLength){
        BigInteger prime;
        Random r = new Random();
        do{
            prime = new BigInteger(bitLength, r);
        } while (!millerRabinTest(prime, 5));
        return prime;
    }

    static boolean millerRabinTest (BigInteger n, int iteration){
        if (n.equals(BigInteger.valueOf(2)) || n.equals(BigInteger.valueOf(3))){
            return true;
        }
        if (n.compareTo(BigInteger.valueOf(2)) < 0 || n.remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
            return false;
        }
        BigInteger t = n.subtract(BigInteger.ONE);
        int s = 0;
        while (n.remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO)){
            t = t.divide(BigInteger.valueOf(2));
            s++;
        }

        for (int i = 0; i < iteration; i++) {
            BigInteger a;
            do{
                a = new BigInteger(n.bitLength(), new Random());
            }while (a.compareTo(BigInteger.valueOf(2)) < 0 || a.compareTo(n.subtract(BigInteger.valueOf(2))) >= 0);
            BigInteger x = a.modPow(t, n);
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))){
                continue;
            }
            for (int r = 1; r < s; r++) {
                x = x.modPow(BigInteger.valueOf(2), n);
                if (x.equals(BigInteger.ONE)){
                    return false;
                }
                if (x.equals(n.subtract(BigInteger.ONE))){
                    break;
                }
            }
            if (!x.equals(n.subtract(BigInteger.ONE))){
                return false;
            }
        }
        return true;
    }

    static BigInteger getPRoot(BigInteger p){
        ArrayList<BigInteger> fact = new ArrayList<>();
        BigInteger phi = p.subtract(BigInteger.ONE);
        BigInteger i = BigInteger.valueOf(2);
        fact.add(i);
        fact.add(phi.divide(BigInteger.valueOf(2)));
        for (; i.compareTo(p) < 0; i = i.add(BigInteger.ONE)) {
            boolean ok = true;
            for (BigInteger value : fact) {
                ok = ok && !(i.modPow(phi.divide(value), p).equals(BigInteger.ONE));
            }
            if (ok){
                return i;
            }
        }
        return BigInteger.ZERO;
    }

    static BigInteger generateP(int length){
        BigInteger q;
        BigInteger p;
        do{
            q = Utils.generatePrime(length - 1);
            p = q.multiply(BigInteger.valueOf(2)).add(BigInteger.ONE);
        }while (!Utils.millerRabinTest(p, 5));
        return p;
    }

    static BigInteger getPRootOld(BigInteger p){
        ArrayList<BigInteger> fact = new ArrayList<>();
        BigInteger phi = p.subtract(BigInteger.ONE);
        BigInteger n = new BigInteger(String.valueOf(phi));
        BigInteger i = BigInteger.valueOf(2);
        for (; i.pow(2).compareTo(n) < 0; i = i.add(BigInteger.ONE)) {
            if (millerRabinTest(n, 5)){
                break;
            }
            if (n.mod(i).equals(BigInteger.ZERO)){
                fact.add(i);
                while (n.mod(i).equals(BigInteger.ZERO)){
                    n = n.divide(i);
                }
            }
        }
        if (n.compareTo(BigInteger.ONE) > 0){
            fact.add(n);
        }
        for (; i.compareTo(p) < 0; i = i.add(BigInteger.ONE)) {
            boolean ok = true;
            for (int j = 0; j < fact.size(); j++) {
                ok = ok && !(i.modPow(phi.divide(fact.get(j)), p).equals(BigInteger.ONE));
            }
            if (ok){
                return i;
            }
        }
        return BigInteger.ZERO;
    }
}
