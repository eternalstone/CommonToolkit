package io.github.eternalstone.common.toolkit.security.mac;

public enum MacAlgorithm {
    HmacMD5("HmacMD5"), HmacSHA1("HmacSHA1"), HmacSHA224("HmacSHA224"), HmacSHA256("HmacSHA256"), HmacSHA384(
            "HmacSHA384"), HmacSHA512("HmacSHA512");
    private String name;

    private MacAlgorithm(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
