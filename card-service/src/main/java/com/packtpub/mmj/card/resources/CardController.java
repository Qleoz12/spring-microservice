package com.packtpub.mmj.card.resources;

import com.packtpub.mmj.card.domain.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

/**
 * @author Sourabh Sharma
 */
@RestController
@RequestMapping("/v1/card")
public class CardController {

    /**
     *
     */
    protected static final Logger logger = Logger.getLogger(CardController.class.getName());

    /**
     *
     */
    protected CardService service;

    /**
     * @param service
     */
    @Autowired
    public CardController(CardService service) {
        this.service = service;
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> findByName(@RequestParam("name") String name)
            throws Exception {
        logger.info(String.format("restaurant-service findByName() invoked:{} for {} ", service.getClass().getName(), name));
        name = name.trim().toLowerCase();

//        try {
//            String a;
//        } catch (RestaurantNotFoundException ex) {
//            logger.log(Level.WARNING, "Exception raised findByName REST Call", ex);
//            throw ex;
//        } catch (Exception ex) {
//            logger.log(Level.SEVERE, "Exception raised findByName REST Call", ex);
//            throw ex;
//        }
        return new ResponseEntity<>("restaurants", HttpStatus.OK);
    }


}
