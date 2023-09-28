FROM node:19-alpine

WORKDIR /app

RUN npm install -g @angular/cli

COPY package.json ./

COPY . ./

RUN npm install

ENTRYPOINT ["ng","serve","--host","0.0.0.0","--poll=2000"]

# Việc angular default run ở localhost:4200, nên phải chuyển host qua 0.0.0.0 của docker

# --poll=2000, hot reload của angular trong docker k kiểm tra được. Sử dụng flag này để mỗi 2000ms thì build lại 1 lần

# --disable-host-check
