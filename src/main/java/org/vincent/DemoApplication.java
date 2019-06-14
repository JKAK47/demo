package org.vincent;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.vincent.handler.AbstractHandlerChain;
import org.vincent.handler.SalesHandlerChain;

@SpringBootApplication
public class DemoApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("begin ");
        AbstractHandlerChain chain = SalesHandlerChain.builder().build();
        chain.doHandler(200);
        chain.doHandler(20);
        chain.doHandler(200000);
    }
}
