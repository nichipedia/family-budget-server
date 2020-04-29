package tech.housemoran.realgood.application;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.housemoran.realgood.application.datalayer.api.ReceiptRepository;
import tech.housemoran.realgood.application.models.Receipt;

import java.util.Date;

@SpringBootApplication
public class FamilyBudgetServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(FamilyBudgetServiceApplication.class);

    @Autowired
    private ReceiptRepository repo;

    public static void main(String[] args) {
        SpringApplication.run(FamilyBudgetServiceApplication.class, args);
    }

}
