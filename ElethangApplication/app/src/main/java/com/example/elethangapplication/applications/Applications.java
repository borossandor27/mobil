package com.example.elethangapplication.applications;

public class Applications {
    private int id;
    private int program_info_id;

    public Applications(int id, int program_info_id) {
        this.id = id;
        this.program_info_id =program_info_id;
    }

    public int getId() {
        return id;
    }

    public int getProgram_info_id() {
        return program_info_id;
    }


}
