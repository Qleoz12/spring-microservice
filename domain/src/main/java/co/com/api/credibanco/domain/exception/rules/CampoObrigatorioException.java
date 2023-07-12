package co.com.api.credibanco.domain.exception.rules;

public class CampoObrigatorioException extends RuleException {

    public CampoObrigatorioException(String campo) {
        super(campo + " é campo obrigatório.");
    }
}
