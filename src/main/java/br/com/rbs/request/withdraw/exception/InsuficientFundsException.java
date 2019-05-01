package br.com.rbs.request.withdraw.exception;

public class InsuficientFundsException extends Exception {

    public InsuficientFundsException(String message) {
        super(message);
    }
}
