🛳️ Warehouse Monitoring & Microservices System

This project is a real-world simulation of a warehouse monitoring system built with a full event-driven microservices architecture** using Kafka and MongoDB.
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

Spin up Kafka, Zookeeper, and MongoDB services:


