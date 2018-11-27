package cz.profinit.training.parallel.algorithm;

import org.springframework.util.StopWatch;

import java.math.BigInteger;
import java.util.concurrent.Callable;

public class ParallelUtils {

    public static <T> T withStopWatch(Callable<T> task) throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        T result = task.call();

        stopWatch.stop();
        System.out.println("Time: " + stopWatch.getTotalTimeMillis());

        return result;
    }

    public  static boolean isPrime(int number) {
        slowDown();
        return BigInteger.valueOf(number).isProbablePrime(100);
    }

    private static void slowDown() {
        try {
            Thread.sleep(50L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
