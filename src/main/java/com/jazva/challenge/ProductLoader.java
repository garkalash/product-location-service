package com.jazva.challenge;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Loads stored objects from the file system and builds up
 * the appropriate objects to add to the data source.
 *
 */
@Component
public class ProductLoader implements InitializingBean {
    private static final String INSERT_PRODUCT_QUERY = "INSERT INTO product(id, name) VALUES (?, ?)";

    @Value("classpath:data/products.txt")
    private Resource products;

    @Autowired
    DataSource dataSource;

    /**
     * Load the products into the data source after
     * the application is ready.
     *
     * @throws Exception In case something goes wrong while we load
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(products.getInputStream()))) {
            String line;
            PreparedStatement prpdStmnt;
            try (Connection connection = dataSource.getConnection()) {
                prpdStmnt = connection.prepareStatement(INSERT_PRODUCT_QUERY);
                while ((line = br.readLine()) != null) {
                    int id = Integer.parseInt(line.split(" ")[0]);
                    String name = Arrays.stream(line.split(" ")).skip(1).collect(Collectors.joining(" "));
                    prpdStmnt.setInt(1, id);
                    prpdStmnt.setString(2, name);
                    prpdStmnt.addBatch();
                }

                prpdStmnt.executeBatch();
            }
        }
    }

}
