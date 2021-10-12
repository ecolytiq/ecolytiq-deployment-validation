import org.junit.jupiter.api.Test;

import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CiidTest {

    //Start testplan tests
    @Test
    void
    singleToString() {
        String simpleTest = "MsA/0.1%123s";
        Ciid ciid = new Ciid(simpleTest);
        assertEquals("MsA/0.1%123s",ciid.toString());
    }

    @Test
    void
    singleToStringWithVa() {
        String simpleTest = "MsA/0.1/dev-ccaaffee%123s";
        Ciid ciid = new Ciid(simpleTest);
        assertEquals("MsA/0.1/dev-ccaaffee%123s",ciid.toString());
    }

    @Test
    void
    toStringWithDepthTwo() {
        String depthOneTest = "MsA/0.1%123s(MsA/0.2%123s(MsA/0.3%123s))";
        Ciid ciid = new Ciid(depthOneTest);
        assertEquals( "MsA/0.1%123s(MsA/0.2%123s(MsA/0.3%123s))",ciid.toString());
    }

    @Test
    void
    toStringWithBreadthTwo() {
        String depthOneTest = "MsA/0.1%123s(MsA/0.2%123s+MsA/0.3%123s)";
        Ciid ciid = new Ciid(depthOneTest);
        Ciid child = ciid.ciids.get(0);
        assertEquals( "MsA/0.1%123s(MsA/0.2%123s+MsA/0.3%123s)",ciid.toString());
    }

    @Test
    void
    singleCIIDRegularInput() {
        String simpleTest = "MsA/0.1%123s";
        Ciid ciid = new Ciid(simpleTest);
        assertEquals("MsA",ciid.miid.sn);
        assertEquals("0.1",ciid.miid.vn);
        assertEquals(123,ciid.miid.time);
    }

    @Test
    void
    singleCIIDRegularInputWithVa() {
        String simpleTest = "MsA/0.1/dev-ccaaffee%123s";
        Ciid ciid = new Ciid(simpleTest);
        assertEquals("MsA",ciid.miid.sn);
        assertEquals("0.1",ciid.miid.vn);
        assertEquals("dev-ccaaffee",ciid.miid.va);
        assertEquals(123,ciid.miid.time);
    }

    @Test
    void
    RegularInputWithDepthOne() {
        String depthOneTest = "MsA/0.1%123s(MsA/0.2%123s)";
        Ciid ciid = new Ciid(depthOneTest);
        assertEquals( "MsA",ciid.miid.sn);
        assertEquals("0.1",ciid.miid.vn);
        assertEquals(123,ciid.miid.time);
        assertEquals(1,ciid.ciids.size());
    }

    @Test
    void
    RegularInputWithDepthOneChildValues() {
        String depthOneTest = "MsA/0.1%123s(MsA/0.2%123s)";
        Ciid ciid = new Ciid(depthOneTest);
        Ciid child = ciid.ciids.get(0);
        assertEquals("MsA",child.miid.sn);
        assertEquals("0.2",child.miid.vn);
        assertEquals(123,child.miid.time);
    }

    @Test
    void
    RegularInputWithDepthTwo() {
        String depthOneTest = "MsA/0.1%123s(MsA/0.2%123s(MsA/0.3%123s))";
        Ciid ciid = new Ciid(depthOneTest);
        Ciid child = ciid.ciids.get(0);
        assertEquals( 1,ciid.ciids.size());
        assertEquals(1,child.ciids.size());
    }

    @Test
    void
    RegularInputWithBreadthTwo() {
        String depthOneTest = "MsA/0.1%123s(MsA/0.2%123s+MsA/0.3%123s)";
        Ciid ciid = new Ciid(depthOneTest);
        Ciid child = ciid.ciids.get(0);
        assertEquals( 2,ciid.ciids.size());
    }

    @Test
    void
    RegularInputWithBreadthThree() {
        String depthThreeTest = "MsA/0.1%123s(MsA/0.2%123s+MsA/0.3%123s+MsA/0.4%123s)";

        Ciid ciid = new Ciid(depthThreeTest);

        assertEquals( 3,ciid.ciids.size());

        Ciid child1 = ciid.ciids.get(0);
        Ciid child2 = ciid.ciids.get(1);
        Ciid child3 = ciid.ciids.get(2);


        assertEquals("MsA",ciid.miid.sn);
        assertEquals("0.1",ciid.miid.vn);
        assertEquals(123,ciid.miid.time);

        assertEquals("MsA",child1.miid.sn);
        assertEquals("0.2",child1.miid.vn);
        assertEquals(123,child1.miid.time);

        assertEquals("MsA",child2.miid.sn);
        assertEquals("0.3",child2.miid.vn);
        assertEquals(123,child2.miid.time);

        assertEquals("MsA",child3.miid.sn);
        assertEquals("0.4",child3.miid.vn);
        assertEquals(123,child3.miid.time);
    }


    @Test
    void
    RegularInputWithBreadthTwoDepthTwo() {
        String depthOneTest = "MsA/0.1%123s(MsA/0.2%123s(MsA/0.3%123s+MsA/0.4%123s))";
        Ciid ciid = new Ciid(depthOneTest);
        Ciid child = ciid.ciids.get(0);
        assertEquals( 1,ciid.ciids.size());
        assertEquals(2,child.ciids.size());
    }

    @Test
    void
    InvalidTimeInputNegativeValue() {
        String simpleTest = "MsA/0.1%-2s";
        Ciid ciid = new Ciid(simpleTest);
        assertNull(ciid.miid);
        assertNull(ciid.ciids);
    }

    @Test
    void
    InvalidTimeInputChar() {
        String simpleTest = "MsA/0.1%xs";
        Ciid ciid = new Ciid(simpleTest);
        assertNull(ciid.miid);
        assertNull(ciid.ciids);
    }

    @Test
    void
    InvalidSpecialCharacterInputSlash() {
        String simpleTest = "/MsA/0.1/%123s";
        Ciid ciid = new Ciid(simpleTest);
        assertNull(ciid.miid);
        assertNull(ciid.ciids);
    }

    @Test
    void
    InvalidSpecialCharacterInputPlus() {
        String simpleTest = "MsA+/0.1/%123s";
        Ciid ciid = new Ciid(simpleTest);
        assertNull(ciid.miid);
        assertNull(ciid.ciids);
    }

    @Test
    void
    InvalidSpecialCharacterInputBracketsOpen() {
        String simpleTest = "MsA(/0.1/%123s";
        Ciid ciid = new Ciid(simpleTest);
        assertNull(ciid.miid);
        assertNull(ciid.ciids);
    }

    @Test
    void
    InvalidSpecialCharacterInputBracketsClose() {
        String simpleTest = "MsA)/0.1/%123s";
        Ciid ciid = new Ciid(simpleTest);
        assertNull(ciid.miid);
        assertNull(ciid.ciids);
    }

    @Test
    void
    InvalidChildInput() {
        String depthOneTest = "MsA/0.1%123s(MsA)/0.1/%123s)";
        Ciid ciid = new Ciid(depthOneTest);
        assertNull(ciid.ciids);
    }

    @Test
    void
    WrongConcatination() {
        String test = "MsA/1.1/xxx%22s+msB/2.0.1/yyyy%444s";
        Ciid ciid = new Ciid(test);
        assertNull(ciid.miid);
        assertNull(ciid.ciids);
    }

    @Test
    void
    setValidEpoch() {
        String simpleTest = "MsA/0.1%1s";
        Ciid ciid = new Ciid(simpleTest);
        long millis = System.currentTimeMillis();
        long seconds = millis / 1000;
        ciid.miid.setEpoch(seconds);
        assertEquals("MsA",ciid.miid.sn);
        assertEquals("0.1",ciid.miid.vn);
        assertEquals(seconds,ciid.miid.time);
    }

    //End testplan tests
}