package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.*;
import io.vertx.ext.web.*;
import com.example.starter.User;
import java.util.*;
import io.vertx.core.json.*;
import io.vertx.ext.web.handler.*;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.ResultSet;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.*;
import io.vertx.ext.sql.*;

public class MainVerticle extends AbstractVerticle {

  // Store our readingList
  private Map readingList = new LinkedHashMap();
  // Create a readingList
  private void createSomeData() {
      User user1 = new User(
          "Pramuditha",
          "15000");
      readingList.put(user1.getId(), user1);

      User user2 = new User(
          "Achini",
          "25000");
      readingList.put(user2.getId(), user2);
  };

  private void getAll(RoutingContext rc) {
    rc.response()
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(readingList.values()));
  }

  private void getOne(RoutingContext rc) {
    String id = rc.request().getParam("id");
    Integer idAsInteger = Integer.valueOf(id);

    rc.response()
      .end(Json.encodePrettily(readingList.get(idAsInteger)));
  }

  private void addOne(RoutingContext rc) {
    User user = rc.getBodyAsJson().mapTo(User.class);
    readingList.put(user.getId(), user);
    rc.response()
        .setStatusCode(201)
        .putHeader("content-type", "application/json; charset=utf-8")
        .end(Json.encodePrettily(user));
  }

  private void deleteOne(RoutingContext rc) {
    String id = rc.request().getParam("id");
    try {
        Integer idAsInteger = Integer.valueOf(id);
        readingList.remove(idAsInteger);
        rc.response().setStatusCode(204).end();
    } catch (NumberFormatException e) {
        rc.response().setStatusCode(400).end();
    }
  }

  private void updateOne(RoutingContext rc){

    String id = rc.request().getParam("id");
    Integer idAsInteger = Integer.valueOf(id);

    User user = rc.getBodyAsJson().mapTo(User.class);

    try {
      readingList.put(idAsInteger, user);
      rc.response().end(Json.encodePrettily(readingList.values()));
    } catch (NumberFormatException e) {
      rc.response().setStatusCode(400).end();
    }
  }

  @Override
  public void start(Promise<Void> startPromise) throws Exception {

    HttpServer httpServer = vertx.createHttpServer();

    Router router = Router.router(vertx);

    httpServer.requestHandler(router::accept)
              .listen(8888);

    // MySQLConnectOptions connectOptions = new MySQLConnectOptions()
    //   .setPort(3306)
    //   .setHost("the-host")
    //   .setDatabase("the-db")
    //   .setUser("user")
    //   .setPassword("secret");

    // // Pool options
    // PoolOptions poolOptions = new PoolOptions()
    //   .setMaxSize(5);
    // // Create the pooled client
    // MySQLPool client = MySQLPool.pool(vertx, connectOptions, poolOptions);

    // // Get a connection from the pool
    // client.getConnection(ar1 -> {

    //   if (ar1.succeeded()) {

    //     System.out.println("Connected");

    //     // Obtain our connection
    //     SqlConnection conn = ar1.result();

    //     // All operations execute on the same connection
    //     conn.query("SELECT * FROM users WHERE id='julien'", ar2 -> {
    //       if (ar2.succeeded()) {
    //         conn.query("SELECT * FROM users WHERE id='emad'", ar3 -> {
    //           // Release the connection to the pool
    //           conn.close();
    //         });
    //       } else {
    //         // Release the connection to the pool
    //         conn.close();
    //       }
    //     });
    //   } else {
    //     System.out.println("Could not connect: " + ar1.cause().getMessage());
    //   }
    // });

    router.get("/pk/:name")
          .handler(routingContext -> {
            String name = routingContext.request().getParam("name");
            HttpServerResponse response = routingContext.response();
            response.putHeader("contex-type", "text/plain");
            response.setChunked(true);
            response.write("Hi "+ name);
            response.end();
          });

    router.post("/pk")
          .handler(routingContext -> {
            System.out.println("Ho Hoooo");
            HttpServerResponse response = routingContext.response();
            response.putHeader("contex-type", "text/plain");
            response.end("Hi Achini");
          });

    createSomeData();

    //Get All Records
    router.get("/api/users").handler(this::getAll);

    //Get One Record
    router.get("/api/users/:id").handler(this::getOne);

    //Add New Record
    router.route("/api/users*").handler(BodyHandler.create());
    router.post("/api/users").handler(this::addOne);

    //Delete Record
    router.delete("/api/users/:id").handler(this::deleteOne);

    //Update Record
    router.post("/api/users/:id").handler(this::updateOne);
   }
}
