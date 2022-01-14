package Swing;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class Quiz {

    Random rand = new Random();

    Word ques;                              //문제에 들어가는 Word 객체 : 프레임에 넘겨줄때 사용
    String[] choices = new String[4];       //프레임에 들어갈 버튼에 넣어야 하는 값들

    Set<Word> used = new HashSet<>();       //중복을 처리하기 위해서
    Set<String> ans = new HashSet<>();      //뜻 중복을 처리하기 위해서
    int size = 0;                           //중복을 처리하기 위해서

    int num;                                //ques의 list에서의 index
    int Qnum = 1;                           //10문제를 출력하기 위한 변수
    String userAns;                         //Frame에서 선택 된걸 받아옴
    int score = 0;

    Instant start;
    Instant end;
    Duration timeElapsed;
    Boolean record = true;                      //시간 재는데 쓸 flag이다.

    public void setchoice() {
        if(record){
            start = Instant.now();
            record = false;
        }
        do {
            size = used.size();
            num = rand.nextInt(VocManager.list.size());//원래 59 였음          num이 문제 단어
            used.add(VocManager.list.get(num));        //중복 제거
            VocManager.list.get(num).occur++;
        }
        while (size == used.size());
        ans.add(VocManager.list.get(num).kor);
        ques = VocManager.list.get(num);


        while (ans.size() < 4) {
            int Anum = rand.nextInt(VocManager.list.size());            //뜻 같은거 제거하는 법 추가해야됨
            ans.add(VocManager.list.get(Anum).kor);                    //ans가 Set이라서 중복은 알아서 걸러주ㅠㅁ
            VocManager.list.get(Anum).occur++;
            for (int j = 0; j < ans.size(); j++) { //중복 제거 하는 로직
                if (!ans.contains(VocManager.list.get(Anum).kor)) {
                    ans.remove(VocManager.list.get(Anum).kor);
                    VocManager.list.get(Anum).occur--;
                    break;
                }
            }

        }

        Iterator<String> it = ans.iterator();
        while (it.hasNext()) {
            for (int i = 1; i < 5; i++) {
                String str = it.next();
                choices[i - 1] = str;
            }
            ans.clear();
        }

    }

    public boolean checkAns() {
        if (Objects.equals(VocManager.list.get(num).kor, userAns)) {
            score++;
            return true;
        } else {
            VocManager.list.get(num).status = false;
            return false;
        }
    }

    public boolean controller() {
        if (Qnum == 10) {
            Qnum = 1;
            used.clear();
            ans.clear();
            end = Instant.now();
            timeElapsed = Duration.between(start, end);
            return false;
        } else {
            Qnum++;
            return true;
        }
    }
}