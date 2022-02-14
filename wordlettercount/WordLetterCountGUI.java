/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordlettercount;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Sara
 */
public class WordLetterCountGUI extends JFrame{
    private final JButton b1;
    private final JButton b2;
    private final JButton b3;
    private final JButton openButton;
    private final JButton saveNewButton;
    private final JButton saveExistButton;
    
    private BufferedReader br1;
    private BufferedReader br2;
    private BufferedReader br3;
    
    private static String fn;
    private String fn1;
    private String fn2;
    private String fn3;
    

    public WordLetterCountGUI(){
        JPanel butPanel = new JPanel();
        JPanel taPanel = new JPanel();
        add(butPanel,BorderLayout.NORTH);
        add(taPanel,BorderLayout.CENTER);
        

        br1 = null;
        br2 = null;
        br3 = null;
        
        JTextArea ta1 = new JTextArea(30,30);
        JScrollPane scrollPane1 = new JScrollPane(ta1);
        scrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ta1.setEditable(false);
        ta1.setLineWrap(true);
        
        JTextArea ta2 = new JTextArea(30,30);
        JScrollPane scrollPane2 = new JScrollPane(ta2);
        scrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ta2.setEditable(false);
        ta2.setLineWrap(true);
        
        b1 = new JButton("File 1");
        b2 = new JButton("File 2");
        b3 = new JButton("File 3");
        
        
        
        FileHandler fh = new FileHandler();
        b1.addActionListener(fh);
        b2.addActionListener(fh);
        b3.addActionListener(fh);
        
        Object[] buffReads = {
            "File 1", b1,
            "File 2", b2,
            "File 3", b3
        };
        
        
        
        openButton = new JButton("Open Files");
        openButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                br1 = null;
                br2 = null;
                br3 = null;
                int result = JOptionPane.showConfirmDialog(null, buffReads, "Get Files", JOptionPane.OK_CANCEL_OPTION);
                
                Runnable r1;
                Thread t1;
                Runnable r2;
                Thread t2;
                Runnable r3;
                Thread t3;
                
                if(result == JOptionPane.OK_OPTION){
                    // 5 words  -  24 letters
                    if(br1!=null){
                        r1 = new WordLetterCount(br1, ta1, ta2, fn1);
                        t1 = new Thread(r1);
                        t1.start();
                    }
                    
                    // 8 words  -  16 letters
                    if(br2!=null){
                        r2 = new WordLetterCount(br2, ta1, ta2, fn2);
                        t2 = new Thread(r2);
                        t2.start();
                    }
                    
                    // 10 words  =  34 letters
                    if(br3!=null){
                        r3 = new WordLetterCount(br3, ta1, ta2, fn3);
                        t3 = new Thread(r3);
                        t3.start();
                    }
                    
                    
                    
                }
            }
            
        }); // END openButton ACTION LISTENER
        
        saveNewButton = new JButton("Save to New File");
        saveNewButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //System.out.println(ta.getText());
                saveNewFile(ta1);
            }
        
        });
        
        saveExistButton = new JButton("Save to Existing File");
        saveExistButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //System.out.println(ta.getText());
                saveExistFile(ta1);
            }
        
        });
        
        butPanel.add(openButton);
        butPanel.add(saveNewButton);
        butPanel.add(saveExistButton);
        taPanel.add(scrollPane1, BorderLayout.WEST);
        taPanel.add(scrollPane2, BorderLayout.WEST);
        
        //
        //String counts = wlc.open();
        //wordCount = wordCount(list);
        //System.out.println(wordCount + " " + letterCount);
    }
    
    private class FileHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == b1){
                br1 = openFile();
                fn1=fn;
            }else if(e.getSource() == b2){
                br2 = openFile();
                fn2=fn;
            }else if(e.getSource() == b3){
                br3 = openFile();
                fn3=fn;
            }
        }
        
    }

    /**
     *
     * @return 
     */
    public static BufferedReader openFile(){
        Frame f = new Frame();
        FileDialog fd = new FileDialog(f,"Reading text file", FileDialog.LOAD);
        fd.setVisible(true);
        String fName = fd.getFile();
        fn = fName;
        String dp = fd.getDirectory();
        
        File inFile = new File(dp + fName);
        if(!inFile.exists()){
            System.out.println("That file doesn't exist");
            System.exit(0);
        }
        
        BufferedReader in = null;
        try{
            in = new BufferedReader(new FileReader(inFile));
        }catch(IOException e){
            System.out.println("An io exception occured!");
        }
        
        return in;
    }// end openFile
    
    
    
    
    public static void saveNewFile(JTextArea ta){
        Frame f = new Frame();
        FileDialog fd = new FileDialog(f,"Saving Data", FileDialog.SAVE);
        fd.setVisible(true);
        String fName = fd.getFile();
        String dp = fd.getDirectory();
        
        File outFile = new File(dp + fName);
        
        String date = getCurDate();
        
        StringBuilder sb = new StringBuilder();
        sb.append(ta.getText());
        //FileOutputStream fos = null;
        //ObjectOutputStream oos = null;
        
        try{
            /*fos = new FileOutputStream(outFile);
            oos = new ObjectOutputStream(fos);

            oos.writeObject(ta.getText().getBytes());
            */
            BufferedWriter bw = new BufferedWriter(new FileWriter(outFile));
            bw.write("-----------------------\n  " + date + "\n-----------------------\n" + sb.toString() + "\n\n");
            bw.close();
            
            JOptionPane.showMessageDialog(ta, "File Saved Successfully.", "File Saved", JOptionPane.INFORMATION_MESSAGE);
        }catch(IOException e){
            System.out.println("An io exception occured!");
        }

    }// end saveNewFile
    
    public static void saveExistFile(JTextArea ta){
        Frame fo = new Frame();
        FileDialog fd = new FileDialog(fo,"Saving Data", FileDialog.LOAD);
        fd.setVisible(true);
        String fName = fd.getFile();
        String dp = fd.getDirectory();
        
        File outFile = new File(dp + fName);
        
        String date = getCurDate();
        StringBuilder sb = new StringBuilder();
        sb.append(ta.getText());
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(outFile, true))){
            bw.append("-----------------------\n  " + date + "\n-----------------------\n" + sb.toString() + "\n\n");
            bw.close();
            
            JOptionPane.showMessageDialog(ta, "File Saved Successfully.", "File Saved", JOptionPane.INFORMATION_MESSAGE);
        }catch(IOException e){
            System.out.println("An io exception occured!");
        }

    }// end saveExistFile
    
    
    private static String getCurDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        
        
        return dtf.format(now);
    }
    
}
