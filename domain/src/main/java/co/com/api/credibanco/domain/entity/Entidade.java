package co.com.api.credibanco.domain.entity;

import java.io.Serializable;

public interface Entidade<E> extends Serializable {
    E getId();

    AbstractCampo field();
}
