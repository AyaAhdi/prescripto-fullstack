# Utiliser l'image officielle Tomcat avec Java 8 ou 11
FROM tomcat:9.0-jdk11-corretto

# Supprimer les applications par défaut de Tomcat pour faire de la place
RUN rm -rf /usr/local/tomcat/webapps/*

# Copier le fichier backend.war généré par Jenkins dans le dossier de déploiement de Tomcat
# On le renomme 'ROOT.war' pour qu'il soit accessible directement à la racine de l'URL
COPY target/backend.war /usr/local/tomcat/webapps/ROOT.war

# Exposer le port par défaut de Tomcat
EXPOSE 8080

# Lancer le serveur Tomcat
CMD ["catalina.sh", "run"]