package co.com.api.credibanco.domain.entity.card;


import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CardVOid {

    @NotBlank(message = "not empty cardId")
    @Size(min = 16,max = 16)
    private String cardId;

}