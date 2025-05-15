package tripAutomation;

import userAuthentication.LoginScreen;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Biletlerim extends JFrame {

    private EventDetails1 parentEventDetails;

    public Biletlerim(ArrayList<String[]> tickets, EventDetails1 parentEventDetails) {
        this.parentEventDetails = parentEventDetails;
        String username = LoginScreen.getCurrentUsername();

        //Kullanıcının biletlerini almak için TicketManager kullanılır.
        List<String[]> userTickets = TicketManager.getUserTickets(username);

        // Panelin başlığı ve özellikleri ayarlanma işlemi.
        setTitle("Cruisé - Biletlerim");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        //Ana panel oluşturulur ve düzenlenir.
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 255, 255));
        getContentPane().add(panel);

        //Üst başlık etiketi özellikleri.
        JLabel label = new JLabel("Cruisé - Biletlerim");
        label.setBounds(0, 0, 800, 50);
        label.setForeground(new Color(0, 0, 0));
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setOpaque(true);
        label.setBackground(new Color(236, 77, 49));
        panel.add(label);

        //Kullanıcı bilgilerini listeleme işlemi yapılır.
        int yPosition = 100;
        for (String[] ticket : userTickets) {
            // Her bilet için etiket oluşturulur
            JLabel ticketLabel = new JLabel("Etkinlik: " + ticket[0] + " | Şehir: " + ticket[1] + " | Tarih: " + ticket[2] + " | Kod: " + ticket[3]);
            ticketLabel.setBounds(20, yPosition, 750, 30);
            ticketLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            panel.add(ticketLabel);
            yPosition += 40;// Bir sonraki bilet için aralık bırakılır
        }

        //Geri butonu oluşturulur ve düzenlenir.
        JButton backButton = new JButton("Geri");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBackground(new Color(236, 77, 49));
        backButton.setForeground(Color.BLACK);
        backButton.setMargin(new Insets(10, 20, 10, 20));
        backButton.setBounds(320, 420, 145, 40);
        backButton.addActionListener(e -> {
            // Biletlerim panelini kapatıyoruz
            setVisible(false);
            parentEventDetails.setVisible(true);
        });
        panel.add(backButton);

        // Kullanıcı biletleri için iptal butonları eklenir.
        for (int i = 0; i < userTickets.size(); i++) {
            String[] ticket = userTickets.get(i);
            JButton cancelButton = new JButton("İptal Et");
            cancelButton.setFont(new Font("Arial", Font.PLAIN, 12));
            cancelButton.setBackground(new Color(236, 77, 49));
            cancelButton.setForeground(Color.BLACK);
            cancelButton.setBounds(650, 100 + (i * 40), 100, 30);

            final String ticketCode = ticket[3];// İptal edilecek biletin kodu alınır.
            cancelButton.addActionListener(e -> {
                TicketManager.cancelTicket(ticketCode);
                JOptionPane.showMessageDialog(this, "Bilet başarıyla iptal edildi.", "İptal", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                new Biletlerim(new ArrayList<>(TicketManager.getUserTickets(username)), parentEventDetails);
            });

            panel.add(cancelButton);
        }

        setVisible(true);
    }
}
