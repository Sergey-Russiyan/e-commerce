package framework.util;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * Utility class for manipulating data values such as Strings, integers, and doubles.
 * Provides methods for parsing strings into integers or doubles, and other data manipulation tasks.
 */
public class DataManipulationUtils {
    private static final Random random = new Random();

    public static double parseAndRoundPriceString(String priceString) {
        // Remove dollar sign and any other non-numeric characters
        String cleanedPrice = priceString.replaceAll("[^\\d.]", "");
        // Parse the cleaned string to double
        double parsedValue = Double.parseDouble(cleanedPrice);

        // Format the parsed value to two decimal places
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedValue = df.format(parsedValue);

        // Parse the formatted value back to double
        return Double.parseDouble(formattedValue);
    }

    public static double roundToTwoDecimalPlaces(double value) {
        // Convert the double value to string and round it to two decimal places
        String formattedValue = String.format("%.2f", value);
        // Parse the formatted value back to double
        return Double.parseDouble(formattedValue);
    }


    private static final String[] firstNames = {"John", "Alice", "Michael", "Emily", "David", "Sophia"};
    private static final String[] lastNames = {"Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia"};
    private static final String[] postalCodes = {"10001", "20001", "30001", "40001", "50001", "60001"};

    public static String generateRandomFirstName() {
        return firstNames[random.nextInt(firstNames.length)];
    }

    public static String generateRandomLastName() {
        return lastNames[random.nextInt(lastNames.length)];
    }

    public static String generateRandomPostalCode() {
        return postalCodes[random.nextInt(postalCodes.length)];
    }
}
