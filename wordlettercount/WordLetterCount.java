
package wordlettercount;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JTextArea;

/**
 *
 * @author Sara
 */
public class WordLetterCount implements Runnable {
    private static int wordCount;
    private static int letterCount;
    private final BufferedReader in;
    private String fn;
    private JTextArea ta1;
    private JTextArea ta2;
    private static final Lock lock = new ReentrantLock();
    private static final Condition valueAvailableCondition = lock.newCondition();
    
    public WordLetterCount(BufferedReader br, JTextArea ta1, JTextArea ta2, String fni){
        this.in = br;
        this.ta1 = ta1;
        this.ta2 = ta2;
        this.fn = fni;
    }
    
    @Override
    public void run(){
        try{
            //System.out.println(wordCount + " " + letterCount);
            composeList(in, fn, ta1, ta2);
            //System.out.println(wordCount + " " + letterCount + " before");
            //System.out.println(Thread.currentThread().getId() + " " +wordCount + " " + letterCount);
            Thread.sleep(100);
        }catch(InterruptedException e){
            System.out.println("An ie exception occurred!");
        }
    }
    /**
     *
     * @param br
     * @param fn
     * @param ta1
     * @param ta2
     */
    public static void composeList(BufferedReader br, String fn, JTextArea ta1, JTextArea ta2){
        lock.lock();
        ArrayList<String> list = new ArrayList<String>();
        try{
            //System.out.println("IN CL");
            String delim = " ";
            String[] words = null;
            String[] letters = null;
            wordCount = 0;
            letterCount = 0;
            try{
                String line = br.readLine();
                
                while(line!=null){
                    list.add(line);
                    words = line.split(delim);
                    wordCount += words.length;
                    //gets word[i] and breaks it up by character
                    for(int i=0;i<words.length;i++){
                        letters = words[i].split("");

                        for(int j=0;j<letters.length;j++){
                            String word = letters[j];
                            //boolean lett = ;
                            for(int k=0;k<word.length();k++){
                                if(Character.isLetter(word.charAt(k))==true){
                                    //System.out.println(letters[j]);
                                    letterCount++;
                                }
                            }
                        }
                    }
                    
                    line = br.readLine();
                }//end WHILE
                
                br.close();
            }catch(IOException e){
                System.out.println("An io exception occured");
            }

            
            //System.out.println(wordCount + " " + letterCount);
            addToTA1(fn, ta1);
            addToTA2(fn,ta2,list);
            valueAvailableCondition.signalAll();
        }finally{
            lock.unlock();
        }
    }
    
    private static void addToTA1(String fn, JTextArea ta){
        ta.append("  Thread " + Thread.currentThread().getId() + ": The file " + fn + " has "+ wordCount + " words and " + letterCount + " letters.\n\n");
        
    }
    
    private static void addToTA2(String fn, JTextArea ta, ArrayList<String> list){
        //System.out.println("in method" + fn);
        ta.append("  Thread:" +Thread.currentThread().getId() + "\n  File:  " +fn + "\n");
        
        for(int i=0;i<list.size();i++){
            //System.out.println(list.get(i));
            ta.append("     " +list.get(i) + "\n");
        }
        ta.append("\n\n");
    }
}
