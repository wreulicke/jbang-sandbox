//JAVA 19
//DEPS org.openjdk.jcstress:jcstress-core:0.16

package com.github.wreulicke.test;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.L_Result;

import static org.openjdk.jcstress.annotations.Expect.*;

@JCStressTest
@Outcome(id = "null",                   expect = ACCEPTABLE, desc = "Not seeing the object yet")
@Outcome(id = "class java.lang.Object", expect = ACCEPTABLE, desc = "Seeing the object, valid class")
@Outcome(                               expect = FORBIDDEN,  desc = "Other cases are illegal")
@State
public class TestJcstress {

  Object o;

  @Actor
  public void writer() {
      o = new Object();
  }

  @Actor
  public void reader(L_Result r) {
      Object lo = o;
      if (lo != null) {
          try {
              r.r1 = lo.getClass();
          } catch (NullPointerException npe) {
              r.r1 = npe;
          }
      } else {
          r.r1 = null;
      }
  }

    public static void main(String[] args) throws Exception {
        org.openjdk.jcstress.Main.main(args);
    }
}
