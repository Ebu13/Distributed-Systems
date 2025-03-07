# Notepad Dağıtık Sistem Projesi

Bu proje, Spring Boot ile geliştirilmiş bir backend servisi, Redis ve PostgreSQL entegrasyonu olan basit bir not defteri uygulamasıdır. Proje Docker kullanarak mikroservis mimarisiyle çalışacak şekilde tasarlanmıştır.

## Özellikler
- Spring Boot ile geliştirilmiş REST API
- PostgreSQL veri tabanı kullanımı
- Redis ile cache desteği
- Nginx ters proxy olarak kullanılması
- Docker Compose ile kolay kurulum
- Basit bir CSS ve JavaScript ile geliştirilmiş frontend/index.html sayfası

## Kurulum

### Gereksinimler
- Docker ve Docker Compose yüklenmiş olmalıdır.

### Docker Kullanarak Başlatma
Aşağıdaki komutu çalıştırarak projenin tüm bileşenlerini Docker ortamında ayağa kaldırabilirsiniz:
```sh
docker-compose up --build
```
Bu komut aşağıdaki servisleri ayağa kaldırır:
- **nginx** (Ters proxy)
- **app1 ve app2** (Spring Boot backend)
- **db** (PostgreSQL veri tabanı)
- **redis** (Cache servisi)

Servisleri durdurmak için:
```sh
docker-compose down
```

## API Kullanımı

Backend uygulaması REST API sunmaktadır.

### Notları Listeleme
```http
GET /notes
```
Yanıt:
```json
[
  {
    "id": 1,
    "title": "Docker Öğreniyorum",
    "content": "Docker Compose ile sistem kuruyorum."
  }
]
```

### Yeni Not Ekleme
```http
POST /notes
Content-Type: application/json
{
  "title": "Yeni Not",
  "content": "Bu bir test notudur."
}
```
Yanıt:
```json
{
  "id": 2,
  "title": "Yeni Not",
  "content": "Bu bir test notudur."
}
```

## Frontend Kullanımı
Frontend kısmı **front/index.html** dosyasında bulunmaktadır. Backend API'yi kullanarak not ekleme ve listeleme işlemlerini gerçekleştirir.

- **Frontend, herhangi bir ek sunucu gerektirmeden doğrudan açılarak çalıştırılabilir.**
- Not ekleme ve listeleme işlemleri backend'e yapılan API istekleriyle gerçekleşir.
- **API çağrılarının çalışabilmesi için backend'in çalışıyor olması gerekmektedir.**

## Proje Yapısı
```
Distributed-Systems/
├── backend/         # Spring Boot backend kodları
├── db/              # PostgreSQL veritabanı init scripti
├── front/           # index.html dosyası
├── nginx/           # Nginx konfigürasyonu
├── docker-compose.yml # Docker Compose konfigürasyonu
└── Readme.md        # Proje dokümantasyonu
```

## Teknik Detaylar
### Docker Compose Konfigürasyonu
Proje, **docker-compose.yml** dosyası ile yönetilmektedir:
- **nginx** ters proxy olarak çalışır ve **app1** ile **app2** yük dengesini sağlamak için kullanılır.
- **app1 ve app2**, Spring Boot backend olarak PostgreSQL ve Redis ile iletişim kurar.
- **db**, PostgreSQL veritabanı servisidir.
- **redis**, cacheleme amaçlı Redis servisini çalıştırır.

### Nginx Konfigürasyonu
**nginx/nginx.conf** dosyasında Nginx, yük dengeleyici olarak ayarlanmıştır:
```
events {}

http {
    upstream backend {
        server app1:8080;
        server app2:8080;
    }

    server {
        listen 80;

        location / {
            proxy_pass http://backend;
            proxy_set_header Host $host;
            proxy_set_header X-Real-IP $remote_addr;
        }
    }
}
```
### PostgreSQL Veritabanı Yapılandırması
**db/init.sql** dosyasında başlangıç verisiyle birlikte notlar tablosu oluşturulmaktadır:
```
CREATE TABLE IF NOT EXISTS note (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL
);

INSERT INTO note (title, content) VALUES ('Docker Öğreniyorum', 'Docker Compose ile sistem kuruyorum.');
```

## Sonuç
Bu proje, mikroservis mimarisi ve Docker kullanarak basit bir not defteri uygulamasının nasıl geliştirilebileceğini göstermektedir. Backend, Redis ve PostgreSQL kullanırken, frontend ise doğrudan açılarak API'yi tüketen basit bir istemci olarak çalışmaktadır. Hedef basit bir uygulama üzerinden birçok sisteme de uyarlanabilecek ve bağımlılığı az bir uygulama yapmaktır.

