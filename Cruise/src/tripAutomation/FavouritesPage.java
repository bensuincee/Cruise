package tripAutomation;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class FavouritesPage extends JFrame {

    public FavouritesPage(String username, JFrame previousPage) {
        try {
            // Favori etkinlikleri dosyadan oku
            List<String[]> favourites = FavoritesManager.getUserFavorites(username);

            // Eğer favoriler dosyasında bir sorun varsa ya da favori yoksa
            if (favourites == null || favourites.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Hiçbir favori bulunamadı!", "Bilgi", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                previousPage.setVisible(true);
                return;
            }

            // Panel ve genel düzen oluşturuluyor.
            JPanel panel = new JPanel();
            panel.setLayout(null);
            panel.setBackground(new Color(255, 255, 255));
            getContentPane().add(panel);

            // Başlık
            JLabel label = new JLabel("Cruisé - Favorilerim");
            label.setBounds(0, 0, 800, 50);
            label.setForeground(new Color(0, 0, 0));
            label.setFont(new Font("Arial", Font.BOLD, 20));
            label.setOpaque(true);
            label.setBackground(new Color(236, 77, 49));
            panel.add(label);

            // Favori etkinliklerin listelenmesi
            int yPosition = 100;// Etkinliklerin başlama konumu
            for (String[] favourite : favourites) {
                // Etkinlik bilgilerini gösteren
                JLabel eventLabel = new JLabel("Etkinlik: " + favourite[1] + " | Şehir: " + favourite[2] + " | Tarih: " + favourite[3]);
                eventLabel.setBounds(20, yPosition, 600, 30);
                eventLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                panel.add(eventLabel);

                // Favorilerden Kaldır butonu oluşturuluyor.
                JButton removeButton = new JButton("Favorilerden Kaldır");
                removeButton.setFont(new Font("Arial", Font.PLAIN, 14));
                removeButton.setBounds(620, yPosition - 5, 160, 40);
                removeButton.setBackground(new Color(236, 77, 49)); // Kırmızı buton
                removeButton.setForeground(Color.WHITE); // Beyaz yazı
                removeButton.addActionListener(e -> {
                    // Favorilerden etkinliği kaldırma işlemi
                    FavoritesManager.removeFavorite(favourite[4]); // Bilet kodu
                    JOptionPane.showMessageDialog(this, "Etkinlik favorilerinizden kaldırıldı.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    this.setVisible(false);
                    new FavouritesPage(username, previousPage); // Sayfayı yeniden yükle
                });
                panel.add(removeButton);

                yPosition += 50;// Bir sonraki etkinlik için y konumunu artır
            }

            // Geri butonu oluşturuluyor
            JButton backButton = new JButton("Geri");
            backButton.setFont(new Font("Arial", Font.PLAIN, 14));
            backButton.setBounds(320, yPosition + 40, 145, 40);
            backButton.addActionListener(e -> {
                this.setVisible(false);
                previousPage.setVisible(true);
            });
            panel.add(backButton);

            setTitle("Favorilerim");
            setSize(800, 500);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setResizable(false);
            setVisible(true);


        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Favorilerim ekranında bir hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }
}