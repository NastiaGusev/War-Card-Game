package com.example.war.objects;

import java.util.ArrayList;
import java.util.Collections;

public class TopTen {
    private final int SIZE = 10;
    private ArrayList<Record> records;

    public TopTen() {
        records = new ArrayList<>();
    }

    public TopTen(ArrayList<Record> records) {
        this.records = records;
    }

    private boolean recordsFull(){
        if(records.size() == SIZE){
            return true;
        }
        return false;
    }

    private void sortRecords(){
        Collections.sort(records);
    }

    //Insert record to top ten -
    //If the record makes top ten - return true
    //If the record doesn't name top ten - return false
    public boolean addRecord(Record record){
        //Check if there is draw (id = 0)
        if (record.getPlayerId() == 0){
            return false;
        }
        if (!recordsFull()) {
            records.add(record);
            return true;
        }else {
            sortRecords();
            if (record.compareTo(records.get(0)) > 0){
                records.remove(records.get(0));
                records.add(record);
                return true;
            }
        }
        sortRecords();
        return false;
    }

    public ArrayList<Record> getRecords() {
        //Sorts the record in descending order (the order of the list we show)
        Collections.sort(records, Collections.reverseOrder());
        return records;
    }

    public int getSize(){
        return records.size();
    }
}
