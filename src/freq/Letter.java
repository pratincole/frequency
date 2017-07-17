package freq;

public class Letter{
  private String letter;
  private int occurences=0;
  private float ratio=1.0f;
  private static int maxSize=40;
  private static int minSize=5;
  public Letter (String l){
    letter=l;
    }
  public void print(){
    System.out.println("letter:"+letter+", occured:"+occurences);
    }
  public String getLetter(){ return letter; }
  protected void increaseOccurence(){ occurences++; }
  protected void setRatio(int totalLetters){
    ratio=totalLetters/((float) occurences);
    }
  public String getCSSClass(float baseSize){
    float size=ratio*baseSize;
    if (size<minSize) size=minSize;
    if (size>maxSize) size=maxSize;
    return "."+letter+" { font-size:"+size+"pt !important; display:inline-block; }\n";
    }
  public String getSpanClass(){
    return "<span class=\""+letter+"\">"+letter+"</span>";
    }
  };
