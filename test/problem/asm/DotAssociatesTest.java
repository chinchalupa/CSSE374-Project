//package problem.asm;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
///**
// * Created by wrightjt on 1/12/2016.
// */
//public class DotAssociatesTest {
//
//    private DotAssociates dotAssociates;
//
//    @Before
//    public void setUp() throws Exception {
//        this.dotAssociates = new DotAssociates("ToString", "FromString");
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        this.dotAssociates = null;
//    }
//
//    @Test
//    public void testGetTo() throws Exception {
//        assertEquals("ToString", this.dotAssociates.getTo());
//    }
//
//    @Test
//    public void testGetFrom() throws Exception {
//        assertEquals("FromString", this.dotAssociates.getFrom());
//    }
//
//    @Test
//    public void testGetLine() throws Exception {
//        assertEquals("solid", this.dotAssociates.getLine());
//    }
//
//    @Test
//    public void testGetArrow() throws Exception {
//        assertEquals("vee", this.dotAssociates.getArrow());
//    }
//}