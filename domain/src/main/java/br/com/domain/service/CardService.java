package br.com.domain.service;

import br.com.domain.entity.card.BalanceCard;
import br.com.domain.entity.card.Card;
import br.com.domain.entity.card.CardVO;
import br.com.domain.entity.card.CardVOid;

public interface CardService extends DeleteService<Card, CardVO, String> {


    public CardVO add(CardVO card) throws Exception;

    public CardVO enroll(CardVOid restaurant) throws Exception;

    public CardVO balance(BalanceCard card) throws Exception;

    public BalanceCard balance(String id) throws Exception;


}
