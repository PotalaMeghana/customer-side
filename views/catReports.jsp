<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="eStoreProduct.model.admin.entities.*,java.util.*,eStoreProduct.model.admin.output.*"
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    body {
        font-family: Arial, sans-serif;
    }
    
    table {
        border-collapse: collapse;
        width: 100%;
        margin-bottom: 20px;
        border-radius: 5px;
        box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    }
    
    th, td {
        text-align: left;
        padding: 12px;
        border-bottom: 1px solid #ddd;
    }
    
    th {
        background-color: #f8f8f8;
        font-weight: bold;
    }
    
    tr:hover {
        background-color: #f2f2f2;
    }
    
    .total-row {
        font-weight: bold;
    }
</style>
<script type="text/javascript">
function totalGST(){
	 $.ajax({
	      url: "remRegion",
	      method: 'POST',
	      data:{
	    	 regionId:id
	      },
	      success: function(response) {
	      console.log(response);
	      	// Set the response HTML as the inner HTML of the select element
	      dis();
	      },
	      error: function(xhr, status, error) {
	        console.log('AJAX Error: ' + error);
	      }
	    }); 

}
</script>
</head>
<body>
<% List<CategoryReportViewModel> report = (List<CategoryReportViewModel>) request.getAttribute("categoryReport"); %>
<% 
    int totalProducts = 0;
    double totalGST = 0.0;
    double totalSales = 0.0;
    double totalAmount = 0.0;
    for (CategoryReportViewModel viewModel : report) {
        totalProducts += viewModel.getTotal_products();
        totalGST += viewModel.getTotal_gst();
        totalSales += viewModel.getTotal_sales();
        totalAmount += viewModel.getTotal_amount();
    }
%>
<table>
    <thead>
        <tr>
            <th >Category ID</th>
            <th>Category Name</th>
            <th>Total Products</th>
            <th onclick="totalGST()">Total GST</th>
            <th onclick="totalSales()">Total Sales</th>
            <th onclick="totalAmount()">Total Amount</th>
        </tr>
    </thead>
    <tbody>
        <% for (CategoryReportViewModel viewModel : report) { %>
            <tr>
                <td><%= viewModel.getPtct_id() %></td>
                <td><%= viewModel.getPtct_name() %></td>
                <td><%= viewModel.getTotal_products() %></td>
                <td><%= viewModel.getTotal_gst() %></td>
                <td><%= viewModel.getTotal_sales() %></td>
                <td><%= viewModel.getTotal_amount() %></td>
            </tr>
        <% } %>
        <tr class="total-row">
            <td colspan="2">Total</td>
            <td><%= totalProducts %></td>
            <td><%= totalGST %></td>
            <td><%= totalSales %></td>
            <td><%= totalAmount %></td>
        </tr>
    </tbody>
</table>
</body>
</html>
