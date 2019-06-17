package org.vincent;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.concurrent.TimeUnit;

/**
 * @author PengRong
 * @package org.vincent
 * @ClassName FirstBenchmark.java
 * @date 2019/6/15 - 0:45
 * @ProjectName demo
 * @Description: TODO
 */
@BenchmarkMode(value = Mode.AverageTime)
@OutputTimeUnit(value = TimeUnit.MICROSECONDS)
//@State(value = Scope.Thread)
@Timeout(timeUnit = TimeUnit.MINUTES, time = 2)/** 超时时间*/
@Threads(value = 8)/** 设置并发执行的线程数量*/
public class FirstBenchmark {

    @Benchmark
    public int sleepAWhile() {
        try {
            System.out.println("=================================== xxx ===================================");
            Thread.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
        return 0;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(FirstBenchmark.class.getSimpleName())
                .forks(1) // fork 1 表示只有一个副本去执行benchmark测试，跟线程不一样。如果需要两个副本 fork 2即可,这个时候每个benchmark 测试方法都执行两次
                .warmupIterations(5) // 表示 预热5次，每次预热持续1s ，另外还有一个默认的参数是每次预热持续时间 10s，在 10s中内不断调用benchmark 测试方法 ；然后根据模式采样测试数据; 持续时间可以通过Warmup注解time属性设置
                .warmupTime(new TimeValue(1, TimeUnit.SECONDS)) /** 设置持续时间 */
                .measurementIterations(5)// 表示 策略5次，每次测量周期持续2s，另外还有一个默认的参数是每次预热持续时间 10s, 在 10s中内不断调用benchmark 测试方法 ；然后根据模式采样测试数据; 持续时间可以通过Measurement 注解time属性设置
                .measurementTime(new TimeValue(2, TimeUnit.SECONDS))
                .build();

        new Runner(opt).run();
    }
}
