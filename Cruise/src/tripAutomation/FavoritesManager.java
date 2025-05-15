package tripAutomation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FavoritesManager extends BaseManager{
        private static final String FAVORITES_FILE = "favourites.txt";

    public FavoritesManager() {
        super(FAVORITES_FILE);
    }

    // Kullanıcının favori etkinliklerini ekler
    public static void addFavorite(String username, String event, String city, String date, String ticketCode) {
        String[] data = {username, event, city, date, ticketCode};
        BaseManager.writeToFile(data,FAVORITES_FILE);
    }

    // Kullanıcının favori etkinliklerini okur
    public static List<String[]> getUserFavorites(String username) {
        List<String[]> favourites = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FAVORITES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5 && parts[0].equals(username)) {
                    favourites.add(parts);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return favourites;
    }

    // Dosya okuma metoduyla favorilere zaten eklenmiş mi kontrol edilir
    public static boolean isAlreadyFavorite(String username, String event, String city, String date) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FAVORITES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 5 && parts[0].equals(username) && parts[1].equals(event) && parts[2].equals(city) && parts[3].equals(date)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Kullanıcının favorilerinden bir etkinliği kaldırır
    public static void removeFavorite(String ticketCode) {
        BaseManager.removeFromFile(ticketCode,FAVORITES_FILE);

    }
}