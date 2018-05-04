package cz.profinit.training.parallel.computation;

import cz.profinit.training.parallel.algorithm.ParallelUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BareThreadComputation {

    public static void main(String[] args) throws Exception {
        Set<Integer> primes = ParallelUtils.withStopWatch(() -> {
            Set<Integer> set = new HashSet<>();

            List<Thread> threads = IntStream.range(1, 101).boxed()
                    .map(number ->
                            new Thread(() -> {
                                if (ParallelUtils.isPrime(number)) {
                                    set.add(number);
                                }
                            }))
                    .collect(Collectors.toList());

            threads.forEach(Thread::start);

            threads.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            return set;
        });

        System.out.println("Prime#: " + primes.size());
    }
}
