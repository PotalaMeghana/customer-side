<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product Catalog</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            padding: 20px;
        }

        .container {
            margin-top: 20px;
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #333;
        }

        .product-card {
            background-color: #fff;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            overflow: hidden;
            transition: box-shadow 0.3s;
            cursor: pointer;
            margin-bottom: 20px;
            display: flex;
        }

        .product-card:hover {
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        .product-card img {
            width: 200px;
            height: 200px;
            object-fit: cover;
        }

        .product-details {
            padding: 20px;
            flex-grow: 1;
        }

        .product-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 10px;
            color: #333;
        }

        .product-description {
            font-size: 14px;
            margin-bottom: 10px;
            color: #666;
        }

        .product-price {
            font-size: 16px;
            font-weight: bold;
            color: #e91e63;
        }

        a {
            text-decoration: none;
        }

        /* popupmodal */
        .modal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.5);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 400px;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }

        /* tracjstyle */
        .shipment-status-line {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px;
        }

        .dot {
            width: 15px;
            height: 15px;
            border-radius: 50%;
            background-color: gray;
        }

        .shipment-status-text {
            display: flex;
            justify-content: space-between;
            font-size: 14px;
            color: gray;
            margin-top: 10px;
        }

        .status {
            flex-grow: 1;
            text-align: center;
        }
    </style>
</head>
<body >
    <!-- Add this code snippet inside the <body> tag of your JSP page -->
  

    <div id="trackOrderModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h3>Order Tracking</h3>
            <div class="shipment-status-line">
                <div class="dot"></div>
                <div class="dot"></div>
                <div class="dot"></div>
                <div class="dot"></div>
                <div class="dot"></div>
            </div>
            <div class="shipment-status-text">
                <div class="status">Order Placed</div>
                <div class="status">Order Processed</div>
                <div class="status">Dispatched</div>
                <div class="status">Out for Delivery</div>
                <div class="status">Delivered</div>
            </div>
        </div>
    </div>

    <div id="cancelOrderModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h3>Your order has been cancelled</h3>
            <p>Insert any additional message or details here.</p>
        </div>
    </div>
    

 
    
    <div class="container">
      <h2>Orders Catalog</h2>
        <c:forEach var="product" items="${orderProducts}">
<%--             <a href="productDetails?id=${product.getId()}">
 --%>               <a href="productDetails?id=${product.getId()}&orderId=${product.getOrdId()}">
 
                    <div class="product-card">
                    <img class="card-img-top" src="${product.getImageUrl()}" alt="${product.getName()}">
                    <div class="product-details">
                        <div class="product-title">${product.getName()}</div>
                        <div class="product-description">${product.getDescription()}</div>
                        <div class="product-price">${product.getPrice()}</div></a>
                        <c:if test="${product.getShipStat() != 'delivered' && product.getShipStat() != 'out for delivery' && product.getShipStat() != 'cancelled'}">
                            <button class="btn btn-danger cancel-order-btn" data-orderproid="${product.getId()}"  data-orderid="${product. getOrdId()}" >Cancel Order</button>
                        </c:if>
                        <c:if test="${product.getShipStat() != 'delivered' && product.getShipStat() != 'cancelled'}">
                            <button class="btn btn-primary track-order-btn" data-orderproid="${product.getId()}"   data-orderid="${product. getOrdId()}">Track Order</button>
                        </c:if>
                    </div>
                </div>
            
        </c:forEach>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script>
    $(document).ready(function() {
        function displayModal(modalId) {
            var modal = $('#' + modalId);
            modal.css('display', 'block');
        }

        function closeModal(modalId) {
            var modal = $('#' + modalId);
            modal.css('display', 'none');
        }
        
        function hideButtons(orderproId, orderId) {
             $('.cancel-order-btn[data-orderproid="' + orderproId + '"][data-orderid="' + orderId + '"]').hide(); 
            $('.track-order-btn[data-orderproid="' + orderproId + '"][data-orderid="' + orderId + '"]').hide();
        }

        $('.cancel-order-btn').click(function() {
            var orderproId = $(this).data('orderproid');
            var orderId = $(this).data('orderid');
            var cancelButton = $(this);
            console.log("in ajax")
            $.ajax({
                url: 'cancelOrder',
                type: 'POST',
                data: { orderproId: orderproId,
                        orderId:orderId	
                },
                success: function(response) {
                    displayModal('cancelOrderModal');
                    hideButtons(orderproId, orderId);
                    /* cancelButton.text('Cancelled');
                    cancelButton.removeClass('btn-danger').addClass('btn-secondary');
                    cancelButton.prop('disabled', true); */
                },
                error: function(xhr, status, error) {
                    // Handle error
                }
            });
        });

        $('.track-order-btn').click(function() {
            var orderproId = $(this).data('orderproid');
            var orderId = $(this).data('orderid');
            $.ajax({
                url: 'trackOrder',
                type: 'GET',
                data: { orderproId: orderproId ,
                	orderId:orderId		
                },
                success: function(response) {
                    updateShipmentStatus(response);
                    displayModal('trackOrderModal');
                },
                error: function(xhr, status, error) {
                    // Handle error
                }
            });
        });

        function updateShipmentStatus(shipmentStatus) {
            $('.dot').css('background-color', 'gray');

            var statusIndex;
            switch (shipmentStatus) {
                case 'Order_Placed':
                    statusIndex = 0;
                    break;
                case 'Order Processed':
                    statusIndex = 1;
                    break;
                case 'dispatched':
                    statusIndex = 2;
                    break;
                case 'Out for Delivery':
                    statusIndex = 3;
                    break;
                case 'delivered':
                    statusIndex = 4;
                    break;
                default:
                    statusIndex = -1;
                    break;
            }

            if (statusIndex >= 0) {
                $('.dot').eq(statusIndex).css('background-color', 'green');
            }
        }

        $('.close').click(function() {
            var modalId = $(this).closest('.modal').attr('id');
            closeModal(modalId);
        });
    });
    </script>
</body>
</html>