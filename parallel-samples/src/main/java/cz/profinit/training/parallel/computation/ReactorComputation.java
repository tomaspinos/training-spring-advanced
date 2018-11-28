package cz.profinit.training.parallel.computation;

import cz.profinit.training.parallel.algorithm.ParallelUtils;
import cz.profinit.training.parallel.algorithm.PrimeCandidate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;

import static cz.profinit.training.parallel.algorithm.ParallelUtils.withStopWatch;

public class ReactorComputation {

    public static void main(String[] args) throws Exception {
        Scheduler scheduler = Schedulers.newParallel("primes", 4);

        List<PrimeCandidate> primes = withStopWatch(() ->
                Flux.range(1, 100)
                        .flatMap(i ->
                                Mono.fromCallable(() -> ParallelUtils.isPrime(i)).map(p -> new PrimeCandidate(i, p))
                                .subscribeOn(scheduler))
                        .filter(PrimeCandidate::isPrime)
                        .collectList()
                        .block());

        scheduler.dispose();

        System.out.println("Prime#: " + primes.size());
    }
}