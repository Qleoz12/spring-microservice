package com.packtpub.mmj.card.domain.service;

import com.packtpub.mmj.service.persistence.converters.ConverterCard;
import com.packtpub.mmj.service.persistence.entities.CardVO;
import com.packtpub.mmj.service.persistence.entities.Card;
import com.packtpub.mmj.service.persistence.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * @author Sourabh Sharma
 */
@Service("CardService")
public class CardServiceImpl implements CardService {

  private final CardRepository repository;

  /**
   * @param repository
   */
  @Autowired
  public CardServiceImpl(CardRepository repository) {
    this.repository = repository;
  }


  @Override
  public CardVO add(CardVO cardVO) throws Exception {
//    ProductData product = productDataRepository.findById(String.valueOf(productId))
//            .orElseThrow(() -> new ResourceAccessException("Producto no encontrado"));
    try {
//      String fullName = product.getClient().getFullName();
//      String cardNumber = GeneratorCardNumbers.generate(String.valueOf(productId));
      LocalDate expirationDate = LocalDate.now().plusYears(3);

      Card newCard = repository.save(Card.builder()
              .cardId("1020301234567801")
              .titularName("fullName")
//              .balance(0)
//              .productId(product.getProductId())
//              .typeOfCard(typeOfCard.getType())
              .expirationDate(expirationDate)
              .isActivated(false)
              .isBlocked(false)
              .build());
//      product.setCardId(newCard.getCardId());
//      productDataRepository.save(product);
      return ConverterCard.convertCardToVO(newCard);
    } catch (Exception e) {
//      return Mono
//              .error(new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en el repositorio", e));
      return null;
    }
  }

  @Override
  public void enroll(Card restaurant) throws Exception {

  }

  @Override
  public void delete(String id) throws Exception {

  }

//  @Override
//  public void add(Restaurant restaurant) throws Exception {
//    if (restaurantRepository.containsName(restaurant.getName())) {
//      Object[] args = {restaurant.getName()};
//      throw new DuplicateRestaurantException("duplicateRestaurant", args);
//    }
//
//    if (restaurant.getName() == null || "".equals(restaurant.getName())) {
//      Object[] args = {"Restaurant with null or empty name"};
//      throw new InvalidRestaurantException("invalidRestaurant", args);
//    }
//    super.add(restaurant);
//  }


}
