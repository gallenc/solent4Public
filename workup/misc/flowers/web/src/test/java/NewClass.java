/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gallenc
 */
public class NewClass {

    @Test
    public void test() {
        int i = 0 % 10;
        System.out.println(" i=" + i);
        i = 2 % 10;
        System.out.println(" i=" + i);

        i = 21 % 10;
        System.out.println(" i=" + i);
    }
}
