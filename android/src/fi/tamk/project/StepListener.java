package fi.tamk.project;


// Will listen to step alerts
public interface StepListener {

    public void step(long timeNs);

}
