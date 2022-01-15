# Ressource used https://vuejs.org/v2/cookbook/dockerize-vuejs-app.html#Real-World-Example
# Rssource begin
FROM node:16 as build-stage

# install simple http server for serving static content
RUN npm install -g http-server

# make the 'app' folder the current working directory
WORKDIR /app

# copy both 'package.json' and 'package-lock.json' (if available)
COPY package*.json ./

# install project dependencies
RUN npm install

# copy project files and folders to the current working directory (i.e. 'app' folder)
COPY . .

RUN npm run build


FROM nginx as production-stage
RUN mkdir /app
COPY --from=build-stage /app/dist /app
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 8080
CMD [ "nginx", "-g", "daemon off;" ]

#Ressource end