package co.com.api.credibanco.domain.entity.card;


import co.com.api.credibanco.domain.enums.EnumTypeCard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardPurchase {

    private String cardId;
    private EnumTypeCard typeOfCard;
    private String productId;
    private Boolean isActivated;
    private Boolean isBlocked;
    private Integer balance;

}