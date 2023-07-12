package co.com.api.credibanco.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum EnumErrors {

    CARDNOTFOUND("No fue posible encontrar la tarjeta"),
    DELETE("No fue posible encontrar la tarjeta")
    ;

    @ToString.Include
    private String codigo;

    public static EnumTypeCard getEnum(String codigo) {
        return codigo == null ? null : Arrays.stream(EnumTypeCard.values())
                .filter(a -> a.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }
}
