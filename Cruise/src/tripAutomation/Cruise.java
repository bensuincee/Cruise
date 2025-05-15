package tripAutomation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.*;

public class Cruise extends JFrame implements ActionListener {
		
	//Sekmede gerekli kısımların tanımlanması
	JPanel p1 = new JPanel();
	JButton buton = new JButton();
	JComboBox cityComboBox;
	JComboBox categoryComboBox;
	JComboBox dateComboBox;		
	
	public Cruise() {
		
        //Program tasarımını Nimbus yapıyoruz.
        for(UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()){
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (UnsupportedLookAndFeelException e) {
                throw new RuntimeException(e);
            }

        }

        setTitle("Cruisé.com");
        setSize(800,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Kapatma yerine basılınca terminalde de kapanması için bu işlem yapılır.
        setResizable(false); // Yeniden boyutlanırmayı kapanır.

        setLocationRelativeTo(null);//Panelin ekranın ortasında kalmasını sağlar.

        // Panel oluşturulur.
        
        p1.setForeground(new Color(0, 0, 0));
        p1.setLayout(null); 
        p1.setBackground(new Color(255, 255, 255));  //Panele arka plan rengi eklenir.
        getContentPane().add(p1);
        
        //Buton oluşturmak için JButton kullanılır.
        
        buton.setText("Ara");
        buton.setBounds(620, 100, 145, 40);
        buton.addActionListener(this);
        p1.add(buton);

        // Sitenin başlığının rengini ve ismi ayarlanır.
        JLabel l1 = new JLabel();
        l1.setText("Cruisé");
        l1.setBounds(0, 0, 800, 50);  // Başlığın konumunu belirlenir.
        l1.setForeground(new Color(0, 0, 0));  // Yazı rengini ayarlanır.
        l1.setFont(new Font("Arial", Font.BOLD, 20)); // Yazı fontu ve büyüklüğü ayarlanır.
        l1.setOpaque(true);  // Satırın arka planını şeffaf yapılır.
        l1.setBackground(new Color(236, 77, 49));  // Satıra seçilen kırmızı renk yapılır.
        p1.add(l1);//Panele eklenir.
        
        // Şehir seçimi için panel oluşturulur.
        JLabel l2 = new JLabel();
        l2.setText("Şehir Seçiniz");
        l2.setBounds(420, 60, 200, 30); 
        l2.setForeground(Color.BLACK);  
        l2.setFont(new Font("Arial", Font.BOLD, 15));
        p1.add(l2);
        
        // Şehirleri ArrayList ile tutulur.
        String[] city = {"Seçiniz"
        		,"İstanbul","Ankara","İzmir","Bursa","Antalya","Ordu","Denizli","Mersin" ,"Trabzon","Mardin"};
        
        // Şehir için JComboBox oluşturuluyor.
        cityComboBox = new JComboBox(city);
      //ActionListener eklenir
        cityComboBox.addActionListener(this);
        cityComboBox.setBounds(420, 100, 170, 40);  

        p1.add(cityComboBox);// JComboBox panele ekleniyor

        //Tarih seçimi için panel oluşturulur.
        JLabel l3 = new JLabel();
        l3.setText("Tarih Seçiniz");
        l3.setBounds(220, 60, 200, 30); 
        l3.setForeground(Color.BLACK);  
        l3.setFont(new Font("Arial", Font.BOLD, 15));
        p1.add(l3);

        // Aylar Arraylist ile tutulur.
        String[] date = {"Seçiniz"
        		,"Ocak","Şubat","Mart","Nisan"
                ,"Mayıs"
                ,"Haziran"
                ,"Temmuz"
                ,"Ağustos"
                ,"Eylül"
                ,"Ekim"
                ,"Kasım",
                "Aralık"};
        

        // Tarih için JComboBox oluşturulur.
        dateComboBox = new JComboBox(date);
        //ActionListener eklenir
        dateComboBox.addActionListener(this);
        dateComboBox.setBounds(220, 100, 170, 40);  

        // Tarihleri JComboBox'a eklenir.
        p1.add(dateComboBox);
 
        //Kategori seçimi için panel oluşturulur.
        JLabel l4 = new JLabel();
        l4.setText("Kategori Seçiniz");
        l4.setBounds(20, 60, 200, 30); 
        l4.setForeground(Color.BLACK);  // Yazı rengini seçeriz.
        l4.setFont(new Font("Arial", Font.BOLD, 15));
        p1.add(l4);
        
        //Kategorilerimizi ArrayList ile tutulur.
        String[] categories = {"Seçiniz"
        		,"Sinema"
                ,"Tiyatro"
                ,"Bale"
                ,"Konser"
                ,"Seminer"
                ,"Festival"
                ,"Sergi"
                ,"Yoga"};


        // Kategori JComboBox oluşturulur.
        categoryComboBox = new JComboBox(categories);
        //ActionListener eklenir
        categoryComboBox.addActionListener(this);
        categoryComboBox.setBounds(20, 100, 170, 40);

        // Kategoriler JComboBox'a eklenir.
        p1.add(categoryComboBox);

        //Panele resim ekleme işlemleri.
        try {
            URL imageURL = new URL("https://static.vecteezy.com/system/resources/thumbnails/016/651/359/small/plane-silhouette-illustration-png.png");  
            ImageIcon imageIcon = new ImageIcon(imageURL); // Resmimizin URL'si eklenir.
     
            Image image = imageIcon.getImage();  // Resmi bir Image nesnesine dönüştürürüz.
           
            Image scaledImage = image.getScaledInstance(600, 300, Image.SCALE_SMOOTH); // Resmin boyutlarını ayarlamak için kullanılır.
            
            ImageIcon scaledImageIcon = new ImageIcon(scaledImage); // Boyutları seçilmiş resmi ImageIcon olarak tekrar yükler.
       
            JLabel imageLabel = new JLabel(scaledImageIcon);
            imageLabel.setBounds(98, 73, 568, 400);  //Resmin konum ve boyut ayarlaması yapılır.
            p1.add(imageLabel);

        } catch (Exception e) {
            e.printStackTrace();
        }

        setVisible(true); //Swing panelinin görünmesi sağlanır.
    }

    //Paneli gösterme işlemleri.
    public void showCruisePanel() {

        if (!this.isVisible()) {
            setVisible(true); // Cruise penceresi gösterilir.
            setLocationRelativeTo(null); // Paneli ortalar.

        }
    }

    //ActionListener'ın fonksüyonları
    public void actionPerformed(ActionEvent e) {
        // Kategori, şehir ve tarih bilgilerini dosyaya yazmaya devam ediyoruz.
        if(e.getSource()==categoryComboBox) {
            try {
            	FileWriter writer1 = new FileWriter("category.txt");
                writer1.write((String)categoryComboBox.getSelectedItem());
                writer1.close();
            } catch (IOException a) {
                a.printStackTrace();
            }
        }
        if(e.getSource()==cityComboBox) {
            try {
            	FileWriter writer2 = new FileWriter("city.txt");
                
                writer2.write((String)cityComboBox.getSelectedItem());
                writer2.close();
            } catch (IOException a) {
                a.printStackTrace();
            }
        }
        if(e.getSource()==dateComboBox) {
            try {
                FileWriter writer3 = new FileWriter("date.txt");
                
                writer3.write((String)dateComboBox.getSelectedItem());
                writer3.close();
            } catch (IOException a) {
                a.printStackTrace();
            }
        }

        // Ara butonuna basıldığında EventDetails panelini göster
        if(e.getSource()==buton) {
            
            
            setVisible(false);
            Choice choice = new Choice();
            choice.showChoicePanel();

        }
    }

}
