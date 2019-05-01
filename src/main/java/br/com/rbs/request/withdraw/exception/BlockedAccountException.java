package br.com.rbs.request.withdraw.exception;

public class BlockedAccountException extends Exception {

    public BlockedAccountException(String message) {
        super(message);
    }
}
