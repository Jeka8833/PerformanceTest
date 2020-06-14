package com.Jeka8833.Test;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Thread)
@Fork(value = 2)
@Warmup(iterations = 3)
@Measurement(iterations = 3)
public class ArrVsListBench {

    @Param({"20", "100", "10000000"})
    private int N;

    private List<String> DATA__FOR__TESTING;
    private String[] DATA__FOR__TESTING_ARRAY;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(ArrVsListBench.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    @Setup
    public void setup() {
        DATA__FOR__TESTING = createData();
    }

    @Benchmark
    public void loopStreamForEachStatic(Blackhole bh) {
        DATA__FOR__TESTING.stream().forEach(bh::consume);
    }

    @Benchmark
    public void loopFor(Blackhole bh) {
        for (int i = 0; i < DATA__FOR__TESTING.size(); i++)
            bh.consume(DATA__FOR__TESTING.get(i));
    }

    @Benchmark
    public void loopForInVar(Blackhole bh) {
        final int len = DATA__FOR__TESTING.size();
        for (int i = 0; i < len; i++)
            bh.consume(DATA__FOR__TESTING.get(i));
    }

    @Benchmark
    public void loopForEach(Blackhole bh) {
        for (String s : DATA__FOR__TESTING)
            bh.consume(s);
    }

    @Benchmark
    public void loopIterator(Blackhole bh){
       Iterator<String> a = DATA__FOR__TESTING.iterator();
       while (a.hasNext()){
           bh.consume(a.next());
       }
    }

    @Benchmark
    public void ArrayloopStreamForEachStatic(Blackhole bh) {
        Arrays.stream(DATA__FOR__TESTING_ARRAY).forEach(bh::consume);
    }

    @Benchmark
    public void ArrayloopFor(Blackhole bh) {
        for (int i = 0; i < DATA__FOR__TESTING_ARRAY.length; i++)
            bh.consume(DATA__FOR__TESTING_ARRAY[i]);
    }

    @Benchmark
    public void ArrayloopForInVar(Blackhole bh) {
        final int len = DATA__FOR__TESTING_ARRAY.length;
        for (int i = 0; i < len; i++)
            bh.consume(DATA__FOR__TESTING_ARRAY[i]);
    }

    @Benchmark
    public void ArrayloopForEach(Blackhole bh) {
        for (String s : DATA__FOR__TESTING_ARRAY)
            bh.consume(s);
    }

    private List<String> createData() {
        DATA__FOR__TESTING_ARRAY = new String[N];
        List<String> data = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            final String dd = "Number : " + r.nextInt();
            data.add(dd);
            DATA__FOR__TESTING_ARRAY[i] = dd;
        }
        return data;
    }

}