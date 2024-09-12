# Food Order App

## Genel Bakış
Bu Android uygulaması, bir yemek sipariş sistemi simülasyonu oluşturmak için tasarlanmıştır ve kodun temiz ve düzenli kalmasını sağlamak amacıyla **MVVM mimarisi** kullanmaktadır. Kullanıcılar uygulama aracılığıyla farklı yemekleri inceleyebilir, sepetlerine ekleyebilir ve favorilerini yönetebilir. Proje **Kotlin** kullanılarak geliştirilmiş olup, verilerin yerel olarak saklanması için **Room**, ağ istekleri için **Retrofit**, bağımlılık enjeksiyonu için **Hilt**, ve veri değişikliklerini gözlemlemek için **LiveData** kullanılmaktadır.

<table>
  <tr>
    <th>Ana Sayfa</th>
    <th>Yemek Detayı</th>
    <th>Sepet</th>
    <th>Favoriler</th>
  </tr>
  <tr>
    <td><img src="https://github.com/user-attachments/assets/8234f341-99dd-47e3-a0a9-f13bbfd2e937" alt="anasayfa" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/b931b8cd-d2ae-43c2-a0f6-d020c8e20cd5" alt="yemek_detayı" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/18340c66-db16-47fe-9c03-419d2de5cbc0" alt="sepet" width="250"/></td>
    <td><img src="https://github.com/user-attachments/assets/5c341662-2cef-49c1-8c74-24f1e954e7ca" alt="favoriler" width="250"/></td>
  </tr>
</table>

https://github.com/user-attachments/assets/d1fefccd-ebdc-49c2-8d41-29ea9878cdb3

## Proje Yapısı
Proje, **MVVM (Model-View-ViewModel)** mimari modelini takip etmekte olup, şu dizinlerde organize edilmiştir:

- **data**:
  - **datasource**: API ya da yerel veritabanı gibi veri kaynaklarıyla etkileşime giren sınıfları içerir.
  - **model**: `CartFood`, `Foods`, ve API yanıtları gibi veri modellerini içerir.
  - **repo**: Repository katmanı veri kaynaklarını soyutlar ve ViewModel'e veri sağlar.
  
- **di** (Dependency Injection): 
  - `AppModule` ve `HiltApplication` gibi Hilt ile ilgili bağımlılık enjeksiyonu sınıflarını içerir.
  
- **retrofit**: 
  - Ağ istekleri yapmak için Retrofit yapılandırmalarını içerir.

- **room**: 
  - Room veritabanı yapılandırmasını ve `RoomFoodsDao` veri erişim nesnelerini içerir.
  
- **ui**:
  - **adapter**: Yemek listesi ve sepeti yönetmek için kullanılan RecyclerView adaptörlerini içerir.
  - **fragment**: Kullanıcı arayüzü ve kullanıcı etkileşimlerinin tanımlandığı UI bileşenlerini içerir.
  - **viewmodel**: ViewModel sınıflarını içerir, UI ile veri kaynakları arasındaki iletişimi sağlar.
  
- **util**: Yardımcı sınıflar ve uzantı fonksiyonları içerir.

## MVVM Mimarisi
Uygulama, **MVVM (Model-View-ViewModel)** modelini takip eder. MVVM, iş mantığının kullanıcı arayüzünden ayrılmasını sağlar, böylece kod daha bakımlı ve test edilebilir hale gelir. İşte bu uygulamada MVVM'in nasıl kullanıldığı:

### 1. Model
**Model** katmanı veri sınıflarından ve repository'den oluşur. Verilerin elde edilmesi gibi işlemler bu katmanda gerçekleşir.

- **Veri Modelleri**: `Foods`, `CartFood` gibi uygulamada kullanılan veri yapılarının tanımlarını içerir.
- **Repository**: Veriler için tek bir kaynak olarak görev yapar. Hem yerel veritabanı hem de ağ istekleri ile etkileşimde bulunur ve verileri ViewModel’e sağlar.

### 2. View (Görünüm)
**View** katmanı, kullanıcıya verileri gösteren ve kullanıcı etkileşimlerini yöneten kısımdır. Bu katman Fragments ve Adapters sınıflarından oluşur.

- **Fragments**: Her özellik için bir Fragment bulunur ve **DataBinding** kullanılarak, ViewModel’den gelen veriler bu katmanda görüntülenir.
- **Adapters**: RecyclerView gibi bileşenlerdeki verilerin yönetilmesinde kullanılır.

### 3. ViewModel (Görünüm Modeli)
**ViewModel** katmanı, uygulamanın iş mantığını yönetir ve Model ile View arasındaki iletişimi sağlar. **LiveData** kullanarak veri değişikliklerini gözlemler ve UI'yi bu değişikliklere göre günceller.

## Kullanılan Teknolojiler
- **Kotlin**: Projenin ana programlama dili.
- **MVVM Mimarisi**: Kodun daha bakımlı ve sürdürülebilir olması için tercih edilmiştir.
- **Hilt**: Bağımlılık enjeksiyonu kütüphanesi.
- **Retrofit**: REST API isteklerini gerçekleştirmek için kullanılır.
- **Room**: Yerel veri depolama için kullanılır.
- **LiveData**: Verilerdeki değişiklikleri gözlemlemek ve UI'yi güncellemek için kullanılır.
- **Coroutines**: Arka plan görevlerini (örneğin, ağ istekleri) yönetmek için kullanılır.
- **Glide**: Görselleri yüklemek için kullanılır.
- **Lottie Animations**: UI'yi zenginleştirmek ve daha etkileşimli hale getirmek için Lottie animasyonları kullanılır.
- **DataBinding**: UI bileşenleri ile ViewModel arasında doğrudan veri bağlama için kullanılır. Bu sayede arayüz kodları xml'e aktarılır.

## Özellikler
- **Yemek Listesi**: Kullanıcıya yemeklerin isimleri, görselleri ve fiyatları ile birlikte bir liste gösterir.
- **Sepete Ekleme**: Kullanıcılar yemekleri sepetlerine ekleyebilir.
- **Favoriler**: Yemekler favorilere eklenebilir veya favorilerden çıkarılabilir.
- **Sepet**: Sepetteki ürünlerin adetleri değiştirilebilir ve toplam fiyat görüntülenir.
- **Veri Kalıcılığı**: Room kullanılarak çevrimdışı veri kalıcılığı sağlanır.
- **Snackbar Bildirimleri**: Kullanıcıya sepet veya favorilere ekleme işlemi hakkında geri bildirim verir.
- **Lottie Animasyonları**: Kullanıcı deneyimini geliştirmek için farklı animasyonlar kullanılır.
- **DataBinding**: Fragment ve XML arasında veri bağlama yapılır, UI bileşenleri dinamik olarak ViewModel'den gelen verilere göre güncellenir.

