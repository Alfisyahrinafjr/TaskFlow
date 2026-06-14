# TaskFlow — Architectural Task & Productivity Management App

TaskFlow adalah aplikasi manajemen tugas dan produktivitas harian yang dirancang khusus dengan pendekatan estetika *Editorial Brutalism* yang tegas, maskulin, dan minimalis. Aplikasi ini dibangun menggunakan **Jetpack Compose** dan mengadopsi prinsip **Clean Architecture** untuk memisahkan logika bisnis, data harian lokal, serta sinkronisasi data eksternal secara reaktif.

---

## Fitur Utama 

* **Security Gate (Simple Login Screen):** Gerbang pengaman aplikasi terintegrasi `NavHost` dengan skenario akun kelompok/admin.
* **panduan awal (Onboarding Screen):** Media pengenalan alur kerja *workspace* aplikasi kepada pengguna baru.
* **Analytics Dashboard (Progress Screen):** Visualisasi performa efisiensi penyelesaian tugas (*Weekly Impact Chart*) dan ringkasan tugas berdasarkan kategori (*College, Personal, Organization*).
* **Real-Time Task Search (FR-10):** Fitur pencarian tugas responsif berdasarkan judul secara *live filter* langsung dari database lokal.
* **Core BREAD/CRUD Operations:** Manajemen tugas lengkap meliputi penambahan (*Add*), pembaruan status selesai/edit (*Edit/Read*), dan penghapusan data (*Delete*).
* **Third-Party API Integration (Remote Data):** Sinkronisasi data Hari Libur Nasional secara reaktif menggunakan **Retrofit** dengan implementasi *Fallback Mockup Mechanism* demi menjamin stabilitas *User Experience* (Anti-*Infinite Loading*).

---

##  Arsitektur Sistem (Clean Architecture)

Proyek ini diisolasi ke dalam 3 layer utama untuk menjamin skalabilitas, modularitas, dan kemudahan pengujian (*testability*):

1.  **`Data Layer`:** Bertanggung jawab atas pengelolaan data mentah.
    * *Local:* Menggunakan **Room Database** untuk presistensi data offline (`TaskEntity` & `TaskDao`).
    * *Remote:* Menggunakan **Retrofit2** untuk konsumsi API pihak ketiga (`HolidayApiService` & `HolidayResponse` DTO).
2.  **`Domain Layer`:** Jantung logika bisnis aplikasi yang murni menggunakan bahasa Kotlin tanpa dependensi framework Android.
    * Berisi model data representasi UI (`Task` & `Holiday`) serta kontrak abstraksi (`TaskRepository` Interface).
3.  **`Presentation Layer (UI)`:** Menggunakan paradigma deklaratif modern **Jetpack Compose**.
    * Mengadopsi pola **StateFlow** di dalam `TaskViewModel` untuk mengalirkan perubahan data secara *asynchronous* dan *lifecycle-aware* ke seluruh komponen layar (`HomeScreen`, `ProgressScreen`, `AddTaskScreen`, `AboutScreen`, `LoginScreen`).

---

## Pustaka & Teknologi yang Digunakan

* **Jetpack Compose** — Framework UI Deklaratif modern Android.
* **Kotlin Coroutines & Flow** — Pengelolaan proses latar belakang (*asynchronous*) dan *reactive data stream*.
* **Room Database** — Solusi penyimpanan data lokal terstruktur berbasis SQLite.
* **Retrofit 2 & OkHttp 3** — HTTP Client untuk koneksi dan komunikasi dengan API REST pihak ketiga.
* **Gson Converter** — Serialisasi dan deserialisasi data JSON dari internet menjadi objek Kotlin.
* **Material Design 3** — Implementasi sistem desain komponen visual komponen antarmuka.

---

## Prasyarat Menjalankan Proyek

* **Android Studio** Ladybug (2024.2) atau versi di atasnya.
* **Android SDK** Minimum: API 26 (Android 8.0 Oreo), Target: API 34 (Android 14).
* **Gradle Build System** versi terbaru berbasis Kotlin DSL.
* Koneksi Internet aktif (untuk pengambilan data awal API Hari Libur Nasional).

---

## Cara Menjalankan Aplikasi & Kredensial Login

### Kredensial Akses (Skenario Demo)
Untuk keperluan pengujian dan demonstrasi fungsionalitas sistem pada ruang sidang UAS, halaman *Login Screen* telah dikunci menggunakan kredensial statis resmi kelompok berikut:
* **Username:** `admin`
* **Password:** `taskflow123`

*Catatan: Jika salah satu kolom dikosongkan atau diinput dengan akun yang salah, sistem secara reaktif akan memunculkan pesan peringatan (Toast).*

### Langkah-Langkah Menjalankan Proyek via Android Studio:
1.  **Clone Repositori:**
    ```bash
    git clone [https://github.com/Alfisyahrinafjr/TaskFlow.git](https://github.com/Alfisyahrinafjr/TaskFlow.git)
    ```
2.  **Buka di Android Studio:**
    * Buka Android Studio, pilih **File -> Open**.
    * Arahkan ke folder hasil *clone* proyek `taskflow`, lalu klik **OK**.
3.  **Sinkronisasi Gradle:**
    * Tunggu hingga proses *Gradle Sync* selesai otomatis (pastikan perangkat komputer Anda terhubung ke internet untuk mengunduh *dependencies*).
4.  **Jalankan Aplikasi:**
    * Sambungkan HP Android melalui kabel data (pastikan *USB Debugging* aktif) atau gunakan Emulator bawaan.
    * Klik tombol **Run 'app'** (ikon *play* hijau) di toolbar atas Android Studio atau gunakan pintasan `Shift + F10`.

## Anggota Tim Kelompok

* **[Alfisyah Rina Fajriati - NIM. 2310817320015]** - UI/UX Design, Presentation Layer, & Jetpack Compose Layout
* **[Naila Hanifah - NIM. 2310817220016]** - Core Architecture, Data Layer, & Backend Integration API

---
© 2026 Tim Proyek TaskFlow. All Rights Reserved.