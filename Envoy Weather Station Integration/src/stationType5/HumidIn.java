package stationType5;


public class HumidIn extends Sensor{
    // Inside humidity is the default data field (myData)
    
    /**
     * Main public constructor.
     * 
     * @param theHumid
     */
    public HumidIn(final double theHumid) {
        super(theHumid);
    }
    
    /**
     * Return inside humidity. Same effect as getData()
     * 
     * @return inside humidity
     */
    public double getMyHumid() {
        return getData();
    }
}