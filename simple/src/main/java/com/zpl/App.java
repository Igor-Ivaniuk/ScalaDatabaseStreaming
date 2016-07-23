package com.zpl;

import com.zpl.web.ReviewHttpServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by IgorIvaniuk on 07.04.2016.
 */

@SpringBootApplication
public class App {

    @Autowired
    private ReviewHttpServer reviewHttpServer;

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(App.class, args);
        //reviewHttpServer.serve();
    }
}
