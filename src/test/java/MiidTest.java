import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MiidTest {

    @Test
    void
    RegularInputSimpleNumber() {
        String simpleTest = "MsA/1%123s";
        Miid miid = new Miid(simpleTest);
        assertEquals("MsA",miid.sn);
        assertEquals("1",miid.vn);
        assertEquals(123,miid.time);
    }

    @Test
    void
    RegularInputSimpleVersion() {
        String simpleTest = "MsA/v1%123s";
        Miid miid = new Miid(simpleTest);
        assertEquals("MsA",miid.sn);
        assertEquals("v1",miid.vn);
        assertEquals(123,miid.time);
    }

    @Test
    void
    RegularInputComplexNumber() {
        String simpleTest = "MsA/1.0.1%123s";
        Miid miid = new Miid(simpleTest);
        assertEquals("MsA",miid.sn);
        assertEquals("1.0.1",miid.vn);
        assertEquals(123,miid.time);
    }

    @Test
    void
    RegularInputComplexVersion() {
        String simpleTest = "MsA/v1.0.1%123s";
        Miid miid = new Miid(simpleTest);
        assertEquals("MsA",miid.sn);
        assertEquals("v1.0.1",miid.vn);
        assertEquals(123,miid.time);
    }

    @Test
    void
    NoneLatinCharacter() {
        String simpleTest = "猫/1.0.1%123s";
        Miid miid = new Miid(simpleTest);
        assertEquals("猫",miid.sn);
        assertEquals("1.0.1",miid.vn);
        assertEquals(123,miid.time);
    }


    @Test
    void
    RegularInputTimeUnkownService() {
        String simpleTest = "MsA/1%-1s";
        Miid miid = new Miid(simpleTest);
        assertEquals("MsA",miid.sn);
        assertEquals("1",miid.vn);
        assertEquals(-1,miid.time);
    }

    @Test
    void
    NegativeTimeValue() {
        String simpleTest = "MsA/1%-2s";
        Miid miid = new Miid(simpleTest);
        assertNull(miid.sn);
        assertNull(miid.vn);
    }

    @Test
    void
    TimeValueNotANumber() {
        String simpleTest = "MsA/1%xs";
        Miid miid = new Miid(simpleTest);
        assertNull(miid.sn);
        assertNull(miid.vn);
    }

    @Test
    void
    LongServiceName() {
        String simpleTest = "Wer wünschte so?" +
                "Mein Vetter Westmoreland? – Nein, bester Vetter:" +
                "Zum Tode ausersehn, sind wir genug" +
                "Zu unsers Lands Verlust; und wenn wir leben," +
                "Je klein're Zahl, je größres Ehrenteil." +
                "Wie Gott will! Wünsche nur nicht einen mehr!" +
                "Beim Zeus, ich habe keine Gier nach Gold/1%123s";
        Miid miid = new Miid(simpleTest);
        assertEquals("Wer wünschte so?" +
                        "Mein Vetter Westmoreland? – Nein, bester Vetter:" +
                        "Zum Tode ausersehn, sind wir genug" +
                        "Zu unsers Lands Verlust; und wenn wir leben," +
                        "Je klein're Zahl, je größres Ehrenteil." +
                        "Wie Gott will! Wünsche nur nicht einen mehr!" +
                        "Beim Zeus, ich habe keine Gier nach Gold" ,miid.sn);
        assertEquals("1",miid.vn);
        assertEquals(123,miid.time);
    }

    @Test
    void
    RegularInputAdditionalInfo() {
        String simpleTest = "MsA/0.1/devccaaffee%123s";
        Miid miid = new Miid(simpleTest);
        assertEquals("MsA",miid.sn);
        assertEquals("0.1",miid.vn);
        assertEquals("devccaaffee",miid.va);
        assertEquals(123,miid.time);
    }

    @Test
    void
    RegularInputAdditionalInfoSpecialChar() {
        String simpleTest = "MsA/0.1/dev-ccaaffee%123s";
        Miid miid = new Miid(simpleTest);
        assertEquals("MsA",miid.sn);
        assertEquals("0.1",miid.vn);
        assertEquals("dev-ccaaffee",miid.va);
        assertEquals(123,miid.time);
    }

    @Test
    void
    FalseInputAdditionalInfoSpecialCharPlus() {
        String simpleTest = "MsA/0.1/dev+ccaaffee%123s";
        Miid miid = new Miid(simpleTest);
        assertNull(miid.sn);
        assertNull(miid.vn);
    }

    @Test
    void
    FalseInputAdditionalInfoSpecialCharSlash() {
        String simpleTest = "MsA/0.1/dev/ccaaffee%123s";
        Miid miid = new Miid(simpleTest);
        assertNull(miid.sn);
        assertNull(miid.vn);
    }

    @Test
    void
    EmptyAdditionalInformation() {
        String simpleTest = "MsA/0.1/%123s";
        Miid miid = new Miid(simpleTest);
        assertEquals("MsA",miid.sn);
        assertEquals("0.1",miid.vn);
        assertEquals("",miid.va);
        assertEquals(123,miid.time);
    }

    @Test
    void
    EmptyServiceName() {
        String simpleTest = "/0.1/dev-ccaaffee%123s";
        Miid miid = new Miid(simpleTest);
        assertNull(miid.sn);
        assertNull(miid.vn);
    }

    @Test
    void
    EmptyVersion() {
        String simpleTest = "MsA//dev-ccaaffee%123s";
        Miid miid = new Miid(simpleTest);
        assertNull(miid.sn);
        assertNull(miid.vn);
    }

    @Test
    void
    MissingSeparationCharSlash() {
        String simpleTest = "MsA0.1dev-ccaaffee%123s";
        Miid miid = new Miid(simpleTest);
        assertNull(miid.sn);
        assertNull(miid.vn);
    }

    @Test
    void
    MissingSeparationCharPercent() {
        String simpleTest = "MsA/0.1/dev-ccaaffee123s";
        Miid miid = new Miid(simpleTest);
        assertNull(miid.sn);
        assertNull(miid.vn);
    }

    @Test
    void
    ServiceNameWithSeparationChar() {
        String simpleTest = "Test/Test/0.1/dev-ccaaffee%123s";
        Miid miid = new Miid(simpleTest);
        assertNull(miid.sn);
        assertNull(miid.vn);
    }
}