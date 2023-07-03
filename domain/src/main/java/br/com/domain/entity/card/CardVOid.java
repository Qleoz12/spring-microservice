package br.com.domain.entity.card;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class CardVOid {

    private String cardId;

}