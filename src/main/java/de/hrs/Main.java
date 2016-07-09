package de.hrs;

import de.hrs.model.Eurusd;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by hrs on 18.06.16.
 */
@SpringBootApplication
public class Main {
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc-config.xml");
        context.getBean(FetchEurUsd.class).start();
    }
}
