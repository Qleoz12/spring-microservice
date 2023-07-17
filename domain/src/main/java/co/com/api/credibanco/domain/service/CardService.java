package co.com.api.credibanco.domain.service;

import co.com.api.credibanco.domain.entity.card.BalanceCard;
import co.com.api.credibanco.domain.entity.card.Card;
import co.com.api.credibanco.domain.entity.card.CardVO;
import co.com.api.credibanco.domain.entity.card.CardVOid;
import co.com.api.credibanco.domain.exception.rules.RuleException;

public interface CardService extends DeleteService<Card, CardVO, String> {


    public CardVO add(CardVO card) throws RuleException;

    public CardVO enroll(CardVOid restaurant) throws RuleException;

    public CardVO balance(BalanceCard card) throws RuleException;

    public BalanceCard balance(String id) throws RuleException;


}
