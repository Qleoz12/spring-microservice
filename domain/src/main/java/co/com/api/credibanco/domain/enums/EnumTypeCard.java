package co.com.api.credibanco.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum EnumTypeCard {
    CREDIT("CREDIT", ""),
    DEBIT("DEBIT", "");

    private String codigo;

    @ToString.Include
    private String descricao;

    public static EnumTypeCard getEnum(String codigo) {
        return codigo == null ? null : Arrays.stream(EnumTypeCard.values())
                .filter(a -> a.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }
}
