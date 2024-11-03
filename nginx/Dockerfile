FROM nginx:1.22
RUN rm /etc/nginx/conf.d/default.conf
RUN apt-get update
RUN apt-get install nginx-extras -y
COPY nginx.conf /etc/nginx/conf.d/default.conf