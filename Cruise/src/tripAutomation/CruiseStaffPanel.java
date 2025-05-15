package tripAutomation;

import javax.swing.*;
import userAuthentication.LoginScreen;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CruiseStaffPanel extends JFrame {

    private Map<String, Integer> eventStaffCount;

    public CruiseStaffPanel() {
        // Nimbus görünümünü ayarla
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Panel başlığı ve özelliklerini ayarlama.
        setTitle("Cruisé.com - Görevli Paneli");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Görevli sayaçlarını dosyadan yükle.
        eventStaffCount = loadStaffCountFromFile();

        // Ana paneli oluştur.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Başlık bölümü ve özelliklerini ayarla.
        JLabel headerLabel = new JLabel("Cruisé.com - Görevli Paneli");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(new Color(236, 77, 49));
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerLabel.setPreferredSize(new Dimension(500, 50));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // İçerik panelini oluşturma.
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(0, 1, 10, 10)); // Butonlar arasına boşluk ekleniyor

        // Event butonlarını oluşturma
        for (String event : getEventList()) {
            // Etkinlik için mevcut görevli sayısını alıyoruz.
            int count = eventStaffCount.getOrDefault(event, 0);
            JButton eventButton = new JButton(event + " - Görevli Sayısı: " + count);
            eventButton.setFont(new Font("Arial", Font.PLAIN, 16));
            eventButton.setPreferredSize(new Dimension(460, 40));
            eventButton.setBackground(new Color(220, 220, 220));
            eventButton.setFocusPainted(false);
            eventButton.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(169, 169, 169)),
                    BorderFactory.createEmptyBorder(5, 15, 5, 15)
            ));

            // Hover efekti ekleme işlemi.
            eventButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    eventButton.setBackground(new Color(200, 200, 255));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    eventButton.setBackground(new Color(220, 220, 220));
                }
            });

            // Butona tıklandığında görev ekleme işlemi.
            eventButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Kullanıcının o etkinlikte daha önce görev alıp almadığını kontrol eder
                    if (eventStaffCount.getOrDefault(event, 0) > 0) {
                        JOptionPane.showMessageDialog(null, "Bu etkinlikte zaten görev aldınız!", "Bilgi", JOptionPane.WARNING_MESSAGE);
                    } else {
                        // Görev sayacını günceller
                        int updatedCount = eventStaffCount.getOrDefault(event, 0) + 1;
                        eventStaffCount.put(event, updatedCount);
                        eventButton.setText(event + " - Görevli Sayısı: " + updatedCount);
                        saveStaffCountToFile(eventStaffCount);
            
                        JOptionPane.showMessageDialog(null, "Değerli Cruise.com organizasyon görevlimiz,\n" + event + " etkinliğinde görev aldınız!\n\nEtkinlik saatinden 1 saat önce etkinlik alanında olmanızı rica eder,\nalmış olduğunuz görev için başarılar dileriz.",
                                "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            });
            // Butonu içerik paneline ekle
            contentPanel.add(eventButton);
        }

        // Kaydırılabilir içerik için JScrollPane ekliyoruz.
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Geri Dön butonu
        JPanel bottomPanel = new JPanel();
        JButton backButton = new JButton("Geri Dön");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setBackground(new Color(236, 77, 49));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(460, 50));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(169, 169, 169)),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));

        // Hover efekti ekliyoruz.
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(200, 50, 50));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(236, 77, 49));
            }
        });

        // Geri Dön butonuna tıklanırsa LoginScreen'i açma işlemi.
        backButton.addActionListener(e -> {
            setVisible(false);
            new LoginScreen();
        });
        bottomPanel.add(backButton);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Ana paneli içerik olarak ayarlıyoruz.
        setContentPane(mainPanel);
        setVisible(true);
    }

    // Görevli sayaçlarını dosyadan yükleme metodu
    private Map<String, Integer> loadStaffCountFromFile() {
        Map<String, Integer> staffCount = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("staff_count.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Dosyadan okunan satırı parse et ve etkinlik ile görevlileri eşleştirme işlemi.
                String[] parts = line.split(",");
                String event = parts[0];
                int count = Integer.parseInt(parts[1]);
                staffCount.put(event, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return staffCount;
    }

    // Görevli sayaçlarını dosyaya kaydetme metodu
    private void saveStaffCountToFile(Map<String, Integer> staffCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("staff_count.txt"))) {
            // Görevli sayısını her etkinlik için dosyaya yazdırma işlemi.
            for (Map.Entry<String, Integer> entry : staffCount.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Etkinlik listesi
    private String[] getEventList() {
        return new String[]{
                "Sagopa Kajmer Konseri", "Mabel Matiz 360", "Emir Can İğrek Konseri", "Yıldız Tilbe Konseri",
                "Zakkum Konseri", "Simge Sağın İle Yılbaşı", "Kalben Konseri", "Dedublüman Konseri",
                "İrem Derici Konseri", "Tuğkan Konseri", "Berkay Konseri", "Gökhan Türkmen Konseri",
                "Skapova Konseri", "ATİ242 Konseri", "Melek Mosso Konseri", "Gladyatör 2", "Vahşi Robot",
                "Ters Yüz 2", "Bir Cumhuriyet Şarkısı", "Doğulu", "Sayara: İntikam Meleği", "Mustafa",
                "Yandaki Oda", "Yeni Yıl Şarkısı", "Başlangıçlar", "Aşk Hikayen Düşmüş", "Meçhul Paşa",
                "Piramitlere Yolculuk - Antik Mısır'ın Keşfi", "Anna Karenina", "Ölü'n Bizi Ayırana Dek Oyunu",
                "Prenses Hamlet Oyunu", "Bir Delinin Hatıra Defteri", "Kariyer Planlamasında Dönüm Noktaları",
                "Yapay Zeka Çağında Girişimcilik", "Zihinsel Sağlık ve Stres Yönetimi", "Anadolu'da Türk İzleri",
                "Psikoloji Zirvesi", "Milyonfest İstanbul", "Rock Festivali", "Antalya Altın Portakal Film Festivali",
                "İzmir Enternasyonal Fuarı", "Osmanlı'nın İzinde: Sanat ve Mimari", "Cumhuriyetin Renkleri",
                "Ege'nin Doğası: Deniz ve Dağların Buluşması", "Kuğu Gölü", "Mevsimler",
                "Alice Harikalar Diyarında", "Troya: Bir Destanın Dansı", "Huzur İstanbul'da: Yoga ile Yenilen",
                "Beden ve Zihin Dengesi", "Sahilde Yoga Günü"
        };
    }

    public static void main(String[] args) {
        new CruiseStaffPanel();
    }
}
