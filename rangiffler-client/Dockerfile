FROM node:18.13.0 as build
ARG NPM_COMMAND
WORKDIR /app
COPY package.json ./
RUN npm update
RUN npm install
COPY . ./
RUN npm i
RUN npm run ${NPM_COMMAND}

# release step
FROM nginx:1.23.3-alpine as release
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/dist /usr/share/nginx/html/
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]