# Java-Fruityvice-API

Hi! This is a pure Java console application I built to practice REST API integration and file I/O operations. 

It fetches real-time fruit nutrition data from the [Fruityvice API](https://www.fruityvice.com/) and lets you create, filter, and manage a custom diet list.

## Why I built this
I wanted to understand how HTTP requests and data parsing work under the hood. So, instead of using popular external libraries like Gson or Jackson, I handled the API connection and JSON parsing manually using only standard Java libraries (`java.net` and `java.io`).

## What it does
* Pulls fruit data (calories, sugar, protein, etc.) directly from the API.
* Filters fruits based on nutritional goals (e.g., finding fruits with less than 50 calories).
* Saves your custom diet list persistently to a local `meyveler.txt` file (CRUD operations).
* Runs entirely in the console with a simple interactive menu.

---


## 🇹🇷 Türkçe Açıklama

Merhaba! Bu, REST API entegrasyonu ve dosya okuma/yazma (I/O) işlemlerini pratik yapmak için geliştirdiğim saf bir Java konsol uygulamasıdır.

Gerçek zamanlı meyve besin değerlerini [Fruityvice API](https://www.fruityvice.com/)'sinden çeker ve kendinize özel bir diyet listesi oluşturmanıza, bu listeyi filtrelemenize ve yönetmenize olanak tanır.

## Bunu neden geliştirdim
HTTP isteklerinin ve veri ayrıştırma (parsing) işlemlerinin arka planda nasıl çalıştığını anlamak istedim. Bu nedenle, Gson veya Jackson gibi popüler harici kütüphaneler kullanmak yerine, API bağlantısını ve JSON çözümleme işlemlerini sadece standart Java kütüphanelerini (`java.net` ve `java.io`) kullanarak manuel olarak hallettim.

## Neler yapıyor
* Meyve verilerini (kalori, şeker, protein vb.) doğrudan API'den çeker.
* Meyveleri beslenme hedeflerine göre filtreler (örneğin, 50 kaloriden az olan meyveleri bulmak).
* Özel diyet listenizi kalıcı olarak yerel bir `meyveler.txt` dosyasına kaydeder (CRUD işlemleri).
* Basit, etkileşimli bir menü ile tamamen konsolda çalışır.
