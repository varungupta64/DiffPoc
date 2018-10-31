import java.util.Objects;

public class Info {
    String fieldName;
    String dataType;

    public Info(String fieldName, String dataType) {
        this.fieldName = fieldName;
        this.dataType = dataType;
    }

    public Info() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Info)) return false;
        Info info = (Info) o;
        return Objects.equals(fieldName, info.fieldName) &&
                Objects.equals(dataType, info.dataType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(fieldName, dataType);
    }

    @Override
    public String toString() {
        return "Info{" +
                "fieldName='" + fieldName + '\'' +
                ", dataType='" + dataType + '\'' +
                '}';
    }
}
