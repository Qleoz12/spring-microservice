package co.com.api.credibanco.domain.entity.transaction;

import co.com.api.credibanco.domain.util.UUID;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionVO {

    @UUID(message = "not valid uuid")
    private String transactionId;
    @NotBlank(message = "not empty cardId")
    @Size(min = 16,max = 16)
    private String cardId;
}