package co.com.api.credibanco.domain.converters;


import co.com.api.credibanco.domain.entity.card.BalanceCard;
import co.com.api.credibanco.domain.entity.card.Card;
import co.com.api.credibanco.domain.entity.card.CardPurchase;
import co.com.api.credibanco.domain.entity.card.CardVO;
import co.com.api.credibanco.domain.enums.EnumTypeCard;

public class ConverterCard {

    private ConverterCard() {
        throw new IllegalStateException("Utility class");
    }

    public static Card convertVOToCard(CardVO cardVO) {
        return cardVO != null ? Card.builder()
                .cardId(cardVO.getCardId())
                .typeOfCard(cardVO.getTypeOfCard().toString())
                .productId(cardVO.getProductId())
                .titularName(cardVO.getTitularName())
                .expirationDate(cardVO.getExpirationDate())
                .isActivated(cardVO.getIsActivated())
                .isBlocked(cardVO.getIsBlocked())
                .balance(cardVO.getBalance())
                .build()
                : Card.builder().build();

    }

    public static CardVO convertCardToVO(Card card) {
        CardVO cardVO = new CardVO();
        if (card != null) {
            cardVO.setCardId(card.getCardId());
            cardVO.setTypeOfCard(EnumTypeCard.getEnum(card.getTypeOfCard()));
            cardVO.setProductId(card.getProductId());
            cardVO.setTitularName(card.getTitularName());
            cardVO.setExpirationDate(card.getExpirationDate());
            cardVO.setIsActivated(card.getIsActivated());
            cardVO.setIsBlocked(card.getIsBlocked());
            cardVO.setBalance(card.getBalance());
        }
        return cardVO;
    }

    public static CardPurchase convertCardToCardPurchaseVO(Card card) {
        CardPurchase cardVO = new CardPurchase();
        if (card != null) {
            cardVO.setCardId(card.getCardId());
            cardVO.setTypeOfCard(EnumTypeCard.getEnum(card.getTypeOfCard()));
            cardVO.setProductId(card.getProductId());
            cardVO.setIsActivated(card.getIsActivated());
            cardVO.setIsBlocked(card.getIsBlocked());
            cardVO.setBalance(card.getBalance());
        }
        return cardVO;
    }

    public static BalanceCard convertCardToBalanceCard(Card card) {
        BalanceCard balanceCard = new BalanceCard();
        if (card != null) {
            balanceCard.setBalance(card.getBalance());
            balanceCard.setCardId(card.getCardId());
        }
        return balanceCard;
    }

}
