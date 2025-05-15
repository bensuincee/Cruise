package tripAutomation;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import userAuthentication.LoginScreen;

public class EventDetails1 extends JFrame {
    // Etkinlikleri depolamak için bir HashMap oluşturulur.
    Map<String, String[]> events = new HashMap<>();
    private String[] eventDetails; // Etkinlik detaylarını tutan dizi
    private JLabel stockLabel;

    public EventDetails1(String category, String city, String date, int no) {
        populateEvents();// Etkinlik verilerini haritaya yükle

        // Etkinlik anahtarını oluştur
        String key = city + "_" + date + "_" + category + "_" + no;

        // Etkinlik bilgilerini al
        eventDetails = events.get(key);


        // Panel ve genel düzen oluşumu
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(255, 255, 255));
        getContentPane().add(panel);
        
        // Başlık
        JLabel label = new JLabel("Cruisé - Etkinlik Detayları");
        label.setBounds(0, 0, 800, 50);
        label.setForeground(new Color(0, 0, 0));
        label.setFont(new Font("Arial", Font.BOLD, 20));
        label.setOpaque(true);
        label.setBackground(new Color(236, 77, 49));
        setLocationRelativeTo(null);
        panel.add(label);

        // Etkinlik bilgilerini gösteren etiketler.
        JLabel eventLabel = new JLabel("Etkinlik: " + eventDetails[0]);
        eventLabel.setBounds(20, 100, 400, 30);
        eventLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(eventLabel);

        JLabel categoryLabel = new JLabel("Kategori: " + category);
        categoryLabel.setBounds(20, 140, 400, 30);
        panel.add(categoryLabel);

        JLabel cityLabel = new JLabel("Şehir: " + city);
        cityLabel.setBounds(20, 180, 400, 30);
        panel.add(cityLabel);

        JLabel dateLabel = new JLabel("Tarih: " + date);
        dateLabel.setBounds(20, 220, 400, 30);
        panel.add(dateLabel);

        // Stok bilgisi gösterimi
        int currentStock = TicketManager.getStock(eventDetails[0]);
        stockLabel = new JLabel("Kalan Bilet: " + currentStock);
        stockLabel.setBounds(22, 390, 400, 25);
        stockLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(stockLabel);

        // Etkinlik açıklaması ve scroll pane
        JTextArea eventDescription = new JTextArea(eventDetails[1]);
        eventDescription.setEditable(false);
        eventDescription.setLineWrap(true);
        eventDescription.setWrapStyleWord(true);
        eventDescription.setFont(new Font("Arial", Font.PLAIN, 14));
        eventDescription.setForeground(new Color(50, 50, 50));

        JScrollPane scrollPane = new JScrollPane(eventDescription);
        scrollPane.setBounds(20, 260, 400, 80);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.RED, 2)); // 2 piksel kırmızı kenarlık
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane);

        // Bilet Satın Al butonu oluşturulur.
        JButton buyTicketButton = new JButton("Bilet Satın Al");
        buyTicketButton.setFont(new Font("Arial", Font.PLAIN, 14));
        buyTicketButton.setBounds(20, 350, 145, 40);
        buyTicketButton.addActionListener(e -> {
            int stock = TicketManager.getStock(eventDetails[0]);
            if (stock > 0) {
                String ticketCode = TicketManager.generateTicketCode();
                TicketManager.saveTicket(LoginScreen.getCurrentUsername(), eventDetails[0], city, date, ticketCode);
                
                JOptionPane.showMessageDialog(this, "Bilet satın alındı! Kod: " + ticketCode, "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Üzgünüz, tüm biletler tükenmiştir.", "Bilet Tükendi", JOptionPane.WARNING_MESSAGE);
            }
        });
        panel.add(buyTicketButton);

        // Favorilere Ekle butonu oluşturulur.
        JButton addToFavoritesButton = new JButton("Favorilere Ekle");
        addToFavoritesButton.setFont(new Font("Arial", Font.PLAIN, 14));
        addToFavoritesButton.setBounds(180, 350, 145, 40);  // Y koordinatını 390'a aldık
        addToFavoritesButton.addActionListener(e -> {
            // Aynı etkinliğin tekrar eklenmesini ve "Etkinlik Bulunamadı" yazısının eklenmesini önler
            if (eventDetails[0].equals("Etkinlik Bulunamadı")) {
                JOptionPane.showMessageDialog(this, "Bu etkinlik favorilere eklenemez!", "Hata", JOptionPane.ERROR_MESSAGE);
            } else if (FavoritesManager.isAlreadyFavorite(LoginScreen.getCurrentUsername(), eventDetails[0], city, date)) {
                JOptionPane.showMessageDialog(this, "Bu etkinlik zaten favorilere eklenmiş!", "Hata", JOptionPane.ERROR_MESSAGE);
            } else {
                String favCode = TicketManager.generateTicketCode();
                FavoritesManager.addFavorite(LoginScreen.getCurrentUsername(), eventDetails[0], city, date, favCode);
                JOptionPane.showMessageDialog(this, "Favorilere eklendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(addToFavoritesButton);

        // Biletlerim butonu oluşturulur.
        JButton ticketsButton = new JButton("Biletlerim");
        ticketsButton.setFont(new Font("Arial", Font.PLAIN, 14));
        ticketsButton.setBounds(340, 350, 145, 40);  // Y koordinatını 390'a aldık
        ticketsButton.addActionListener(e -> {
            this.setVisible(false);
            new Biletlerim(new java.util.ArrayList<>(), this);
        });
        panel.add(ticketsButton);

        // Favorilerim butonu oluşturulur.
        JButton favoritesButton = new JButton("Favorilerim");
        favoritesButton.setFont(new Font("Arial", Font.PLAIN, 14));
        favoritesButton.setBounds(500, 350, 145, 40);  // Y koordinatını 390'a aldık
        favoritesButton.addActionListener(e -> {
            try {
                this.setVisible(false);
                new FavouritesPage(LoginScreen.getCurrentUsername(), this);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Favorilerim ekranı açılamadı.", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(favoritesButton);

        //Geri butonu oluşturulur.
        JButton backButton = new JButton("Geri");
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.setBounds(320, 420, 145, 40);
        backButton.addActionListener(e -> {
            this.setVisible(false);
            Choice choice = new Choice();
            choice.showChoicePanel();
        });
        panel.add(backButton);

        // Pencere ayarları yapılır.
        setTitle("Cruisé - Etkinlik Detayları");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Etkinlik verilerini map'e ekliyoruz
    private void populateEvents() {
        // Buraya etkinlikleri ekledim
        events.put("İstanbul_Ocak_Konser_1", new String[] {"Sagopa Kajmer Konseri","Etkinlik Detayları: Rap müziğine yıllarını vermiş, her bestesiyle ruhumuza dokunan Sagopa Kajmer sizlerle buluşmaya geliyor! Eşsiz bir rap gecesi yaşamak istiyorsanız biletleriniz Cruisé'da."});
        events.put("İstanbul_Ocak_Konser_2", new String[] {"Mabel Matiz 360","Etkinlik Detayları: Mabel Matiz'in büyüleyici sesiyle 360 derece sahne şovunun içinde kaybolmaya hazır mısınız? Atlantis Yapım organizasyonuyla, 14-15 Şubat'ta Volkswagen Arena'da gerçekleşecek bu eşsiz performansı kaçırmayın!"});
        events.put("İstanbul_Mart_Konser_1", new String[] {"Emir Can İğrek Konseri","Etkinlik Detayları: 'Nalan', 'Dargın', 'Beyaz Skandalım' ve 'Kor' gibi şarkıların mimarı Emir Can İğrek, unutulmaz bir konsere imza atmaya geliyor. Sen de Emir Can İğrek konserinde unutulmaz bir gece yaşamak istiyorsan, seni de aramızda görmeyi çok isteriz!"});
        events.put("İstanbul_Mart_Konser_2", new String[] {"Yıldız Tilbe Konseri","Etkinlik Detayları: Yıldız Tilbe, muhteşem sahne performansıyla sevenleriyle buluşuyor. Sen de bu güzel gecede bizimle birlikte eğlenmek istiyorsan, bu fırsatı kaçırma. Unutma Cruisé senin!"});
        events.put("İstanbul_Haziran_Konser_1", new String[] {"Zakkum Konseri","Etkinlik Detayları: Grup Zakkum, sevilen şarkılarını hayranlarıyla birlikte seslendiriyor. 'Anason' ve 'Ben Ne Yangınlar Gördüm' şarkılarıyla gönüllere kazınan grup, dinleyicisine keyif dolu bir gece yaşatmayı vaadediyor."});
        events.put("İstanbul_Aralık_Konser_1", new String[] {"Simge Sağın İle Yılbaşı","Etkinlik Detayları: Ünlü şarkıcı Simge Sağın, yılbaşında sevenleriyle buluşuyor. Sende bu eşsiz atmosferde bulunmak istiyorsan Cruisé'da biletini kaçırma!"});
        events.put("Ankara_Ocak_Konser_1", new String[] {"Kalben Konseri","Etkinlik Detayları: Kalben, sevilen tüm şarkılarını söylemek için şehrine geliyor! Onun enerjisine dahil olmak için, unutma Cruisé senin!"});
        events.put("Ankara_Nisan_Konser_1", new String[] {"Dedublüman Konseri","Etkinlik Detayları: 'Gamzedeyim Deva Bulmam', 'Ah Bir Ataş Ver', 'Fikrimin İnce Gülü', 'Çözemezsin', 'Sakladığın Bir Şeyler Var',  'Belki' ve 'En Dibine Kadar' gibi şarkılara yaptıkları farklı coverlar ile son döneme damga vuran Dedublüman, dinleyicileriyle buluşuyor. Sen de bu güzel gecede Dedublüman ile eğlenmek istiyorsan, unutma Cruisé senin!"});
        events.put("Ankara_Mayıs_Konser_1", new String[] {"İrem Derici Konseri","Etkinlik Detayları: Türk pop müziğinin en enerjik ve esprili kadın vokallerinden İrem Derici, milyonların diline dolanan şarkılarını sevenleriyle birlikte söylüyor."});
        events.put("Ankara_Ağustos_Konser_1", new String[] {"Tuğkan Konseri","Etkinlik Detayları: 'Kusura Bakma', 'Civciv', 'Ele Layık' gibi şarkıları ile tanınan Tuğkan, dinleyicisiyle buluşmaya devam ediyor. Sen de bu müzik dolu gecede şarkılara eşlik etmek istiyorsan, bu fırsatı kaçırma. Unutma Cruisé senin!"});
        events.put("İzmir_Ocak_Konser_1", new String[] {"Berkay Konseri","Etkinlik Detayları: Berkay, en sevilen parçaları ile SUNSET sahnesinde İzmirlilere unutulmaz bir gece yaşatmaya hazırlanıyor."});
        events.put("İzmir_Mart_Konser_1", new String[] {"Gökhan Türkmen Konseri","Etkinlik Detayları: Gökhan Türkmen, 8 Mart Dünya Kadınlar Gününe Özel konseri ile unutulmaz bir akşam için sizleri bekliyor."});
        events.put("İzmir_Temmuz_Konser_1", new String[] {"Skapova Konseri","Etkinlik Detayları: 'Ben Senden Vazgeçtim', 'Sen Bana Aitsin', 'Neden Bu Kadar Güzelsin' ve 'Ben Hala Vazgeçmedim' isimli şarkıları ile popülerleşen Skapova, dinleyicileriyle buluşmaya devam ediyor."});
        events.put("Antalya_Nisan_Konser_1", new String[] {"ATİ242 Konseri","Etkinlik Detayları: Rap piyasasına hızlı bir şekilde giriş yapıp, listeleri kasıp kavuran Antalyalı rapçi Ati242, Alman ekolü ve newschool ruhunu ülkemizde yaşatmaya devam ediyor. Sizde Ati242'nin kendine has rap müziğini dinlemek için biletleri hemen kapın!"});
        events.put("Antalya_Ağustos_Konser_1", new String[] {"Melek Mosso Konseri","Etkinlik Detayları: 'Melek Mosso gelse de dinlesek' diyenlerdenseniz bu etkinlik tam size göre! Son çıkardığı 'Hayatım Kaymış' ile beğeni toplayan Melek Mosso, sahne performansıyla da izleyicisine keyifli bir akşam yaşatacak."});
        events.put("İstanbul_Ocak_Sinema_1", new String[] {"Gladyatör 2","Etkinlik Detayları: Antik Roma'da ihanet ve intikamla dolu bir yolculuk: Gladyatör II'de Lucius'ın destansı hikayesi."});
        events.put("İstanbul_Şubat_Sinema_1", new String[] {"Vahşi Robot","Etkinlik Detayları: Roz, ıssız adada dostluk ve aileyi keşfederken hayatta kalmanın gerçek anlamını öğreniyor!"});
        events.put("İstanbul_Nisan_Sinema_1", new String[] {"Ters Yüz 2","Etkinlik Detayları: Ergen Riley, duygularla dolu çılgın maceralara atılıyor!"});
        events.put("İstanbul_Haziran_Sinema_1", new String[] {"Bir Cumhuriyet Şarkısı","Etkinlik Detayları: Genç Türkiye'nin modernleşme yolculuğu: 1930'ların değişim rüzgarlarıyla şekillenen bir ülke!"});
        events.put("İstanbul_Eylül_Sinema_1", new String[] {"Doğulu","Etkinlik Detayları: Adaletin bittiği yerde intikam başlar; Fırat ve Can, geçmişin hesaplarını kapatmaya kararlı!"});
        events.put("Denizli_Ekim_Sinema_1", new String[] {"Sayara: İntikam Meleği","Etkinlik Detayları: Adaleti aramak için sessiz değil, güçlü olmalı. Sayara'nın intikamı kaçınılmaz."});
        events.put("Mardin_Mayıs_Sinema_1", new String[] {"Mustafa","Etkinlik Detayları: Mustafa Kemal'in çocukluk hayali, Kemal'e ilham olur: Büyük hayallerin peşinde bir yolculuk başlıyor!"});
        events.put("Trabzon_Kasım_Sinema_1", new String[] {"Yandaki Oda","Etkinlik Detayları: Yıllar sonra kesişen yollar: Ingrid ve Martha, zorlu hayat deneyimlerinin ardından dostluklarını yeniden keşfederler."});
        events.put("Ordu_Aralık_Sinema_1", new String[] {"Yeni Yıl Şarkısı","Etkinlik Detayları: Geçmiş, bugün ve geleceğin hayaletleriyle yüzleşen Scrooge, kalbini değişimle ısıtıyor!"});
        events.put("Antalya_Şubat_Sinema_1", new String[] {"Başlangıçlar","Etkinlik Detayları: Gizemli bir tablo, kaybolan bir dost ve kendi içine doğru bir yolculuk!"});
        events.put("İstanbul_Ocak_Tiyatro_1", new String[] {"Aşk Hikayen Düşmüş","Etkinlik Detayları: Ruh eşini arama ve bulma serüveni..."});
        events.put("İstanbul_Eylül_Tiyatro_1", new String[] {"Meçhul Paşa","Etkinlik Detayları: Mizah gazetesi Marko Paşa'nın günlüğünü tutan şenlikli bir ortaoyunu..."});
        events.put("İstanbul_Ekim_Tiyatro_1", new String[] {"Piramitlere Yolculuk - Antik Mısır'ın Keşfi","Etkinlik Detayları: Piramitlere Yolculuk: Antik Mısır'ın Keşfi, ziyaretçileri 4.500 yıl öncesine götürerek Kral Khufu'nun cenaze törenine tanıklık etme ve Giza Platosu, Sfenks ve Büyük Piramit'in gizli alanlarını keşfetme fırsatı sunacak. Aynı anda 100 kişiye kadar ziyaretçiyi ağırlayabilecek kapasiteye sahip olan Müzeverse, katılımcıları pasif izleyicilerden aktif tarih kaşiflerine dönüştüren eşsiz bir toplu deneyim sunuyor."});
        events.put("Ankara_Nisan_Tiyatro_1", new String[] {"Anna Karenina","Etkinlik Detayları: Lev Tolstoy tarafından kaleme alınan ve 125 farklı yazar tarafından tarihin en iyi romanı seçilen Anna Karenina Pray Tiyatro ile sahneye taşınıyor."});
        events.put("Trabzon_Ocak_Tiyatro_1", new String[] {"Ölü'n Bizi Ayırana Dek Oyunu","Etkinlik Detayları: Cansu ve Serdar; evlilik hayatı canlarına tak etmiş ve boşanmaya karar vermiş bir çifttir. Boşanmadan bir gece önce, kendilerine düzenlenen bir 'kutlama' partisi ardından, sabah uyandıklarında ise birbirlerini sızmış olarak aynı yatakta bulurlar. Bir gece öncesine dair çok az şey hatırlayan Cansu ve Serdar'ı bekleyen asıl sürpriz salondaki kanepede uzanmakta olan cesettir. Ne yapacağını bilemeyen çiftimiz bir yandan 'Hangimiz katil?' sorusuna yanıt ararken bir yandan da ilişkilerini, evliliklerini, geçmişlerini, kendilerini bulundukları noktaya taşıyan olayları yeniden değerlendirecek ve karşılarına çıkacak bambaşka sürprizlerle mücadele etmeye çalışacaktır."});
        events.put("Denizli_Haziran_Tiyatro_1", new String[] {"Prenses Hamlet Oyunu","Etkinlik Detayları: Penses Hamlet ; Danimarka kralı olan babasının ölümünden sonra bunalıma girer. Eski kralın ölümü üzerinden daha iki ay geçmeden, amcası Claudius annesiyle evlenmiş ve tahta geçmiştir. Bu durum , onun tahammül eşiğini iyice zorlamıştır. Keza bazı gerçekleri de hatırlatmıştır; \"Yıldızlar ancak seyredilir..\" bu idea onu statüsel durumları sorgulamaya ve gerçeklerle yüzleşmeye mahkum kılar. Poprişçin , girdiği düşünce harbinde, kendi ütopyasına göç eder. O artık İspanya kralı !"});
        events.put("Ordu_Kasım_Tiyatro_1", new String[] {"Bir Delinin Hatıra Defteri","Etkinlik Detayları: Sahnede bir deli.. Adı Poprişçin. Poprişçin bakanlıkta çalışan bir memurdur. Tek suçu \"soylu kişi\" olmamasıdır. Müdürünün kızına (Sofya) 'delicesine' tutkun olan Poprişçin, Sofya'nın bir binbaşıyla evlendirileceğini öğrenir. Bu durum , onun tahammül eşiğini iyice zorlamıştır. Keza bazı gerçekleri de hatırlatmıştır; \"Yıldızlar ancak seyredilir..\" bu idea onu statüsel durumları sorgulamaya ve gerçeklerle yüzleşmeye mahkum kılar. Poprişçin , girdiği düşünce harbinde, kendi ütopyasına göç eder. O artık İspanya kralı !"});
        events.put("İstanbul_Mayıs_Seminer_1", new String[] {"Kariyer Planlamasında Dönüm Noktaları","Etkinlik Detayları: Katılımcılar, CV hazırlama ve etkili mülakat teknikleri üzerine uygulamalı bir atölye çalışmasına katılacak.\nSeminer sonunda networking oturumu düzenlenecek.\nTüm katılımcılara katılım sertifikası verilecektir."});
        events.put("İstanbul_Şubat_Seminer_1", new String[] {"Yapay Zeka Çağında Girişimcilik","Etkinlik Detayları: Konuşmacı, yapay zeka girişimlerinin başarı hikayelerini ve sektördeki trendleri paylaşacak.\nEtkinlik sırasında mini hackathon düzenlenecek.\nKatılımcılar, yapay zeka odaklı iş fikirlerini sunma şansı yakalayacak."});
        events.put("İstanbul_Temmuz_Seminer_1", new String[] {"Zihinsel Sağlık ve Stres Yönetimi","Etkinlik Detayları: Seminer sırasında günlük yaşamda stresle başa çıkma yöntemleri üzerine interaktif bir bölüm yer alacak.\nKatılımcılara mindfulness tekniklerini deneyimleme fırsatı sunulacak.\nZihinsel sağlık konularında birebir danışmanlık hizmeti sunulacak."});
        events.put("Trabzon_Haziran_Seminer_1", new String[] {"Anadolu'da Türk İzleri","Etkinlik Detayları: Karadeniz Araştırmaları Enstitüsü tarafından gerçekleştirilmekte olan Karadeniz Konferanslarının konuğu Anadolu'da Erken Türk İzleri başlığı ile Atatürk Üniversitesi'nden Prof. Dr. Alparslan CEYLAN olacaktır."});
        events.put("Mersin_Temmuz_Seminer_1", new String[] {"Psikoloji Zirvesi","Türkiye'nin en çok tercih edilen eğitim ve gelişim platformu ALFA ETKİNLİK 2025 yılının ilk zirvesini sunar!"});
        events.put("İstanbul_Temmuz_Festival_1", new String[] {"Milyonfest İstanbul","Etkinlik Detayları: Milyonfest İstanbul 17-18-19-20 Temmuz tarihlerinde Milyon Beach Kilyos'da."});
        events.put("İstanbul_Haziran_Festival_1", new String[] {"Rock Festivali","Etkinlik Detayları: Türk Rock Müziğinin Geçit Töreni; Rock Festivali, 11. yılında İstanbul'da!"});
        events.put("Antalya_Eylül_Festival_1", new String[] {"Antalya Altın Portakal Film Festivali","Etkinlik Detayları: Türkiye'nin en prestijli film festivallerinden biri olan Antalya Altın Portakal Festivali, 1964 yılından beri gerçekleştirilmeye devam ediyor. Festival, Antalya Büyükşehir Belediyesi ve Antalya Kültür Sanat Vakfının ortak katkılarıyla sonbahar aylarında düzenleniyor. 2005 yılından bu yana Uluslararası Antalya Altın Portakal Film Festivali olarak anılan bu etkinlik, dünya sinemasından seçkin örnekleri sanatseverlere sunuyor. Bu büyük festival, film gösterimlerinin yanında paneller, atölyeler ve söyleşilerle daha da zenginleşiyor!"});
        events.put("İzmir_Ağustos_Festival_1", new String[] {"İzmir Enternasyonal Fuarı","Etkinlik Detayları: Dedelerimizden günümüze neredeyse bir asırdır süregelen bir gelenek… Düşünebiliyor musun? İzmir Enternasyonal Fuarı tam 93 yıldır aralıksız bir şekilde düzenleniyor! En önemlisi de pandemi döneminde bile fiziki olarak gerçekleştirilen tek fuar olması. Bu fuar her yıl ağustos ve eylül aylarında İzmir'in gözbebeği Kültürpark'ta gerçekleştiriliyor. Türkiye'nin en köklü ve en popüler fuarı olan İzmir Enternasyonal Fuarı; kültür, sanat, ticaret ve daha pek çok alanda zengin bir tema sunuyor. Ayrıca Türkiye'nin ilk televizyon yayını gibi birçok yenilik de burada tanıtılmış. Kim bilir, belki de geleceğin yeniliklerinden birini de ilk sen göreceksin!"});
        events.put("İstanbul_Mart_Sergi_1", new String[] {"Osmanlı'nın İzinde: Sanat ve Mimari","Etkinlik Deatayları: Sergi, Osmanlı dönemine ait hat sanatı, minyatürler ve mimari planlar gibi eserleri içerir.\nSanat tarihçileri tarafından günlük rehberli turlar düzenlenecektir.\nEtkinlikte Osmanlı mutfağından örnekler sunulacaktır."});
        events.put("Ankara_Şubat_Sergi_1", new String[] {"Cumhuriyetin Renkleri: Türk Ressamlarının İzinde","Etkinlik Detayları: Cumhuriyet dönemi Türk ressamlarının eserlerine yer verilen sergide yağlı boya ve akrilik çalışmalar sergilenecektir.\nAtölyeler: Katılımcılar için canlı resim yapma etkinlikleri düzenlenecek.\nSergi boyunca klasik Türk müziği eşliğinde sanat etkinlikleri yapılacaktır."});
        events.put("İzmir_Ekim_Sergi_1", new String[] {"Ege'nin Doğası: Deniz ve Dağların Buluşması","Etkinlik Detayları: Sergide Ege Bölgesi'nin eşsiz doğasını yansıtan fotoğraflar yer alır.\nFotoğraf sanatçılarıyla tanışma ve söyleşi etkinlikleri düzenlenecek.\nÇocuklar için fotoğrafçılık eğitim atölyesi yer alacaktır."});
        events.put("İstanbul_Ocak_Bale_1", new String[] {"Kuğu Gölü","Etkinlik Detayları: Çaykovski'nin ölümsüz eseri \"Kuğu Gölü\" klasik bale gösterisi olarak sahnelenecek.\nGösteri, ünlü koreograf Alexander Ivanov'un düzenlemesiyle izleyicilerle buluşacak.\nOrkestra, canlı performans ile gösteriye eşlik edecektir."});
        events.put("Ordu_Nisan_Bale_1", new String[] {"Mevsimler","Etkinlik Detayları: Vivaldi'nin \"Dört Mevsim\" eseri, modern bale tarzında yorumlanacak.\nGösteride dijital sahne efektleri ve ışık şovları kullanılacak.\nİzleyiciler için etkinlik sonrası dansçılarla tanışma oturumu düzenlenecektir."});
        events.put("Mersin_Kasım_Bale_1", new String[] {"Alice Harikalar Diyarında","Etkinlik Detayları: Çocuklar için özel olarak hazırlanmış bu bale gösterisi, renkli kostümler ve eğlenceli sahne tasarımı ile sunulacak.\nGösteri interaktif bir bölüm içeriyor; çocuklar sahneye katılabilecek.\nGösteri sonrası çocuklar için bale eğitimi atölyesi düzenlenecek."});
        events.put("Bursa_Aralık_Bale_1", new String[] {"Troya: Bir Destanın Dansı","Etkinlik Detayları: Homeros'un \"İlyada\" destanı bale ile sahneye taşınacak.\nGösteride Anadolu kültürüne ait motifler kullanılacak.\nEtkinlik, açık hava sahnesinde düzenlenecektir."});
        events.put("İstanbul_Eylül_Yoga_1", new String[] {"Huzur İstanbul'da: Yoga ile Yenilen","Etkinlik Detayları: Sabah yoga seansı: Güneş doğarken açık havada Vinyasa yoga.\nMeditasyon: Doğada rehber eşliğinde nefes teknikleri ve mindfulness çalışmaları.\nAtölyeler: Yoga felsefesi, sağlıklı beslenme, ve aromaterapi üzerine seminerler.\nKatılımcılara özel organik ikramlar sunulacaktır."});
        events.put("Mardin_Mart_Yoga_1", new String[] {"Beden ve Zihin Dengesi","Etkinlik Detayları: Hatha yoga seansı: Tüm seviyelere uygun temel yoga hareketleri.\nNefes çalışmaları: Diyafram nefesi ve stresten arınma teknikleri.\nSöyleşi: \"Modern Hayatta Yoga ve Stres Yönetimi\" konulu panel.\nYoga matı ve aksesuarlar katılımcılara ücretsiz sağlanacaktır."});
        events.put("İzmir_Mayıs_Yoga_1", new String[] {"Sahilde Yoga Günü","Etkinlik Detayları: Sabah başlangıç: Gün doğumu Ashtanga yoga pratiği.\nPlaj aktiviteleri: Kumda meditasyon ve doğa yürüyüşü.\nÖzel dersler: Yoga başlangıç rehberi ve ileri seviye pozlar.\nEtkinlik sonunda katılımcılara bitki çayı ikramı yapılacaktır."});
    }

    public void showChoicePanel() {
        if (!this.isVisible()) {
            setVisible(true); // Cruise penceresi gösterilir.
            setLocationRelativeTo(null); // Paneli ortalar.
        }
    }
}

