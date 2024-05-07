package com.example.renewnsell.Service;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAll() {
        return userRepository.findAll();
    }


    public void delete(String username){
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new ApiException("user not found");
        }
        userRepository.delete(user);

    }

    public User getMyInfo(Integer userId){

        User findUser = userRepository.findUserById(userId);
        return findUser;
    }

    public User getUserByUsername(String username){
        User user = userRepository.findUserByUsername(username);
        if (user == null){
            throw new ApiException("User not found with username : " + username);
        }
        return user;
    }

    public User getUserById(Integer id){
        User user = userRepository.findUserById(id);
        if (user == null){
            throw new ApiException("User not found with ID : " + id);
        }
        return user;
    }



}
