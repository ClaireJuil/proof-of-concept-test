package fr.ans.psc.rass.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckController {

    @GetMapping(value = "/check")
    public ResponseEntity<String> check() {
        return new ResponseEntity<>("rass-structure-api is running !", HttpStatus.OK);
    }
}
