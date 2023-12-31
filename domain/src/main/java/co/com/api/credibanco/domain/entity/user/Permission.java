package co.com.api.credibanco.domain.entity.user;

import co.com.api.credibanco.domain.entity.AbstractCampo;
import co.com.api.credibanco.domain.entity.Entidade;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Permission implements Entidade {

    @Id
    @Column(name = Campo.ID, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Campo.NOME, nullable = false)
    private String nome;

    @Column(name = Campo.CODIGO, nullable = false)
    private String codigo;

    @Column(name = Campo.ACTION)
    private String action;

    @Override
    public AbstractCampo field() {
        return new Campo();
    }

    public static class Campo extends AbstractCampo {
        public static final String ID = "permission_id";
        public static final String NOME = "name";
        public static final String CODIGO = "code";
        public static final String ACTION = "ACTION";
    }

}