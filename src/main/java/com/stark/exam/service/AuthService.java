package com.stark.exam.service;

import com.stark.exam.dao.FacultyDAO;
import com.stark.exam.dao.StudentDAO;
import com.stark.exam.dao.UserDAO;
import com.stark.exam.model.Faculty;
import com.stark.exam.model.Student;
import com.stark.exam.model.User;

import java.util.List;

public class AuthService {

    private final UserDAO userDAO = new UserDAO();
    private final StudentDAO studentDAO = new StudentDAO();
    private final FacultyDAO facultyDAO = new FacultyDAO();

    public User login(String username, String password) {
        if (username == null || password == null || username.isBlank() || password.isBlank()) {
            return null;
        }
        return userDAO.authenticate(username.trim(), password.trim());
    }

    public User getUserById(int id) {
        return userDAO.findById(id);
    }

    public Student getStudentByUserId(int userId) {
        return studentDAO.findByUserId(userId);
    }

    public Faculty getFacultyByUserId(int userId) {
        return facultyDAO.findByUserId(userId);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean createUser(User user) {
        if (userDAO.findByUsername(user.getUsername()) != null) {
            return false; // Username already exists
        }
        return userDAO.createUser(user);
    }

    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }

    public boolean deleteUser(int id) {
        return userDAO.deleteUser(id);
    }
}
