package org.vincent;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.vincent.service.Calculator;
import org.vincent.service.impl.MultithreadCalculator;
import org.vincent.service.impl.SinglethreadCalculator;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @Package org.vincent
 * @ClassName SecondBenchmark.java
 * @date 2019/6/14 - 18:46
 * @description : 该JMH 基准测试 有两个方法，每个方法是一个 Benchmark 注解测试方法
 * Created by PengRong .
 */
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Benchmark)
@Warmup(iterations = 10,time = 2,timeUnit = TimeUnit.SECONDS) /** 预热迭代 1一次，每次 迭代时间持续 2s */
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS) /** 测量迭代 10次，每次测量迭代持续时间 5s */
public class SecondBenchmark {
    @Param({"10000","100000","1000000"})
    private int length;

    private int[] numbers;
    private Calculator singleThreadCalc;
    private Calculator multiThreadCalc;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(SecondBenchmark.class.getSimpleName())
                .forks(1)  /** 创建两个线程，每个线程跑一个完整的JMH 测试流程 */
               //.warmupIterations(1) /** 设置预热次数; 不设置默认是 5次预热  */
                //.measurementIterations(5)/** 设置测量迭代次数 */

                .resultFormat(ResultFormatType.JSON) /** 设置输出数据格式*/
                .result("file.json")/** 设置 输出json 文件 然后上传 http://jmh.morethan.io/ 可视化 基准测试 */
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public long singleThreadBench() {
        return singleThreadCalc.sum(numbers);
    }

    @Benchmark
    public long multiThreadBench() {
        return multiThreadCalc.sum(numbers);
    }

    /**
     * @Setup  注解时 在JMH 测试 之前进行初始化
     */
    @Setup
    public void prepare() {
        numbers = IntStream.rangeClosed(1, length).toArray();
        singleThreadCalc = new SinglethreadCalculator();
        multiThreadCalc = new MultithreadCalculator(Runtime.getRuntime().availableProcessors());
    }

    /**
     * @TearDown 注解时 在JMH 测试 之后进行回收资源
     */
    @TearDown
    public void shutdown() {
        singleThreadCalc.shutdown();
        multiThreadCalc.shutdown();
    }
}
