package co.com.api.credibanco.domain.entity.user;

import co.com.api.credibanco.domain.entity.DTO;
import lombok.*;
import org.springframework.jdbc.core.RowMapper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class PermissionDTO extends DTO<Permission> {

    private Long id;
    private String nome;
    private String codigo;
    private String action;

    @Override
    public Permission toEntidade() {
        return Permission.builder()
                .id(this.id)
                .nome(this.nome)
                .codigo(this.codigo)
                .action(this.action)
                .build();
    }

    @Override
    public RowMapper<PermissionDTO> getRowMapper() {
        return (rs, i) ->
                PermissionDTO.builder()
                        .id(rs.getLong(Permission.Campo.ID))
                        .nome(rs.getString(Permission.Campo.NOME))
                        .codigo(rs.getString(Permission.Campo.CODIGO))
                        .action(rs.getString(Permission.Campo.ACTION))
                        .build();
    }

}
