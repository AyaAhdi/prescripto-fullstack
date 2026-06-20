# Utiliser l'image officielle Nginx stable et légère
FROM nginx:alpine

# Supprimer les fichiers HTML par défaut de Nginx
RUN rm -rf /usr/share/nginx/html/*

# Copier les fichiers de production générés par 'npm run build' (dossier dist) vers le dossier Nginx
COPY dist /usr/share/nginx/html

# Exposer le port par défaut d'un serveur web
EXPOSE 80

# Lancer Nginx au premier plan
CMD ["nginx", "-g", "daemon off;"]