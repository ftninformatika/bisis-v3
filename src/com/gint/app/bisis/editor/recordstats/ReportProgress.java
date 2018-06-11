package com.gint.app.bisis.editor.recordstats;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.WindowConstants;

import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.lang.time.StopWatch;

import com.gint.util.gui.WindowUtils;
import com.gint.util.string.StringUtils;

/**
 *
 * @author branko
 */
public class ReportProgress extends JDialog {

  public ReportProgress(Frame parent) {
    super(parent, "Generisanje izve\u0161taja u toku", true);
    this.parent = parent;
    
    getContentPane().setLayout(new BorderLayout());
    JPanel pSouth = new JPanel();
    pSouth.setLayout(new FlowLayout());
    getContentPane().add(pSouth, BorderLayout.SOUTH);
    pSouth.add(btnCancel);
    btnCancel.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        handle_cancel();
      }
    });
    
    JPanel pCenter = new JPanel();
    pCenter.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));
    getContentPane().add(pCenter, BorderLayout.CENTER);
    pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
    pCenter.add(lTimeElapsed);
    pCenter.add(lTimeRemaining);
    pCenter.add(pbProgress);
    pbProgress.setStringPainted(true);
    pbProgress.setMinimum(0);
    pbProgress.setMaximum(100);
    pbProgress.setValue(0);
    
    timer = new Timer(1000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        if (finished) {
          cancelled = false;
          setVisible(false);
        } else {
          pbProgress.setMaximum(maximum);
          pbProgress.setValue(value);
          recalc();
        }
      }
    });
    
    pack();
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    WindowUtils.centerOnScreen(this);
  }
  
  public void setVisible(boolean visible) {
    pbProgress.setValue(0);
    value = 0;
    if (visible) {
      clock.start();
      timer.start();
      finished = false;
      cancelled = false;
    } else {
      clock.stop();
      timer.stop();
    }
    super.setVisible(visible);
  }
  
  public void handle_cancel() {
    cancelled = true;
    setVisible(false);
  }
  
  public boolean isCancelled() {
    return cancelled;
  }
  
  public void setFinished(boolean finished) {
    this.finished = finished;
  }
  
  public void setMaximum(int maximum) {
    this.maximum = maximum;
  }
  
  public void setValue(int value) {
    this.value = value;
  }
  
  public void setReport(JasperPrint jp) {
    this.jp = jp;
  }
  
  public JasperPrint getReport() {
    return jp;
  }
  
  public void setProblems(String problems) {
    this.problems = problems;
  }
  
  public String getProblems() {
    return problems;
  }
  
  private void recalc() {
    if (value == 0)
      return;
    long timeElapsed = clock.getTime();
    long totalTime = (long)((float)timeElapsed * ((float)maximum / (float)value));
    long timeRemaining = totalTime - timeElapsed;
    lTimeElapsed.setText("Proteklo vreme:  " + timeToString(timeElapsed));
    lTimeRemaining.setText("Preostalo vreme: " + timeToString(timeRemaining));
  }
  
  private String timeToString(long time) {
    int hours = (int)time / 3600000;
    long remainder = time % 3600000;
    int minutes = (int)remainder / 60000;
    remainder = remainder % 60000;
    int seconds = (int)remainder / 1000;
    return StringUtils.padZeros(hours, 2) + ":" + 
      StringUtils.padZeros(minutes, 2) + ":" + StringUtils.padZeros(seconds, 2);
  }
  
  Frame parent;
  JLabel lTimeElapsed = new JLabel("Proteklo vreme: ");
  JLabel lTimeRemaining = new JLabel("Preostalo vreme: ");
  JProgressBar pbProgress = new JProgressBar();
  JButton btnCancel = new JButton(" Prekini ");
  int maximum;
  int value;
  boolean cancelled = false;
  boolean finished = false;
  StopWatch clock = new StopWatch();
  Timer timer;
  JasperPrint jp;
  String problems = "";
}
