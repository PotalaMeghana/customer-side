<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ page import="eStoreProduct.utility.ProductStockPrice" %>
<%@ page import="eStoreProduct.model.custCredModel" %>
<%@ page import="eStoreProduct.model.productqty" %>
<%@ page import="eStoreProduct.model.wallet" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Order Summary</title>
 <script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
         <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
     <script src="https://checkout.razorpay.com/v1/checkout.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        
       .container {
		    max-width: 960px;
		    margin: 0 auto;
		}
		
		.mt-5 {
		    margin-top: 3rem;
		}
		
		.mt-4 {
		    margin-top: 2rem;
		}
		
		.mb-4 {
		    margin-bottom: 2rem;
		}
		
		.card {
		    border: 1px solid #ddd;
		    border-radius: 4px;
		}
		
		.card-img-top {
		    width: 100%;
		    height: 200px;
		    object-fit: cover;
		    border-top-left-radius: 4px;
		    border-top-right-radius: 4px;
		}
		
		.card-body {
		    padding: 1rem;
		}
		
		.card-title {
		    font-size: 1.25rem;
		    font-weight: bold;
		    margin-bottom: 0.5rem;
		}
		
		.card-text {
		    font-size: 1rem;
		    margin-bottom: 0.5rem;
		}
		
		.btn-primary {
		    background-color: #007bff;
		    color: #fff;
		    border-color: #007bff;
		    padding: 0.5rem 1rem;
		    font-size: 1rem;
		    cursor: pointer;
		}
		
		.btn-primary:hover {
		    background-color: #0069d9;
		    border-color: #0062cc;
		}
		
		.container.mt-5:nth-child(2) {
		    background-color: #f9f9f9;
		    padding: 20px;
		    border: 1px solid #ddd;
		    border-radius: 4px;
		    margin-bottom: 20px;
		}
        .btn-primary {
            background-color: #007bff;
            color: #fff;
            border-color: #007bff;
            padding: 0.5rem 1rem;
            font-size: 1rem;
            cursor: pointer;
        }
        
        .btn-primary:hover {
            background-color: #0069d9;
            border-color: #0062cc;
        }
        
        table {
            width: 100%;
            margin-bottom: 1rem;
            color: #212529;
            table-layout: fixed; /* Added to enforce consistent width */
        }
        
        table td {
            padding: 0.25rem;
            word-wrap: break-word; /* Added to wrap long content */
        }
        
        input[type="text"] {
            width: 100%;
            padding: 0.375rem 0.75rem;
            font-size: 1rem;
            border: 1px solid #ced4da;
            border-radius: 0.25rem;
        }
        
        .table-col1 {
            width: 20%; /* Adjust the width as needed */
        }
        
        .table-col2 {
            width: 80%; /* Adjust the width as needed */
        }
        /* Custom styles for the box containing the shipment form */
		#cont {
		    background-color: #f9f9f9;
		    padding: 20px;
		    margin-bottom: 20px;
		    border: 1px solid #ddd;
		    border-radius: 4px;
		    text-align: center;
	        margin: 0 auto;
	        max-width: 700px; /* Adjust the max-width as needed */
		}
        
    </style>
    <script>
    function openCheckout() {
        var amt = document.getElementById("tid").value;
        console.log("amount in payment options jsp " + amt);
        var orderId;

        $.ajax({
            url: "getOrderId",
            method: 'GET',
            data: { amt: amt },
            success: function(response) {
                orderId = response;
                console.log("response == " + response);
                handleOrder(orderId, amt);
            },
            error: function(xhr, status, error) {
                console.log('AJAX Error: ' + error);
            }
        });
    }

    function handleOrder(orderId, amt) {
        var options = {
            key: "rzp_test_Eu94k5nuplVQzA",
            name: "E-Cart",
            // amount: 1000,
            description: "SLAM payments",
            image: "https://s29.postimg.org/r6dj1g85z/daft_punk.jpg",
            prefill: {
                name: "Adithya",
                email: "adithya.thandra@gmail.com",
                contact: "9290005690"
            },
            notes: {
                address: "India",
                merchant_order_id: orderId
            },
            theme: {
                color: "#F37254"
            },
            order_id: orderId,
            handler: function(response) {
                document.getElementById('paymentReference').value = response.razorpay_payment_id;
                document.getElementById('razorpay_order_id').value = orderId;
                // document.getElementById('razorpay_signature').value = response.razorpay_signature;
                document.getElementById('razorpay_amount').value = amt;

                    // Make an AJAX request to the server-side endpoint
                  
                document.razorpayForm.submit();
            },
            modal: {
                ondismiss: function() {
                    console.log("This code runs when the popup is closed");
                },
                escape: true,
                backdropclose: false
            }
        };

        var rzpButton = document.getElementById("rzp-button1");
        rzpButton.addEventListener("click", function(e) {
            e.preventDefault();
            console.log("inside");

            // Open Razorpay checkout with updated options
            var rzp = new Razorpay(options);
            rzp.open();
        });
    }

    </script>
</head>
<body>
<br/>
   <b><h3 align="center">Order Summary</h3></b>
    <div id="id1">
        <div class="container mt-5">
            <div class="row mt-4">
                <% custCredModel cust1 = (custCredModel) session.getAttribute("customer");
               
                List<ProductStockPrice> products = (List<ProductStockPrice>) request.getAttribute("products");
                 wallet Wallet=(wallet) request.getAttribute("Wallet");
                for (ProductStockPrice product : products) {
                %>
                <div class="col-lg-4 col-md-6 mb-4">
                    <div class="card h-100">
                        <img class="card-img-top" src="<%= product.getImage_url() %>" alt="<%= product.getProd_title() %>">
                        <div class="card-body">
                            <h5 class="card-title"><%= product.getProd_title() %></h5>
                            <p class="card-text">Quantity: <%= product.getQuantity() %></p>
                            <p class="card-text">Price: <%= product.getPrice() %></p>
                            <p class="card-text">Subtotal: <%= product.getQtyprice() %></p>
                             <p class="card-text">gst: <%= product.getGst() %></p>
                            
                            
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
        <div id="cont" class="text-center">
		    <form id="shipment-form">
		        <table align="center">
		            <tr>
		                <td class="table-col2"><b>Delivery Location:</b></td>
		                <td class="table-col2"></td>
		            </tr>
		            <tr>
		                <td class="table-col1"><b>Name:</b></td>
		                <td class="table-col2"><%=cust1.getCustName()%></td>
		            </tr>
		            <tr>
		                <td class="table-col1"><b>Address:</b></td>
		                <td class="table-col2"><%=cust1.getCustSAddress()%></td>
		            </tr>
		            <tr>
		                <td class="table-col1"><b>Pincode:</b></td>
		                <td class="table-col2"><%=cust1.getCustPincode()%></td>
		            </tr>
		        </table>
		    </form>
		</div>

       <div style="text-align: center;">
		    <label for="wallet">Use Wallet:</label>
		    <input type="number" id="wallet" name="wallet" value="<%=Wallet.getAmount()%>">
		    <input type="checkbox" id="walletAmount10" name="walletAmount" onclick="checkWalletAmount()">
		</div>
		<div style="text-align: center;">
		    <div style="display: inline-block; text-align: center;">
		        <label for="tid">Total Order Cost:</label>
		        <input type="number" id="tid" value="<%=ProductStockPrice.getTotal()%>">
		    </div>
		    <br>
<!-- 		    <button class="btn btn-primary back">Back</button> -->
		    <button class="btn btn-primary pay" id="rzp-button1" onclick="openCheckout('${orderId}')">Proceed to Checkout</button>
		</div>
		</div>


    <script>
    function checkWalletAmount() {
        var total = parseFloat(<%=ProductStockPrice.getTotal()%>);
        var wallet =document.getElementById("wallet").value;
        var useWallet = document.getElementById("walletAmount10").checked;
        console.log(total+""+wallet);
        if (useWallet && total > wallet && wallet>=0) {
            total -= wallet;
            document.getElementById("tid").value=total;
        } else {
            document.getElementById("tid").value=total;
                   if(usewallet)
                	   {
                alert("Wallet amount is insufficient.");
                	   }
        }
    }
     

       /*  $(document).on('click', '.back', function(event) {
            event.preventDefault();
            history.back();
        }); */

        $('#walletAmount10').click(function() {
        	checkWalletAmount();
          });
 
    </script>
    <form action="invoice" method="POST" name="razorpayForm">
    
        <input id="paymentReference" type="hidden" name="paymentReference" />
        <input id="razorpay_order_id" type="hidden" name="billNumber" />
		<input id="razorpay_amount" type="hidden" name="total" />

		<input id="shippingAddress" type="hidden" name="shippingAddress" value="<%=cust1.getCustSAddress()%>" />
		<input id="customerId" type="hidden" name="ordr_cust_id" value="<%=cust1.getCustId()%>" />
		<input id="shippingPincode" type="hidden" name="shippingPincode" value="<%=cust1.getCustSpincode() %>">
    </form>
</body>
</html>
 