package cz.profinit.training.parallel.computation;

import cz.profinit.training.parallel.algorithm.ParallelUtils;
import cz.profinit.training.parallel.algorithm.PrimeCandidate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamComputation {

    public static void main(String[] args) throws Exception {
        List<PrimeCandidate> primes = ParallelUtils.withStopWatch(() -> {
            Map<Integer, PrimeCandidate> primeMap = new ForkJoinPool(4)
                    .submit(() -> IntStream.range(1, 101).boxed()
                            .parallel()
                            .map(number -> new PrimeCandidate(number, ParallelUtils.isPrime(number)))
                            .filter(PrimeCandidate::isPrime)
                            .collect(Collectors.toMap(PrimeCandidate::getNumber, primeCandidate -> primeCandidate)))
                    .get();

            ArrayList<PrimeCandidate> list = new ArrayList<>(primeMap.values());
            list.sort(Comparator.comparingInt(PrimeCandidate::getNumber));
            return list;
        });

        System.out.println("Prime#: " + primes.size());
    }
}
