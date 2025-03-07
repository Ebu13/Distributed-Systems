# 1. Build aşaması: Maven kullanarak uygulamayı derliyoruz
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Maven projesini kopyalıyoruz
COPY pom.xml .
COPY src ./src

# Maven bağımlılıklarını indiriyoruz ve projeyi derliyoruz
RUN mvn clean package -DskipTests

# 2. Çalıştırma aşaması: JAR dosyasını küçük bir image'a kopyalıyoruz
FROM openjdk:17-jdk-slim

# Uygulamanın çalışacağı dizini oluşturuyoruz
WORKDIR /app

# Derlenen .jar dosyasını kopyalıyoruz
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# Uygulama çalıştırma komutu
ENTRYPOINT ["java", "-jar", "app.jar"]

# Portu expose ediyoruz
EXPOSE 8080
