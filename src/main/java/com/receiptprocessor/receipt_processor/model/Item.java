package com.receiptprocessor.receipt_processor.model;


import lombok.Data;

@Data
public class Item {
    private String shortDescription;
    private Double price;
}
