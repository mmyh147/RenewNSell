package com.example.renewnsell.Controller;

import com.example.renewnsell.Api.ApiException;
import com.example.renewnsell.Api.ApiResponse;
import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.UserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {


    private final UserService userService;
    //================================= By Mohammed Alhajri ===================================

    @GetMapping("/get-all")
    public ResponseEntity getAll(){

        if (userService.getAll().isEmpty()){
            throw new ApiException("No user found");
        }
        return ResponseEntity.ok(userService.getAll());
    }
    //================================= By Mohammed Alhajri ===================================

    @DeleteMapping("/delete/{username}")
    public ResponseEntity delete(@PathVariable String username){
        userService.delete(username);
        return ResponseEntity.ok(new ApiResponse("user deleted"));
    }
    //================================= By Mohammed Alhajri ===================================

    //Endpoint 56
    @DeleteMapping("/delete-my-user")
    public ResponseEntity deleteMyUser(@AuthenticationPrincipal User user){
        userService.delete(user.getUsername());
        return ResponseEntity.ok(new ApiResponse("user deleted"));
    }
    //================================= By Mohammed Alhajri ===================================

    //Endpoint 57
    @GetMapping("/get-user-by-username/{username}")
    public ResponseEntity getByUsername(@PathVariable String username){

        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    //================================= By Mohammed Alhajri ===================================
    //Endpoint 58
    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id){

        return ResponseEntity.ok(userService.getUserById(id));
    }
    //================================= By Mohammed Alhajri ===================================
    //Endpoint 59
    @GetMapping("/get-my-info")
    public ResponseEntity getMyInfo(@AuthenticationPrincipal User user){
        return ResponseEntity.ok(userService.getMyInfo(user.getId()));
    }
    //================================= By Mohammed Alhajri ===================================
    //Endpoint 60
    @PostMapping("/login")
    public ResponseEntity login(@AuthenticationPrincipal User user){

        return ResponseEntity.ok(new ApiResponse("welcome, " + user.getName()));
    }
    //================================= By Mohammed Alhajri ===================================
    //Endpoint 61
    @PostMapping("/logout")
    public ResponseEntity logout(@AuthenticationPrincipal User user){

        return ResponseEntity.ok(new ApiResponse("bye!"));
    }


}
