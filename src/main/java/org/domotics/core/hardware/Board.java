package org.domotics.core.hardware;

public interface Board {
    public String serial();
    public int status(int gpio);
    public int on(int gpio);
    public int off(int gpio);
    public int value(int gpio);
}
