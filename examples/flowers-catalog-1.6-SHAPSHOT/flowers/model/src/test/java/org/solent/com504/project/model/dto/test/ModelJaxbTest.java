
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.solent.com504.project.model.dto.test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.solent.com504.project.model.flower.dto.Flower;

import org.solent.com504.project.model.flower.dto.ReplyMessage;

public class ModelJaxbTest {

    final static Logger LOG = LogManager.getLogger(ModelJaxbTest.class);

    public JAXBContext jaxbContext;

    @Before
    public void setup() {
        // this contains a list of Jaxb annotated classes for the context to parse, seperated by :
        // NOTE you must also have a jaxb.index or jaxb ObjectFactory in the same classpath
        try {
            jaxbContext = JAXBContext.newInstance(
                    "org.solent.com504.project.model.flower.dto"
            );
        } catch (JAXBException e) {
            throw new RuntimeException("problem creating jaxb context", e);
        }
    }

    @Test
    public void testModelJaxb1() {

        try {

            // test file we will write and read. 
            // Note in target folder so that will be deleted on each run and will not be saved to git
            File file = new File("target/testTransactionData.xml");
            LOG.debug("writing test file to " + file.getAbsolutePath());

            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            ReplyMessage replyMessage = new ReplyMessage();

            replyMessage.setCode(200);
            replyMessage.setDebugMessage("debug message");
            List<String> stringList = Arrays.asList("one", "two", "three", "four");
            replyMessage.setStringList(stringList);
            
            

            
            List<Flower> flowerList = Arrays.asList(new Flower("ABAB",
                    "",
                    "Abutilon abutiloides (Jacq.) Garcke ex Hochr.",
                    "shrubby Indian mallow",
                    "Malvaceae",
                    "https://plants.usda.gov/core/profile?symbol=ABAB"));
            
            replyMessage.setFlowerList(flowerList);

            // create XML from the object
            // marshal the object lists to system out, a file and a stringWriter
            LOG.debug("marshalled replymessage");
            jaxbMarshaller.marshal(replyMessage, System.out);
            jaxbMarshaller.marshal(replyMessage, file);

            // string writer is used to compare received object
            StringWriter sw1 = new StringWriter();
            jaxbMarshaller.marshal(replyMessage, sw1);

            // having written the file we now read in the file for test
            Unmarshaller jaxbUnMarshaller = jaxbContext.createUnmarshaller();
            ReplyMessage receivedTransactionResult = (ReplyMessage) jaxbUnMarshaller.unmarshal(file);

            StringWriter sw2 = new StringWriter();
            jaxbMarshaller.marshal(receivedTransactionResult, sw2);

            // check transmitted and recieved messages are the same
            assertEquals(sw1.toString(), sw2.toString());

        } catch (JAXBException e) {
            throw new RuntimeException("problem testing jaxb marshalling", e);
        }
    }

}
