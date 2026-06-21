# Utiliser l'image officielle Nginx
FROM nginx:alpine

# Supprimer les fichiers par défaut
RUN rm -rf /usr/share/nginx/html/*

# Copier le dossier 'dist' fraîchement généré sur ton PC
COPY dist /usr/share/nginx/html

# Exposer le port interne
EXPOSE 80

# Lancer Nginx
CMD ["nginx", "-g", "daemon off;"]