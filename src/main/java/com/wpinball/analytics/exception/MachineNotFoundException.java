package com.wpinball.analytics.exception;

public class MachineNotFoundException extends RuntimeException {
    public MachineNotFoundException(String machineName) {
        super("Machine introuvable : " + machineName);
    }
}
