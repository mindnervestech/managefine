package com.mnt.core.ui.component;

import java.util.Date;
import java.util.List;

public class ValueWrapper
{
  public Object id;
  public Object o;
  public String display;
  public Enum option;
  public Date dt;
  public List<ValueWrapper> li;

  public Object getId()
  {
    return this.id;
  }

  public void setId(Object id) {
    this.id = id;
  }

  public Object getO() {
    return this.o;
  }

  public void setO(Object o) {
    this.o = o;
  }

  public String getDisplay() {
    return this.display;
  }

  public void setDisplay(String display) {
    this.display = display;
  }

  public Enum getOption() {
    return this.option;
  }

  public void setOption(Enum option) {
    this.option = option;
  }

  public Date getDt() {
    return this.dt;
  }

  public void setDt(Date dt) {
    this.dt = dt;
  }

  public List<ValueWrapper> getLi() {
    return this.li;
  }

  public void setLi(List<ValueWrapper> li) {
    this.li = li;
  }
}