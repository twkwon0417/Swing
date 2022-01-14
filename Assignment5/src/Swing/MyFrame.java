package Swing;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyFrame extends JFrame {
    VocManager voc = new VocManager();
    Quiz quiz = new Quiz();

    Container frame = this.getContentPane();
    JPanel mainPanel = new JPanel();
    JPanel getNewWord = new JPanel();
    JPanel getAllWord = new JPanel();
    JPanel searchWord = new JPanel();
    JPanel testPanel = new JPanel();
    JPanel reviewPanel = new JPanel();
    JTextArea words = new JTextArea(20, 20);            //모든 단어 추가를 위해서
    String path;                                                      //파일의 경로 저장
    JMenuBar menubar;                                                 //메뉴바 담당
    boolean fileadded = false;                                        //파일이 추가 되자않으면 다른 기능들이 작동이 되지 않게 하려구

    public MyFrame() {
        super("202110363 권태완");
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        init();
        this.setVisible(true);
    }

    public void init() {
        initmenu();
        initMain();
    }

    public void initmenu() {
        menubar = new JMenuBar();//

        JMenu setDefault = new JMenu("메인화면");

        JMenuItem sd = new JMenuItem("메인화면으로");
        setDefault.add(sd);
        sd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    initMain();
                    reviewPanel.removeAll();
            }
        });
        menubar.add(setDefault);

        JMenu wordList = new JMenu("단어장");

        JMenuItem readFile = new JMenuItem("단어장 불러오기");
        readFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser dlg = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("txt File", "txt");
                dlg.setFileFilter(filter);

                int result = dlg.showOpenDialog(readFile);////
                if (result == JFileChooser.APPROVE_OPTION) {
                    path = dlg.getSelectedFile().getAbsolutePath();
                    try {
                        voc.makeVoc(path);
                        fileadded = true;
                        voc.readreminderfile(path);//
                    } catch (FileNotFoundException | ArrayIndexOutOfBoundsException a) {
                        JOptionPane.showMessageDialog(frame, "파일 형식이 일치하지 않습니다.");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    for (Word x : VocManager.list) {////
                        words.append(x + "\n");
                    }
                    //오답하기를 할때 필요한 파일 만들기
                }
                reviewPanel.removeAll();

            }
        });
        wordList.add(readFile);

        JMenuItem addWord = new JMenuItem("단어 추가");
        addWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fileadded){
                    initgetNewWord();
                reviewPanel.removeAll();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "단어장 파일을 추가해주세요.");
                }
            }
        });
        wordList.add(addWord);

        JMenuItem getWord = new JMenuItem("모든 단어 출력");
        getWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileadded) {
                    initgetAllWord();
                    reviewPanel.removeAll();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "단어장 파일을 추가해주세요.");
                }
            }
        });
        wordList.add(getWord);

        JMenuItem searchWord = new JMenuItem("단어 검색");
        searchWord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileadded) {
                    initsearchWord();
                    reviewPanel.removeAll();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "단어장 파일을 추가해주세요.");
                }
            }
        });
        wordList.add(searchWord);

        menubar.add(wordList);


        JMenu test = new JMenu("시험");

        JMenuItem getTest = new JMenuItem("시험 치기");
        getTest.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileadded) {
                    inittestPanel();
                    reviewPanel.removeAll();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "단어장 파일을 추가해주세요.");
                }
            }
        });
        test.add(getTest);

        JMenuItem review = new JMenuItem("오답하기");
        review.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileadded) {
                    reviewPanel.removeAll();
                    initReviewPanel();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "단어장 파일을 추가해주세요.");
                }
            }
        });
        test.add(review);

        menubar.add(test);

        this.setJMenuBar(menubar);
    }

    boolean flag1 = true;
    public void initMain () {

        JLabel myPic = new JLabel(new ImageIcon("img/IMG.png"));
        Font font = new Font("바탕체", 0, 24);
        JLabel myName = new JLabel("202110363 권태완");
        myName.setFont(font);

        if(flag1) {
            mainPanel.add(myPic);
            mainPanel.add(myName);
        }
        flag1 = false;
        removeAllPanel();
        this.add(mainPanel);
        revalidate();
        repaint();
    }

    boolean flag2 = true;
    public void initgetNewWord () {
        getNewWord.setLayout(null);
        JLabel e = new JLabel("영어");
        e.setBounds(80, 30, 60, 30);
        JLabel k = new JLabel("뜻");
        k.setBounds(80, 60, 60, 30);

        JTextField english = new JTextField(20);
        english.setBounds(130, 30, 200, 30);
        JTextField korean = new JTextField(20);
        korean.setBounds(130, 60, 200, 30);
        JButton summit = new JButton("저장");
        summit.setBounds(400, 90, 70, 70);

        summit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileadded) {
                    String eng = english.getText();
                    String kor = korean.getText();
                    Word word = new Word(eng, kor);
                    JOptionPane.showMessageDialog(frame, word + "저장되었습니다.");
                    VocManager.list.add(word);
                    voc.UpdateFile(path, word.eng + "\t" + word.kor);
                    words.append(word + "\n");
                    english.setText(null);
                    korean.setText(null);
                    initgetNewWord();
                    reviewPanel.removeAll();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "단어장 파일을 추가해주세요.");
                }
            }
        });
        if(flag2) {
            getNewWord.add(e);
            getNewWord.add(english);
            getNewWord.add(k);
            getNewWord.add(korean);
            getNewWord.add(summit);
        }
        flag2 = false;

        removeAllPanel();
        this.add(getNewWord);
        revalidate();
        repaint();

    }

    boolean flag3 = true;
    public void initgetAllWord() {
        if(flag3) {
            getAllWord.add(words);
            getAllWord.add(new JScrollPane(words));
        }
        flag3 = false;
        removeAllPanel();
        this.add(getAllWord);
        revalidate();
        repaint();
    }

    boolean flag4 = true;
    public void initsearchWord() {
        JLabel l = new JLabel("검색할 영단어");
        JTextField search = new JTextField(20);
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(fileadded) {
                    String str = search.getText();
                    Word ans = voc.searchVoc(str);
                    if (ans != null) {
                        JOptionPane.showMessageDialog(frame, ans);
                    } else {
                        JOptionPane.showMessageDialog(frame, "단어가 등록 되어있지 않습니다.");
                    }
                    search.setText(null);
                    reviewPanel.removeAll();
                }
                else {
                    JOptionPane.showMessageDialog(frame, "단어장 파일을 추가해주세요.");
                }
            }
        });
        if(flag4) {
            searchWord.add(l);
            searchWord.add(search);
        }
        flag4 = false;

        removeAllPanel();
        this.add(searchWord);
        revalidate();
        repaint();
    }

    boolean flag5 = true;
    public void inittestPanel () {
        ButtonGroup group = new ButtonGroup();
        JRadioButton[] check = new JRadioButton[4];
        JLabel prob;
        JLabel blank = new JLabel("   ");
        menubar.setVisible(false);
        if(flag5) {
            JOptionPane.showMessageDialog(frame, "퀴즈를 다 푸시면 메뉴바가 나옵니다!");///
        }
        testPanel.setLayout(new BoxLayout(testPanel, BoxLayout.Y_AXIS));
        flag5 = false;
        quiz.setchoice();
        prob = new JLabel(quiz.ques.eng);
        testPanel.add(prob);
        prob.setAlignmentX(Component.CENTER_ALIGNMENT);//
        testPanel.add(blank);
        prob.setFont(new Font("굴림체", 0, 20));
        for(int i=0; i < quiz.choices.length; i++) {
            check[i] = new JRadioButton(quiz.choices[i]);
            group.add(check[i]);
            check[i].setBorderPainted(true);
            check[i].addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    for (int i = 0; i < 4; i++) {
                        if (e.getSource() == check[i]) {
                            quiz.userAns = quiz.choices[i];
                            if (quiz.checkAns()) {
                                JOptionPane.showMessageDialog(frame, "정답입니다.");///
                                if(quiz.controller()) {
                                    testPanel.removeAll();
                                    inittestPanel();
                                }
                                else {
                                    setAfterQuiz();
                                }
                            }
                            else {
                                JOptionPane.showMessageDialog(frame, "오답입니다.\n" + quiz.ques + "입니다.");
                                if(quiz.controller()) {
                                    testPanel.removeAll();
                                    inittestPanel();
                                }
                                else{
                                    setAfterQuiz();
                                }
                            }
                        }
                    }
                }
            });
                testPanel.add(check[i]);
                check[i].setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        removeAllPanel();
        this.add(testPanel);
        revalidate();
        repaint();
    }

    public void setAfterQuiz () {
        JOptionPane.showMessageDialog(frame, "점수 : " + quiz.score + "\nTime taken: " + quiz.timeElapsed.toSeconds() + " 초");
        testPanel.removeAll();
        initMain();
        quiz.score = 0;
        quiz.record = true;
        menubar.setVisible(true);
        reviewPanel.removeAll();

        voc.clearfile(path);
        int index = 0;
        for (Word word : VocManager.list) {
            String status = "true";
            if (!word.status) {
                status = "false";
            }

            //아래 수정하는 거는 경로 바꾸기 잊지 말기
            voc.UpdateFile(path.substring(0, path.length() - 4) + "wrong.txt", index + "\t" + VocManager.list.get(index).occur + "\t" + status);

            index++;
        }
    }

    public void initReviewPanel() {
        JTextArea review = new JTextArea(20, 20);
        String[] reviewList = voc.occurrunce();
        for(String i : reviewList) {
            if(i != null) {
                review.append(i + "\n");
            }
        }

            reviewPanel.add(new JScrollPane(review));

        removeAllPanel();
        this.add(reviewPanel);
        revalidate();
        repaint();
    }


    public void removeAllPanel(){
        this.remove(mainPanel);
        this.remove(getNewWord);
        this.remove(getAllWord);
        this.remove(searchWord);
        this.remove(testPanel);
        this.remove(reviewPanel);
    }

}
