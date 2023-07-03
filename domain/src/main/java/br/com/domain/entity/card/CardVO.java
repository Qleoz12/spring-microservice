package br.com.domain.entity.card;


import br.com.domain.enums.EnumTypeCard;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CardVO {

    private String cardId;
    private EnumTypeCard typeOfCard;
    private String productId;
    private String titularName;
    private LocalDate expirationDate;
    private Boolean isActivated;
    private Boolean isBlocked;
    private Integer balance;

}