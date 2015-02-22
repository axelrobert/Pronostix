/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.pronostix.data.collector;

import fr.pronostix.nba.Game;
import fr.pronostix.nba.Journey;
import fr.pronostix.nba.NBACalendar;
import java.io.File;
import java.util.Map.Entry;
import java.util.TreeMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author François_2
 */
public class NBACalendarWriter {

    private static Element addAttributes(Document doc, String name, 
            String value, Element e) {
        Attr attr = doc.createAttribute(name);
        attr.setValue(value);
        e.setAttributeNode(attr);
        return e;
    }

    public static void writeNBACalendar(NBACalendar nbaCalendar) 
            throws ParserConfigurationException, 
            TransformerConfigurationException, 
            TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        // root elements
        org.w3c.dom.Document doc = docBuilder.newDocument();
        org.w3c.dom.Element rootElement = doc.createElement("regular-season");
        doc.appendChild(rootElement);

        // journey elements
        for (Entry<String, Journey> j : nbaCalendar.getJourneys().entrySet()) {

            org.w3c.dom.Element journey = doc.createElement("journey");
            rootElement.appendChild(journey);

            journey = addAttributes(doc, "americanDate", j.getValue().getDate(),
                    journey);
            journey = addAttributes(doc, "games", j.getValue().getGames().size()
                    + "", journey);
            journey = addAttributes(doc, "source", j.getValue().getUri(), 
                    journey);

            //game elements
            for (Game game : j.getValue().getGames()) {
                
                org.w3c.dom.Element gameEl = doc.createElement("game");
                gameEl = addAttributes(doc, "homeTeam", 
                        game.getHome().getNickname(), gameEl);
                gameEl = addAttributes(doc, "awayTeam", 
                        game.getAway().getNickname(), gameEl);
                gameEl = addAttributes(doc, "status", 
                        game.getGameStatus().toString(), gameEl);
                gameEl = addAttributes(doc, "frenchDate", 
                        game.getDate(), gameEl);
                if (game.getHour() != null) {
                    gameEl = addAttributes(doc, "utcHour", 
                            game.getHour(), gameEl);
                }
                
                if (game.hasBeenPlayed()) {
                    
                    org.w3c.dom.Element scoreEl = doc.createElement("score");
                    scoreEl = addAttributes(doc, "homeScore", 
                            game.getHomeScore() + "", scoreEl);
                    scoreEl = addAttributes(doc, "awayScore", 
                            game.getAwayScore() + "", scoreEl);
                    
                    for (int i = 0; i < game.getQtHomeScores().length; i++) {
                        
                        org.w3c.dom.Element qtEl = doc.createElement("qt");
                        qtEl = addAttributes(doc, 
                                "position", (i + 1) + "", qtEl);
                        qtEl = addAttributes(doc, "homeScore", 
                                game.getQtHomeScores()[i] + "", qtEl);
                        qtEl = addAttributes(doc, "awayScore", 
                                game.getQtAwayScores()[i] + "", qtEl);
                        scoreEl.appendChild(qtEl);
                    }
                    gameEl.appendChild(scoreEl);
                }
                journey.appendChild(gameEl);
            }
        }

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(
                new File(NBACalendar.NBA_CALENDAR_FILE));

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}"
                + "indent-amount", "2");
        transformer.transform(source, result);
    }
}
