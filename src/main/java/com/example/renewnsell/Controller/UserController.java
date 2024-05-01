package com.example.renewnsell.Controller;

import com.example.renewnsell.Model.User;
import com.example.renewnsell.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {


    private final UserService userService;
    @GetMapping("/get-all")
    public ResponseEntity getAll(){

        return ResponseEntity.ok(userService.getAll());
    }

    @DeleteMapping("/delete/{username}")
    public ResponseEntity delete(@PathVariable String username){
        userService.delete(username);
        return ResponseEntity.ok("user deleted");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteMyUser(@AuthenticationPrincipal User user){
        userService.delete(user.getUsername());
        return ResponseEntity.ok("user deleted");
    }

    @GetMapping("/get-by-username/{username}")
    public ResponseEntity getByUsername(@PathVariable String username){

        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity getUserById(@PathVariable Integer id){

        return ResponseEntity.ok(userService.getUserById(id));
    }


}
