package stationType5;


public class TempIn extends Sensor{
    // Inside temperature is the default data field (myData)
    
    /**
     * Main public constructor.
     * 
     * @param theTemp
     */
    public TempIn(final double theTemp) {
        super(theTemp);
    }

    /**
     * Return inside temperature. Same effect as getData()
     * 
     * @return inside temperature
     */
    public double getMyTemp() {
        return getData();
    }
}