package co.com.api.credibanco.domain.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "Clients")
public class Client {
    @Id
    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Size(max = 100, message = "Limite máximo de 100 caracteres")
    @Pattern(regexp = "[^0-9]*", message = "El campo no debe contener números")
    @Column(name = "full_name", nullable = false)
    private String fullName;

}

