package org.firstinspires.ftc.teamcode.UsefulFunctions;

import java.util.function.BooleanSupplier;

public class EdgeDetector {
    private boolean previous = false;
    private boolean current = false;

    public void isRisingEdge(BooleanSupplier input){
        boolean risingEdge = (!previous&&input.getAsBoolean());
        previous = input.getAsBoolean();
        current = risingEdge;
    }
    public boolean getRisingEdge(){
        return current;
    }
}
