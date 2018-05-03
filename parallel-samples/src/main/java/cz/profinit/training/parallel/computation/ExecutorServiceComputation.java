package cz.profinit.training.parallel.computation;

import cz.profinit.training.parallel.algorithm.ParallelUtils;
import cz.profinit.training.parallel.algorithm.PrimeCandidate;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutorServiceComputation {

    public static void main(String[] args) throws Exception {
        List<PrimeCandidate> primes = ParallelUtils.withStopWatch(() -> {
            ExecutorService executorService = Executors.newFixedThreadPool(4);

            List<Callable<PrimeCandidate>> callables = IntStream.range(1, 101).boxed()
                    .map(number -> (Callable<PrimeCandidate>) () -> new PrimeCandidate(number, ParallelUtils.isPrime(number)))
                    .collect(Collectors.toList());

            List<Future<PrimeCandidate>> futures = executorService.invokeAll(callables);

            executorService.shutdown();

            return futures.stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return new PrimeCandidate(0, false);
                        }
                    })
                    .filter(PrimeCandidate::isPrime)
                    .collect(Collectors.toList());
        });

        System.out.println("Prime#: " + primes.size());
    }
}
