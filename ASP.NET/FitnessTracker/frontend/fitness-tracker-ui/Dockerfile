FROM node:22-alpine

WORKDIR /app

ENV VITE_API_TARGET=http://host.docker.internal:5095

COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 5173

CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]
