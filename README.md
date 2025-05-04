🛳️ Warehouse Monitoring & Microservices System

This project is a real-world simulation of a warehouse monitoring system built with a full event-driven microservices architecture using Kafka and MongoDB.
Each service communicates only via Kafka topics, promoting a fully decoupled design — scalable, modular, and production-ready.

🚀 Technologies Used

- **Angular 19** + **Angular Material** (frontend)
- **Java 17** ☕
- **Spring Boot 3.2.x** 🚀
- **Spring Kafka** 📡
- **MongoDB** 🍃
- **Docker & Docker Compose** 🐳
- **Kafka Topics** 🔄 for messaging flow

---

🧩 Microservices Overview

| Service | Description | Port |
|:--|:--|:--|
| **OrderService** | Exposes APIs to manage orders; publishes new orders to Kafka | `8080` |
| **InventoryService** | Manages stock and materials; listens to new orders and purchases | `8081` |
| **ProductionService** | Mocks production time and updates order statuses via Kafka | `8082` |
| **ManagementService** | Tracks budget, simulates purchases, updates inventory | `8083` |

---

🛠 Kafka Topics Overview

|     Topic             |     Purpose 

| newOrderTopic         | Publishes new incoming orders 
| productStatusTopic    | Updates order status post-production 
| requiredPurchaseTopic | Signals need to purchase more raw materials 
| updateInventory       | Updates inventory after purchase 

---

⚡ How It Works

1. **OrderService** accepts a new order → publishes it to `newOrderTopic`.
2. **InventoryService** and **ProductionService** listen:
   - **InventoryService** adjusts stock based on raw materials.
   - **ProductionService** simulates production and, after a delay, publishes order completion status to `productStatusTopic`.
3. **InventoryService** signals when raw materials are low → `requiredPurchaseTopic`.
4. **ManagementService** purchases raw materials and updates inventory via `updateInventory` topic.

Everything is **fully event-driven** with **no direct service-to-service HTTP communication**.

---

🐳 Docker Setup
The frontend application is calling different endpoints of a microservices application.
The requests arrive at the api gateway. WHich forwards these requests to kafka messages.
'http://localhost:3000/finished-products' and 'http://localhost:3000/materials'  are  get requests to inventoryInfo.
,http://localhost:3000/current-orders' here a websocket to update a product's status must be used,'http://localhost:3000/neworder' it is a post request that pubblishes a message on newOrderTopic ,'http://localhost:3000/revenues' it is  a get request for revenueRequest,'http://localhost:3000/products' re-routes to productInfo.

------------------------------
WarehouseService
It emulates the warehouse.There are 2 kinds of items that can be stored: finished products
and raw materials. this service has to add and subtract quantities of these 2 kinds of products when requested by other services through kafka topics.
                  This service: -listens on requestMaterials, the message structure is a list of objects, each object is composed by the material  id (number) and the amount of that amterial (number). When such message arrives, the amount request in the message has to be subtracted from the quantity of that material recorded in the database.
                  -listens on updateInventory, when new materials are purchased, these must be added to the stocks in the database. the message structure is made by the material's id and the amount of material to add. 
                  -pubblishes on purchaseRequest when one material is running low, the message's structure is the same as updateInventory.    
                  -publishes on inventoryInfo pubblishing a message containing the product's id  and its quantity  currently stocked.         
------------------------------
ProductionService listens on newOrderTopic, the message is composed by the product's id and the quantity of that product requested Everytime a new order comes in.
                  pubblishes on updateProductStatus a message containing the order id and a string with its status,  sent after some time, it is a mocked version of a production process. 
                  pubblishes on requestMaterials .
                  pubblishes on productInfoRequest to retrieve the quantites of materials to ask in requestMaterials.
Everytime  a message arrives on newOrderTopic, a production order is created, the amounts of raw material are retrieved and these materials   are requested to the warehouse. 
Sends updated on the product's status.

  
------------------------------
ManagemenetService 
It is the service responsible to make new purchases.
                  -pubblishes on updateInventory
                  -listens on purchaseRequest
                  -listens on revenueRequest for a message containing the a date 'month/year'
                  -pubblishes on revenueResponse topic, a message containing the revenue for the month requested in revenueRequest topic.
Both of these message's structure have been described before.
------------------------------
ProductServices
                  -pubblishes on productInfo where the message structure is composed by a list of objects,   where each object is made by: "id","name","category", "price" ,"rawMaterials" and "productionTime".
                  -listens on productInfoRequest where inside the message there is a product's id.
                  -listens on newProduct, where the message's structure is the same as in productInfo.
This service provides infos about products, and allows to  record a new product.



