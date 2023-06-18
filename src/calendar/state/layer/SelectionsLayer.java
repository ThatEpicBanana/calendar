package calendar.state.layer;

import calendar.state.State;

public class SelectionsLayer {
    protected int selection = 0;
    private boolean editing = false;

    private int min = 0;
    private int max = -1;

    protected State state;

    public SelectionsLayer(State state) {
        this.state = state;
    }

    public void setBounds(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int selection() { return this.selection; }
    public void setSelection(int i) { this.selection = i; state.updateScreen(); }
    public void moveSelection(int by) { 
        int newSelection = Math.max(this.selection + by, min);
        if(max != -1) 
            newSelection = Math.min(max, newSelection);

        setSelection(newSelection);
    }

    public void nextSelection() { this.moveSelection(1); }
    public void prevSelection() { this.moveSelection(-1); }

    public boolean selected(int i) { return this.selection == i; }

    public boolean editing() { return this.editing; }
    public void startEditing() { this.editing = true; state.updateScreen(); }
    public void endEditing() { this.editing = false; state.updateScreen(); }
}
