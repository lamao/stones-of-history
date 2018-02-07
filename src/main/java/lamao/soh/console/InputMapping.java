package lamao.soh.console;

/**
 * Created by Vycheslav Mischeryakov on 31.03.16.
 */
public enum InputMapping {
    SWITCH("console-switch"),
    EXECUTE("console-execute"),
    DELETE_LAST_CHARACTER("console-delete-last-character"),
    PREVIOUS_COMMAND("console-previous-command"),
    NEXT_COMMAND("console-next-command");

    private String name;

    private InputMapping(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
