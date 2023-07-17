package co.com.api.credibanco.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

/**
 *
 * This number is used to advise the load of an ID.
 * Generating random numbers in a growing field can become complicated after completing the half.
 *
 */
@Getter
@AllArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public enum EnumNumerics {
    //9999999999999999 16pos
    MIDID(5555555555555555L)
    ;
    @ToString.Include
    private Long codigo;

    public static EnumTypeCard getEnum(Long codigo) {
        return codigo == null ? null : Arrays.stream(EnumTypeCard.values())
                .filter(a -> a.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }
}
