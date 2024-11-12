//JAVA 19
//DEPS io.gatling:gatling-core-java:3.12.0 io.gatling:gatling-http-java:3.12.0 io.gatling:gatling-app:3.12.0

// あんま使い物にならないけど試したやつ
// ログの設定とか絞らないとしんどい

package com.github.wreulicke.test;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.app.Gatling;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class TestGatling extends Simulation {

  HttpProtocolBuilder httpProtocol =
    http.baseUrl("https://computer-database.gatling.io")
      .acceptHeader("application/json")
      .contentTypeHeader("application/json");

  ScenarioBuilder myFirstScenario = scenario("My First Scenario")
    .exec(http("Request 1")
      .get("/computers/"));

  {
    setUp(
      myFirstScenario.injectOpen(constantUsersPerSec(2).during(60))
    ).protocols(httpProtocol);
  }

  public static void main(String[] args) {
    Gatling.main(args);
  }
}