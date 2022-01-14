package Swing;

public class Word  {
    String eng;
    String kor;
    int occur;
    Boolean status;

    public Word(String eng, String kor) {
        this.eng = eng;
        this.kor = kor;
        this.occur = 0;
        this.status = true;
    }

    @Override
    public String toString() {
        return eng+" : "+ kor;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Word){
            Word temp = (Word)obj;
            return this.eng.equals(temp.eng) && this.kor.equals(temp.kor);
        }
        else{
            return false;
        }
    }

    @Override
    public int hashCode() {
        return this.eng.hashCode() + this.kor.hashCode();
    }

}

