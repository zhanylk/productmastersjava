<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Add User</title>
</head>
<body>
    <h2>Add New User</h2>
    <form action="users" method="post">
        <label>Username:</label><br>
        <input type="text" name="username" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <label>Email:</label><br>
        <input type="email" name="email" required><br><br>

        <label>Phone:</label><br>
        <input type="text" name="phone" required><br><br>

        <label>Address:</label><br>
        <input type="text" name="address"><br><br>

        <label>Role:</label><br>
        <select name="role" required>
            <option value="CUSTOMER">Customer</option>
            <option value="RESTAURANT_OWNER">Restaurant Owner</option>
            <option value="COURIER">Courier</option>
            <option value="ADMIN">Admin</option>
        </select><br><br>

        <input type="submit" value="Add User">
    </form>
</body>
</html>
