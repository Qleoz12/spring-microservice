package co.com.api.credibanco.domain.entity.card;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class BalanceCard {

    @NotBlank(message = "not empty cardId")
    @Size(min = 16,max = 16)
    private String cardId;
    @Min(0)
    private Integer balance;
}