package com.gymmembership.reports;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import com.gymmembership.membership.Membership;
import com.gymmembership.membership.MembershipService;

import java.util.ArrayList;

public class MembershipReportExporter {
    private final MembershipService membershipService = new MembershipService();

    private void createReportsFolder() {
        File reportsFolder = new File("reports");

        if (!reportsFolder.exists()) {
            reportsFolder.mkdir();
        }
    }

    public void exportReport() throws SQLException {

        createReportsFolder();

        ArrayList<Membership> memberships =
                membershipService.getAllMemberships();

        try (FileWriter writer =
                     new FileWriter("reports/membership_revenue_report.txt")) {

            writer.write("Membership Revenue Report\n");
            writer.write("=========================\n");

            double totalRevenue = 0;

            for (Membership membership : memberships) {

                writer.write("Membership ID: " + membership.getMembershipId() + "\n");
                writer.write("User ID: " + membership.getUserId() + "\n");
                writer.write("Membership Type: " + membership.getMembershipType() + "\n");
                writer.write("Price: $" + membership.getPrice() + "\n");
                writer.write("Purchase Date: " + membership.getPurchaseDate() + "\n");
                writer.write("-------------------------\n");

                totalRevenue += membership.getPrice();
            }

            writer.write("\n");
            writer.write("Total Membership Revenue: $" + totalRevenue + "\n");

        } catch (IOException e) {
            System.out.println("Unable to create membership report.");
        }
    }
}
