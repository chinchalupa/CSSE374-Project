package oldLab;

/**
 * Created by wrightjt on 12/3/2015.
 */
public interface IWeatherReporter {
    public void registerDisplay(IDisplay display);
    public void removeDisplay(IDisplay display);

    /**
     * Sets the weather data. Assumed data is not null.
     * @param newData
     */
    public void setWeatherData(IWeatherData newData);


}
