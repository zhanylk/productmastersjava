package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.StudentAttendanceDto;
import org.example.util.AttendanceNameUtil;

import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/attendance")
public class StudentAttendanceServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123456";

    @Override
    public void init() throws ServletException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selectedGroupId = req.getParameter("groupFilter");
        List<StudentAttendanceDto> students = getStudentsFromDB(selectedGroupId);
        List<Map<String, String>> groups = getAllGroups();

        resp.setContentType("text/html; charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<html><head><title>Посещаемость</title>");
        out.println("<style>");
        out.println("table { width: 80%; border-collapse: collapse; margin: 20px 0; }");
        out.println("th, td { border: 1px solid black; padding: 8px; text-align: left; }");
        out.println("th { background-color: #f2f2f2; }");
        out.println(".filter { margin-bottom: 20px; }");
        out.println("</style></head><body>");

        out.println("<h2>Посещение лекций</h2>");

        // Форма фильтрации по группам
        out.println("<div class='filter'>");
        out.println("<form method='GET' action='attendance'>");
        out.println("<label for='groupFilter'>Фильтр по группе:</label>");
        out.println("<select name='groupFilter' id='groupFilter' onchange='this.form.submit()'>");
        out.println("<option value=''>Все группы</option>");
        for (Map<String, String> group : groups) {
            out.println("<option value='" + group.get("id") + "'"
                    + (group.get("id").equals(selectedGroupId) ? " selected" : "") + ">"
                    + group.get("name") + "</option>");
        }
        out.println("</select>");
        out.println("</form>");
        out.println("</div>");

        // Форма добавления нового студента
        out.println("<form method='POST' action='attendance'>");
        out.println("<h3>Добавить нового студента</h3>");
        out.println("ФИО: <input type='text' name='name' required><br>");
        out.println("Группа: <select name='groupId' required>");
        for (Map<String, String> group : groups) {
            out.println("<option value='" + group.get("id") + "'>" + group.get("name") + "</option>");
        }
        out.println("</select><br>");
        out.println("Посетил: <select name='isAttended'>");
        out.println("<option value='true'>Да</option>");
        out.println("<option value='false'>Нет</option>");
        out.println("</select><br>");
        out.println("<input type='submit' value='Добавить'>");
        out.println("</form>");

        // Таблица студентов
        out.println("<table>");
        out.println("<tr><th>ФИО</th><th>Группа</th><th>Посетил</th><th>Действия</th></tr>");

        if (students.isEmpty()) {
            out.println("<tr><td colspan='4'>Нет данных для отображения</td></tr>");
        } else {
            for (StudentAttendanceDto student : students) {
                out.println("<tr>");
                out.println("<td>" + student.getName() + "</td>");
                out.println("<td>" + student.getGroupName() + "</td>");
                out.println("<td>" + AttendanceNameUtil.fromBooleanToString(student.isAttended()) + "</td>");
                out.println("<td><form method='POST' action='deleteStudent'>");
                out.println("<input type='hidden' name='id' value='" + student.getId() + "'>");
                out.println("<input type='submit' value='Удалить'>");
                out.println("</form></td>");
                out.println("</tr>");
            }
        }
        out.println("</table></body></html>");
    }

    private List<StudentAttendanceDto> getStudentsFromDB(String groupId) {
        String sql = "SELECT s.id, s.name, g.name as group_name, s.is_attended " +
                "FROM students s JOIN groups g ON s.group_id = g.id";

        if (groupId != null && !groupId.isEmpty()) {
            sql += " WHERE s.group_id = " + groupId;
        }

        List<StudentAttendanceDto> result = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                result.add(StudentAttendanceDto.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .groupName(rs.getString("group_name"))
                        .isAttended(rs.getBoolean("is_attended"))
                        .build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Map<String, String>> getAllGroups() {
        String sql = "SELECT id, name FROM groups ORDER BY name";
        List<Map<String, String>> groups = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Map<String, String> group = new HashMap<>();
                group.put("id", rs.getString("id"));
                group.put("name", rs.getString("name"));
                groups.add(group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURI().endsWith("/deleteStudent")) {
            deleteStudent(req, resp);
        } else {
            addStudent(req, resp);
        }
    }

    private void addStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String groupId = req.getParameter("groupId");
        boolean isAttended = Boolean.parseBoolean(req.getParameter("isAttended"));

        String sql = "INSERT INTO students (name, group_id, is_attended) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setInt(2, Integer.parseInt(groupId));
            stmt.setBoolean(3, isAttended);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("attendance");
    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.sendRedirect("attendance");
    }
}