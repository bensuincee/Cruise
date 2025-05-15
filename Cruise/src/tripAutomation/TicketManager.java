package tripAutomation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicketManager extends BaseManager {
    private static final String FILE_NAME = "bilet_kayitlari.txt";// Bilet kayıtları dosyasının adı
    private static final String STOCK_NAME = "bilet_stoklari.txt";// Bilet stokları dosyasının adı

    public TicketManager() {
        super(FILE_NAME);
    }

    // Kullanıcının bilet kaydını dosyaya ekleme işlemi.
    public static void saveTicket(String username, String event, String city, String date, String ticketCode) {
        String[] data = {username, event, city, date, ticketCode};// Kaydedilecek bilet bilgileri
        BaseManager.writeToFile(data, FILE_NAME);// Veriyi dosyaya yaz
        updateStock(event, -1); // Stoktaki bilet sayısını 1 azalt
    }

    // Kullanıcının biletini iptal etme işlemi
    public static void cancelTicket(String ticketCode) {
        String event = findEvent(ticketCode);// Ticket code'a göre etkinlik adı bul
        BaseManager.removeFromFile(ticketCode, FILE_NAME);// Bilet kaydını dosyadan sil
        updateStock(event, 1);// Stoktaki bilet sayısını 1 artır

    }

    // Bilet koduna göre etkinlik adını bulan yardımcı metot
     private static String findEvent(String ticketCode) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[4].equals(ticketCode)) {
                    return parts[1]; // Event adını döndür
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;// Eğer ticketCode bulunamazsa null döndürülür
    }

    // Verilen etkinlik için stok bilgilerini okur
    public static int getStock(String event) {
        try (BufferedReader reader = new BufferedReader(new FileReader(STOCK_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(event)) {
                    return Integer.parseInt(parts[1].trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Etkinlik stoklarını günceller
    public static void updateStock(String event, int change) {
        try {
            List<String> lines = new ArrayList<>();// Dosyadaki satırları tutacak liste

            // Mevcut stokları oku ve güncelle
            try (BufferedReader reader = new BufferedReader(new FileReader(STOCK_NAME))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts[0].equals(event)) {
                        // Stok güncelleme
                        int newStock = Integer.parseInt(parts[1].trim()) + change;
                        lines.add(event + "|" + newStock);
                    } else {
                        lines.add(line);
                    }
                }
            }

            // Güncellenmiş stokları tekrar yaz
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(STOCK_NAME))) {
                for (String line : lines) {
                    writer.write(line);// Satır yazılıyor
                    writer.newLine();// Yeni satır
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Yeni bilet kodu üretme metodu
    public static String generateTicketCode() {
        Random rand = new Random();
        return "BILET" + rand.nextInt(100000); // 5 haneli bir bilet kodu oluşturuyor
    }

    // Kullanıcının biletlerini okuma işlemi
    public static List<String[]> getUserTickets(String username) {
        List<String[]> tickets = new ArrayList<>();// Kullanıcının biletlerini tutacak liste
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(username)) {
                    tickets.add(new String[]{parts[1], parts[2], parts[3], parts[4]});// Bilet bilgileri listeye eklenir
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }

}