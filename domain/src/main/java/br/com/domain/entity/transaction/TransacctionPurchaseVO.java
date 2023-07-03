package br.com.domain.entity.transaction;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransacctionPurchaseVO extends TransactionVO {
    private Integer price;
}
