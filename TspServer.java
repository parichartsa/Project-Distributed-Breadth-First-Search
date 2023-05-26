import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TspServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5000);
            System.out.println("Server started. Waiting for clients...");

            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);

                String result = solveTSP(inputLine);
                out.println(result);
            }

            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String solveTSP(String input) {
        String[] cities = input.split(","); // Split city names into an array

        int numCities = cities.length; // Number of cities
        int[] permutation = new int[numCities]; // Store the permutation of city visits
        int[] bestPermutation = new int[numCities]; // Store the best permutation with the shortest distance
        double shortestDistance = Double.POSITIVE_INFINITY; // Store the shortest distance

        // Create an initial permutation [0, 1, 2, ..., numCities-1]
        for (int i = 0; i < numCities; i++) {
            permutation[i] = i;
        }

        // Check all possible routes
        do {
            double distance = calculateDistance(permutation, cities); // Calculate the distance for the current permutation

            // Store the shortest distance and permutation
            if (distance < shortestDistance) {
                shortestDistance = distance;
                System.arraycopy(permutation, 0, bestPermutation, 0, numCities);
            }
        } while (nextPermutation(permutation)); // Generate the next permutation

        // Create the path for the best permutation
        StringBuilder pathBuilder = new StringBuilder();
        for (int i = 0; i < numCities; i++) {
            int cityIndex = bestPermutation[i];
            pathBuilder.append(cities[cityIndex]);
            if (i < numCities - 1) {
                pathBuilder.append(" -> ");
            }
        }

        String shortestPath = pathBuilder.toString();

        return "Shortest Path: " + shortestPath + "\nShortest Distance: " + shortestDistance;
    }

    private static double calculateDistance(int[] permutation, String[] cities) {
        double distance = 0;
        int numCities = permutation.length;

        for (int i = 0; i < numCities - 1; i++) {
            int cityIndex1 = permutation[i];
            int cityIndex2 = permutation[i + 1];
            String city1 = cities[cityIndex1];
            String city2 = cities[cityIndex2];

            distance += calculateDistanceBetweenCities(city1, city2);
        }

        return distance;
    }

    private static double calculateDistanceBetweenCities(String city1, String city2) {
        // Distance matrix between cities
        double[][] distanceMatrix = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };

        // Assuming city1 and city2 are valid city indices
        int index1 = Integer.parseInt(city1);
        int index2 = Integer.parseInt(city2);

        return distanceMatrix[index1][index2];
    }

    private static boolean nextPermutation(int[] permutation) {
        int i = permutation.length - 2;

        while (i >= 0 && permutation[i] > permutation[i + 1]) {
            i--;
        }

        if (i < 0) {
            return false; // Last permutation reached
        }

        int j = permutation.length - 1;
        while (permutation[j] < permutation[i]) {
            j--;
        }

        swap(permutation, i, j);

        int left = i + 1;
        int right = permutation.length - 1;

        while (left < right) {
            swap(permutation, left, right);
            left++;
            right--;
        }

        return true;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
