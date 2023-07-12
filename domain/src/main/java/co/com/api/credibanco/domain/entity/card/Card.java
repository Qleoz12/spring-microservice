package co.com.api.credibanco.domain.entity.card;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cards")

public class Card {

    @Id
    @Column(name = "card_id", nullable = false)
    private String cardId;

    @Column(name = "product_id")
    private String productId;

    @Column(name = "type_of_card")
//  @Pattern(regexp = "[^0-9]*", message = "El campo no debe contener números")
    private String typeOfCard;

    @Column(name = "titular_name", nullable = false)
//  @Size(max = 100, message = "Limite máximo de 100 caracteres")
//  @Pattern(regexp = "[^0-9]*", message = "El campo no debe contener números")
    private String titularName;

    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "is_activated", nullable = false)
    private Boolean isActivated;
    @Column(name = "is_blocked", nullable = false)
    private Boolean isBlocked;

    @Min(0)
    @Column(name = "balance", nullable = false)
    private Integer balance;

    @Column(name = "pre_balance", nullable = false)
    private Integer previusbalance;


    public void activateCard() {
        if (this.isActivated == false && this.isBlocked == false) {
            this.setIsActivated(true);
        }

    }

    public void blockCard() {
        if (this.isActivated == true && this.isBlocked == false) {
            this.setIsBlocked(true);
            this.setIsActivated(false);
        }
    }
}
