package freq;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.*;
import org.jsoup.Jsoup;

import freq.Letter;

public class Main {
  public static void main(String [ ] args) { 
    String matchNotUnicodeLetter="\\P{L}";
    Document doc=new Document("");
    try {
    doc=Jsoup.connect("https://en.wikipedia.org/wiki/2005_Azores_subtropical_storm").get();
    } catch (Exception e){e.printStackTrace();}
    String htmlText=doc.body().text();
    Letter ltr;
    int len=htmlText.length();
    String c;
    Map <String, Letter> map= new HashMap<String, Letter>();
    int allTheLetters=0;
    for (int i=0; i<len; i++){
      c=htmlText.substring(i, i+1);
      if (!Pattern.matches(matchNotUnicodeLetter, c)){
        ltr=map.get(c);
        if (ltr==null){
          ltr=new Letter(c);
          map.put(ltr.getLetter(), ltr);
          }
        ltr.increaseOccurence();
        allTheLetters++;
        }
      }
    try {
    FileWriter fw = new FileWriter("letter_frequency.css");
    BufferedWriter bw = new BufferedWriter(fw);
    PrintWriter out = new PrintWriter(bw);
    for (Letter value: map.values()){
      value.setRatio(allTheLetters);      
      out.println(value.getCSSClass(0.5f));
      }
      out.close();
    }
      catch (IOException e){
      System.out.println("Exception ");
      }
    doc.outputSettings().prettyPrint(false);
    try {
    FileWriter fw3 = new FileWriter("webpage_src.html");
    BufferedWriter bw3 = new BufferedWriter(fw3);
    PrintWriter out3 = new PrintWriter(bw3);
      out3.println(doc.outerHtml());
      out3.close();
    }
      catch (IOException e){
      System.out.println("Exception ");
      }
    doc.head().append("<link rel=\"stylesheet\" type=\"text/css\" href=\"letter_frequency.css\">\n");
    String innerBody=setLetterClasses(map, doc);
    doc.body().html("");
    String outputString=doc.outerHtml();
    outputString=outputString.replace("</body>",innerBody+"</body>");
    try {
    FileWriter fw2 = new FileWriter("webpage.html");
    BufferedWriter bw2 = new BufferedWriter(fw2);
    PrintWriter out2 = new PrintWriter(bw2);
      out2.println(outputString);      
      out2.close();
    }
      catch (IOException e){
      System.out.println("Exception ");
      }
    }
    public static String setLetterClasses(Map <String, Letter> map, Document d){
      StringBuilder bodyInnerHTML=new StringBuilder(d.body().html());
      String c;
      int tags=0;
      for (int i=0; i<bodyInnerHTML.length(); i++){
      c=bodyInnerHTML.substring(i, i+1);
        if (c.equals("<")) tags++;
        if (tags==0 && map.get(c)!=null){
          bodyInnerHTML.deleteCharAt(i);
          bodyInnerHTML.insert(i, map.get(c).getSpanClass());
          i=i+map.get(c).getSpanClass().length()-1;
          }
        if (c.equals(">")) tags--;
      }
      return bodyInnerHTML.toString();
      }
  };
