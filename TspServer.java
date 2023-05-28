import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TspServer {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(5500, 0, InetAddress.getByName("192.168.1.180"));
            System.out.println("Server is running. Waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                Thread clientThread = new Thread(() -> {
                    try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                        String input = in.readLine();
                        System.out.println("Received: " + input);

                        String response = solveTSP(input);
                        out.println(response);
                        System.out.println("Sent: " + response);

                        in.close();
                        out.close();
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String solveTSP(String input) {
        String[] cities = input.split(",");
        int numCities = cities.length;

        int[] permutation = new int[numCities];
        int[] bestPermutation = new int[numCities];
        double shortestDistance = Double.POSITIVE_INFINITY;

        for (int i = 0; i < numCities; i++) {
            permutation[i] = i;
        }

        do {
            double distance = calculateDistance(permutation, cities);

            if (distance < shortestDistance) {
                shortestDistance = distance;
                System.arraycopy(permutation, 0, bestPermutation, 0, numCities);
            }
        } while (nextPermutation(permutation));

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

            double lat1 = getLatitude(city1);
            double lon1 = getLongitude(city1);
            double lat2 = getLatitude(city2);
            double lon2 = getLongitude(city2);

            double cityDistance = calculateDistanceBetweenCities(lat1, lon1, lat2, lon2);
            distance += cityDistance;
        }

        int lastCityIndex = permutation[numCities - 1];
        int firstCityIndex = permutation[0];
        String lastCity = cities[lastCityIndex];
        String firstCity = cities[firstCityIndex];

        double lastCityLat = getLatitude(lastCity);
        double lastCityLon = getLongitude(lastCity);
        double firstCityLat = getLatitude(firstCity);
        double firstCityLon = getLongitude(firstCity);

        double lastToFirstDistance = calculateDistanceBetweenCities(lastCityLat, lastCityLon, firstCityLat, firstCityLon);
        distance += lastToFirstDistance;

        return distance;
    }

    private static double calculateDistanceBetweenCities(double lat1, double lon1, double lat2, double lon2) {
        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double earthRadius = 6371.0;
        double dlon = lon2Rad - lon1Rad;
        double dlat = lat2Rad - lat1Rad;
        double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1Rad) * Math.cos(lat2Rad) * Math.pow(Math.sin(dlon / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        return distance;
    }

    private static double getLatitude(String city) {
        Random random = new Random();
        double minLat = 13.5;
        double maxLat = 14.5;
        return minLat + (maxLat - minLat) * random.nextDouble();
    }

    private static double getLongitude(String city) {
        Random random = new Random();
        double minLon = 100.0;
        double maxLon = 101.0;
        return minLon + (maxLon - minLon) * random.nextDouble();
    }

    private static boolean nextPermutation(int[] permutation) {
        int i = permutation.length - 2;

        while (i >= 0 && permutation[i] > permutation[i + 1]) {
            i--;
        }

        if (i < 0) {
            return false;
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
