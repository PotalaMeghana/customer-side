/*
 * package eStoreProduct.DAO.common;
 * 
 * import java.sql.Timestamp; import java.util.List;
 * 
 * import javax.persistence.EntityManager; import
 * javax.persistence.PersistenceContext; import javax.persistence.TypedQuery;
 * import javax.persistence.criteria.CriteriaBuilder; import
 * javax.persistence.criteria.CriteriaQuery; import
 * javax.persistence.criteria.Root; import javax.transaction.Transactional;
 * 
 * import org.hibernate.Session; import
 * org.springframework.stereotype.Component;
 * 
 * import eStoreProduct.model.admin.entities.orderModel;
 * 
 * @Component public class OrderDAOImp implements OrderDAO {
 * 
 * @PersistenceContext private EntityManager entityManager;
 * 
 * //method to insert into database when order is placed
 * 
 * @Override
 * 
 * @Transactional public void insertOrder(orderModel order) {
 * entityManager.persist(order); }
 * 
 * //method to get all the placed orders
 * 
 * @Override
 * 
 * @Transactional public List<orderModel> getAllOrders() { Session
 * currentSession = entityManager.unwrap(Session.class); CriteriaBuilder
 * criteriaBuilder = currentSession.getCriteriaBuilder();
 * CriteriaQuery<orderModel> criteriaQuery =
 * criteriaBuilder.createQuery(orderModel.class); Root<orderModel> root =
 * criteriaQuery.from(orderModel.class); criteriaQuery.select(root);
 * 
 * TypedQuery<orderModel> query = currentSession.createQuery(criteriaQuery);
 * return query.getResultList(); }
 * 
 * //method to update the order pocessed by whom to keep track
 * 
 * @Override
 * 
 * @Transactional public void updateOrderProcessedBy(Long orderId, Integer
 * processedBy) { // Retrieve the order entity based on the order ID orderModel
 * order = entityManager.find(orderModel.class, orderId);
 * 
 * // Check if the order exists if (order != null) { // Set the processed by
 * information on the order entity order.setOrdr_processedby(processedBy);
 * 
 * // Save the updated order entity to the database entityManager.merge(order);
 * } } //method to get orders placed on certain dates selected
 * 
 * @Override
 * 
 * @Transactional public List<orderModel> loadOrdersByDate(Timestamp startDate,
 * Timestamp endDate) { System.out.println("loading"); Session currentSession =
 * entityManager.unwrap(Session.class); CriteriaBuilder criteriaBuilder =
 * currentSession.getCriteriaBuilder(); CriteriaQuery<orderModel> criteriaQuery
 * = criteriaBuilder.createQuery(orderModel.class); Root<orderModel> root =
 * criteriaQuery.from(orderModel.class); criteriaQuery.select(root);
 * criteriaQuery.where(criteriaBuilder.between(root.get("orderDate"), startDate,
 * endDate));
 * 
 * TypedQuery<orderModel> query = currentSession.createQuery(criteriaQuery);
 * return query.getResultList(); }
 * 
 * //methods to update the status of the order placed
 * 
 * @Override
 * 
 * @Transactional public void updateOrderShipmentStatus(int orderId, String
 * status) { // Retrieve the order entity based on the order ID orderModel order
 * = entityManager.find(orderModel.class, orderId);
 * 
 * // Check if the order exists if (order != null) { // Set the processed by
 * information on the order entity order.setShipment_status(status);
 * 
 * // Save the updated order entity to the database entityManager.merge(order);
 * } }
 * 
 * }
 */