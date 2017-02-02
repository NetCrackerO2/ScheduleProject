package gui;


public abstract class ContentPane {
    public ContentPane() {
        MainForm.getMainForm().setCurrentContentPane(this);
    }

    public abstract void update();
}
