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
public class CompanyDTO extends DTO<Company> {

    private Long id;
    private String nome;
    private String conta;
    private String username;
    private String password;
    private String caixa;

    @Override
    public Company toEntidade() {
        return Company.builder()
                .id(this.id)
                .name(this.nome)
                .account(this.conta)
                .username(this.username)
                .password(this.password)
                .caixa(this.caixa)
                .build();
    }

    @Override
    public RowMapper<CompanyDTO> getRowMapper() {
        return (rs, i) ->
                CompanyDTO.builder()
                        .id(rs.getLong(Company.Campo.ID))
                        .nome(rs.getString(Company.Campo.NAME))
                        .conta(rs.getString(Company.Campo.ACCOUNT))
                        .username(rs.getString(Company.Campo.USERNAME))
                        .password(rs.getString(Company.Campo.PASSWORD))
                        .caixa(rs.getString(Company.Campo.CAIXA))
                        .build();
    }

}
