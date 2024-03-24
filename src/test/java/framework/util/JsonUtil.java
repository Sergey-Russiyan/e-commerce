package framework.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static framework.context.Constants.WORKING_DIRECTORY;

/**
 * Utility class for handling JSON data.
 * Provides methods for reading JSON files, parsing JSON strings, and manipulating JSON objects.
 * Uses Jackson ObjectMapper for JSON processing.
 */
public class JsonUtil {

    public static final String SWAG_LABS_PRODUCTS = WORKING_DIRECTORY + "//src//test//java//framework//testData//SwagLabsProducts.json";

    public List < HashMap < String, String >> getListOfProductsFromJson() {
        return getJsonData(SWAG_LABS_PRODUCTS);
    }

    /**
     * Reads JSON data from the specified file.
     *
     * @param jsonFilePath The path to the JSON file.
     * @return A list of hash maps representing the JSON data.
     */
    public List < HashMap < String, String >> getJsonData(String jsonFilePath) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File(jsonFilePath);

        try {
            return mapper.readValue(file,
                    new TypeReference < List < HashMap < String, String >>> () {});
        } catch (IOException e) {
            LoggerUtil.getLogger().error("Error reading JSON file: " + e.getMessage());
            return null;
        }
    }

    /**
     * Searches for elements in the list whose name contains any of the specified strings.
     *
     * @param dataList The list of hash maps representing the JSON data.
     * @param searchStrings The list of strings to search for in the 'name' field of each element.
     * @return A list of hash maps representing the matching elements.
     */
    public List < HashMap < String, String >> searchByNames(List < HashMap < String, String >> dataList, List < String > searchStrings) {
        // Create a new list to store matching elements
        List < HashMap < String, String >> resultList = new ArrayList < > ();

        // Iterate over each element in the list
        for (HashMap < String, String > element: dataList) {
            // Get the value of the 'name' field
            String name = element.get("name");

            // Check if the 'name' field contains any of the search strings
            if (name != null && containsAny(name, searchStrings)) {
                // If yes, add the element to the result list
                resultList.add(element);
            }
        }

        // Return the list of matching elements
        return resultList;
    }

    /**
     * Checks if the specified string contains any of the strings in the given list.
     *
     * @param str The string to check.
     * @param searchStrings The list of strings to search for.
     * @return True if the string contains any of the search strings, false otherwise.
     */


    private boolean containsAny(String str, List < String > searchStrings) {
        for (String searchString: searchStrings) {
            if (str.contains(searchString)) {
                return true;
            }
        }
        return false;
    }

    public double calculateTotalPrice(List<HashMap<String, String>> products) {
        DataManipulationUtils utils = new DataManipulationUtils();
        double totalPrice = 0;
        for (HashMap<String, String> product : products) {
            totalPrice += utils.parsePriceString(product.get("priceUsd"));
        }
        return totalPrice;
    }

}