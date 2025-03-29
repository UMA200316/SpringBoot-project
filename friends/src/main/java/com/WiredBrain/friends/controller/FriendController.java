package com.WiredBrain.friends.controller;

import com.WiredBrain.friends.model.Friend;
import com.WiredBrain.friends.service.FriendService;
import com.WiredBrain.friends.util.ErrorMessage;
import com.WiredBrain.friends.util.FieldErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FriendController {

    @Autowired
    FriendService friendService;

//    @PostMapping("/friend")
//    Friend create( @RequestBody Friend friend){
//         return friendService.save(friend);
//    }
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    List<FieldErrorMessage> exceptionHandler(MethodArgumentNotValidException e){
//        List<FieldError> fieldErrorList = e.getBindingResult().getFieldErrors();
//        List<FieldErrorMessage> fieldErrorMessage= fieldErrorList.stream().map(fieldError -> new FieldErrorMessage(fieldError.getField(),fieldError.getDefaultMessage())).collect(Collectors.toList());
//        return fieldErrorMessage;
//    }
    @PostMapping("/friend")
    Friend create(@RequestBody Friend friend) throws Exception {
        if (friend.getId() != 0 && friend.getFirstName() != null && friend.getLastName() != null)
            return friendService.save(friend);
        else throw new Exception("Not found");
    }
//    -----------Without using the error message class---------
    @ExceptionHandler(Exception.class)
    ResponseEntity<String> exceptionhandler(Exception e){
        return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
    }


//    ------------------With Error message class--------------------
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler
//    ErrorMessage exceptionhandler(Exception e){
//        return new ErrorMessage("400",e.getMessage());
//    }

    @GetMapping("/friend")
    Iterable<Friend> read(){
//        System.out.println("jon");
        return friendService.findAll();
    }

    @PutMapping("/friend")
    Friend update(@RequestBody Friend friend){
        return friendService.save(friend);
    }

    @DeleteMapping("/friend/{id}")
    String delete(@PathVariable Integer id){

        friendService.deleteById(id);
        return "Deleted successfully";
    }

    @GetMapping("/friend/{id}")
    Optional<Friend> findById(@PathVariable Integer id){
        return friendService.findById(id);
    }

    @GetMapping("/friend/search")
    Iterable<Friend> findByQuery(@RequestParam(value = "first",required = false) String firstname, @RequestParam(value = "last",required = false) String lastname){
        if(firstname!=null && lastname!=null)
            return friendService.findByFirstNameAndLastName(firstname, lastname);
        else if (firstname!=null)
            return friendService.findByFirstName(firstname);
        else if (lastname!=null)
            return friendService.findByLastName(lastname);
        else
            return friendService.findAll();
    }

    @PutMapping("/frienderror")
    ResponseEntity<Friend> updates(@RequestBody Friend friend){
        if(friendService.findById(friend.getId()).isPresent())
            return new ResponseEntity(friendService.save(friend), HttpStatus.OK);
        else
            return new ResponseEntity(friend,HttpStatus.BAD_REQUEST);
    }
}
