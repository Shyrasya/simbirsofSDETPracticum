package enums;

public enum AutomationAnswer {

    YES("yes"),
    NO("no"),
    UNDECIDED("undecided");

    private final String value;

    AutomationAnswer(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
