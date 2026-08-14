package com.gymmembership.reports;

import com.gymmembership.merchandise.Merchandise;
import com.gymmembership.merchandise.MerchandiseService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class MerchandiseReportExporter {

    private final MerchandiseService merchandiseService =
            new MerchandiseService();

    private void createReportsFolder() {
        File reportsFolder = new File("reports");

        if (!reportsFolder.exists()) {
            reportsFolder.mkdir();
        }
    }

    public void exportReport() throws SQLException, IOException {

        createReportsFolder();

        ArrayList<Merchandise> merchandise =
                merchandiseService.browseMerchandise();

        try (FileWriter writer =
                     new FileWriter("reports/merchandise_report.txt")) {

            writer.write("Merchandise Inventory Report\n");
            writer.write("============================\n");

            double totalInventoryValue = 0;

            for (Merchandise item : merchandise) {

                double itemValue =
                        item.getPrice() * item.getStockLevel();

                writer.write("Merchandise ID: " + item.getMerchandiseID() + "\n");
                writer.write("Product Name: " + item.getProductName() + "\n");
                writer.write("Type: " + item.getType() + "\n");
                writer.write(String.format("Price: $%.2f%n", item.getPrice()));
                writer.write("Stock Level: " + item.getStockLevel() + "\n");
                writer.write(String.format("Item Value: $%.2f%n", itemValue));
                writer.write("----------------------------\n");

                totalInventoryValue += itemValue;
            }

            writer.write("\n");
            writer.write(String.format("Total Inventory Value: $%.2f%n", totalInventoryValue));
        }
    }
}