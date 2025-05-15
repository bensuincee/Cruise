package userAuthentication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import tripAutomation.Cruise;
import tripAutomation.CruiseStaffPanel;

public class LoginScreen extends JFrame {
    private static String currentUsername;

    public LoginScreen() {
        // Nimbus tasarımı uygulanır
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        setTitle("Cruisé.com");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Kapatma yerine basılınca terminalde de kapanması için bu işlem yapılır.
        setResizable(false);// Yeniden boyutlanırmayı kapanır.
        setLocationRelativeTo(null);//Panelin ekranın ortasında kalmasını sağlar.

        // Panel oluşturulur.
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(252, 252, 253));
        getContentPane().add(panel);

        // Başlık oluşturulur ve özellikleri eklenir.
        JLabel titleLabel = new JLabel("Cruisé");
        titleLabel.setBounds(0, 0, 400, 25); // Başlık konumu ve boyutu
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));// Başlık yazı tipi
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);// Ortalanmış yazı
        titleLabel.setOpaque(true);// Arka plan rengini değiştirebilmek için opaklık
        titleLabel.setBackground(new Color(236, 77, 49));// Başlık arka plan rengi
        titleLabel.setForeground(Color.BLACK);// Başlık yazı rengi
        panel.add(titleLabel);

        // Kullanıcı Girişi oluşturulur.
        JLabel subTitleLabel = new JLabel("Kullanıcı Girişi");
        subTitleLabel.setBounds(0, 25, 400, 25);
        subTitleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        subTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        subTitleLabel.setOpaque(true);
        subTitleLabel.setBackground(new Color(236, 77, 49));
        subTitleLabel.setForeground(Color.BLACK);
        panel.add(subTitleLabel);

        // Kullanıcı adı etiketi
        JLabel userLabel = new JLabel("Kullanıcı Adı:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setBounds(100, 120, 200, 25);
        panel.add(userLabel);

        // Kullanıcı adı giriş kutusu
        JTextField userField = new JTextField();
        userField.setBounds(100, 150, 200, 25);
        panel.add(userField);

        // Şifre etiketi
        JLabel passLabel = new JLabel("Şifre:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passLabel.setBounds(100, 180, 200, 25);
        panel.add(passLabel);

        // Şifre giriş kutusu
        JPasswordField passField = new JPasswordField();
        passField.setBounds(100, 210, 200, 25);
        panel.add(passField);

        // Giriş Yap Butonu oluşturuluyor.
        JButton loginButton = new JButton("Giriş Yap");
        loginButton.setBounds(150, 250, 100, 30);
        panel.add(loginButton);

        // Görevli Giriş Butonu oluşturuluyor.
        JButton staffLoginButton = new JButton("Görevli Girişi");
        staffLoginButton.setBounds(150, 290, 100, 30);
        panel.add(staffLoginButton);

        // Kayıt Ol Butonu oluşturuluyor.
        JButton registerButton = new JButton("Kayıt Ol");
        registerButton.setBounds(150, 330, 100, 30);
        panel.add(registerButton);

        // Giriş Yap İşlemleri
        loginButton.addActionListener(e -> {
            //Kullanıcı adı ve şifreyi alıyoruz.
            String enteredUsername = userField.getText();
            String enteredPassword = new String(passField.getPassword());

            try (BufferedReader reader = new BufferedReader(new FileReader("kullanici_bilgileri.txt"))) {
                String line;
                boolean loginSuccess = false;

                // Kullanıcı bilgilerini dosyadan kontrol et
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(enteredUsername) && parts[1].equals(enteredPassword)) {
                        loginSuccess = true;
                        break;
                    }
                }

                //Eğer giriş başarılı ise
                if (loginSuccess) {
                    JOptionPane.showMessageDialog(this, "Giriş Yapılıyor!", "Giriş Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    setVisible(false);// Mevcut pencereyi gizle
                    Cruise cruise = new Cruise();
                    cruise.showCruisePanel();// Cruise panelini göster
                    setCurrentUsername(enteredUsername);
                } else {
                    // Giriş başarısızsa hata mesajı göster
                    JOptionPane.showMessageDialog(this, "Geçersiz kullanıcı adı veya şifre.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Dosya okuma hatası!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Görevli Giriş İşlemleri
        staffLoginButton.addActionListener(e -> {
            String username = userField.getText();
            String password = new String(passField.getPassword());

            // Sabit görevli bilgileri ile kontrol et
            if (username.equals("gorevli") && password.equals("cruise2025")) {
                JOptionPane.showMessageDialog(this, "Görevli girişi başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                setVisible(false);
                new CruiseStaffPanel().setVisible(true);// Görevli panelini göster
            } else {
                JOptionPane.showMessageDialog(this, "Geçersiz görevli bilgileri.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Kayıt Ol İşlemleri
        registerButton.addActionListener(e -> {
            // Yeni bir kayıt ekranı oluşturuluyor
            JFrame registrationFrame = new JFrame("Kayıt Ol");
            registrationFrame.setSize(400, 400);
            registrationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            registrationFrame.setLocationRelativeTo(null);
            registrationFrame.setResizable(false); // Boyut değiştirilmesini engelle

            // Panelin oluşturulması
            JPanel registerPanel = new JPanel();
            registerPanel.setLayout(null);
            registerPanel.setBackground(new Color(252, 252, 253));
            registrationFrame.getContentPane().add(registerPanel); // Paneli JFrame'e ekliyoruz

            // Başlık etiketi oluşturuluyor
            JLabel titleLabel1 = new JLabel("Kayıt Ol");
            titleLabel1.setBounds(0, 0, 400, 25);
            titleLabel1.setFont(new Font("Arial", Font.BOLD, 20));
            titleLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            titleLabel1.setOpaque(true);
            titleLabel1.setBackground(new Color(236, 77, 49));
            registerPanel.add(titleLabel1);

            // Kullanıcı adı etiketi ve text alanı oluşturuluyor
            JLabel newUserLabel = new JLabel("Kullanıcı Adı:");
            newUserLabel.setBounds(50, 100, 100, 30);
            registerPanel.add(newUserLabel);

            JTextField newUserField = new JTextField();
            newUserField.setBounds(150, 100, 150, 30);
            registerPanel.add(newUserField);

            // Şifre etiketi ve password alanı oluşturuluyor.
            JLabel newPassLabel = new JLabel("Şifre:");
            newPassLabel.setBounds(50, 150, 100, 30);
            registerPanel.add(newPassLabel);

            JPasswordField newPassField = new JPasswordField();
            newPassField.setBounds(150, 150, 150, 30);
            registerPanel.add(newPassField);

            // Kayıt ol butonu
            JButton newRegisterButton = new JButton("Kayıt Ol");
            newRegisterButton.setBounds(150, 200, 100, 30);
            registerPanel.add(newRegisterButton);

            // Kayıt ol butonuna tıklanıldığında yapılacak işlemler
            newRegisterButton.addActionListener(ev -> {
                // Kullanıcı adı ve şifre alınıyor
                String newUsername = newUserField.getText();
                String newPassword = new String(newPassField.getPassword());

                // Eğer kullanıcı adı veya şifre boş ise hata mesajı gösteriliyor
                if (newUsername.isEmpty() || newPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(registrationFrame, "Tüm alanları doldurun!", "Hata", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Kullanıcı bilgileri dosyaya yazılıyor
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("kullanici_bilgileri.txt", true))) {
                        writer.write(newUsername + "," + newPassword);
                        writer.newLine();// Satır sonu ekleniyor
                        JOptionPane.showMessageDialog(registrationFrame, "Kayıt Başarılı!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                        registrationFrame.dispose();// Kayıt ekranı kapanıyor
                    } catch (IOException ex) {
                        ex.printStackTrace();// Hata durumunda istisna yazdırılıyor
                    }
                }
            });

            registrationFrame.setVisible(true);// Kayıt ekranı görünür hale getiriliyor
        });

        setVisible(true);// Ana ekran görünür hale getiriliyor
    }

    // Geçerli kullanıcı adını döndüren getter metodu
    public static String getCurrentUsername() {
        return currentUsername;
    }

    // Geçerli kullanıcı adını ayarlayan setter metodu
    public static void setCurrentUsername(String username) {
        currentUsername = username;
    }
}