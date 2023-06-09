//package kr.ac.jipark09.domain;
//
//import org.junit.Test;
//
//import static org.junit.Assert.*;
//
//public class PageHandlerTest {
//    @Test
//    public void test() {
//        PageHandler ph = new PageHandler(250, 1); // pageSize = 10
//        assertTrue(ph.getBeginPage() == 1);
//        assertTrue(ph.getEndPage() == 10);
//
//    }
//
//    @Test
//    public void test2() {
//        PageHandler ph = new PageHandler(255, 26);
//        ph.print();
//        System.out.println("ph=" + ph);
//        assertTrue(ph.getBeginPage() == 21);
//        assertTrue(ph.getEndPage() == 26);
//
//    }
//    @Test
//    public void test3() {
//        PageHandler ph = new PageHandler(255, 10);
//        ph.print();
//        System.out.println("ph=" + ph);
//        assertTrue(ph.getBeginPage() == 1);
//        assertTrue(ph.getEndPage() == 10);
//
//    }
//
//}