package co.com.api.credibanco.domain.entity.transaction;

import co.com.api.credibanco.domain.entity.card.CardPurchase;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionResultVO extends TransactionVO {
    private CardPurchase cardVO;
    private LocalDateTime transactionDate;
    private Boolean isValid;
    private Integer price;

    public void invalidTransaction() {
        if (this.isValid) {
            this.setIsValid(false);
        }
    }

}