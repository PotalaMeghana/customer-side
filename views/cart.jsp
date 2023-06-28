 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="eStoreProduct.utility.ProductStockPrice" %>
<%@ page import="eStoreProduct.model.custCredModel" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cart</title>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
		<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
		
        <style>
           body {
            font-family: Arial, sans-serif;
      		margin: 0;
      		padding: 0;
            background-color: #f2f2f2;
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
		#pcd{
		  flex: 0 0 auto;
		  width: 25%;
		}
		#btn{
		  flex: 0 0 auto;
		  width: 15%;
		}
        .product-price {
            font-size: 16px;
            font-weight: bold;
            color: #e91e63;
        }
         .input-button-container {
		    display: flex;
		    align-items: center;
		    justify-content: center;
		  }
		
		  .input-button-container .form-control {
		    margin-right: -10px; /* Adjust the margin as needed */
		  }
		   .not-available {
        color: red;
        font-style: italic;
    }

    .available {
        color: green;
    }
    .input-width{
    width: 50px; /* Adjust the width as per your preference */
}
    
        </style>
    <script>
    $(document).ready(function() {
        updateCostOnLoad();
    });
    function buynow()
    {
      	  console.log("buy now now now");
      	  var stock=$("#stockval").val();
      	  console.log(stock);

      	 var notAvailable = $(".not-available");
      
           if (notAvailable.length > 0 ) {
               alert("Please check the availability of Shipment Location before placing order!");
           } 
           if(stock<=0){
               alert("products out of stock");

           }
           else{	 
		    	 $.ajax({
		           url: 'buycartitems',
		           method: 'GET',
		           success: function(response) {
		               $('#payment').html(response); // Set the response HTML as the inner HTML of the cart items element
		           },
		           error: function(xhr, status, error) {
		               console.log('AJAX Error: ' + error);
		           }
		       });
           }
    	  	//window.location.href="buycartitems";  
    	    }
    function updateQuantity(input) {
        var quantity = input.value;
        console.log(quantity+"qnty!!!!!!!!!!!");
        var productId = input.getAttribute('data-product-id');
        console.log("qty in updateqty method "+quantity);
        console.log("product no=" + productId);
        $.ajax({
            url: 'updateQuantity',
            method: 'POST',
            data: { productId: productId, quantity: quantity },
            success: function(response) {
                console.log("response of updateqty  "+response);
                $("#tcost").html("Total Cost: " + response);
                
            },
            error: function(xhr, status, error) {
                console.log('AJAX Error: ' + error);
            }
        });
    }
    
  
    
    function updateCostOnLoad(){
        console.log("updateCostOnLoad");

    	$.ajax({
            url: 'updateCostOnLoad',
            method: 'POST',
            success: function(response) {
            	console.log(response);
                $("#tcost").html("Total Cost: " + response);

            },
            error: function(xhr, status, error) {
                console.log('AJAX Error: ' + error);
            }
        });
    }
    function showProductDetails(productId) {
        window.location.href = "prodDescription?productId=" + productId;
        console.log(productId);
    }
    
    function decreaseQuantity(input) {
        var quantityInput = input.parentNode.parentNode.querySelector('input[type="text"]');
        var currentQuantity = parseInt(quantityInput.value);

        if (currentQuantity > 1) {
            quantityInput.value = currentQuantity - 1;
            updateQuantity(quantityInput);
        }
    }

    function increaseQuantity(input) {
        var quantityInput = input.parentNode.parentNode.querySelector('input[type="text"]');
        var currentQuantity = parseInt(quantityInput.value);

        quantityInput.value = currentQuantity + 1;
        updateQuantity(quantityInput);
    }
    
   
    </script>
</head>
<body>
    <div class="container mt-5">
        <h2>Cart</h2>
        <div class="row mt-4">
            <%-- Iterate over the products and render the HTML content --%>
            <%		custCredModel cust1 = (custCredModel) session.getAttribute("customer");
           			 List<ProductStockPrice> products;
    		if (cust1 != null) {
               products = (List<ProductStockPrice>) request.getAttribute("products");
    		} else {
                products = (List<ProductStockPrice>) request.getAttribute("alist");
			}
    		if(products==null){ %>
    			<div>No Cart Items</div> <%
            }else{
        
				double totalcost=0.0;
                for (ProductStockPrice product : products) {
            %>
             <div class="product-card">
                            <a href="javascript:void(0)" onclick="showProductDetails('<%= product.getProd_id() %>')">
                                <img class="card-img-top" src="<%= product.getImage_url() %>" alt="<%= product.getProd_title() %>">
                            </a>
                            <div class="product-details">
			                   <h5 class="card-title"><%= product.getProd_title() %></h5>
							   <p class="card-text"><%= product.getProd_desc() %></p>
		                       <%if(product.getProduct_stock()<=5 && product.getProduct_stock()>0){ %>
		                            <input type="hidden" id="stockval" value="<%=product.getProduct_stock() %>">
					                <p class="card-text" ><i><b>Only <%=product.getProduct_stock() %> are left..Hurry Up!! </i></b></p>
					           <%}else{%>
					                    <b><p class="stockp">Out of Stock</p></b>
					            <%} %>
					            <p class="card-text"><%= product.getPrice() %></p>
					            <label>Qty:</label>

    <span class="input-group-btn">
        <button class="btn btn-primary btn-minus" type="button" onclick="decreaseQuantity(this)">
            <i class="fa fa-minus"></i>
        </button>
    </span>
    <input type="text" class="btn btn-primary qtyinp input-width" id="qtyinp" value="<%=product.getQuantity() %>" min="1" onchange="updateQuantity(this)" data-product-id="<%= product.getProd_id() %>">
    <span class="input-group-btn">
        <button class="btn btn-primary btn-plus" type="button" onclick="increaseQuantity(this)">
            <i class="fa fa-plus"></i>
        </button>
    </span>
<br>
<br>
                        <button class="btn btn-primary removeFromCart" data-product-id="<%= product.getProd_id() %>">Remove from Cart</button>
                        <button class="btn btn-secondary addToWishlistButton" data-product-id="<%= product.getProd_id() %>">Add to Wishlist</button>
                </div>
                
            </div>
            <%
                }
            %>
        </div>
    </div>
    
 <div align="center" class="container mt-3">
  <div id="checkpincode" class="row justify-content-center">
   <div>
        <p id="availability"></p>

        <form id="shipment-form">
            <p id="ship"></p>
            <table class="shipment-table">
                <tr>
                    <td>Delivery Location:</td>
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
                        <button class="btn btn-primary changeaddress" type="submit">Change Address</button>
                    </td>
                </tr>
            </table>
        </form>
    </div>
  </div>
  <br/>
  <div id="tcost"></div>
  <button class="btn btn-primary BuyNow" id="BuyNow" onclick="buynow()">Place Order</button>
</div>
 <%
                }
    		
   %>
   
<script>
$(document).ready(function(){
	console.log("hiiiiiiiiiii");
	  var pin=$("#custPincode");
	  console.log(pin.val());
	  checkPincodeAvailability(pin.val());
	  
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
                availabilityElement.text("Shipment is Available for this Pincode").removeClass("not-available").addClass("available");
            } else {
                availabilityElement.text("Shipment is not Available for this Pincode").removeClass("available").addClass("not-available");
            }
        },
        error: function(error) {
            console.error(error);
        }
    });
}

$(document).ready(function() {
    $('.changeaddress').click(function(e) {
        e.preventDefault();
        var submitButton = $(this);
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
                    toastr.success("Address Changed");
                } else {
                    toastr.info("Shipment is Not available for this Address");
                }
            },
            error: function(error) {
                console.error(error);
            }
        });
    });
});

function disableBuyNow(){
	var button = document.getElementById("BuyNow");
	button.setAttribute("disabled", "true");
	button.innerHTML = "Out of Stock";
}

</script>

</body>
</html>
 
 
