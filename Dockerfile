#stage1
FROM --platform=linux/amd64 node:alpine as node
WORKDIR frontend/src/app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

#stage2
FROM  --platform=linux/amd64 nginx:alpine
COPY --from=node /frontend/dist/axioma-frontend /usr/share/nginx/html
