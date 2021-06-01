package nju.zjl;

import java.util.LinkedList;

public class Recorder {
    public Recorder(int steps){
        this.steps = steps;
        records = new LinkedList<>();
    }

    public void addRecord(Record r){
        records.addLast(r);
        if(records.size() > steps){
            records.removeFirst();
        }
    }

    public void undo(){
        if(!records.isEmpty()){
            records.removeLast().undo();
        }
    }

    protected int steps;
    protected LinkedList<Record> records;
}

interface Record {
    void undo();
}
