import datetime
import csv
import string
import base64

from random import randint, choice
from faker import Faker
from uuid import uuid4
import json
import pytz

csvDirPath = "./dump/csv/"

def readIdsCsv(filename):
    ids = []
    with open(filename, newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            ids.append(row['id'])

    return ids

def readCustomerLoginsCsv(filename):
    logins = []
    with open(filename, newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            if row['role_'] == 'CUSTOMER':
                logins.append(row['login'])

    return logins

def readIdsNamesCsv(filename):
    res = []
    with open(filename, newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            res.append({'id': row['id'], 'name': row['name']})

    return res

def readProductsCsv(filename):
    res = []
    with open(filename, newline='') as csvfile:
        reader = csv.DictReader(csvfile)
        for row in reader:
            res.append({'id': row['id'], 'price': row['price']})

    return res

def genManufactures():
    names = [line.strip() for line in open("txt/manufacturers.txt", "r")]

    with open(csvDirPath + 'manufacturers.csv', 'w', newline='') as csvfile:
        fieldnames = ['id', 'name']
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()

        for name in names:
            id = uuid4()
            writer.writerow({'id': id, 'name': name})

def genProducts():    
    def genProductValues(fieldValues):
        def genProductName(manufacturer):
            return manufacturer + ' ' + ''.join(choice(string.ascii_uppercase) for _ in range(3)) + \
                   '-' + ''.join(choice(string.digits) for _ in range(4))

        def genDescription(category):
            if category == 'Гитары':
                return 'Гитара проходит предпродажный осмотр, что гарантирует получение качественного инструмента. \
            В комплекте с гитарой прилагается фирменный утепленный чехол, который надежно защищает её.'
            elif category == 'Синтезаторы':
                return 'Данный инструмент - хороший выбор для начинающих музыкантов и любителей, сочетает в себе бюджетность\
                      инструмента начального уровня с функционалом устройств более высокого класса. \
                        Синтезатор имеет большое количество тембров и стилей, широкий набор функций, \
                            а также необходимые разъемы, включая микрофонный вход.'
        
        def genGuitarCharacteristics(fieldValues):
            characteristics = {}
            characteristics["Материал корпуса"] = choice(materials)
            characteristics["Материал деки"] = choice(materials)
            characteristics["Кол-во ладов"] = choice([15, 18, 24])
            ch = json.dumps(characteristics, ensure_ascii=False)
            fieldValues['characteristics'] = ch
            fieldValues['img_ref'] = "https://kombik.com/resources/img/000/001/822/img_182276.jpg"            

        def genSynthesizerCharacteristics(fieldValues):
            characteristics = {}
            characteristics["Кол-во клавиш"] = choice([44, 49, 61, 76])
            characteristics['Кол-во стилей'] = randint(100, 800)
            characteristics['Кол-во тембров'] = randint(100, 400)
            ch = json.dumps(characteristics, ensure_ascii=False)
            fieldValues['characteristics'] = ch
            fieldValues['img_ref'] = "https://muzakkord.ru/wa-data/public/shop/products/87/96/489687/images/136026/136026.750x0.jpg"

        manufacturer = choice(manufacturers)
        category = choice(['Гитары', 'Синтезаторы'])

        fieldValues['id'] = uuid4()
        fieldValues['name'] = genProductName(manufacturer['name'])
        fieldValues['description'] = genDescription(category)
        fieldValues['price'] = randint(5000, 90000) // 500 * 500
        # fieldValues['storage_cnt'] = randint(1, 20)
        fieldValues['color'] = choice(colors)
        fieldValues['manufacturer_id'] = manufacturer['id']        

        if category == 'Гитары':
            genGuitarCharacteristics(fieldValues)
        elif category == 'Синтезаторы':
            genSynthesizerCharacteristics(fieldValues)            

    cntRecords = 50
    manufacturers = readIdsNamesCsv(csvDirPath + 'manufacturers.csv')
    colors = [line.strip() for line in open("txt/colors.txt", "r")]
    materials = [line.strip() for line in open("txt/materials.txt", "r")]

    for m in manufacturers:
        print(m)

    with open(csvDirPath + 'products.csv', 'w', newline='') as csvfile:
        fieldNames = ['id', 'name', 'price', 'description', 'color', 'img_ref', 'manufacturer_id', 'characteristics']
        writer = csv.DictWriter(csvfile, fieldnames=fieldNames)
        writer.writeheader()

        for i in range(cntRecords):
            fieldValues = {field: None for field in fieldNames}
            genProductValues(fieldValues)
            writer.writerow(fieldValues)

def genDeliveryPoints():
    addresses = ['Улица Иванова, 9', 'Проспект Пушкина, 14']

    with open(csvDirPath + 'DeliveryPoint.csv', 'w', newline='') as csvfile:
        fieldnames = ['id', 'address']
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()

        for address in addresses:
            id = uuid4()
            writer.writerow({'id': id, 'address': address})

def genUsers():
    countRecords = 10
    logins = [fake.first_name() for _ in range(countRecords)]
    customerLogins = []

    with open('User.csv', 'w', newline='') as csvfile:
        fieldnames = ['login', 'password', 'role_', 'email']
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()

        for login in logins:
            password = login + "123"
            print(password)
            encPassword = base64.b64encode(bytes(password, 'utf-8')) # bytes(password, 'utf-8')
            print(encPassword)
            role_ = choice(['CUSTOMER', 'EMPLOYEE'])
            if role_ == 'CUSTOMER':
                customerLogins.append(login)
            email = login + "@gmail.com"
            writer.writerow({'login': login, 'password': encPassword, 'role_': role_, 'email': email})

    with open('Card.csv', 'w', newline='') as csvfile:
        fieldnames = ['login']
        writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
        writer.writeheader()

        for login in customerLogins:
            writer.writerow({'login': login})

def genCart():
    cntRecords = 50
    logins = readCustomerLoginsCsv('User.csv')
    productIds = readIdsCsv('Product.csv')

    with open('Cart.csv', 'w', newline='') as csvfile:
        fieldNames = ['login', 'product_id', 'cnt_products']
        writer = csv.DictWriter(csvfile, fieldnames=fieldNames)
        writer.writeheader()

        # records = set()
        for i in range(cntRecords):
            login = logins[i % len(logins)]
            productId = productIds[i % len(productIds)]
            # records.add([login, productId])
            cntProducts = randint(1, 3)
            writer.writerow({'login': login, 'product_id': productId, 'cnt_products': cntProducts})

def genOrders():
    cntRecords = 10
    statuses = ['formed', 'built', 'delivered', 'received']
    customerLogins = readCustomerLoginsCsv(csvDirPath + 'User.csv')
    deliveryPointIds = readIdsCsv(csvDirPath + 'DeliveryPoint.csv') 
    products = readProductsCsv(csvDirPath + 'Product.csv')

    with open(csvDirPath + 'Order.csv', 'w', newline='') as orderCsvFile:
        with open(csvDirPath + 'OrderProduct.csv', 'w', newline='') as orderProductCsvFile:
            fieldnames = ['id', 'customer_login', 'date', 'status', 'delivery_point_id', 'initial_cost', 'paid_by_bonuses']
            orderWriter = csv.DictWriter(orderCsvFile, fieldnames=fieldnames)
            orderWriter.writeheader()
            fieldnames = ['order_id', 'product_id', 'price', 'cnt_products']
            orderProductWriter = csv.DictWriter(orderProductCsvFile, fieldnames=fieldnames)
            orderProductWriter.writeheader()

            for _ in range(cntRecords):
                orderId = uuid4()
                product = choice(products)
                orderWriter.writerow({'id': orderId,
                                'customer_login': choice(customerLogins),
                                'date': fake.date_between(datetime.datetime(2005, 1, 1, tzinfo=pytz.UTC),
                                                          datetime.datetime(2021, 12, 31, tzinfo=pytz.UTC)).strftime('%Y-%m-%d %H:%M:%S %Z'),
                                'status': choice(statuses),
                                'delivery_point_id': choice(deliveryPointIds),
                                'initial_cost': product['price'],
                                'paid_by_bonuses': '0'})
                orderProductWriter.writerow({
                    'order_id': orderId,
                    'product_id': product['id'],
                    'price': product['price'],
                    'cnt_products': '1'})

if __name__ == "__main__":
    fake = Faker()
    # genDeliveryPoints()
    genManufactures()
    genProducts()
