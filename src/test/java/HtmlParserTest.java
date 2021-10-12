import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class HtmlParserTest {

    @Test
    void
    externalServiceToStringParser() {
       String test =  HtmlParser.externalServiceToStringParser("TestName", "TestURl");
       String base64Url = Base64.getUrlEncoder().encodeToString("TestURl".getBytes());
       assertEquals("TestName/x/"+base64Url+"%-1s",test);
    }

    @Test
    void
    externalServiceToCiidParser() {
        Ciid test =  HtmlParser.externalServiceToCiidParser("TestName", "TestURl");
        String base64Url = Base64.getUrlEncoder().encodeToString("TestURl".getBytes());
        assertEquals("TestName",test.miid.sn);
        assertEquals(base64Url,test.miid.va);
        assertEquals("x",test.miid.vn);
    }

    @Test
    void
    externalServiceToCiidParserFailNameEmpty() {
        String test =  HtmlParser.externalServiceToStringParser("", "TestURl");
        assertNull(test);
    }

    @Test
    void
    externalServiceToCiidParserFailURLEmpty() {
        String test =  HtmlParser.externalServiceToStringParser("TestName", "");
        assertNull(test);
    }

    @Test
    void
    externalServiceToStringParserFailNameEmpty() {
        String test =  HtmlParser.externalServiceToStringParser("", "TestURl");
        assertNull(test);
    }

    @Test
    void
    externalServiceToStringParserFailURLEmpty() {
        Ciid test =  HtmlParser.externalServiceToCiidParser("TestName", "");
        assertNull(test);
    }
}