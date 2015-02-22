/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pronostix.test;

import fr.pronostix.data.collector.NBACalendarCollector;
import fr.pronostix.data.collector.NBACalendarReader;
import fr.pronostix.data.collector.NBACalendarWriter;
import fr.pronostix.nba.NBACalendar;
import fr.pronostix.nba.utils.DateUtils;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

/**
 *
 * @author François_2
 */
public class PronostixCalendarParserTest {

    public static void testHourConversion() {
        String[] hours = new String[]{
            "1:00 PM ET",
            "3:30 PM ET",
            "6:00 PM ET",
            "7:00 PM ET",
            "7:30 PM ET",
            "9:00 PM ET",
            "9:30 PM ET"
        };
        for (String s : hours) {
            System.out.println(s + " --> " + DateUtils.convertETHourToUTC1(s));
        }
    }

    public static void testCalendarMethods() {
        NBACalendar nbaCalendar = null;
        if (!NBACalendarReader.nbaCalendarFileExists()) {
            System.out.println("NO XML FILE WAS FOUND.");
            NBACalendarCollector dataCollector = new NBACalendarCollector();
            nbaCalendar = dataCollector.getNBACalendar();
            if (nbaCalendar.dataSanityChecked()) {
                System.out.println("SANITY CHECK : OK. READY FOR WRITING.");
                try {
                    NBACalendarWriter.writeNBACalendar(nbaCalendar);
                    System.out.println("WRITING SUCCEEDED.");
                } catch (ParserConfigurationException | TransformerException ex) {
                    Logger.getLogger(PronostixCalendarParserTest.class.getName()).log(Level.SEVERE, null, ex);
                    System.err.println("WRITING FAILED.");
                }
            } else {
                System.out.println("SANITY CHECK : KO");
            }
        } else {
            System.out.println("XML CALENDAR PRESENT.");
            try {
                nbaCalendar = NBACalendarReader.readNBACalendar();
                System.out.println("READING SUCCEEDED.");
                if (nbaCalendar.dataSanityChecked()) {
                    System.out.println("SANITY CHECK : OK.");
                } else {
                    System.out.println("SANITY CHECK : KO.");
                }
                nbaCalendar.getTeams().get("Trail Blazers").printCalendar();
            } catch (ParserConfigurationException | SAXException | IOException ex) {
                Logger.getLogger(PronostixCalendarParserTest.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("READING FAILED.");
            }
        }

    }

    public static void main(String[] args) {
        testCalendarMethods();
    }

}
