package Swing;

import java.io.*;
import java.util.*;

public class VocManager{
    public static ArrayList<Word> list = new ArrayList<>();


    void addword(Word word) {
        list.add(word);
    }

    void makeVoc(String filename) throws FileNotFoundException, ArrayIndexOutOfBoundsException {

        Scanner scan = new Scanner(new File(filename));
            while (scan.hasNextLine()) {
                String str = scan.nextLine();
                String[] temp = str.split("\t");
                this.addword(new Word(temp[0].trim(), temp[1].trim()));//trim 앞뒤 공백 삭제
            }
    }

    public Word searchVoc(String sWord) {
        sWord = sWord.trim();
        for (Word word : list) {
            if (word != null && word.eng.equals(sWord)) {
                return word;
            }
        }
        return null;
    }


    public String[] occurrunce() {
        ArrayList<String> al = new ArrayList<>();
        String[] pass = new String[20];
        Collections.sort(list, (w1, w2) -> w2.occur - w1.occur);
        for (Word i : list) {
            if (!i.status) {
                al.add(i.occur + "번" + "->" + i);
            }
        }

        int j = 0;
        for (String i : al) {
            pass[j] = i;
            j++;
            if (j == 20) {
                break;
            }
        }
        return pass;
    }

    public void UpdateFile(String FilePath, String Text) {          //reminder file도 공통으로 이용할수 있겠둔
        try {
            File f = new File(FilePath);


            // 파일 읽기
            String fileText = ReadFileText(f);
            BufferedWriter buffWrite = new BufferedWriter(new FileWriter(f));
            Text = fileText + "\n" + Text;
            // 파일 쓰기
            buffWrite.write(Text, 0, Text.length());
            // 파일 닫기
            buffWrite.flush();
            buffWrite.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String ReadFileText(File file) {
        String strText = "";
        int nBuffer;
        try {
            BufferedReader buffRead = new BufferedReader(new FileReader(file));
            while ((nBuffer = buffRead.read()) != -1) {
                strText += (char)nBuffer;
            }
            buffRead.close();
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return strText;
    }

    public void readreminderfile(String filepath) throws IOException {
        String newpath = filepath.substring(0, filepath.length() - 4);
        Scanner scan = null;
        try {
            scan = new Scanner(new File(newpath + "wrong.txt"));
            if(scan.hasNextLine()) {
                scan.nextLine();
            }
            while (scan.hasNextLine()) {
                String str = scan.nextLine();
                String[] temp = str.split("\t");
                VocManager.list.get(Integer.parseInt(temp[0].trim())).occur = Integer.parseInt(temp[1]);
                if (Objects.equals(temp[2], "false")) {
                    VocManager.list.get(Integer.parseInt(temp[0].trim())).status = false;
                }
            }
        } catch (FileNotFoundException e) {
            makereminderfile(filepath);
        }

    }


    public void makereminderfile(String filepath) throws IOException {          //
        String newpath = filepath.substring(0, filepath.length() - 4);
        File wrong = new File(newpath + "wrong.txt");
        wrong.createNewFile();
    }

    public void clearfile(String filepath) {
        String newpath = filepath.substring(0, filepath.length() - 4);
        try {
            new FileOutputStream(newpath + "wrong.txt").close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
