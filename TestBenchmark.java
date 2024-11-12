//JAVA 19
//DEPS org.openjdk.jmh:jmh-generator-annprocess:1.36

package com.github.wreulicke.test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Level;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Warmup(iterations = 10, time = 100, timeUnit = TimeUnit.MILLISECONDS)
public class TestBenchmark {

  private final ExecutorService cachedExecutor = Executors.newCachedThreadPool();
  private final ExecutorService fixedExecutor = Executors.newFixedThreadPool(100);

  @Benchmark
  public void benchmarkCachedExecutor(Blackhole blackhole) throws InterruptedException, ExecutionException {
    blackhole.consume(cachedExecutor.submit(() -> "").get());    
  }

  @Benchmark
  public void benchmarkFixedExecutor(Blackhole blackhole) throws InterruptedException, ExecutionException {
    blackhole.consume(fixedExecutor.submit(() -> "").get());
  }

  @Benchmark
  public void benchmarkStringRepeat(Blackhole blackhole) {
    blackhole.consume("test".repeat(100000));
  }

  @TearDown(Level.Trial)
  public void tearDown() {
    cachedExecutor.shutdown();
    fixedExecutor.shutdown();
  }

  public static void main(String[] args) throws Exception {
     org.openjdk.jmh.Main.main(args);
  }
}