truncate public.manufacturers cascade;
truncate public.products cascade;

COPY public.Manufacturers FROM '/dump/csv/manufacturers.csv' DELIMITER ',' CSV HEADER;
COPY public.Products FROM '/dump/csv/products.csv' DELIMITER ',' CSV HEADER;
