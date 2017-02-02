package org.domotics.core.hardware;

import com.pi4j.io.gpio.*;
import com.pi4j.system.SystemInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
@Named("raspi")
public class RaspberryPiBoard implements Board {
    public final static int VALUE_ON = 1;
    public final static int VALUE_OFF = 0;
    private Logger logger = LoggerFactory.getLogger(getClass());
    GpioPinDigitalOutput[] pins;

    public RaspberryPiBoard(){
        logger.info("==========> [Raspberry Pi Board] new instance.");
        initBoard();
    }

    private void initBoard() {
        logger.info("==========> [Raspberry Pi Board] Initialize Board...");

        GpioController gpio = GpioFactory.getInstance();

        pins = new GpioPinDigitalOutput[4];
        pins[0]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01,"pin_01", PinState.LOW);
        pins[0].setShutdownOptions(true, PinState.LOW);
        pins[1]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03,"pin_03",PinState.LOW);
        pins[1].setShutdownOptions(true, PinState.LOW);
        pins[2]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05,"pin_05", PinState.LOW);
        pins[2].setShutdownOptions(true, PinState.LOW);
        pins[3]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07,"pin_07",PinState.LOW);
        pins[3].setShutdownOptions(true, PinState.LOW);

        for (int i=0; i<4; i++){
            logger.info("==========> [Raspberry Pi Board] GPIO available at pin: " + i + " name: " + pins[i].getName() + " state: " + pins[i].getState());
        }
    }

    @Override
    public String serial() {
        try {
            return SystemInfo.getSerial().toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Board not available";
    }

    @Override
    public int status(int gpio) {
        if (gpio >= pins.length){
            return -1;
        }
        if (pins[gpio] == null){
            return -1;
        }
        return pins[gpio].getState().getValue();
    }

    @Override
    public int on(int gpio) {
        if (gpio >= pins.length){
            return -1;
        }
        if (pins[gpio] == null){
            return -1;
        }
        if (pins[gpio].isLow()){
            pins[gpio].high();
        }
        return pins[gpio].getState().getValue();
    }

    @Override
    public int off(int gpio) {
        if (gpio >= pins.length){
            return -1;
        }
        if (pins[gpio] == null){
            return -1;
        }
        if (pins[gpio].isHigh()){
            pins[gpio].low();
        }
        return pins[gpio].getState().getValue();
    }

    @Override
    public int value(int gpio) {
        if (gpio >= pins.length){
            return -1;
        }
        if (pins[gpio] == null){
            return -1;
        }
        return pins[gpio].getState().getValue();
    }
}
