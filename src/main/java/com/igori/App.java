package com.igori;

import com.igori.web.AkkaHttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class App {

  @Autowired
  private AkkaHttpServer akkaHttpServer;

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(App.class, args);
  }
}
