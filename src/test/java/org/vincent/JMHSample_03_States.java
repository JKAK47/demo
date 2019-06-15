package org.vincent;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * @author PengRong
 * @package org.vincent
 * @ClassName JMHSample_03_States.java
 * @date 2019/6/15 - 1:46
 * @ProjectName demo
 * @Description: TODO
 */

public class JMHSample_03_States {
    /*

     * Most of the time, you need to maintain some state while the benchmark is

     * running. Since JMH is heavily used to build concurrent benchmarks, we

     * opted for an explicit notion of state-bearing objects.

     *

     * Below are two state objects. Their class names are not essential, it

     * matters they are marked with @State. These objects will be instantiated

     * on demand, and reused during the entire benchmark trial.

     *

     * The important property is that state is always instantiated by one of

     * those benchmark threads which will then have the access to that state.

     * That means you can initialize the fields as if you do that in worker

     * threads (ThreadLocals are yours, etc).

     */


    @State(Scope.Benchmark)
    public static class BenchmarkState {
        public static  String bench="BenchmarkState";
        volatile double x = Math.PI;
    }


    @State(Scope.Thread)
    public static class ThreadState {
        public static  String bench="ThreadState";
        volatile double x = Math.PI;
    }


    /*

     * Benchmark methods can reference the states, and JMH will inject the

     * appropriate states while calling these methods. You can have no states at

     * all, or have only one state, or have multiple states referenced. This

     * makes building multi-threaded benchmark a breeze.

     *

     * For this exercise, we have two methods.

     */


    @Benchmark
    public void measureUnshared(ThreadState state) {

        // All benchmark threads will call in this method.

        //

        // However, since ThreadState is the Scope.Thread, each thread

        // will have it's own copy of the state, and this benchmark

        // will measure unshared case.

        System.out.println(ThreadState.bench+" : " +Thread.currentThread().getName()+" : "+state.x++);

    }


    @Benchmark
    public void measureShared(BenchmarkState state) {
        // All benchmark threads will call in this method.
        // Since BenchmarkState is the Scope.Benchmark, all threads
        // will share the state instance, and we will end up measuring
        // shared case.
        System.out.println(BenchmarkState.bench+" : " +Thread.currentThread().getName()+" : "+state.x++);
    }

    /*
     * ============================== HOW TO RUN THIS TEST: ====================================
     *
     * You are expected to see the drastic difference in shared and unshared cases,
     * because you either contend for single memory location, or not. This effect
     * is more articulated on large machines.
     *
     * You can run this test:
     *
     * a) Via the command line:
     *    $ mvn clean install
     *    $ java -jar target/benchmarks.jar JMHSample_03 -wi 5 -i 5 -t 4 -f 1
     *    (we requested 5 measurement/warmup iterations, with 4 threads, single fork)
     * b) Via the Java API:
     *    (see the JMH homepage for possible caveats when running from IDE:
     *      http://openjdk.java.net/projects/code-tools/jmh/)
     */
    public static void main(String[] args) throws RunnerException {

        Options opt = new OptionsBuilder()
                .include(JMHSample_03_States.class.getSimpleName())
                .warmupIterations(1)
                .warmupTime(new TimeValue(1, TimeUnit.MILLISECONDS))
                .measurementIterations(1)
                .measurementTime(new TimeValue(1, TimeUnit.MILLISECONDS))
                .threads(4) /** 创建四个线程测试*/

                .forks(1)
                 .output("file.txt")
                .build();
        new Runner(opt).run();

    }
}
