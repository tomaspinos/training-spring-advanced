package cz.profinit.training.parallel.algorithm;

public class PrimeCandidate {

    private final int number;
    private final boolean prime;

    public PrimeCandidate(int number, boolean prime) {
        this.number = number;
        this.prime = prime;
    }

    public int getNumber() {
        return number;
    }

    public boolean isPrime() {
        return prime;
    }

    @Override
    public String toString() {
        return "PrimeCandidate{" +
                "number=" + number +
                ", prime=" + prime +
                '}';
    }
}
