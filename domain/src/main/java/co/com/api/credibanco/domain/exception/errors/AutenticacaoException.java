package co.com.api.credibanco.domain.exception.errors;

import co.com.api.credibanco.domain.exception.rules.RuleException;

public class AutenticacaoException extends RuleException {

    public AutenticacaoException() {
        super("Usuário e/ou senha inválido!");
    }

}
