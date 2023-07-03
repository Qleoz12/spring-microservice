package br.com.domain.entity.user;

import br.com.domain.entity.DTO;
import lombok.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class ProfileVO extends DTO<Profile> {

    private Long id;
    private String nome;
    private List<UsuarioDTO> usuarios;
    private List<PermissaoDTO> permissoes;

    @Override
    public Profile toEntidade() {
        return Profile.builder()
                .id(this.id)
                .nome(this.nome)
                .usuarios(CollectionUtils.isEmpty(this.usuarios) ? null : this.usuarios.stream().map(UsuarioDTO::toEntidade).collect(Collectors.toSet()))
                .permissoes(CollectionUtils.isEmpty(this.permissoes) ? null : this.permissoes.stream().map(PermissaoDTO::toEntidade).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public RowMapper<ProfileVO> getRowMapper() {
        return (rs, i) ->
                ProfileVO.builder()
                        .id(rs.getLong(Profile.Campo.ID))
                        .nome(rs.getString(Profile.Campo.NAME))
                        .build();
    }

}
