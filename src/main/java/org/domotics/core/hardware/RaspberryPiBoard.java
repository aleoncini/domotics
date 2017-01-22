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
        logger.info("==========> [Raspberry Pi Board] Initiaize Board...");

        /* setup wiringPi
        if (Gpio.wiringPiSetup() == -1) {
            logger.error("==========> [Raspberry Pi Board] GPIO SETUP FAILED.");
            return;
        }
        GpioUtil.export(11, GpioUtil.DIRECTION_OUT);
        */



        GpioController gpio = GpioFactory.getInstance();

        // temporary
        //pons = new GpioPinAnalogInput()
        GpioPinDigitalInput pon = gpio.provisionDigitalInputPin(RaspiPin.GPIO_29, PinPullResistance.PULL_DOWN);
        PinState state = pon.getState();
        state.getValue();
        // end of temporary section

        pins = new GpioPinDigitalOutput[30];
        pins[0]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00,"pin_00", PinState.LOW);
        pins[1]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01,"pin_01",PinState.LOW);
        pins[2]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02,"pin_02", PinState.LOW);
        pins[3]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03,"pin_03",PinState.LOW);
        pins[4]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,"pin_04", PinState.LOW);
        pins[5]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05,"pin_05",PinState.LOW);
        pins[6]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06,"pin_06", PinState.LOW);
        pins[7]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07,"pin_07",PinState.LOW);
        pins[8]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08,"pin_08", PinState.LOW);
        pins[9]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09,"pin_09",PinState.LOW);
        pins[21]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_21,"pin_21",PinState.LOW);
        pins[22]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_22,"pin_22",PinState.LOW);
        pins[23]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_23,"pin_23",PinState.LOW);
        pins[24]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_24,"pin_24",PinState.LOW);
        pins[25]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_25,"pin_25",PinState.LOW);
        pins[26]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_26,"pin_26",PinState.LOW);
        pins[27]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_27,"pin_27",PinState.LOW);
        pins[28]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_28,"pin_28",PinState.LOW);
        pins[29]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_29,"pin_29",PinState.LOW);

        /*
        pins[4]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04,"pin_04",PinState.LOW);
        pins[5]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_05,"pin_05",PinState.LOW);
        pins[6]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_06,"pin_06",PinState.LOW);
        pins[7]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07,"pin_07",PinState.LOW);
        pins[8]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_08,"pin_08",PinState.LOW);
        pins[9]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_09,"pin_09",PinState.LOW);
        pins[10]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_10,"pin_10",PinState.LOW);
        pins[11]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_11,"pin_11",PinState.LOW);
        pins[12]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_12,"pin_12",PinState.LOW);
        pins[13]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_13,"pin_13",PinState.LOW);
        pins[14]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_14,"pin_14",PinState.LOW);
        pins[15]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_15,"pin_15",PinState.LOW);
        pins[16]= gpio.provisionDigitalOutputPin(RaspiPin.GPIO_16,"pin_16",PinState.LOW);
        */
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
        if (pins[gpio] == null){
            return -1;
        }
        return pins[gpio].getState().getValue();
    }

    @Override
    public int on(int gpio) {
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
        if (pins[gpio] == null){
            return -1;
        }
        return pins[gpio].getState().getValue();
    }
}
