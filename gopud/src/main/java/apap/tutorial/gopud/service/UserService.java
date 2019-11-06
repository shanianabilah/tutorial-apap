package apap.tutorial.gopud.service;

import apap.tutorial.gopud.model.UserModel;

import java.util.Optional;

public interface UserService {
    UserModel addUser(UserModel user);
    public String encrypt(String password);
    UserModel getUserByUsername(String username);
    void updatePassword(UserModel user, String newPass);
}
