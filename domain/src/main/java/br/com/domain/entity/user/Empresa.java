package br.com.domain.entity.user;

import br.com.domain.entity.AbstractCampo;
import br.com.domain.entity.Entidade;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Empresa implements Entidade {

    @Id
    @Column(name = Campo.ID, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Campo.NAME, nullable = false)
    private String name;

    @Column(name = Campo.ACCOUNT, nullable = false)
    private String account;

    @Column(name = Campo.USERNAME, nullable = false)
    private String username;

    @Column(name = Campo.PASSWORD, nullable = false)
    private String password;

    @Column(name = Campo.CAIXA, nullable = false)
    private String caixa;

    @Override
    public AbstractCampo field() {
        return new Campo();
    }

    public static class Campo extends AbstractCampo {
        public static final String ID = "COMPANY_ID";
        public static final String NAME = "NAME";
        public static final String ACCOUNT = "ACCOUNT";
        public static final String USERNAME = "USERNAME";
        public static final String PASSWORD = "PASSWORD";
        public static final String CAIXA = "CAIXA";
    }

}
