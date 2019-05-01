package br.com.rbs.request.withdraw.enuns;

public enum WithdrawEnum {
    AUTHORIZED("00", "aprovada"),
    INVALID_ACCOUNT("14", "conta inválida"),
    INSUFFICIENT_FUNDS("51", "saldo insuficiente"),
    PROCESSING_ERROR("96", "erro de processamento");

    private final String code;
    private final String description;

    WithdrawEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }
}
