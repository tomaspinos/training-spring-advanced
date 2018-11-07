package cz.profinit.training.parallel.computation;

import cz.profinit.training.parallel.algorithm.ParallelUtils;
import cz.profinit.training.parallel.algorithm.PrimeCandidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BareThreadComputation {

    public static void main(String[] args) throws Exception {
        List<PrimeCandidate> primes = ParallelUtils.withStopWatch(() -> {
            Map<Integer, PrimeCandidate> map = new HashMap<>();

            List<Thread> threads = IntStream.range(1, 101).boxed()
                    .map(number ->
                            new Thread(() -> map.put(number, new PrimeCandidate(number, ParallelUtils.isPrime(number)))))
                    .collect(Collectors.toList());

            threads.forEach(Thread::start);

            threads.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            return map.values().stream()
                    .filter(PrimeCandidate::isPrime)
                    .collect(Collectors.toList());
        });

        System.out.println("Prime#: " + primes.size());
    }
}
