truncate public.manufacturers cascade;
truncate public.Categories cascade;
truncate public.products cascade;
-- truncate public.User_ cascade;
-- truncate public.Card;
-- truncate public.Cart;
-- truncate public.DeliveryPoint cascade;
-- truncate public.Order_ cascade;
-- truncate public.Order_Product;

COPY public.Manufacturers FROM '/dump/csv/manufacturers.csv' DELIMITER ',' CSV HEADER;
COPY public.Categories FROM '/dump/csv/categories.csv' DELIMITER ',' CSV HEADER;
COPY public.Products FROM '/dump/csv/products.csv' DELIMITER ',' CSV HEADER;
--COPY public.DeliveryPoint FROM '/dump/csv/DeliveryPoint.csv' DELIMITER ',' CSV HEADER;
