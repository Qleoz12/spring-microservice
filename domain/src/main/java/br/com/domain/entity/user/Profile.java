package br.com.domain.entity.user;

import br.com.domain.entity.AbstractCampo;
import br.com.domain.entity.Entidade;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Profile implements Entidade {

    @Id
    @Column(name = Campo.ID, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = Campo.NAME, nullable = false)
    private String nome;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="PROFILE_PERMISSION",
            joinColumns={ @JoinColumn(name = "ID_PROFILE", referencedColumnName = Campo.ID) },
            inverseJoinColumns={ @JoinColumn(name = "ID_PERMISSION", referencedColumnName = Permissao.Campo.ID) })
    private Set<Permissao> permissoes;

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY)
    private Set<Usuario> usuarios;

    @Override
    public AbstractCampo field() {
        return new Campo();
    }

    public static class Campo extends AbstractCampo {
        public static final String ID = "profile_id";
        public static final String NAME = "name";
    }

}
