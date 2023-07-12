package co.com.api.credibanco.domain.entity.transaction;


import lombok.*;

import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransacctionPurchaseVO extends TransactionVO {

    @Min(0)
    private Integer price;
}
