
public class Sensor {
    private String UID;
    private String Location;
    private double setCO2_min, setCO2_max;
    private double setTEMP_min, setTEMP_max;
    private double co2, temperature, humidity;
    private boolean status;
    private String MachineName;

    public Sensor() {
        Sensor sensor = this;
        sensor.UID = " ";
        sensor.Location = " ";
        sensor.setCO2_min = 12;
        sensor.setCO2_max = 100;
        sensor.setTEMP_min = 10;
        sensor.setTEMP_max = 120;
        sensor.status = true;
        sensor.MachineName = "";
    }

    public void setValues(String s[]) {
      Sensor sensor = this;
      sensor.UID = s[0];
      sensor.Location = s[1];
      sensor.setCO2_min = Double.parseDouble(s[2]);
      sensor.setCO2_max = Double.parseDouble(s[3]);
      sensor.setTEMP_min = Double.parseDouble(s[4]);
      sensor.setTEMP_max = Double.parseDouble(s[5]);
    }
    public String getUID() {
        return this.UID;
    }

    public String getLocation() {
        return this.Location;
    }

    public double getHumidity() {
      return this.humidity;
    }

    public void setHumidity(double hum) {
      this.humidity = hum;
    }

    public double getSetCO2_min() {
        return this.setCO2_min;
    }

    public double getSetCO2_max() {
        return this.setCO2_max;
    }
    public double getSetTEMP_min() {
        return this.setTEMP_min;
    }
    public double getSetTEMP_max() {
        return this.setTEMP_max;
    }

    public double getCo2() {
        return this.co2;
    }

    public double getTemperature() {
        return this.temperature;
    }

    public String getStatus() {
        return (this.status) ? "OK" : "NOT OK";
    }

    public String getMachineName() {
    	return this.MachineName;
    }

    public void setUID(String uid) {
        this.UID = uid;
    }

    public void setLocation(String loc) {
        this.Location = loc;
    }

    public void setSetCO2_min(double d) {
        this.setCO2_min = d;
    }

    public void setSetCO2_max(double d) {
        this.setCO2_max = d;
    }

    public void setSetTEMP_min(double d) {
        this.setTEMP_min = d;
    }

    public void setSetTEMP_max(double d) {
        this.setTEMP_max = d;
    }

    public void setCo2(double d) {
        this.co2 = d;
    }

    public void setTemperature(double d) {
        this.temperature = d;
    }

    public void setStatus(boolean stat) {
        this.status  = stat;
    }

    public void setMachineName(String name) {
        this.MachineName = name;
    }
}
