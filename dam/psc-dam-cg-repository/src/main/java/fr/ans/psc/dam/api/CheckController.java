package fr.ans.psc.dam.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {

    @GetMapping(value = "/check")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("dam-reader is running !", HttpStatus.OK);
    }
}
