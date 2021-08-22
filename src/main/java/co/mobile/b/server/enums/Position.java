package co.mobile.b.server.enums;

import java.util.Arrays;

public enum Position {
    TOP("탑", 1),
    JUNGLE("정글", 2),
    MID("미드", 3),
    ADC("원딜", 4),
    SUP("서포터", 5);

    private String name;
    private int type;

    Position(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public static Position valueOf (int type) {
        return Arrays.stream(Position.values())
                .filter(t -> t.getType() == type)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type %s.", type)));
    }

    public static Position nameOf (String name) {
        return Arrays.stream(Position.values())
                .filter(t -> t.getName() == name)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported name %s.", name)));
    }
}
