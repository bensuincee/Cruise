package tripAutomation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseManager {
    private static String filename;

    public BaseManager(String filename) {
        this.filename = filename;
    }

    // Dosyaya verileri yazmak için kullanılan metot.
    protected static void writeToFile(String[] data, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            // Verileri belirtilen formatta dosyaya yazma işlemi.
            writer.write(data[0] + "|" + data[1] + "|" + data[2] + "|" + data[3] + "|" + data[4]);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();// Eğer hata oluşursa hata mesajını yazdırır.
        }
    }

    // Dosyadan eşleşen satırı silmek için kullanılan metot.
    protected static void removeFromFile(String ticketCode, String filename) {
        List<String> lines = new ArrayList<>();// Geçici liste dosyadaki veriyi tutar.
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            // Dosyadaki her satır okunur.
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 5 && !parts[4].equals(ticketCode)) {
                    lines.add(line);
                }
            }

            // Dosyanın içeriğini güncellenmiş listeyle yeniden yazma işlemi.
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                for (String newLine : lines) {
                    writer.write(newLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
