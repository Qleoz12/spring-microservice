package co.com.api.credibanco.domain.entity.user;

import co.com.api.credibanco.domain.entity.AbstractCampo;
import co.com.api.credibanco.domain.entity.Entidade;
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
            inverseJoinColumns={ @JoinColumn(name = "ID_PERMISSION", referencedColumnName = Permission.Campo.ID) })
    private Set<Permission> permissoes;

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
