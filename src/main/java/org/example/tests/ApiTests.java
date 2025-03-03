package org.example.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.base.BaseApiActions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

public class ApiTests {

    private BaseApiActions baseApiActions;

    @BeforeClass
    public void setUp() {
        String baseUrl = "https://airportgap.com/api";
        this.baseApiActions = new BaseApiActions(baseUrl, "ZxutYt1RY2YTrNRpXWqZCnjG");
    }

    /*
        Verify Airport Count
        1. Send a GET request to the endpoint https://airportgap.com/api/airports.
        2. Verify that the response contains exactly 30 airports.
     */
    @Test
    public void VerifyAirportCount() throws IOException, URISyntaxException {
        HttpURLConnection connection = this.baseApiActions.CreateGetConnection("airports");

        // Get response code and handle response
        JsonNode data = getResponseData(connection);
        int airports = data.size();

        // Verify that the response contains exactly 30 airports.
        Assert.assertEquals(airports, 30, "Incorrect number of airport items");
    }

    /*
        Verify Specific Airports
        1. Send a GET request to the endpoint https://airportgap.com/api/airports.
        2. Verify that the response includes the following airports:
            ○ Akureyri Airport
            ○ St. Anthony Airport
            ○ CFB Bagotville
     */
    @Test
    public void VerifySpecificAirports() throws IOException, URISyntaxException {
        HttpURLConnection connection = this.baseApiActions.CreateGetConnection("/airports");
        connection.setRequestMethod("GET");

        // Get response code and handle response
        JsonNode data = getResponseData(connection);

        // Verify that the response includes the following airports:
//         Akureyri Airport,  St. Anthony Airport, CFB Bagotville
//             simply get response as string:
//                boolean simply1 = response.contains("\"name\":\"Akureyri Airport\"");
//                boolean simply2 = response.contains("\"name\":\"St. Anthony Airport\"");
//                boolean simply3 = response.contains("\"name\":\"CFB Bagotville\"");

        Set<String> set = new HashSet<String>();
        data.forEach(d -> {
            JsonNode attributes = d.path("attributes");
            set.add(attributes.path("name").asText());
        });

        boolean exists1 = set.contains("Akureyri Airport");
        boolean exists2 = set.contains("St. Anthony Airport");
        boolean exists3 = set.contains("CFB Bagotville");

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(exists1, "Akureyri Airport doesn't exist");
        softAssert.assertTrue(exists2, "St. Anthony Airport doesn't exist");
        softAssert.assertTrue(exists3, "CFB Bagotville airport doesn't exist");
        softAssert.assertAll();
    }

    /*
        Verify Distance Between Airports
        1. Send a POST request to the endpoint https://airportgap.com/api/airports/distance
            with parameters for the airports KIX and NRT.
        2. Verify that the calculated distance between these airports is greater than 400 km.
     */
    @Test
    public void VerifyDistanceBetweenAirports() throws IOException, URISyntaxException {
        String data_to_send = "{\"from\": \"KIX\", \"to\": \"NRT\" }";

        HttpURLConnection connection = this.baseApiActions.CreatePostConnection("/airports/distance");

        try (DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            os.writeBytes(data_to_send);
            os.flush();
        }

        // Get response code and handle response
        JsonNode data = getResponseData(connection);
        JsonNode attributes = data.path("attributes");
        double kilometers = attributes.path("kilometers").asDouble();
        Assert.assertTrue(kilometers > 400,"Calculated distance between these airports is not greater than 400 km.");
    }

    private JsonNode getResponseData(HttpURLConnection connection) throws IOException {

        int responseCode = connection.getResponseCode();
        Assert.assertEquals(responseCode, 200, "Error: HTTP Response code - " + responseCode);

        StringBuilder response = new StringBuilder();

        // Read response content
        // connection.getInputStream() purpose is to obtain an input stream for reading the server's response.
        try (BufferedReader reader = new BufferedReader( new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line); // Adds every line to response till the end of file.
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.toString());

        connection.disconnect();

        return rootNode.path("data");
    }
}
