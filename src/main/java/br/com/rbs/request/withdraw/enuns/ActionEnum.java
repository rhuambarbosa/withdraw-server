package br.com.rbs.request.withdraw.enuns;

public enum ActionEnum {
    WITHDRAW("withdraw");

    private String action;

    ActionEnum(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
