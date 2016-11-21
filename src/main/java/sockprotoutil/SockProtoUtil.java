package sockprotoutil;

import java.io.Serializable;

import proto.MyIntegerProto;

public class SockProtoUtil {

    private SockProtoUtil() {
    }

    public static void closeIfPossible(AutoCloseable... autoCloseables) {
        for (AutoCloseable currentCloseable : autoCloseables) {
            if (currentCloseable != null) {
                try {
                    currentCloseable.close();
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        }
    }

    public static Object exitSignalObject() {
        class ExitSignal implements Serializable {}
        return new ExitSignal();
    }

    public static MyIntegerProto.MyInteger createProtoMessage(int intValue) {
        return MyIntegerProto.MyInteger.newBuilder().setIntValue(intValue).build();
    }

}
