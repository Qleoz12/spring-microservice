package br.com.domain.entity.user;

import br.com.domain.entity.AbstractCampo;
import br.com.domain.entity.Entidade;
import br.com.domain.enums.EnumTipoAutenticacao;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Usuario implements Entidade {

    @Id
    @Column(name = Campo.ID, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Campo.NOME, nullable = false)
    private String nome;

    @Column(name = Campo.USERNAME, nullable = false)
    private String username;

    @Column(name = Campo.SENHA, insertable = true, updatable = false)
    private String senha;

    @Column(name = Campo.SETOR)
    private String setor;

    @Column(name = Campo.CARGO)
    private String cargo;

    @Column(name = Campo.DOMINIO)
    private String dominio;

    @Column(name = Campo.TIPO_AUTENTICACAO, nullable = false)
    @Enumerated(EnumType.STRING)
    private EnumTipoAutenticacao tipoAutenticacao;

    @JoinColumn(name = Campo.ID_PERFIL, referencedColumnName = Profile.Campo.ID)
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profile;

    @Override
    public AbstractCampo field() {
        return new Campo();
    }

    public static class Campo extends AbstractCampo {
        public static final String ID = "usuario_id";
        public static final String NOME = "name";
        public static final String USERNAME = "username";
        public static final String SENHA = "password";
        public static final String SETOR = "department";
        public static final String CARGO = "position";
        public static final String DOMINIO = "domain";
        public static final String TIPO_AUTENTICACAO = "authentication_type";
        public static final String ID_EMPRESA = "company_id";
        public static final String ID_PERFIL = "profile_id";
    }
}