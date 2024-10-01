package com.receiptprocessor.receipt_processor.service;

import com.receiptprocessor.receipt_processor.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ReceiptService {
    private final Map<String, Receipt> receipts = new HashMap<>();
    private final Map<String, Integer> pointsMap = new HashMap<>();

    public String processReceipt(Receipt receipt) {
        String receiptId = UUID.randomUUID().toString();
        receipts.put(receiptId, receipt);
        int points = calculatePoints(receipt);
        pointsMap.put(receiptId, points);
        return receiptId;
    }

    public int getPoints(String receiptId) {
        return pointsMap.getOrDefault(receiptId, 0);
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // 1. Points for alphanumeric characters in retailer name
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // 2. Points for round dollar total
        BigDecimal total = new BigDecimal(receipt.getTotal());
        if (total.scale() == 0) {
            points += 50;
        }

        // 3. Points for multiples of 0.25 in total
        if (new BigDecimal(receipt.getTotal()).remainder(BigDecimal.valueOf(0.25)).compareTo(BigDecimal.ZERO) == 0) {
            points += 25;
        }

        // 4. Points for every two items
        points += (receipt.getItems().size() / 2) * 5;

        // 5. Points for item descriptions and odd day check
        points += receipt.getItems().stream().mapToInt(item -> {
            String desc = item.getShortDescription().trim();
            if (desc.length() % 3 == 0) {
                return new BigDecimal(item.getPrice()).multiply(BigDecimal.valueOf(0.2)).setScale(0, RoundingMode.UP).intValue();
            }
            return 0;
        }).sum();

        // 6. Points for odd day
        LocalDate purchaseDate = LocalDate.parse(receipt.getPurchaseDate());
        if (purchaseDate.getDayOfMonth() % 2 != 0) {
            points += 6;
        }

        // 7. Points for purchase time
        LocalTime purchaseTime = LocalTime.parse(receipt.getPurchaseTime());
        if (purchaseTime.isAfter(LocalTime.of(14, 0)) && purchaseTime.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }
}

