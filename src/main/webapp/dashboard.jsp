<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="style.css">
</head>

<body>

<div class="navbar">Expense Dashboard</div>

<div class="dashboard-container">

    <h2 class="welcome">Welcome, ${sessionScope.user}</h2>

    <div class="card total-card">
        <h3>Total Expenses</h3>
        <h1>₹${total}</h1>
    </div>

    <div class="button-group">
        <a href="addExpense.html"><button>Add Expense</button></a>
        <a href="view"><button>View Expenses</button></a>
        <a href="logout"><button class="secondary">Logout</button></a>
    </div>

</div>

</body>
</html>