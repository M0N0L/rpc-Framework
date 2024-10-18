import org.example.myrpc.serializer.Serializer;
import org.example.myrpc.spi.SpiLoader;
import org.junit.Test;

public class RPCTest {
    @Test
    public void testSPILoader() {
        SpiLoader.load(Serializer.class);
        assert SpiLoader.getInstance(Serializer.class, "a") != null;
    }
}
