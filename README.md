# **ThunderBirds Express: UBC Campus Food Delivery Management System**

## **What will the application do?**
I propose to design **ThunderBirds Express**, a project aimed at managing food delivery within UBC's campus. During peak meal times, restaurants often *struggle to handle the large volume of orders*, leading to inefficiencies. Additionally, students may become frustrated due to *delayed deliveries*. This system will help *students* **track their food delivery** in real-time, **check their order**, and **delete their order**, while enabling *restaurant owners* to better **manage and organize their incoming orders**, ultimately improving the overall delivery experience.

### **Core Functionalities**
1. **Order Management:** Restaurants can receive, organize, and update the status of incoming orders in real time.
2. **Order Tracking:** Students can track their order status (e.g., preparation, delivery) and get real-time delivery information to reduce frustration.
3. **Delete an Order:** Both restaurants and students can delete orders from the order list.
4. **Rate My Order:** After delivery, students can rate and leave comments based on their experience with the restaurant and delivery personnel.

---

## **Who will use it?**
### **Students**
- Place orders and keep track of their delivery.  
- Cancel their orders when needed.  
- Rate their experience for both the meal and the delivery.  

### **Restaurant Owners**
- Manage incoming orders, including adding and deleting orders.  
- Update their menu.  
- Keep track of delivery statuses.

---

## **Why am I interested in this project?**
The food delivery system in my home country, *China*, has already become very mature and efficient. However, I still can't forget the one time I ordered food online during a busy meal period here at UBC—it took **more than an hour** to receive my food. I felt hungry and helpless during that time.  

As a **UBC student with a passion for computer science**, I am willing to design a *delivery management system* to help all ThunderBirds enjoy a more pleasant meal experience and *reduce the pressure on restaurant owners*. My goal is to make the food delivery system at UBC more **well-organized**.  

By the way, as the boss of ThunderBirds Express, my biggest dream is to run a **hotpot restaurant** near UBC someday!  

---

## **User Stories**
1. **As a student**, I want to **place an order** at a restaurant, so that I can receive food delivered to me.  
2. **As a student**, I want to **track the status** of my order in real time, so that I can stay updated.  
3. **As a student**, I want to **delete my order**, so that I can cancel it if needed.  
4. **As a restaurant owner**, I want to **view and manage incoming orders**, so that I can process and update their status.  
5. **As a restaurant owner**, I want to **update the status of an order**, so that students can track the progress of their orders.  
6. **As a restaurant owner**, I want to **delete unavailable orders**, ensuring my order list remains accurate.  
7. **As a restaurant owner**, I want to **save my order list to a file** before exiting the application.  
8. **As a restaurant owner**, I want to **load my order list from a file** when starting the application.  
9. **As a student**, I want to **rate my order**, so that I can provide feedback on my experience.  

---

## **Future Enhancements**
- **Delivery Personnel Integration:** Introduce a `DeliveryPerson` class to assign orders to delivery personnel. Students can tip the delivery person and provide feedback.  
- **Shared Utility Methods:** Refactor common order operations into a centralized `OrderManager` class to reduce redundancy and improve maintainability.  
- **Advanced Features:** Explore additional functionalities such as analytics for restaurant performance, delivery heatmaps, or customizable notifications for students.  
