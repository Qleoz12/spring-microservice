package br.com.domain.entity.user;

import br.com.domain.entity.DTO;
import br.com.domain.enums.EnumTipoAutenticacao;
import lombok.*;
import org.springframework.jdbc.core.RowMapper;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString
@Builder
public class UsuarioDTO extends DTO<Usuario> {

    private Long id;
    private String nome;
    private String username;
    private String senha;
    private String setor;
    private String cargo;
    private String dominio;
    private EnumTipoAutenticacao tipoAutenticacao;
    private EmpresaDTO empresa;
    private ProfileVO perfil;

    @Override
    public Usuario toEntidade() {
        return Usuario.builder()
                .id(this.id)
                .nome(this.nome)
                .username(this.username)
                .senha(this.senha)
                .setor(this.setor)
                .cargo(this.cargo)
                .dominio(this.dominio)
                .tipoAutenticacao(this.tipoAutenticacao)
//                .empresa(this.empresa == null ? null : this.empresa.toEntidade())
                .profile(this.perfil == null ? null : this.perfil.toEntidade())
                .build();
    }

    @Override
    public RowMapper<UsuarioDTO> getRowMapper() {
        return (rs, i) ->
                UsuarioDTO.builder()
                        .id(rs.getLong(Usuario.Campo.ID))
                        .nome(rs.getString(Usuario.Campo.NOME))
                        .username(rs.getString(Usuario.Campo.USERNAME))
                        .senha(rs.getString(Usuario.Campo.SENHA))
                        .setor(rs.getString(Usuario.Campo.SETOR))
                        .cargo(rs.getString(Usuario.Campo.CARGO))
                        .dominio(rs.getString(Usuario.Campo.DOMINIO))
                        .tipoAutenticacao(EnumTipoAutenticacao.getEnum(rs.getString(Usuario.Campo.TIPO_AUTENTICACAO)))
                        .empresa((EmpresaDTO) getDtoPorId(rs.getLong(Usuario.Campo.ID_EMPRESA), EmpresaDTO.class))
                        .perfil((ProfileVO) getDtoPorId(rs.getLong(Usuario.Campo.ID_PERFIL), ProfileVO.class))
                        .build();
    }

}
