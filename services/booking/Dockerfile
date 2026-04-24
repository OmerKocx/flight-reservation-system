# 1. AŞAMA: İNŞAAT (BUILD) KATI
# Kodun derlendiği yer. Bize JDK (Java Development Kit) lazım.
FROM eclipse-temurin:21-jdk-alpine AS builder

# Konteyner içinde çalışacağımız klasörü oluşturup oraya giriyoruz.
WORKDIR /build

# Bilgisayarındaki tüm proje dosyalarını (pom.xml, src vb.) buraya kopyalıyoruz.
COPY . .

# Maven Wrapper dosyasına çalıştırma yetkisi veriyoruz (Linux izni).
RUN chmod +x mvnw

# Maven kullanarak projeyi paketliyoruz (JAR oluşturuyoruz). 
# -DskipTests ile testleri atlıyoruz ki build süreci hızlı bitsin.
RUN ./mvnw clean package -DskipTests


# 2. AŞAMA: ÇALIŞTIRMA (RUNNER) KATI
# Artık koda veya JDK'ya gerek yok, sadece çalıştırmak için JRE (Runtime) yeterli.
# Bu sayede imaj boyutu devasa oranda küçülür.
FROM eclipse-temurin:21-jre-alpine

# Uygulamanın çalışacağı klasörü belirliyoruz.
WORKDIR /app

# SİHİRLİ SATIR: builder aşamasında oluşan o ağır /build/target içindeki 
# .jar dosyasını alıp, bu yeni hafif imajın içine "app.jar" adıyla kopyalıyoruz.
COPY --from=builder /build/target/*.jar app.jar

# Bu konteyner dış dünyaya hangi porttan konuşacak? (Senin Booking portun gibi)
EXPOSE 8060

# Konteyner ayağa kalktığı an çalışacak olan komut.
# Yani terminale "java -jar app.jar" yazmakla aynı şey.
ENTRYPOINT ["java", "-jar", "app.jar"]