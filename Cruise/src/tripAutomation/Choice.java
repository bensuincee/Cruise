package tripAutomation;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import userAuthentication.LoginScreen;
import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;
import tripAutomation.Choice;

public class Choice extends JFrame{
	
	int no = 0;

    // Etkinliklerin depolandığı Map yapısı
	Map<String, String> events = new HashMap<>();

    // Etkinlik detaylarını tutan değişken
	private String eventDetails;

    // Şehir, kategori ve tarih seçimlerini tutacak ArrayList'ler
	ArrayList<Character> city = new ArrayList<Character>();
	 ArrayList<Character> category = new ArrayList<Character>();
	 ArrayList<Character> date = new ArrayList<Character>();

	JPanel p2 = new JPanel();
	ArrayList<String> İstanbul_Ocak_Konser = new ArrayList<String>();

    // Etkinlik detaylarını geçici olarak tutan değişken
	String eventDetails1;

	public Choice() {
		//Kullanıcının şehir seçimi city.txt dosyasından arrayliste aktarılır
		  try {
		   FileReader reader = new FileReader("city.txt");
		   int data = reader.read();
		   while(data != -1) {		   	   
		    	city.add((char)data);	    	
		    	data =reader.read();
		   }
		   reader.close();
		   
		  } catch (FileNotFoundException e) {
              // Dosya bulunamadığında hata mesajı verir.
              e.printStackTrace();
		  } catch (IOException e) {
              // Giriş/çıkış hatalarında mesaj
              e.printStackTrace();
		  }

		  //Kullanıcının kategori seçimi category.txt dosyasından arrayliste aktarılır
		  try {
			   FileReader reader = new FileReader("category.txt");
			   int data = reader.read();
			   while(data != -1) {

			    	category.add((char)data);
			    	data =reader.read();  
			    
			   }
			   reader.close();
			   
			  } catch (FileNotFoundException e) {
			   e.printStackTrace();
			  } catch (IOException e) {
			   e.printStackTrace();
			  }

		  //Kullanıcının tarih seçimi date.txt dosyasından arrayliste aktarılır
		  try {
			   FileReader reader = new FileReader("date.txt");
			   int data = reader.read();
			   while(data != -1) {			   		    
			    	date.add((char)data);		    	
			    	data =reader.read();		    		    		    
			   }
			   reader.close();
			   
			  } catch (FileNotFoundException e) {
                e.printStackTrace();
			  } catch (IOException e) {
			    e.printStackTrace();
			  }

        // ArrayListler içindeki veriler birleştirilip String olarak depolananır.
		  String city1 = city.stream()
	              .map(String::valueOf)
	              .collect(Collectors.joining());
		  String category1 = category.stream()
	              .map(String::valueOf)
	              .collect(Collectors.joining());
		  String date1 = date.stream()
	              .map(String::valueOf)
	              .collect(Collectors.joining());

          // Etkinliklerin haritaya eklenmesi
		  populateEvents();

          // Kullanıcının seçimlerine uygun anahtarın oluşturulması
		  String key2 = city1 + "_" + date1 + "_" + category1 + "_" + 1;
		  
		  String key ;	
		  String key1 ;

		  //Aynı kritirlere uygun kaç tane etkinlik olduğunu bulan döngü
          for(int i = 1;i<=events.size();i++)
          {
        	key = city1 + "_" + date1 + "_" + category1 + "_" + i;
        	if(events.containsKey(key))
        	{
        		no++;// Uygun etkinliklerin sayısını artır
        	}
          }

        //Panelin başlığı ayarlanır.
        setTitle("Cruisé.com");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kapatma işlemi
        setResizable(false); // Yeniden boyutlandırmayı devre dışı bırakır
        setLocationRelativeTo(null); // Ekranın ortasına yerleştirir

        // Panel oluşturulur
        JPanel p2 = new JPanel();
        p2.setLayout(null); // Serbest konumlandırma
        p2.setBackground(new Color(255, 255, 255)); // Arka plan beyaz yapılır
        getContentPane().add(p2); // Panel pencereye eklenir

        // Başlık etiketi
        JLabel l1 = new JLabel("Cruisé");
        l1.setBounds(0, 0, 800, 50);
        l1.setForeground(new Color(0, 0, 0));
        l1.setFont(new Font("Arial", Font.BOLD, 20));
        l1.setOpaque(true);
        l1.setBackground(new Color(236, 77, 49));
        l1.setHorizontalAlignment(SwingConstants.CENTER); // Metini ortalar.
        p2.add(l1);

        //Seçilen özellikler uygun etkinlik olup olmadığını kontrol eden if yapısı
        if (events.containsKey(key2) == false ) {
        	//Hata mesajı
        	JOptionPane.showMessageDialog(null,  "Bu kategori, şehir ve tarihe uygun bir etkinlik bulunamadı.","Etkinlik Bulunamadı", JOptionPane.ERROR_MESSAGE);

            //Kullanıcı hata mesajını gördükten sonra önceki sekmeye dönebilmesi için geri butonu eklenir.
		        p2.setVisible(true);
		        JButton backButton = new JButton("Geri");
		        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
		        backButton.setBounds(320, 420, 145, 40);
		        backButton.addActionListener(e -> {
		            this.setVisible(false);
		            Cruise cruise = new Cruise();
		            cruise.showCruisePanel();
		        });
		        p2.add(backButton);

            //Programı sonlandırmak için
            return;
        }

        // Etkinliklerin liste şeklinde gösterilmesi
        int yPosition = 70; // İlk etiketin başlangıç yüksekliği
        for (int i = 1; i <= no; i++) {
        	JButton dynamicButton = new JButton("Seç");
            dynamicButton.setBounds(400, yPosition, 100, 30); // Düğmenin konum ve boyut ayarları
            dynamicButton.setBackground(new Color(236, 77, 49)); // Arka plan rengi
            dynamicButton.setForeground(Color.WHITE); // Yazı rengi
            dynamicButton.setFocusPainted(false); // Kenar vurgusunu kaldır
            p2.add(dynamicButton); // Düğmeyi panele ekle
            JLabel dynamicLabel = new JLabel(events.get(city1 + "_" + date1 + "_" + category1 + "_" + i));
            dynamicLabel.setBounds(50, yPosition, 700, 30); // Etiketin konum ve boyut ayarları
            dynamicLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // Yazı tipi ve boyutu
            dynamicLabel.setForeground(Color.BLACK); // Yazı rengi
            dynamicLabel.setBackground(new Color(220, 220, 220)); // Arka plan rengi
            dynamicLabel.setOpaque(true); // Arka plan rengini etkinleştir
            p2.add(dynamicLabel); // Etiketi panele ekle


            // Düğme oluşturma
            // Düğmeye benzersiz bir int ActionCommand değeri atama
            int actionCommandInt = i; // ActionCommand için int değeri
            dynamicButton.setActionCommand(String.valueOf(actionCommandInt)); // int değeri String'e dönüştürülür

            // Düğmeye tıklama işlevi
            dynamicButton.addActionListener(e -> {
            	
                // Tıklanan düğmenin ActionCommand'ini al (String olarak)
                String actionCommandString = e.getActionCommand(); // String olarak al
                int Secim = Integer.parseInt(actionCommandString); // String'i int'e dönüştür

                //Seçime göre bilet sayfasını aç
                setVisible(false);
                EventDetails1 event = new EventDetails1(category1,city1,date1,Secim);
            });
           
            yPosition += 40; // Sonraki etiket için dikey konumu artır
        }

        //Sayfada görünürlük sorunlarının düzelmesi için
        p2.revalidate();
        p2.repaint();

        //Geri butonunun eklenmesi
        JButton backButton = new JButton("Geri");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBounds(320, 420, 145, 40);
        backButton.addActionListener(e -> {
            this.setVisible(false);
            Cruise cruise = new Cruise();
            cruise.showCruisePanel();
        });
        p2.add(backButton);

	}

	//Panelin görünür olması için
	public void showChoicePanel() {

        if (!this.isVisible()) {
            setVisible(true); // Cruise penceresi gösterilir.
            setLocationRelativeTo(null); // Paneli ortalar.

        }
    
}
	// Etkinlik verilerini map'e ekliyoruz
	private void populateEvents() {
		events.put("İstanbul_Ocak_Konser_1","Sagopa Kajmer Konseri");
        events.put("İstanbul_Ocak_Konser_2","Mabel Matiz 360");
        events.put("İstanbul_Mart_Konser_1","Emir Can İğrek Konseri");
        events.put("İstanbul_Mart_Konser_2","Yıldız Tilbe Konseri");
        events.put("İstanbul_Haziran_Konser_1","Zakkum Konseri");
        events.put("İstanbul_Aralık_Konser_1","Simge Sağın İle Yılbaşı");
        events.put("Ankara_Ocak_Konser_1","Kalben Konseri");
        events.put("Ankara_Nisan_Konser_1","Dedublüman Konseri");
        events.put("Ankara_Mayıs_Konser_1","İrem Derici Konseri");
        events.put("Ankara_Ağustos_Konser_1","Tuğkan Konseri");
        events.put("İzmir_Ocak_Konser_1","Berkay Konseri");
        events.put("İzmir_Mart_Konser_1","Gökhan Türkmen Konseri");
        events.put("İzmir_Temmuz_Konser_1","Skapova Konseri");
        events.put("Antalya_Nisan_Konser_1","ATİ242 Konseri");
        events.put("Antalya_Ağustos_Konser_1","Melek Mosso Konseri");
        events.put("İstanbul_Ocak_Sinema_1","Gladyatör 2");
        events.put("İstanbul_Şubat_Sinema_1","Vahşi Robot");
        events.put("İstanbul_Nisan_Sinema_1","Ters Yüz 2");
        events.put("İstanbul_Haziran_Sinema_1","Bir Cumhuriyet Şarkısı");
        events.put("İstanbul_Eylül_Sinema_1","Doğulu");
        events.put("Denizli_Ekim_Sinema_1","Sayara: İntikam Meleği");
        events.put("Mardin_Mayıs_Sinema_1","Mustafa");
        events.put("Trabzon_Kasım_Sinema_1","Yandaki Oda");
        events.put("Ordu_Aralık_Sinema_1","Yeni Yıl Şarkısı");
        events.put("Antalya_Şubat_Sinema_1","Başlangıçlar");
        events.put("İstanbul_Ocak_Tiyatro_1","Aşk Hikayen Düşmüş");
        events.put("İstanbul_Eylül_Tiyatro_1","Meçhul Paşa");
        events.put("İstanbul_Ekim_Tiyatro_1","Piramitlere Yolculuk - Antik Mısır'ın Keşfi");
        events.put("Ankara_Nisan_Tiyatro_1","Anna Karenina");
        events.put("Trabzon_Ocak_Tiyatro_1","Ölü'n Bizi Ayırana Dek Oyunu");
        events.put("Denizli_Haziran_Tiyatro_1","Prenses Hamlet Oyunu");
        events.put("Ordu_Kasım_Tiyatro_1","Bir Delinin Hatıra Defteri");
        events.put("İstanbul_Mayıs_Seminer_1","Kariyer Planlamasında Dönüm Noktaları");
        events.put("İstanbul_Şubat_Seminer_1","Yapay Zeka Çağında Girişimcilik");
        events.put("İstanbul_Temmuz_Seminer_1","Zihinsel Sağlık ve Stres Yönetimi");
        events.put("Trabzon_Haziran_Seminer_1","Anadolu'da Türk İzleri");
        events.put("Mersin_Temmuz_Seminer_1","Psikoloji Zirvesi");
        events.put("İstanbul_Temmuz_Festival_1","Milyonfest İstanbul");
        events.put("İstanbul_Haziran_Festival_1","Rock Festivali");
        events.put("Antalya_Eylül_Festival_1","Antalya Altın Portakal Film Festivali");
        events.put("İzmir_Ağustos_Festival_1","İzmir Enternasyonal Fuarı");
        events.put("İstanbul_Mart_Sergi_1","Osmanlı'nın İzinde: Sanat ve Mimari");
        events.put("Ankara_Şubat_Sergi_1","Cumhuriyetin Renkleri: Türk Ressamlarının İzinde");
        events.put("İzmir_Ekim_Sergi_1","Ege'nin Doğası: Deniz ve Dağların Buluşması");
        events.put("İstanbul_Ocak_Bale_1","Kuğu Gölü");
        events.put("Ordu_Nisan_Bale_1","Mevsimler");
        events.put("Mersin_Kasım_Bale_1","Alice Harikalar Diyarında");
        events.put("Bursa_Aralık_Bale_1","Troya: Bir Destanın Dansı");
        events.put("İstanbul_Eylül_Yoga_1","Huzur İstanbul'da: Yoga ile Yenilen");
        events.put("Mardin_Mart_Yoga_1","Beden ve Zihin Dengesi");
        events.put("İzmir_Mayıs_Yoga_1","Sahilde Yoga Günü");
	}

}