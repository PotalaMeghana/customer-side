<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="eStoreProduct.model.admin.entities.SlamOrderModel,eStoreProduct.model.admin.entities.SlamOrderProduct,java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order List</title>
    <style>
        table {
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid black;
            padding: 8px;
        }
    </style>
</head>
<body>
<%List<SlamOrderModel> orderList=(List<SlamOrderModel>)request.getAttribute("orderReport");%>
<%double price=0;double gst=0;double total=0;%>
    <table>
        <thead>
            <tr>
                <th>Order ID</th>
                <th>Bill Number</th>
                <th>Payment Reference</th>
                <th>Customer ID</th>
                <th>Order Date</th>
                <th>Processed By</th>
                <th>Payment Status</th>
                <th>Payment Mode</th>
                <th>Product ID</th>
                <th>Quantity</th>
                <th>Price</th>
                <th>GST</th>
                <th>Total</th>
              
              
              
              
            </tr>
        </thead>
        <tbody>
            <% 
                long previousOrderId = -1;
                for (SlamOrderModel order : orderList) {
                    for (SlamOrderProduct product : order.getOrderProducts()) {
            %>
                        <tr>
                        <%total+=order.getTotal();gst+= order.getGst();price+=(product.getPrice()).doubleValue();%>
                            <td><%= previousOrderId != order.getId() ? order.getId() : "" %></td>
                            <td><%= order.getBillNumber() %></td>
                            <td><%= order.getPaymentReference() %></td>
                            <td><%= order.getOrdr_cust_id() %></td>
                            <td><%= order.getOrderDate() %></td>
                            <td><%= order.getOrdr_processedby() %></td>
                            <td><%= order.getPaymentStatus() %></td>
                            <td><%= order.getPaymentMode() %></td>
              
              
              
                            <td><%= product.getProductId() %></td>
                            <td><%= product.getQuantity() %></td>
                            <td><%= product.getPrice() %></td>
                            <td><%= order.getGst() %></td>
                            <td><%= order.getTotal() %></td>
              
                        </tr>
            <% 
                    }
                    previousOrderId = order.getId();
                }
            %>
        </tbody>
    </table>
</body>
</html>
