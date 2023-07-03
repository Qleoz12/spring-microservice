package com.packtpub.mmj.card.domain.service;


import com.packtpub.mmj.service.persistence.entities.CardVO;
import com.packtpub.mmj.service.persistence.entities.Card;

/**
 * @author Sourabh Sharma
 */
public interface CardService {


  public CardVO add(CardVO card) throws Exception;

  /**
   * @param restaurant
   */
  public void enroll(Card restaurant) throws Exception;

  /**
   * @param id
   */
  public void delete(String id) throws Exception;


}
