# --- ETAPE 1 : BUILD ---
FROM maven:3.8.6-openjdk-8 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# --- ETAPE 2 : EXECUTION ---
# On utilise Tomcat 9 (version "alpine" ultra-légère et sécurisée)
FROM tomcat:9-jre8-alpine

# Bonne pratique de sécurité : on efface les applications d'exemple par défaut de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# On récupère le .war de l'étape 1 et on le place dans le dossier "webapps". 
# On le renomme ROOT.war pour qu'il soit accessible directement à la racine de l'URL !
COPY --from=builder /app/target/backend.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
