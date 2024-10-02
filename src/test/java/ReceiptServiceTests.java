import com.receiptprocessor.receipt_processor.ReceiptProcessorApplication;
import com.receiptprocessor.receipt_processor.model.Item;
import com.receiptprocessor.receipt_processor.model.Receipt;
import com.receiptprocessor.receipt_processor.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = ReceiptProcessorApplication.class)
public class ReceiptServiceTests {

    @Autowired
    private ReceiptService receiptService;

    @Test
    public void testCalculatePoints() {

        // Creating mock receipt
        Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");

        // Creating mock items for receipt
        List<Item> items = new ArrayList<>();

        Item item1 = new Item();
        item1.setShortDescription("Mountain Dew 12PK");
        item1.setPrice(6.49);
        items.add(item1);

        Item item2 = new Item();
        item2.setShortDescription("Emils Cheese Pizza");
        item2.setPrice(12.25);
        items.add(item2);

        Item item3 = new Item();
        item3.setShortDescription("Knorr Creamy Chicken");
        item3.setPrice(1.26);
        items.add(item3);

        Item item4 = new Item();
        item4.setShortDescription("Doritos Nacho Cheese");
        item4.setPrice(3.35);
        items.add(item4);

        Item item5 = new Item();
        item5.setShortDescription("   Klarbrunn 12-PK 12 FL OZ  ");
        item5.setPrice(12.00);
        items.add(item5);

        receipt.setItems(items);
        receipt.setTotal(35.35);

        String receiptId = receiptService.processReceipt(receipt);
        int points = receiptService.getPoints(receiptId);

        int expectedPoints = 28;

        assertEquals(expectedPoints, points);
    }
}

