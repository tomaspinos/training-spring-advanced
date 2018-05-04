package cz.profinit.training.parallel.computation;

import cz.profinit.training.parallel.algorithm.ParallelUtils;
import cz.profinit.training.parallel.algorithm.PrimeCandidate;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ParallelStreamComputation {

    public static void main(String[] args) throws Exception {
        List<PrimeCandidate> primes = ParallelUtils.withStopWatch(() ->
                new ForkJoinPool(4)
                        .submit(() -> IntStream.range(1, 101).boxed()
                                .parallel()
                                .map(number -> new PrimeCandidate(number, ParallelUtils.isPrime(number)))
                                .filter(PrimeCandidate::isPrime)
                                .collect(Collectors.toList()))
                        .get());

        System.out.println("Prime#: " + primes.size());
    }
}
