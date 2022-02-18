/**
 * # Run complete. Total time: 00:08:05
 * <p>
 * REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
 * why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
 * experiments, perform baseline and negative tests that provide experimental control, make sure
 * the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
 * Do not assume the numbers tell you what you want them to tell.
 * <p>
 * NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
 * extra caution when trusting the results, look into the generated code to check the benchmark still
 * works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
 * different JVMs are already problematic, the performance difference caused by different Blackhole
 * modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.
 * <p>
 * Benchmark                                                 Mode  Cnt  Score   Error  Units
 * Jeka8833.Test.AnnotationTest.classWithAnnotation          avgt    5  3,651 ? 0,157  ns/op
 * Jeka8833.Test.AnnotationTest.classWithAnnotationNotNull   avgt    5  3,641 ? 0,144  ns/op
 * Jeka8833.Test.AnnotationTest.classWithoutAnnotation       avgt    5  3,647 ? 0,182  ns/op
 * Jeka8833.Test.AnnotationTest.recordWithAnnotation         avgt    5  3,609 ? 0,151  ns/op
 * Jeka8833.Test.AnnotationTest.recordWithAnnotationNotNull  avgt    5  3,624 ? 0,097  ns/op
 * Jeka8833.Test.AnnotationTest.recordWithoutAnnotation      avgt    5  3,613 ? 0,096  ns/op
 */
package com.Jeka8833.Test;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3)
public class AnnotationTest {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(AnnotationTest.class.getSimpleName())
                .forks(1)
                .build();
        new Runner(opt).run();
    }

    private int value;

    private ClassTest test1;
    private RecordTest test2;

    @Setup
    public void setup() {
        long[] values = new long[10_000 * 10_000];
        for (int i = 0; i < values.length; i++) {
            values[i] = i;
        }
        test1 = new ClassTest(values, 10_000, 10_000);
        test2 = new RecordTest(values, 10_000, 10_000);
    }

    @Benchmark
    public long classWithAnnotationNotNull() {
        long result = test1.withAnnotationNotNull(value % test1.width, value / test1.width);
        value++;
        if (value >= 10_000 * 10_000 - 1)
            value = 0;
        return result;
    }

    @Benchmark
    public long classWithAnnotation() {
        long result = test1.withAnnotation(value % test1.width, value / test1.width);
        value++;
        if (value >= 10_000 * 10_000 - 1)
            value = 0;
        return result;
    }

    @Benchmark
    public long classWithoutAnnotation() {
        long result = test1.withoutAnnotation(value % test1.width, value / test1.width);
        value++;
        if (value >= 10_000 * 10_000 - 1)
            value = 0;
        return result;
    }

    @Benchmark
    public long recordWithAnnotationNotNull() {
        long result = test2.withAnnotationNotNull(value % test2.width(), value / test2.width());
        value++;
        if (value >= 10_000 * 10_000 - 1)
            value = 0;
        return result;
    }

    @Benchmark
    public long recordWithAnnotation() {
        long result = test2.withAnnotation(value % test2.width(), value / test2.width());
        value++;
        if (value >= 10_000 * 10_000 - 1)
            value = 0;
        return result;
    }

    @Benchmark
    public long recordWithoutAnnotation() {
        long result = test2.withoutAnnotation(value % test2.width(), value / test2.width());
        value++;
        if (value >= 10_000 * 10_000 - 1)
            value = 0;
        return result;
    }

    private static class ClassTest {

        private final long[] values;
        private final int width;
        private final int height;

        private ClassTest(long[] values, int width, int height) {
            this.values = values;
            this.width = width;
            this.height = height;
        }

        @NotNull
        private Long withAnnotationNotNull(@Range(from = 0, to = Integer.MAX_VALUE) int x,
                                           @Range(from = 0, to = Integer.MAX_VALUE) int y) {
            return values[x + y * width];
        }

        private Long withAnnotation(@Range(from = 0, to = Integer.MAX_VALUE) int x,
                                    @Range(from = 0, to = Integer.MAX_VALUE) int y) {
            return values[x + y * width];
        }

        private Long withoutAnnotation(int x, int y) {
            return values[x + y * width];
        }
    }

    private record RecordTest(long[] values, int width, int height) {

        @NotNull
        private Long withAnnotationNotNull(@Range(from = 0, to = Integer.MAX_VALUE) int x,
                                           @Range(from = 0, to = Integer.MAX_VALUE) int y) {
            return values[x + y * width];
        }

        private Long withAnnotation(@Range(from = 0, to = Integer.MAX_VALUE) int x,
                                    @Range(from = 0, to = Integer.MAX_VALUE) int y) {
            return values[x + y * width];
        }


        private Long withoutAnnotation(int x, int y) {
            return values[x + y * width];
        }
    }


    /* Decompiled code
     *
     *   private static class ClassTest {
     *         private final long[] values;
     *         private final int width;
     *         private final int height;
     *
     *         private ClassTest(long[] values, int width, int height) {
     *             this.values = values;
     *             this.width = width;
     *             this.height = height;
     *         }
     *
     *         @NotNull
     *         private Long withAnnotationNotNull(@Range(from=0L, to=0x7FFFFFFFL) int x, @Range(from=0L, to=0x7FFFFFFFL) int y) {
     *             Long l = this.values[x + y * this.width];
     *             if (l == null) {
     *                 ClassTest.$$$reportNull$$$0(0);
     *             }
     *             return l;
     *         }
     *
     *         private Long withAnnotation(@Range(from=0L, to=0x7FFFFFFFL) int x, @Range(from=0L, to=0x7FFFFFFFL) int y) {
     *             return this.values[x + y * this.width];
     *         }
     *
     *         private Long withoutAnnotation(int x, int y) {
     *             return this.values[x + y * this.width];
     *         }
     *
     *         private static void $$$reportNull$$$0(int n) {
     *             throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "com/Jeka8833/Test/AnnotationTest$ClassTest", "withAnnotationNotNull"));
     *         }
     *     }
     *
     *     private record RecordTest(long[] values, int width, int height) {
     *         @NotNull
     *         private Long withAnnotationNotNull(@Range(from=0L, to=0x7FFFFFFFL) int x, @Range(from=0L, to=0x7FFFFFFFL) int y) {
     *             Long l = this.values[x + y * this.width];
     *             if (l == null) {
     *                 RecordTest.$$$reportNull$$$0(0);
     *             }
     *             return l;
     *         }
     *
     *         private Long withAnnotation(@Range(from=0L, to=0x7FFFFFFFL) int x, @Range(from=0L, to=0x7FFFFFFFL) int y) {
     *             return this.values[x + y * this.width];
     *         }
     *
     *         private Long withoutAnnotation(int x, int y) {
     *             return this.values[x + y * this.width];
     *         }
     *
     *         private static void $$$reportNull$$$0(int n) {
     *             throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", "com/Jeka8833/Test/AnnotationTest$RecordTest", "withAnnotationNotNull"));
     *         }
     *     }
     * */
}