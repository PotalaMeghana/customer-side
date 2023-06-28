<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.*" %>
<%@ page import="eStoreProduct.utility.ProductStockPrice" %>
<%@ page import="eStoreProduct.model.custCredModel" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Product Description</title>
<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f9f9f9;
    }

    .container {
        max-width: 800px;
        margin: 0 auto;
        background-color: #fff;
        padding: 20px;
        border-radius: 5px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    h2 {
        text-align: center;
        margin-bottom: 20px;
        color: #333;
        text-transform: uppercase;
        letter-spacing: 2px;
    }

    .product-image {
        text-align: center;
        margin-bottom: 20px;
    }

    .product-image img {
        max-width: 100%;
        max-height: 400px;
        border-radius: 5px;
        box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
    }

    .product-details {
        text-align: center;
    }

    .product-name {
        font-size: 24px;
        font-weight: bold;
        margin-bottom: 10px;
        color: #333;
    }

    .product-description {
        margin-bottom: 20px;
        color: #666;
    }

    .product-price {
        font-size: 20px;
        margin-bottom: 20px;
        color: #333;
    }

    label {
        display: inline-block;
        margin-bottom: 5px;
        color: #666;
    }

    .qtyinp {
        width: 50px;
        padding: 5px;
        border: 1px solid #ccc;
        border-radius: 3px;
        color: #333;
    }

    .shipment-table {
        margin-top: 10px;
    }

    .shipment-table td {
        padding: 5px;
    }

    .buynow {
        display: block;
        margin-top: 20px;
        padding: 10px 20px;
        font-size: 16px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 3px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }
    .shipment-table input[type="submit"] {
        display: inline-block;
        padding: 10px 20px;
        font-size: 16px;
        background-color: #007bff;
        color: #fff;
        border: none;
        border-radius: 3px;
        cursor: pointer;
        transition: background-color 0.3s ease;
    }
 .shipment-table input[type="submit"]:hover {
        background-color: #0056b3;
    }

    .buynow:hover {
        background-color: #0056b3;
    }

    .not-available {
        color: red;
        font-style: italic;
    }

    .available {
        color: green;
    }
</style>

</head>

<%
ProductStockPrice product = (ProductStockPrice)request.getAttribute("oneproduct");
%>

<body>
<div class="container">
    <h2>Product Description</h2>
    <div class="product-image">
        <img src="<%= product.getImage_url()%>" alt="<%= product.getProd_title() %>">
    </div>
    <div class="product-details">
        <h3 class="product-name"><%= product.getProd_title() %></h3>
        <p class="product-description"><%= product.getProd_desc() %></p>
     <%if(product.getProduct_stock()>=5){ %>
                    <p class="card-text" id="qty"><b>Stock: </b><%=product.getProduct_stock() %></p>
                    <%} else{%>
                    <b><p>Out of Stock</p></b>
                    <%} %>
        <p class="product-price">Price: $<%= product.getPrice()%></p>
    </div>
    <div>
        <label>Qty:</label>
        <input type="number" class="btn btn-primary qtyinp input-width" id="qtyinp" value="1" min="1" data-product-id="<%= product.getProd_id() %>">
    </div>
    <div>
        <p>Pincode Availability:</p>
        <p id="availability"></p>

        <form id="shipment-form">
            <p id="ship"></p>
            <table class="shipment-table">
                <tr>
                    <td>Delivery To:</td>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" id="custName" name="custName" value="${cust != null ? cust.custName : ""}"></td>
                </tr>
                <tr>
                    <td>Address:</td>
                    <td><input type="text" id="custSAddress" name="custSAddress" value="${cust != null ? cust.custSAddress : ""}"></td>
                </tr>
                <tr>
                    <td>Pincode:</td>
                    <td>
<input type="number" class="custPincode" id="custPincode" name="custPincode" value="${cust != null ? cust.custSpincode: ""}"  oninput="checkPincodeAvailability(this.value);">
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Save">
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div>
        <button class="btn btn-primary buynow" id="buynow" data-product-id="<%= product.getProd_id() %>">Buy Now</button>
    </div>
</div>
<script>
  // Wait for the document to finish loading
  document.addEventListener('DOMContentLoaded', function() {
    var button = document.getElementById("buynow");
    var stockValue = '<%=product.getProduct_stock() %>'; // Assuming you have retrieved the stock value as explained in the previous response
if (stockValue <= 5) {
    console.log("button disabled");
    
    button.setAttribute("disabled", "true");
    button.innerHTML = "Out of Stock";
  }
  });
</script>

<script>
var stockValue = '<%=product.getProduct_stock() %>';
$(document).ready(function(){
	console.log("hiiiiiiiiiii");
	  var pin=$("#custPincode");
	  console.log(pin.val());
	  checkPincodeAvailability(pin.val());
	  
	});


    $(document).ready(function() {
        $('#shipment-form').submit(function(e) {
            e.preventDefault();
            var submitButton = $(this).find('input[type="submit"]');
            console.log("shipment address");

            var name = $("#custName").val();
            var add = $("#custSAddress").val();
            var pin = $(".custPincode").val(); // Corrected id here
            console.log(pin);

            $.ajax({
                type: 'POST',
                url: 'updateshipment',
                data: { name: name, custSAddress: add, custSpincode: pin },
                success: function(response) {
                    console.log(response);
                    if (response === "Valid") {
                        submitButton.val("Changed");
                    } else {
                        alert("Shipment is Not available for this Address");
                    }
                },
                error: function(error) {
                    console.error(error);
                }
            });
        });
    });

    function buyproduct(productId) {
        var qty = $("#qtyinp").val();
        console.log(qty + " quantity");
        var notAvailable = $(".not-available");
console.log(stockValue+"stock value");
if (notAvailable.length > 0) {
            alert("Please check the availability of Shipment Location ");
        }  
        
        else{
        $.ajax({
            url: "checkloginornot",
            method: 'GET',
            data: { productId: productId },
            success: function(response) {
                if (response === "true") {
                    window.location.href = "buythisproduct?productId=" + productId + "&qty=" + qty;
                } else {
                    alert("Please sign in");

                    window.location.href = "signIn";
                }
            },
            error: function(xhr, status, error) {
                console.log('AJAX Error: ' + error);
            }
        });
        }
    }

    $(document).ready(function() {
        $(document).on('click', '.buynow', function(event) {
            event.preventDefault();
            var productId = $(this).data('product-id');
            buyproduct(productId);
        });
    });
  


    function checkPincodeAvailability(pincode) {
        console.log("Checking pincode availability for Product ID: "+pincode);

        $.ajax({
            type: "POST",
            url: "checkPincode",
            data: { pincode: pincode },
            success: function(response) {
                var availabilityElement = $("#availability");
                console.log(response);
                if (response=="true") {
                    availabilityElement.text("Available").removeClass("not-available").addClass("available");
                } else {
                    availabilityElement.text("Not Available").removeClass("available").addClass("not-available");
                }
            },
            error: function(error) {
                console.error(error);
            }
        });
    }
</script>
</body>
</html>
