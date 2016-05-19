package prv.mark.domain;

/**
 * Class implementing overridden equals() and hashCode() methods.
 *
 * Created by mlglenn on 5/16/2016.
 */
public class Soldier {

    private String name;
    private String rank;
    private String serialNumber;

    public Soldier() {
    }

    public Soldier(String name, String rank, String serialNumber) {
        this.name = name;
        this.rank = rank;
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Soldier testOject = (Soldier) o;

        if (!getName().equals(testOject.getName())) return false;
        if (!getRank().equals(testOject.getRank())) return false;
        return getSerialNumber().equals(testOject.getSerialNumber());

    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getRank().hashCode();
        result = 31 * result + getSerialNumber().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Soldier{" +
                "name='" + name + '\'' +
                ", rank='" + rank + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                '}';
    }
}
