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
@Warmup(iterations = 1)
@Measurement(iterations = 2)
public class LinkedListBench {

    @Param({"100", "1000000"})
    private int N;

    private List<String> DATA__FOR__TESTING;
    private LinkedList<String> DATA__FOR__TESTING__LINK;

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LinkedListBench.class.getSimpleName())
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
    public void loopLinkStreamForEachStatic(Blackhole bh) {
        DATA__FOR__TESTING__LINK.stream().forEach(bh::consume);
    }

    @Benchmark
    public void loopLinkForEach(Blackhole bh) {
        for (String s : DATA__FOR__TESTING__LINK)
            bh.consume(s);
    }

    @Benchmark
    public void loopLinkIterator(Blackhole bh){
        Iterator<String> a = DATA__FOR__TESTING__LINK.iterator();
        while (a.hasNext()){
            bh.consume(a.next());
        }
    }

    private List<String> createData() {
        DATA__FOR__TESTING__LINK = new LinkedList<>();
        List<String> data = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < N; i++) {
            final String dd = "Number : " + r.nextInt();
            data.add(dd);
            DATA__FOR__TESTING__LINK.add(dd);
        }
        return data;
    }

}