import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.List;

public class ApiOdev{
    public static String[] meyveler;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            String apiUrl = "https://www.fruityvice.com/api/fruit/all";
            URI uri = URI.create(apiUrl);
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader oku = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String satir;
            StringBuilder veri = new StringBuilder();
            while ((satir = oku.readLine()) != null) {
                veri.append(satir);
            }
            oku.close();

            FileWriter fw = new FileWriter("meyveler.txt", Charset.forName("UTF-8"), false);
            String[] meyveBloklari = veri.toString().split("\\},");

            for (String blok : meyveBloklari) {
                String isim = null;
                String familya = null;
                String kaloriStr = null;
                String sekerStr = null;
                String proteinStr = null;

                String[] bolumler = blok.split(",");

                for (String b : bolumler) {
                    if (b.contains("\"name\"")) {
                        String[] parca = b.split(":");
                        if (parca.length > 1) {
                            isim = parca[parca.length - 1].replace("\"", "").replace("{", "").replace("}", "")
                                    .replace("[", "").replace("]", "").trim();
                        }
                    } else if (b.contains("\"family\"")) {
                        String[] parca = b.split(":");
                        if (parca.length > 1) {
                            familya = parca[parca.length - 1].replace("\"", "").replace("{", "").replace("}", "")
                                    .replace("[", "").replace("]", "").trim();
                        }
                    } else if (b.contains("\"calories\"")) {
                        String[] parca = b.split(":");
                        if (parca.length > 1) {
                            kaloriStr = parca[parca.length - 1].replace("\"", "").replace("{", "").replace("}", "")
                                    .replace("[", "").replace("]", "").trim();
                        }
                    } else if (b.contains("\"sugar\"")) {
                        String[] parca = b.split(":");
                        if (parca.length > 1) {
                            sekerStr = parca[parca.length - 1].replace("\"", "").replace("{", "").replace("}", "")
                                    .replace("[", "").replace("]", "").trim();
                        }
                    } else if (b.contains("\"protein\"")) {
                        String[] parca = b.split(":");
                        if (parca.length > 1) {
                            proteinStr = parca[parca.length - 1].replace("\"", "").replace("{", "").replace("}", "")
                                    .replace("[", "").replace("]", "").trim();
                        }
                    }
                }

                if (isim != null && familya != null && kaloriStr != null && sekerStr != null && proteinStr != null) {
                    int kalori = Integer.parseInt(kaloriStr);
                    double seker = Double.parseDouble(sekerStr);
                    double protein = Double.parseDouble(proteinStr);

                    String satir1 = isim + ";" + familya + ";" + kalori + ";" + seker + ";" + protein;
                    fw.write(satir1 + "\n");
                }

            }
            fw.close();
            boolean devam = true;
            while (devam) {
                devam = anaMenu();
            }

        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static boolean anaMenu() {
        System.out.println("\n====== MEYVE MENÜSÜ ======");
        System.out.println("[1] Meyveleri Listele");
        System.out.println("[2] Meyve Özelleştir (Diyet/Form)");
        System.out.println("[3] Meyve Sil");
        System.out.println("[4] Sistemden Çıkış");
        System.out.print("Seçiminiz: ");

        int secim = scanner.nextInt();
        scanner.nextLine();

        switch (secim) {
            case 1:
                listeleMenu();
                break;
            case 2:
                meyveGuncelle();
                break;
            case 3:
                meyveSil();
                break;
            case 4:
                System.out.println("[!!!PROGRAM KAPANIYOR!!!]");
                return false;
            default:
                System.out.println("[Hatalı seçim yaptınız. Lütfen tekrar deneyin.]");
        }

        return true;
    }

    public static void listeleMenu() {
        System.out.println("\n====== LISTELEME MENÜSÜ ======");
        System.out.println("[1] Tüm meyveleri listele");
        System.out.println("[2] İstenilen meyveyi bul");
        System.out.println("[3] Kaloriye göre listele");
        System.out.println("[4] Şeker miktarına göre listele");
        System.out.println("[5] Protein miktarına göre listele");
        System.out.println("[6] Geri dön<===");
        System.out.print("Seçiminiz: ");

        int secim = scanner.nextInt();
        scanner.nextLine();
        switch (secim) {
            case 1:
                hepsiniYazdir();
                break;
            case 2:
                meyveBul();
                break;
            case 3:
                kaloriyeGoreYazdir();
                break;
            case 4:
                sekereGoreYazdir();
                break;
            case 5:
                proteineGoreYazdir();
                break;
            case 6:
                return;
            default:
                System.out.println("Hatalı seçim yaptınız. Lütfen tekrar deneyin.");
        }

    }

    public static void hepsiniYazdir() {
        try {
            List<String> satirlar = Files.readAllLines(new File("meyveler.txt").toPath(), Charset.forName("UTF-8"));
            System.out.println("\n-------------- TÜM MEYVELER ----------------");
            System.out.println("-----------------------------------------");
            for (String satir : satirlar) {
                String[] parcalar = satir.split(";");
                if (parcalar.length < 5) {
                    continue;
                }
                String isim = parcalar[0];
                String familya = parcalar[1];
                int kalori = Integer.parseInt(parcalar[2]);
                double seker = Double.parseDouble(parcalar[3]);
                double protein = Double.parseDouble(parcalar[4]);

                System.out.println("Meyve Adı : " + isim);
                System.out.println("Familyası : " + familya);
                System.out.println("Kalorisi  : " + kalori + " kcal");
                System.out.println("Şeker     : " + seker + " g");
                System.out.println("Protein   : " + protein + " g");
                System.out.println("---------------------------------");
            }

        } catch (Exception e) {
            System.out.println("Dosya okunurken bir hata oluştu.");
        }
    }

    public static void meyveBul() {
        System.out.print("[Aramak istediğiniz meyvenin adını giriniz: ");
        String arananMeyve = scanner.nextLine();

        try {
            List<String> satirlar = Files.readAllLines(new File("meyveler.txt").toPath(), Charset.forName("UTF-8"));

            boolean bulundu = false;

            for (String satir : satirlar) {
                String[] parcalar = satir.split(";");
                if (parcalar.length < 5) {
                    continue;
                }
                String isim = parcalar[0];
                if (isim.equalsIgnoreCase(arananMeyve)) {
                    String familya = parcalar[1];
                    int kalori = Integer.parseInt(parcalar[2]);
                    double seker = Double.parseDouble(parcalar[3]);
                    double protein = Double.parseDouble(parcalar[4]);

                    System.out.println("\n====== MEYVE BİLGİLERİ ======");
                    System.out.println("[Meyve Adı : " + isim + "]");
                    System.out.println("[Familyası : " + familya + "]");
                    System.out.println("[Kalorisi  : " + kalori + " kcal]");
                    System.out.println("[Şeker     : " + seker + " g]");
                    System.out.println("[Protein   : " + protein + " g]");
                    System.out.println("[=============================");

                    bulundu = true;
                    break;

                }
            }
            if (!bulundu) {
                System.out.println("[Aradığınız '" + arananMeyve + "' isimli meyve sistemde bulunamadı.]");
            }
        } catch (Exception e) {
            System.out.println("[Dosya okunurken bir hata oluştu.]");
        }
    }

    public static void kaloriyeGoreYazdir() {
        System.out.print("[Hedef kalori miktarını giriniz (Örn: 50): ");
        int hedefKalori = scanner.nextInt();
        scanner.nextLine();

        System.out.println("[1] Bu değerin ÜSTÜNDEKİLERİ listele");
        System.out.println("[2] Bu değerin ALTINDAKİLERİ listele");
        System.out.print("[Seçiminiz: ");
        int secim = scanner.nextInt();
        scanner.nextLine();

        try {
            List<String> satirlar = Files.readAllLines(new File("meyveler.txt").toPath(), Charset.forName("UTF-8"));

            System.out.println("[--------İSTENİLEN KALORİDEKİ MEYVELER-------]");
            System.out.println("[---------------------------------]");

            for (String satir : satirlar) {
                String[] parcalar = satir.split(";");
                if (parcalar.length < 5) {
                    continue;
                }
                String isim = parcalar[0];
                String familya = parcalar[1];
                int kalori = Integer.parseInt(parcalar[2]);
                double seker = Double.parseDouble(parcalar[3]);
                double protein = Double.parseDouble(parcalar[4]);

                if (secim == 1 && kalori > hedefKalori) {
                    System.out.println("[Meyve Adı : " + isim);
                    System.out.println("[Familyası : " + familya);
                    System.out.println("[Kalorisi  : " + kalori + " kcal]");
                    System.out.println("[Şeker     : " + seker + " g]");
                    System.out.println("[Protein   : " + protein + " g]");
                    System.out.println("[---------------------------------]");
                } else if (secim == 2 && kalori <= hedefKalori) {
                    System.out.println("[Meyve Adı : " + isim);
                    System.out.println("[Familyası : " + familya);
                    System.out.println("[Kalorisi  : " + kalori + " kcal]");
                    System.out.println("[Şeker     : " + seker + " g]");
                    System.out.println("[Protein   : " + protein + " g]");
                    System.out.println("[---------------------------------]");
                }
            }
        } catch (Exception e) {
            System.out.println("[Dosya okunurken bir hata oluştu.]");
        }
    }

    public static void sekereGoreYazdir() {
        System.out.print("[Hedef şeker miktarını giriniz (Örn: 5.0): ");
        double hedefSeker = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("[1] Bu değerin ÜSTÜNDEKİLERİ listele");
        System.out.println("[2] Bu değerin ALTINDAKİLERİ listele");
        System.out.print("[Seçiminiz: ");
        int secim = scanner.nextInt();
        scanner.nextLine();

        try {
            List<String> satirlar = Files.readAllLines(
                    new File("meyveler.txt").toPath(),
                    Charset.forName("UTF-8"));

            System.out.println("[--------İSTENİLEN ŞEKERDEKİ MEYVELER-------]");
            System.out.println("[---------------------------------]");

            for (String satir : satirlar) {
                String[] parcalar = satir.split(";");
                if (parcalar.length < 5) {
                    continue;
                }
                String isim = parcalar[0];
                String familya = parcalar[1];
                int kalori = Integer.parseInt(parcalar[2]);
                double seker = Double.parseDouble(parcalar[3]);
                double protein = Double.parseDouble(parcalar[4]);

                if (secim == 1 && seker > hedefSeker) {
                    System.out.println("[Meyve Adı : " + isim + "]");
                    System.out.println("[Familyası : " + familya + "]");
                    System.out.println("[Kalorisi  : " + kalori + " kcal]");
                    System.out.println("[Şeker     : " + seker + " g]");
                    System.out.println("[Protein   : " + protein + " g]");
                    System.out.println("[---------------------------------]");
                } else if (secim == 2 && seker < hedefSeker) {
                    System.out.println("[Meyve Adı : " + isim + "]");
                    System.out.println("[Familyası : " + familya + "]");
                    System.out.println("[Kalorisi  : " + kalori + " kcal]");
                    System.out.println("[Şeker     : " + seker + " g]");
                    System.out.println("[Protein   : " + protein + " g]");
                    System.out.println("[---------------------------------]");
                }
            }
        } catch (Exception e) {
            System.out.println("[Dosya okunurken bir hata oluştu.]");
        }
    }

    public static void proteineGoreYazdir() {
        System.out.print("[Hedef protein miktarını giriniz (Örn: 5.0): ");
        double hedefProtein = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("[1] Bu değerin ÜSTÜNDEKİLERİ listele");
        System.out.println("[2] Bu değerin ALTINDAKİLERİ listele");
        System.out.print("[Seçiminiz: ");
        int secim = scanner.nextInt();
        scanner.nextLine();

        try {
            List<String> satirlar = Files.readAllLines(
                    new File("meyveler.txt").toPath(),
                    Charset.forName("UTF-8"));

            System.out.println("[--------İSTENİLEN PROTEİNDEKİ MEYVELER-------]");
            System.out.println("[---------------------------------]");

            for (String satir : satirlar) {
                String[] parcalar = satir.split(";");
                if (parcalar.length < 5) {
                    continue;
                }
                String isim = parcalar[0];
                String familya = parcalar[1];
                int kalori = Integer.parseInt(parcalar[2]);
                double seker = Double.parseDouble(parcalar[3]);
                double protein = Double.parseDouble(parcalar[4]);

                if (secim == 1 && protein > hedefProtein) {
                    System.out.println("[Meyve Adı : " + isim + "]");
                    System.out.println("[Familyası : " + familya + "]");
                    System.out.println("[Kalorisi  : " + kalori + " kcal]");
                    System.out.println("[Şeker     : " + seker + " g]");
                    System.out.println("[Protein   : " + protein + " g]");
                    System.out.println("[---------------------------------]");
                } else if (secim == 2 && protein <= hedefProtein) {
                    System.out.println("[Meyve Adı : " + isim + "]");
                    System.out.println("[Familyası : " + familya + "]");
                    System.out.println("[Kalorisi  : " + kalori + " kcal]");
                    System.out.println("[Şeker     : " + seker + " g]");
                    System.out.println("[Protein   : " + protein + " g]");
                    System.out.println("[---------------------------------]");
                }
            }
        } catch (Exception e) {
            System.out.println("[Dosya okunurken bir hata oluştu.]");
        }
    }

    public static void meyveGuncelle() {
        System.out.print("[Diyet/Form için özelleştirmek istediğiniz meyvenin adını giriniz: ");
        String arananMeyve = scanner.nextLine();

        try {
            File dosya = new File("meyveler.txt");
            List<String> satirlar = Files.readAllLines(dosya.toPath(), Charset.forName("UTF-8"));
            boolean bulundu = false;

            for (int i = 0; i < satirlar.size(); i++) {
                String[] parcalar = satirlar.get(i).split(";");
                if (parcalar.length < 5)
                    continue;

                String isim = parcalar[0];

                if (isim.toLowerCase().contains(arananMeyve.toLowerCase())) {
                    bulundu = true;

                    System.out.println("\n--- Mevcut Meyve Bilgileri ---");
                    System.out.println("İsim: " + parcalar[0] + " | Familya: " + parcalar[1] + " | Kalori: "
                            + parcalar[2] + " | Şeker: " + parcalar[3] + " | Protein: " + parcalar[4]);

                    System.out.print("[Güncellemek istediğiniz kayıt bu mu? (e/h): ");
                    String onay = scanner.nextLine();

                    if (onay.equalsIgnoreCase("e")) {
                        System.out.println("\n--- Yeni Değerleri Giriniz (Kurutulmuş/İşlenmiş Formu) ---");

                        System.out.print("[Yeni Kalori Değeri: ");
                        int yeniKalori = scanner.nextInt();

                        System.out.print("[Yeni Şeker Değeri: ");
                        double yeniSeker = scanner.nextDouble();

                        System.out.print("[Yeni Protein Değeri: ");
                        double yeniProtein = scanner.nextDouble();
                        scanner.nextLine();

                        String yeniSatir = isim + ";" + parcalar[1] + ";" + yeniKalori + ";" + yeniSeker + ";"
                                + yeniProtein;
                        satirlar.set(i, yeniSatir);

                        FileWriter fw = new FileWriter(dosya, Charset.forName("UTF-8"), false);
                        for (String s : satirlar) {
                            fw.write(s + "\n");
                        }
                        fw.close();

                        System.out.println("'" + isim + "' meyvesi başarıyla yeni değerleriyle güncellendi.");
                        break;
                    } else {
                        System.out.println("Güncelleme işlemi kullanıcı tarafından iptal edildi.");
                    }

                }
            }

            if (!bulundu) {
                System.out.println("Güncellenecek '" + arananMeyve + "' isimli bir meyve bulunamadı.");
            }

        } catch (Exception e) {
            System.out.println("Dosya güncellenirken bir hata oluştu.");
        }
    }

    public static void meyveSil() {
        System.out.print(
                "Alerji veya diyet kısıtlaması nedeniyle listeden çıkarmak istediğiniz meyvenin adını giriniz: ");
        String arananMeyve = scanner.nextLine();

        try {
            File dosya = new File("meyveler.txt");
            List<String> satirlar = Files.readAllLines(dosya.toPath(), Charset.forName("UTF-8"));
            boolean bulundu = false;

            for (int i = 0; i < satirlar.size(); i++) {
                String[] parcalar = satirlar.get(i).split(";");
                if (parcalar.length < 5) {
                    continue;
                }
                String isim = parcalar[0];

                if (isim.toLowerCase().contains(arananMeyve.toLowerCase())) {
                    bulundu = true;

                    System.out.println("\n--- Diyet Listesinden Çıkarılacak Meyve ---");
                    System.out.println("İsim: " + parcalar[0] + " | Familya: " + parcalar[1] + " | Kalori: "
                            + parcalar[2] + " | Şeker: " + parcalar[3] + " | Protein: " + parcalar[4]);

                    System.out.print("[Bu meyveyi aktif diyet listenizden çıkarmak istediğinize emin misiniz? (e/h): ");
                    String onay = scanner.nextLine();

                    if (onay.equalsIgnoreCase("e")) {
                        satirlar.remove(i);

                        FileWriter fw = new FileWriter(dosya, Charset.forName("UTF-8"), false);
                        for (String s : satirlar) {
                            fw.write(s + "\n");
                        }
                        fw.close();

                        System.out.println("'" + isim + "' diyet listenizden başarıyla çıkartıldı.");
                        break;
                    } else {
                        System.out.println("İşlem kullanıcı tarafından iptal edildi.");
                    }

                }
            }

            if (!bulundu) {
                System.out.println("[Diyet listenizde zaten '" + arananMeyve + "' isimli bir meyve bulunamadı.]");
            }

        } catch (Exception e) {
            System.out.println("[Dosya okunurken veya yazılırken bir hata oluştu.]");
        }
    }

}
