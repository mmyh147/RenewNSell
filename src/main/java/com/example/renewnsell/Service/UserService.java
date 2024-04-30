package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public void delete(String username){
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new ApiException("user not found");
        }
        userRepository.delete(user);

    }

    public List<User> getAll() {
        return userRepository.findAll();
    }
}
