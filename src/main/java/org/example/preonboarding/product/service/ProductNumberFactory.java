package org.example.preonboarding.product.service;

import lombok.RequiredArgsConstructor;
import org.example.preonboarding.product.repository.ProductRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class ProductNumberFactory {
    private final ProductRepository productRepository;
    private static final int PRODUCT_NUMBER_LENGTH = 10;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new SecureRandom();

    public String createNextProductNumber() {
        String productNumber;
        Set<String> existingProductNumbers = new HashSet<>(productRepository.findAllProductNumbers());

        do {
            productNumber = generateRandomProductNumber();
        } while (existingProductNumbers.contains(productNumber));

        return productNumber;
    }

    private String generateRandomProductNumber() {
        StringBuilder productNumber = new StringBuilder(PRODUCT_NUMBER_LENGTH);
        for (int i = 0; i < PRODUCT_NUMBER_LENGTH; i++) {
            productNumber.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return productNumber.toString();
    }
}
