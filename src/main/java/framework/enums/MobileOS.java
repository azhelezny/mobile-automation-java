package framework.enums;

public enum MobileOS {
    Android("Android"),
    IOS("iOS"),
    undefined("undefined");

    private final String os;

    MobileOS(String os) {
        this.os = os;
    }

    @Override
    public String toString() {
        return this.os;
    }

    public static MobileOS get(String str) {
        if (str == null)
            return MobileOS.undefined;
        for (MobileOS mobileOS : MobileOS.values())
            if (mobileOS.toString().toLowerCase().equals(str.toLowerCase()))
                return mobileOS;
        return MobileOS.undefined;
    }
}
