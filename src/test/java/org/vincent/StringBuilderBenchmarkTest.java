package org.vincent;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.vincent.benchmark.StringBuilderBenchmark;

/**
 * @author PengRong
 * @package org.vincent
 * @ClassName StringBuilderBenchmark.java
 * @date 2019/6/15 - 0:16
 * @ProjectName demo
 * @Description: TODO
 */
public class StringBuilderBenchmarkTest {
    public static void main(String[] args) throws RunnerException {
        Options options =new OptionsBuilder().include(StringBuilderBenchmark.class.getSimpleName())
                                             .resultFormat(ResultFormatType.JSON).result("file.json")
                                             .build();
        new Runner(options).run();
    }
}
