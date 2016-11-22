package sockprotoutil;

import java.io.Serializable;

import proto.MyIntegerProto;

public class SockProtoUtil {

    private static class ExitSignal implements Serializable {
        private static final long serialVersionUID = 1L;
    }

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
        return new ExitSignal();
    }

    public static boolean isExitSignal(Object obj) {
        return obj instanceof ExitSignal;
    }

    public static MyIntegerProto.MyInteger createProtoMessage(int intValue) {
        return MyIntegerProto.MyInteger.newBuilder().setIntValue(intValue).build();
    }

}
