package com.gint.app.bisis.editor.comlin;

import java.util.Vector;
import java.util.StringTokenizer;
import com.objectspace.jgl.HashMap;

import com.gint.app.bisis.editor.*;
import com.gint.app.bisis.editor.edit.*;

public class CommandParser {
  public CommandParser(MainFrame mf) {
    commands = new HashMap();
    DisplayCommand displayCommand = new DisplayCommand(mf);
    commands.add("d", displayCommand);
    commands.add("disp", displayCommand);
    commands.add("display", displayCommand);
    commands.add("f", new FormatCommand(mf.tfComLin));
    commands.add("format", new FormatCommand(mf.tfComLin));
    commands.add("e", new EditCommand(mf));
    commands.add("edit", new EditCommand(mf));
    commands.add("n", new NewCommand(mf));
    commands.add("new", new NewCommand(mf));
    commands.add("q", new QuitCommand(mf));
    commands.add("quit", new QuitCommand(mf));
    commands.add("l", new ListCommand(mf));
    commands.add("list", new ListCommand(mf));
    commands.add("c", new ConnectCommand(mf));
    commands.add("connect", new ConnectCommand(mf));
    NibisCommand nibisCommand = new NibisCommand(mf);
    commands.add("nibis", nibisCommand);
    commands.add("NIBIS", nibisCommand);
    NetSearchCommand net = new NetSearchCommand(mf);
    commands.add("net", net);
    commands.add("netsearch", net);
    StatCommand stat = new StatCommand(mf);
    commands.add("stat", stat);
    MergeCommand merge = new MergeCommand(mf);
    commands.add("merge", merge);
    BranchesCommand branches = new BranchesCommand(mf);
    commands.add("branches", branches);
    ArticleCommand article = new ArticleCommand(mf);
    commands.add("a", article);
    commands.add("article", article);
    
    //Danijela B dodala zbog izvrsnog veca
    commands.add("sredstva",new SredstvaCommand(mf));

    //Tanja dodala
    commands.add("print", new PrintCommand(mf));

  }

  public CommandParser(EditDlg editDlg) {
    commands = new HashMap();
    commands.add("d", new EditDisplayCommand(editDlg));
    commands.add("disp", new EditDisplayCommand(editDlg));
    commands.add("display", new EditDisplayCommand(editDlg));
    commands.add("f", new FormatCommand(editDlg.tfComLin));
    commands.add("format", new FormatCommand(editDlg.tfComLin));
    commands.add("q", new QuitCommand(editDlg));
    commands.add("quit", new QuitCommand(editDlg));
    commands.add("x", new ExitCommand(editDlg));
    commands.add("ex", new ExitCommand(editDlg));
    commands.add("exit", new ExitCommand(editDlg));
    commands.add("s", new SaveCommand(editDlg));
    commands.add("save", new SaveCommand(editDlg));
    commands.add("delete", new DeleteCommand(editDlg));
    commands.add("del", new DeleteCommand(editDlg));
    commands.add("l", new EditListCommand(editDlg));
    commands.add("list", new EditListCommand(editDlg));
  }

  public AbstractCommand parse(String comLin) {
    StringTokenizer st = new StringTokenizer(comLin, " \t-,");
    if (!st.hasMoreTokens())           // nije otkucao nista pametno
      return null;
    String comName = st.nextToken();   // komanda
    Vector params = new Vector();      // parametri
    while (st.hasMoreTokens())
      params.addElement(st.nextToken());
    AbstractCommand command = (AbstractCommand)commands.get(comName);
    if (command != null)
      command.setParams(params);
    return command;
  }

  private HashMap commands;
}
