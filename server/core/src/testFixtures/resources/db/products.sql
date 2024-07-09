INSERT INTO public.Manufacturers (id, name)
VALUES ('2f109076-e8d2-11ed-a05b-0242ac120003', 'CRAFTER');

INSERT INTO public.Manufacturers (id, name)
VALUES ('1b2005dd-65a1-4e56-b757-49cc00af773c', 'ELITARO');

INSERT INTO public.Manufacturers (id, name)
VALUES ('6290410a-88c3-4d5a-9139-1157e63238d8', 'YAMAHA');

INSERT INTO public.Products (id, name, price, color, img_ref, characteristics, description, manufacturer_id)
VALUES ('ab7acbcb-139a-41c4-90e1-fc42cc7d16e4', 'ELITARO ILG-3528', 31000, 'Санберст',
        'https://kombik.com/resources/img/000/001/822/img_182276.jpg',
        '{"Материал корпуса": "Ясень", "Материал деки": "Ясень", "Кол-во ладов": 15}',
        'Гитара проходит предпродажный осмотр, что гарантирует получение качественного инструмента.' ||
        '             В комплекте с гитарой прилагается фирменный утепленный чехол, который надежно защищает её.',
        '1b2005dd-65a1-4e56-b757-49cc00af773c');

INSERT INTO public.Products (id, name, price, color, img_ref, characteristics, description, manufacturer_id)
VALUES ('2ac1ac8f-324b-4cc9-9f91-725612b78300', 'CRAFTER LCK-2002', 65500, 'Красный',
        'https://muzakkord.ru/wa-data/public/shop/products/87/96/489687/images/136026/136026.750x0.jpg',
        '{"Кол-во клавиш": 61, "Кол-во стилей": 541, "Кол-во тембров": 306}',
        'Данный инструмент - хороший выбор для начинающих музыкантов и любителей, сочетает в себе бюджетность' ||
        '                      инструмента начального уровня с функционалом устройств более высокого класса.' ||
        '                         Синтезатор имеет большое количество тембров и стилей, широкий набор функций,' ||
        '                             а также необходимые разъемы, включая микрофонный вход.',
        '2f109076-e8d2-11ed-a05b-0242ac120003');

INSERT INTO public.Products (id, name, price, color, img_ref, characteristics, description, manufacturer_id)
VALUES ('4e644818-112a-40de-9cbc-6d88aafb23f2', 'YAMAHA NRZ-9386', 56500, 'Чёрный',
        'https://muzakkord.ru/wa-data/public/shop/products/87/96/489687/images/136026/136026.750x0.jpg',
        '{"Кол-во клавиш": 49, "Кол-во стилей": 179, "Кол-во тембров": 319}',
        'Данный инструмент - хороший выбор для начинающих музыкантов и любителей, сочетает в себе бюджетность' ||
        '                      инструмента начального уровня с функционалом устройств более высокого класса.' ||
        '                         Синтезатор имеет большое количество тембров и стилей, широкий набор функций,' ||
        '                             а также необходимые разъемы, включая микрофонный вход.',
        '6290410a-88c3-4d5a-9139-1157e63238d8');
